/**
 * 
 */
package edu.cornell.dendro.corina.tridasv2.doc;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

import edu.cornell.dendro.corina.ui.DefaultResourceBundle;

/**
 * @author lucasm
 *
 */
public class Documentation {
	private Documentation() {
		// don't instantiate me!
	}
		
	/**
	 * Retrieves documentation for a hierarchy object, max 3 depth at the end
	 * For instance, if we want documentation for:
	 *    object.a.b.c.d.e.f
	 * We will look for documentation for:
	 *    d.e.f
	 *    e.f
	 *    f
	 * And then give up.
	 * 
	 * @param key
	 * @return The localized documentation, or null if nothing was found
	 */
	public static String getDocumentation(String key) {
		String[] keys = key.split("\\.");
		
		int maxdepth = Math.min(3, keys.length);
		for(int i = maxdepth - 1; i >= 0; i--) {
			String thisKey = StringUtils.join(keys, '.', keys.length - (i+1), keys.length);
			
			try {
				return msg.getString(thisKey);
			} catch (MissingResourceException mre) {
				// continue...
			}
		}
		
		return null;
	}
	
    // the resource bundle to use
    private final static ResourceBundle msg;
    
    static {
		ResourceBundle bundle;
		try {
			bundle = ResourceBundle.getBundle("edu/cornell/dendro/corina/tridasv2/doc/DocsBundle");
		} catch (MissingResourceException mre) {
			mre.printStackTrace();
			bundle = new DefaultResourceBundle();
		}
		msg = bundle;
	}
}
