/**
 * 
 */
package edu.cornell.dendro.corina.wsi.corina;

import javax.xml.bind.annotation.XmlRootElement;

import org.tridas.interfaces.ITridas;
import org.tridas.interfaces.ITridasDerivedSeries;
import org.tridas.schema.ControlledVoc;
import org.tridas.schema.TridasDerivedSeries;
import org.tridas.schema.TridasElement;
import org.tridas.schema.TridasIdentifier;
import org.tridas.schema.TridasMeasurementSeries;
import org.tridas.schema.TridasObject;
import org.tridas.schema.TridasRadius;
import org.tridas.schema.TridasSample;

import edu.cornell.dendro.corina.sample.SampleType;
import edu.cornell.dendro.corina.schema.CorinaRequestType;
import edu.cornell.dendro.corina.schema.EntityType;
import edu.cornell.dendro.corina.schema.WSIEntity;
import edu.cornell.dendro.corina.schema.WSIRequest;

/**
 * @author Lucas Madar
 *
 */
public abstract class CorinaEntityAssociatedResource<T> extends
		CorinaAssociatedResource<T> {
	
	private WSIEntity readOrDeleteEntity;
	private ITridas createOrUpdateEntity;
	private String parentEntityID;

	/**
	 * Constructor for create, update, or delete
	 * 
	 * @param entity a tridas entity to perform an operation on
	 * @param parentEntityID the parent object id, requred only for create, optional for update
	 * @param queryType one of create, update, or delete
	 */
	public CorinaEntityAssociatedResource(ITridas entity, String parentEntityID, 
			CorinaRequestType queryType) {
		super(getXMLName(entity), queryType);
		
		if(entity == null)
			throw new NullPointerException("Entity may not be null");
		
		switch(queryType) {
		case CREATE:
		case UPDATE:
			this.parentEntityID = parentEntityID;
			this.createOrUpdateEntity = entity;
			break;
			
		case DELETE: {
			TridasIdentifier identifier = entity.getIdentifier();

			if(parentEntityID != null)
				throw new IllegalArgumentException("Delete called with parentObjectID!");
			
			if(identifier == null)
				throw new IllegalArgumentException("Delete called with a tridas entity with no identifier!");
			
			readOrDeleteEntity = new WSIEntity();
			readOrDeleteEntity.setId(identifier.getValue());
			// cheap-ish: XmlRootElement (above) is the xml tag, which is also our entity type
			readOrDeleteEntity.setType(EntityType.fromValue(getResourceName()));
			break;
		}
			
		default:
			throw new IllegalArgumentException("Invalid request type: must be one of CREATE, UPDATE or DELETE for this method");
		}
		
		if (queryType == CorinaRequestType.CREATE && parentEntityID == null) {
			if (entity instanceof ITridasDerivedSeries) {
				ITridasDerivedSeries dentity = (ITridasDerivedSeries) entity;
				
				if(!dentity.isSetType())
					throw new IllegalArgumentException("Derived entity has no type specified!");
				
				SampleType stype = SampleType.fromString(dentity.getType().isSetNormal() 
						? dentity.getType().getNormal()
						: dentity.getType().getValue());

				if (stype != SampleType.SUM)
					throw new IllegalArgumentException("CREATE " + stype
							+ " called with ParentObjectID == null!");
			} else
				throw new IllegalArgumentException("CREATE called with ParentObjectID == null!");
		}
	}
	
	/**
	 * Get the name value of the XmlRootElement annotation of this object or its superclasses
	 * @param o
	 * @return a string containing the name attribute
	 */
	private static String getXMLName(Object o) {
		Class<?> clazz = o.getClass();
		
		do {
			XmlRootElement e = clazz.getAnnotation(XmlRootElement.class);
			if(e != null && e.name().length() > 0) {
				return e.name();
			}
		} while((clazz = clazz.getSuperclass()) != null);
		
		throw new IllegalStateException("No XML name for class or superclasses?");
	}

	/**
	 * Constructor for read or delete
	 * 
	 * @param entity a corina WS entity to perform an operation on
	 * @param queryType one of create, update, or delete
	 */
	public CorinaEntityAssociatedResource(WSIEntity entity, CorinaRequestType queryType) {
		super(entity.getClass().getAnnotation(XmlRootElement.class).name(), 
				queryType);

		if(entity == null)
			throw new NullPointerException("Entity may not be null");
		
		switch(queryType) {
		case READ:
		case DELETE:
			this.readOrDeleteEntity = entity;
			break;
					
		default:
			throw new IllegalArgumentException("Invalid request type: must be one of READ or DELETE for this method");
		}		
	}

	/**
	 * Constructor for read or delete
	 * 
	 * @param identifier a tridas identifier
	 * @param entityType the type of entity
	 * @param queryType one of create, update, or delete
	 */
	public CorinaEntityAssociatedResource(TridasIdentifier identifier, 
			EntityType entityType, CorinaRequestType queryType) {
		super(entityType.value(), queryType);
				
		if(identifier == null)
			throw new NullPointerException("Identifier may not be null");
		
		switch(queryType) {
		case READ:
		case DELETE: {
			WSIEntity entity = new WSIEntity();

			entity.setType(entityType);
			entity.setId(identifier.getValue());

			this.readOrDeleteEntity = entity;
			break;
		}
					
		default:
			throw new IllegalArgumentException("Invalid request type: must be one of READ or DELETE for this method");
		}		
	}

	
	private void populateAppropriateList(WSIRequest request) {
		if(createOrUpdateEntity instanceof TridasObject)
			request.getObjects().add((TridasObject) createOrUpdateEntity);
		else if(createOrUpdateEntity instanceof TridasElement)
			request.getElements().add((TridasElement) createOrUpdateEntity);
		else if(createOrUpdateEntity instanceof TridasSample)
			request.getSamples().add((TridasSample) createOrUpdateEntity);
		else if(createOrUpdateEntity instanceof TridasRadius)
			request.getRadiuses().add((TridasRadius) createOrUpdateEntity);
		else if(createOrUpdateEntity instanceof TridasMeasurementSeries)
			request.getMeasurementSeries().add((TridasMeasurementSeries) createOrUpdateEntity);
		else if(createOrUpdateEntity instanceof TridasDerivedSeries)
			request.getDerivedSeries().add((TridasDerivedSeries) createOrUpdateEntity);
		else 
			throw new IllegalArgumentException("Unknown/invalid entity type for update/create: " + 
					createOrUpdateEntity.toString());
	}
	
	@Override
	protected final void populateRequest(WSIRequest request) {
		switch(this.getQueryType()) {
		case READ:
		case DELETE:
			request.getEntities().add(readOrDeleteEntity);
			break;
			
		case UPDATE:
		case CREATE:
			request.setParentEntityID(this.parentEntityID);
			populateAppropriateList(request);
			break;
		}
	}
}
