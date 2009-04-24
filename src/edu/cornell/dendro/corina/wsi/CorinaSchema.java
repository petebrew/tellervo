package edu.cornell.dendro.corina.wsi;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.xml.sax.SAXException;

public class CorinaSchema {
	private CorinaSchema() {
		// not instantiable
	}

	/**
	 * Returns a schema object representing all valid corina schemas
	 * @return
	 */
	public static Schema getCorinaSchema() {
		// be sure we load the schema in a threadsafe manner
		synchronized(schemaLoaded) {
			if(!schemaLoaded) {
				corinaSchema = loadCorinaSchema();
				schemaLoaded = true;
			}
		}
		
		return corinaSchema;
	}
	
	/** Is the schema loaded? */
	private static Boolean schemaLoaded = false;
	/** Our schema object */
	private static Schema corinaSchema;
	
	/**
	 * Loads the actual schema
	 * @return
	 */
	private static Schema loadCorinaSchema() {
		SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try {
			return factory.newSchema(getSchemaSources());
		} catch (SAXException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Get an array of schema sources
	 * @return
	 */
	private static Source[] getSchemaSources() {
		ArrayList<StreamSource> schemas = new ArrayList<StreamSource>();
		Source[] ret;
		
		for(String[] source : VALIDATE_SCHEMAS) {
			InputStream is = findSchema(source[1]);
			
			// couldn't load that schema :(
			if(is == null)
				continue;
			
			schemas.add(new StreamSource(is));
		}
		
		ret = new Source[schemas.size()];
		ret = schemas.toArray(ret);
		
		return ret;
	}
	
	/**
	 * Returns an InputStream pointing to this schema file
	 * @param filename
	 * @return
	 */
    private static InputStream findSchema(String filename) {
    	InputStream ret = CorinaSchema.class.getClassLoader().getResourceAsStream
    		("edu/cornell/dendro/webservice/schemas/" + filename);
    	
    	if(ret == null)
    		System.out.println("FAILED to load local schema: " + filename);
    	
    	return ret;
    }

    /**
     * A list of schema namespaces and their associated namespace files
     */
    
    private final static String VALIDATE_SCHEMAS[][] = {
    	// Order is important!
    	{ "http://www.w3.org/1999/xlink", "xlinks.xsd" },
    	{ "http://www.opengis.net/gml", "gmlsf.xsd" },
    	{ "http://www.tridas.org/1.1", "tridas.xsd" },
    	{ "http://dendro.cornell.edu/schema/corina/1.0", "corina.xsd" },
    };}
