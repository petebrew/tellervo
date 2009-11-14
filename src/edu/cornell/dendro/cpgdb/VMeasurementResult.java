package edu.cornell.dendro.cpgdb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VMeasurementResult {
	// Internal values
	private enum VMeasurementOperation { DIRECT, INDEX, CLEAN, REDATE, SUM, CROSSDATE, TRUNCATE }
		
	// This string holds our result, which is a UUID returned by the DB
	private UUID result;
	
	// we keep this instantiated for easy access to the db
	protected DBQuery dbq;
	
	// owner user ID (for later extension...)
	private int ownerUserID = 1234;
	
	private final Logger logger;

	/**
	 * 
	 * @param VMeasurementID
	 * @param safe true if we should attempt to rollback on error, false otherwise
	 * @throws SQLException
	 */
	public VMeasurementResult(UUID VMeasurementID, boolean safe) throws SQLException {
		this(VMeasurementID, safe, true);
	}

	/**
	 * 
	 * @param VMeasurementID
	 * @param safe true if we should attempt to rollback on error, false otherwise
	 * @param cleanup true if we should close all our queries immediately, false otherwise
	 * @throws SQLException
	 */
	public VMeasurementResult(UUID VMeasurementID, boolean safe, boolean cleanup) throws SQLException {
		logger = Logger.getAnonymousLogger();
		dbq = new DBQuery();
		
		try {
			acquireVMeasurementResult(VMeasurementID, safe);
		} finally {
			if(cleanup)
				// we created this dbquery object, it's our responsibility to clean it up.
				dbq.cleanup();
		}
	}
	
	/**
	 * 
	 * @param VMeasurementID
	 * @param safe
	 * @param dbQuery
	 * @throws SQLException
	 */
	public VMeasurementResult(UUID VMeasurementID, boolean safe, DBQuery dbQuery) throws SQLException {
		this.dbq = dbQuery;		
		logger = Logger.getAnonymousLogger();
		acquireVMeasurementResult(VMeasurementID, safe);
	}

	/**
	 * Starts the recursive process that gets the VMeasurementResult UUID
	 * 
	 * Why a routine for 'safe' cleanup? Well, cleanup works under the native pl/java driver,
	 * but doesn't work under the postgresql jdbc driver. Thus making testing a nightmare!
	 * 
	 * @param VMeasurementID
	 * @param safe true if we should attempt to rollback on failure
	 * @throws SQLException
	 */
	private void acquireVMeasurementResult(UUID VMeasurementID, boolean safe) throws SQLException {
		if(safe) {
			// If we have an error, clean up any mess we made, but pass along the exception.
			Savepoint beforeCreation = dbq.getConnection().setSavepoint();
			
			try {
				result = recursiveGetVMeasurementResult(VMeasurementID, null, null, 0);
			} catch (SQLException sql) {
				dbq.getConnection().rollback(beforeCreation);
				throw sql;
			} finally {
				dbq.getConnection().releaseSavepoint(beforeCreation);
			}					
		}
		else
			result = recursiveGetVMeasurementResult(VMeasurementID, null, null, 0);
	}
	
	/**
	 * @param VMeasurementID The ID of the VMeasurement in the database we are performing an operation on
	 * @param VMeasurementResultGroupID A UUID which groups together VMeasurementResults in this level of summing (for sums; starts as NULL)
	 * @param VMeasurementResultMasterID A UUID which groups together all VMeasurementResults created by this function (starts as NULL)
	 * @param recursionDepth our recursion depth
	 * @return A string indicating the UUID of the VMeasurementResult associated with the provided VMeasurementID
	 * @throws SQLException
	 */
	private UUID recursiveGetVMeasurementResult(UUID VMeasurementID, UUID VMeasurementResultGroupID,	
			UUID VMeasurementResultMasterID, int recursionDepth) throws SQLException {

		if(logger.isLoggable(Level.FINE)) {
			String params = new ParamStringBuilder()
				.append("VMeasurementID", VMeasurementID)
				.append("VMeasurementResultGroupID", VMeasurementResultGroupID)
				.append("VMeasurementResultMasterID", VMeasurementResultMasterID)
				.append("recursionDepth", recursionDepth)
				.toString();
			
			logger.fine("recursiveGETVMR() {" + params + "}");
		}
		
		ResultSet res;
		VMeasurementOperation op;
		Integer VMeasurementOpParameter = null;
				
		// break out of the whole mess if we have an infinite loop
		if(recursionDepth > 50)
			throw new SQLException("VMeasurementResult: Infinite recursion detected!");
		
		if(VMeasurementResultMasterID == null)
			VMeasurementResultMasterID = UUID.randomUUID();
		
		// Figure out what kind of VMeasurement we have to deal with
		res = dbq.query("qryVMeasurementType", VMeasurementID);
		if(res.next()) {
			op = getOp(res.getString("Op"));
			int MeasurementID = res.getInt("MeasurementID");
			
			int VMeasurementsInGroup = res.getInt("VMeasurementsInGroup");

			// If VMeasurementOpParameter is NULL, getInt turns NULL to 0
			VMeasurementOpParameter = (Integer) res.getObject("VMeasurementOpParameter");
			
			if(logger.isLoggable(Level.FINE)) {
				String params = new ParamStringBuilder()
					.append("op", op)
					.append("MeasurementID", MeasurementID)
					.append("VMeasurementsInGroup", VMeasurementsInGroup)
					.append("VMeasurementOpParameter", VMeasurementOpParameter)
					.toString();
				
				logger.fine("qryVMeasurementType values {" + params + "}");
			}
			
			if(op == VMeasurementOperation.DIRECT && VMeasurementsInGroup == 0 && MeasurementID != 0) {
				// Ah, the clean base case. Just drop out nicely, after we clean up.
				res.close();
				
				return doDirectCase(VMeasurementID, VMeasurementResultGroupID, VMeasurementResultMasterID, MeasurementID);
			}
			// These are now just sanity checks. If our base case failed, and it doesn't match any of these...
			else if(!(
					(op == VMeasurementOperation.INDEX && VMeasurementsInGroup == 1) ||
					(op == VMeasurementOperation.CLEAN && VMeasurementsInGroup == 1) ||
					(op == VMeasurementOperation.REDATE && VMeasurementsInGroup == 1) ||
					(op == VMeasurementOperation.CROSSDATE && VMeasurementsInGroup == 1) ||
					(op == VMeasurementOperation.TRUNCATE && VMeasurementsInGroup == 1) ||
					(op == VMeasurementOperation.SUM && VMeasurementsInGroup > 1)
				   )) 
				   throw new SQLException("Malformed VMeasurement (id:" + VMeasurementID + ")");
		}
		else
			throw new SQLException("VMeasurementResult: VMeasurementID " + VMeasurementID + " not found.");
		
		// dispose of the result set.
		res.close();
		
		/*
		 * The recursive case.
		 */

		UUID newVMeasurementResultID = null;
		UUID newVMeasurementResultGroupID = null;
		UUID lastWorkingVMeasurementResultID = null;
		/*
		 * The lastWorkingVMeasurementResultID (CurrentVMeasurementResultID in
		 * Kit's code) is the last VMeasurementResult returned by our recursive
		 * function. It's only meaningful for things that have less than one
		 * member (which, at this point, is everything that's not a sum)
		 */


		switch (op) {
		case SUM:
			/*
			 * For a sum, all the VMeasurementResults generated in this instance
			 * and all direct children will share the same Group ID. Once we get
			 * back here after the recursion, we sum up everything with this
			 * group ID.
			 */
			if (VMeasurementResultGroupID == null)
				newVMeasurementResultGroupID = UUID.randomUUID();
			else
				newVMeasurementResultGroupID = VMeasurementResultGroupID;

			break;

		case CLEAN:
		case REDATE:
		case CROSSDATE:
		case TRUNCATE:
		case INDEX:
			/*
			 * For everything else, we make a new Group ID. If we're a redated
			 * sample underneath a sum, this is so we don't include the original
			 * sample in the sum as well.
			 */
			newVMeasurementResultGroupID = UUID.randomUUID();

			break;

		default:
			// this should be unreachable.
			break;
		}

		// Get the members of each VMeasurement
		res = dbq.query("qryVMeasurementMembers", VMeasurementID);

		while (res.next()) {
			lastWorkingVMeasurementResultID = recursiveGetVMeasurementResult(
					UUID.fromString(res.getString("MemberVMeasurementID")),
					newVMeasurementResultGroupID, VMeasurementResultMasterID,
					recursionDepth + 1);
		}
		res.close();
		
		/*
		 * Now, we have to perform whatever evil operation we were intending to do.
		 */
		
		switch (op) {
		case INDEX: 
		{			
			// First, create a new VMeasurementResult and move our metadata over to it
			newVMeasurementResultID = UUID.randomUUID();
			dbq.execute("qappVMeasurementResultOpIndex",
					newVMeasurementResultID, VMeasurementID,
					VMeasurementResultMasterID,
					ownerUserID,
					lastWorkingVMeasurementResultID);

			// Now, acquire our working dataset and pass it to the indexer.
			res = dbq.query("qacqVMeasurementReadingResult", lastWorkingVMeasurementResultID);
			Indexer idx = new Indexer(res, VMeasurementOpParameter);
			res.close();


			// prepare a statement for inserting our rows	
			PreparedStatement bulkInsert = dbq.getConnection().prepareStatement(
				"INSERT into tblVMeasurementReadingResult (VMeasurementResultID,RelYear,Reading) VALUES (?,?,?)");

			// prepare them in batch, and submit them all.
			idx.batchAddStatements(bulkInsert, newVMeasurementResultID);
			bulkInsert.executeBatch();
			bulkInsert.close();
		}
		break;
			
		case SUM: 
			// create a new VMeasurement, move the metadata
			newVMeasurementResultID = UUID.randomUUID();
			
			// create our vmeasurementresult...
			dbq.execute("qappVMeasurementResultOpSum",
					newVMeasurementResultID, VMeasurementID,
					VMeasurementResultMasterID,
					ownerUserID,
					newVMeasurementResultGroupID);
			
			// create our vmeasurementreadingresult...
			res = dbq.query("qappVMeasurementResultReadingOpSum",
					  newVMeasurementResultGroupID, newVMeasurementResultID);

			// trick the SQL server into executing this last query. Why? don't ask me.
			res.next();
			res.close();

			break;
			
		case CLEAN:
			/*
			 * In the clean case, no changes are made to the measurement results themselves.
			 * All we are doing is "cutting off" any information below us.
			 * The SQL query changes the VMeasurement ID
			 */
			newVMeasurementResultID = lastWorkingVMeasurementResultID;
			dbq.execute("qupdVMeasurementResultOpClean", VMeasurementID, lastWorkingVMeasurementResultID);
			break;
			
		case REDATE:
			/* 
			 * "As we are updating a record, not appending a new one, use the current ID as the new one."
			 */
			newVMeasurementResultID = lastWorkingVMeasurementResultID;
			dbq.execute("qupdVMeasurementResultOpRedate", VMeasurementID, lastWorkingVMeasurementResultID);
			break;

		case CROSSDATE:
			/* 
			 * "As we are updating a record, not appending a new one, use the current ID as the new one."
			 */
			
			newVMeasurementResultID = lastWorkingVMeasurementResultID;
			dbq.execute("qupdVMeasurementResultOpCrossdate", VMeasurementID, lastWorkingVMeasurementResultID);
			break;
			
		case TRUNCATE:
			newVMeasurementResultID = lastWorkingVMeasurementResultID;
			dbq.execute("qupdVMeasurementResultOpTruncate", VMeasurementID, lastWorkingVMeasurementResultID);
			break;
		}
		
		/*
		 * Now, migrate over or aggregate any reading notes
		 * Then apply our own vmeasurement-derived notes
		 */
		switch(op) {
		case INDEX:
			// steal lastWorkingVMeasurementResultID's notes
			res = dbq.query("applyDerivedReadingNotes", VMeasurementID, newVMeasurementResultID, 
					lastWorkingVMeasurementResultID, null);
			break;
		
		case SUM:
			// aggregate all notes from newVMeasurementResultGroupID
			res = dbq.query("applyDerivedReadingNotes", VMeasurementID, newVMeasurementResultID, 
					null, newVMeasurementResultGroupID);
			break;
		
		case CLEAN:
		case REDATE:
		case CROSSDATE:
		case TRUNCATE:
			// just mark the notes as inherited
			res = dbq.query("applyDerivedReadingNotes", VMeasurementID, newVMeasurementResultID, null, null);
			break;
		
		default:
			throw new SQLException("Reading notes not handled by type " + op);
		}
		
		// force above query to execute and clean up
		res.next();
		res.close();
		
		/*
		 * Now, we clean the data for explicit cases (CLEAN)
		 * and implicit cases (CROSSDATE, REDATE and INDEX).
		 * 
		 * TODO: Someone document what this means, it makes my head hurt.
		 */
		
		switch(op) {
		case INDEX:
		case REDATE:
		case CROSSDATE:
		case TRUNCATE:
		case CLEAN:
			// Clear away the group ID and change it to our parent?
			dbq.execute("qupdVMeasurementResultClearGroupID", newVMeasurementResultGroupID);
			dbq.execute("qupdVMeasurementResultAttachGroupID", VMeasurementResultGroupID, lastWorkingVMeasurementResultID);
			break;
		}
		
		if(recursionDepth == 0) {
			// TODO: Warn about duplicate direct VMeasurements!
			
			// migrate cosmetic data over for certain types of VMeasurements
			switch(op) {
			case SUM:
			case REDATE:
			case CLEAN:
			case CROSSDATE:
				res = dbq.query("qupdVMeasurementResultInfo", newVMeasurementResultID);	
				// that trick to get the SQL server to actually execute...
				res.next();
				res.close();
				break;
				
			default:
				break;
			}

			// remove all our child results...
			dbq.execute("qdelVMeasurementResultRemoveMasterID", VMeasurementResultMasterID, newVMeasurementResultID);
		}
		
		if(logger.isLoggable(Level.FINE))
			logger.finer("recursiveGetVMID returning ["+ newVMeasurementResultID + "]");
		
		return newVMeasurementResultID;
	}

	private VMeasurementOperation getOp(String strOp) throws SQLException {
		try {
			return VMeasurementOperation.valueOf(strOp.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new SQLException("Invalid VMeasurement Operation: " + strOp);
		}		
	}

	/*
	 * The base case is easy!
	 * 
	 * VmeasurementID identifies a VMeasurement
	 * Copy measurement into tblVMeasurementResult and tblVMeasurementReadingResult
	 * Place the result (newVMeasurementResultID) in the result class variable.
	 */
	private UUID doDirectCase(UUID VMeasurementID, UUID VMeasurementResultGroupID, 
			UUID VMeasurementResultMasterID, int MeasurementID) throws SQLException {
		
		UUID newVMeasurementResultID = UUID.randomUUID();
		
		// Create a new VMeasurementResult
		dbq.execute("qappVMeasurementResult", 
				newVMeasurementResultID, VMeasurementID, VMeasurementResultGroupID, 
				VMeasurementResultMasterID, ownerUserID, MeasurementID);
		
		// Create new VMeasurementReadingResults...
		dbq.execute("qappVMeasurementReadingResult", newVMeasurementResultID, MeasurementID);
		
		// Copy over any ReadingNotes
		dbq.execute("qappVMeasurementReadingNoteResult", newVMeasurementResultID);
		
		if(logger.isLoggable(Level.FINE))
			logger.finer("doDirectCase returning ["+ newVMeasurementResultID + "]");
		return newVMeasurementResultID;		
	}

	public UUID getResult() { return result; }
}
