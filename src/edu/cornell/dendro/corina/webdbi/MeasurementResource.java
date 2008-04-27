/**
 * 
 */
package edu.cornell.dendro.corina.webdbi;

import org.jdom.*;

import java.io.IOException;
import java.util.*;

import edu.cornell.dendro.corina.formats.CorinaXML;
import edu.cornell.dendro.corina.sample.Sample;

/**
 * @author lucasm
 *
 * The web interface for loading Samples... woot
 */

public class MeasurementResource extends ResourceObject<Sample> {
	/**
	 * Constructor for getting information from the server
	 */
	public MeasurementResource() {
		super("measurements", ResourceQueryType.READ);
	}

	/**
	 * Constructor for putting information on the server
	 * @param queryType
	 */
	public MeasurementResource(ResourceQueryType queryType) {
		super("measurements", queryType);
	}

	/**
	 * Prepare the query document, which is in the format below.
	 * The element passed is "request." 
	 * This is returned so the method can be chained.
	 * 
	 * <corina>
	 *    <request type="action">
	 *    </request>
	 * </corina>
	 * 
	 * @param requestElement 
	 */
	@Override
	protected Element prepareQuery(ResourceQueryType queryType, Element requestElement) throws ResourceException {
		Sample s = this.getObject();
		String id;
		
		if(s == null)
			throw new ResourceException("No object tied to Resource!");
		
		switch(queryType) {
		/*
		 * Generates:
		 * <corina>
		 *    <request type="[read|delete]" id="xxx">
		 *    </request>
		 * </corina>
		 */
		case READ:
		case DELETE:
			id = (String) s.getMeta("id");
			if(id == null)
				throw new ResourceException("Reading in a null id?");
			
			Element measurementElement = new Element("measurement");
			measurementElement.setAttribute("id", id);
			requestElement.addContent(measurementElement);
			
			break;

		/*
		 * Generates:
		 * <corina>
		 *    <request type="update" id="xxx">
		 *    </request>
		 * </corina>
		 */
		case UPDATE: // Update is CREATE with an ID
			id = (String) s.getMeta("id");
			if(id == null)
				throw new ResourceException("Reading in a null id?");
			
			requestElement.setAttribute("id", id);
			requestElement.setAttribute("limit", "5");
			// drop through!
		case CREATE: {
			String title = (String) s.getMeta("title");
			
			if(title != null) {
				Element titleElem = new Element("title");
				
				titleElem.setText(title);
				requestElement.addContent(titleElem);
			}
			break;
		}
			
		}
		
		// important :)
		return requestElement;
	}
	
	/**
	 * In this function, parse the document and populate any internal variables.
	 * 
	 * WARNING: This function *must* be threadsafe. 
	 * This means it must not change any class variables without synchronizing against them!
	 * 
	 * Any UI responses should use a ResourceListener. NO DIALOGS HERE. :)
	 * 
	 * If this function returns false, queryFailed is not called
	 * If it throws an exception, queryFailed IS called
	 * 
	 * @param doc The XML JDOM document obtained by the query function
	 * @return true on success, false on failure
	 * @throws ResourceException if error parsing
	 */
	@Override
	protected boolean processQueryResult(Document doc) throws ResourceException {
		// Extract root and specimen elements from returned XML file
		Element root = doc.getRootElement();
		
		switch(getQueryType()) {
		case READ:
			return loadSample(root);
			
		default:
			throw new ResourceException("I don't handle type " + getQueryType() + " yet :(");
		}
	}
	
	// do the actual loading of the sample. hmm...
	private boolean loadSample(Element root) throws ResourceException {
		// Create new sample to hold data
		Sample s = new Sample();
		
		Element content = root.getChild("content");
		if(content == null)
			throw new MalformedDocumentException("No content element in measurement");
		
		Element measurementElement = content.getChild("measurement");
		if(measurementElement == null)
			throw new MalformedDocumentException("No measurement element in measurement");
		
		CorinaXML loader = new CorinaXML();
		
		try {
			loader.loadMeasurement(s, measurementElement);
		} catch (IOException ioe) {
			throw new MalformedDocumentException("Poorly formed measurement: " + ioe);
		}
		
		// Synchronise object
		setObject(s);
		return true;
	}

	/**
	 * Note this is called in the context of the parsing thread
	 * NO dialogs or anything here, just internal stuff. UI responses should
	 * use a ResourceListener
	 */
	@Override
	protected void queryFailed(Exception e) {
		super.queryFailed(e);
	}
}