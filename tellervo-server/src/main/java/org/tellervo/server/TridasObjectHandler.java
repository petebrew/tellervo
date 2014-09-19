package org.tellervo.server;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tellervo.schema.TellervoRequestFormat;
import org.tellervo.schema.TellervoRequestStatus;
import org.tridas.schema.TridasElement;
import org.tridas.util.TridasObjectEx;

/**
 * Class for handler requests to Create, Read, Update and Delete TRiDaS Objects
 * 
 * @author pbrewer
 *
 */
public class TridasObjectHandler {

	private static final Logger log = LoggerFactory.getLogger(TridasObjectHandler.class);
	RequestHandler handler;
	
	/**
	 * Standard constructor 
	 * 
	 * @param handler
	 */
	public TridasObjectHandler(RequestHandler handler)
	{
		this.handler = handler;
	}
	
	/**
	 * Read a TRiDaS Object from the database based on the specified ID.  The ID is passed as a string but
	 * needs to be a valid UUID to match the Tellervo database
	 * 
	 * @param id
	 */
	public void readTridasObject(String id)
	{
		if(id==null){
			handler.addMessage(TellervoRequestStatus.ERROR, 902,
					"An identifier is required when reading an entity");
			handler.sendResponse();
			return;
		}
				
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			con = Main.getDatabaseConnection();
			con.setAutoCommit(false);
			String sql = "SELECT * FROM vwtblobject WHERE objectid= ?::uuid ";
			
			st = con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			st.setString(1, id);
			rs = st.executeQuery();
			
			int rowCount =0;
			if(rs.last())
			{
				rowCount = rs.getRow();
				rs.beforeFirst();
				while (rs.next()) {
					
					if(handler.getRequest().getFormat().equals(TellervoRequestFormat.STANDARD) || 
					   handler.getRequest().getFormat().equals(TellervoRequestFormat.MINIMAL) || 
					   handler.getRequest().getFormat().equals(TellervoRequestFormat.SUMMARY))
					{
						// Standard, Mimimal and Summary formats all return a simple single object
						handler.getContent().getSqlsAndObjectsAndElements().add(SQLMarshaller.getTridasObjectFromResultSet(rs, handler));
					}
					else if (handler.getRequest().getFormat().equals(TellervoRequestFormat.COMPREHENSIVE))
					{
						// Comprehensive format returns object with child entities
						TridasObjectEx object = SQLMarshaller.getTridasObjectFromResultSet(rs, handler);
						TridasElementHandler teh = new TridasElementHandler(handler);
						ArrayList<TridasElement> elementList = teh.readTridasElements("objectid='"+object.getIdentifier().getValue()+"'::uuid");
						object.getElements().addAll(elementList);
						
						handler.getContent().getSqlsAndObjectsAndElements().add(object);
					
					}
				}
				
				return;
			}
			
			if(rowCount==0)
			{
				handler.addMessage(TellervoRequestStatus.ERROR, 903,
						"There are no matches for the specified identifier in the database");
				handler.sendResponse();
				return;
			}

		} catch (SQLException ex) {
			log.error(ex.getMessage());
			handler.addMessage(TellervoRequestStatus.ERROR, 701,
					ex.getMessage());

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				log.error(ex.getMessage());
				handler.addMessage(TellervoRequestStatus.ERROR, 701,
						"Error connecting to database");
			}
		}
		
	}

	
}
