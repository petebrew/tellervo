package edu.cornell.dendro.corina.wsi.corina;

import edu.cornell.dendro.corina.schema.SearchOperator;
import edu.cornell.dendro.corina.schema.SearchParameterName;
import edu.cornell.dendro.corina.schema.SearchReturnObject;
import edu.cornell.dendro.corina.schema.WSIParam;
import edu.cornell.dendro.corina.schema.WSISearchParams;

public class SearchParameters extends WSISearchParams {	
	private static final long serialVersionUID = 1L;
	
	private String asText;
	private boolean hasConstraints;
	private boolean hasAllConstraint;
	
	public SearchParameters(SearchReturnObject returnObjectType) {
		setReturnObject(returnObjectType);
		asText = returnObjectType + ": ";
		
		hasConstraints = hasAllConstraint = false;
	}
		
	/**
	 * Set to true to return all objects of type 'returnObject'
	 * Useful to search for all sites, for instance
	 * @param all
	 */
	public void addSearchForAll() {
		if(hasConstraints || hasAllConstraint)
			throw new IllegalArgumentException("Cannot have both search constraints and all constraint/multiple all constraints");
		this.setAll(new All());
		asText += "[all]";
		hasAllConstraint = true;
	}
	
	public void addSearchConstraint(SearchParameterName name, SearchOperator comparison, String value) {
		if(hasAllConstraint)
			throw new IllegalArgumentException("Cannot have all constraint AND search constraints");

		WSIParam param = new WSIParam();
		
		param.setName(name);
		param.setOperator(comparison);
		param.setValue(value);
		
		// add this parameter
		getParams().add(param);
	
		
		if(hasConstraints)
			asText += ",";
		asText += name.value() + comparison.toString() + value;

		hasConstraints = true;
	}
		
	public String toString() {
		return asText;
	}
}
