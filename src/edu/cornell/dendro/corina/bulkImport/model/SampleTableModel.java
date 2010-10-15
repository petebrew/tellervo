/**
ss * Created at Aug 23, 2010, 3:35:03 AM
 */
package edu.cornell.dendro.corina.bulkImport.model;

import java.math.BigDecimal;
import java.util.List;

import org.tridas.schema.Date;
import org.tridas.schema.TridasElement;
import org.tridas.schema.TridasObject;

import edu.cornell.dendro.corina.schema.CorinaRequestFormat;
import edu.cornell.dendro.corina.schema.SearchOperator;
import edu.cornell.dendro.corina.schema.SearchParameterName;
import edu.cornell.dendro.corina.schema.SearchReturnObject;
import edu.cornell.dendro.corina.schema.WSIBoxDictionary;
import edu.cornell.dendro.corina.schema.WSISampleTypeDictionary;
import edu.cornell.dendro.corina.wsi.corina.CorinaResourceAccessDialog;
import edu.cornell.dendro.corina.wsi.corina.CorinaResourceProperties;
import edu.cornell.dendro.corina.wsi.corina.SearchParameters;
import edu.cornell.dendro.corina.wsi.corina.resources.EntitySearchResource;

/**
 * @author Daniel
 *
 */
public class SampleTableModel extends AbstractBulkImportTableModel {
	private static final long serialVersionUID = 2L;
	
	public SampleTableModel(SampleModel argModel){
		super(argModel);
	}
	
	/**
	 * @see edu.cornell.dendro.corina.bulkImport.model.AbstractBulkImportTableModel#getColumnClass(java.lang.String)
	 */
	public Class<?> getColumnClass(String argColumn){
		if(argColumn.equals(SingleSampleModel.TYPE)){
			return WSISampleTypeDictionary.class;
		}else if(argColumn.equals(SingleSampleModel.IMPORTED)){
			return Boolean.class;
		}else if(argColumn.equals(SingleSampleModel.SAMPLING_DATE)){
			return Date.class;
		}else if(argColumn.equals(SingleSampleModel.KNOTS)){
			return Boolean.class;
		}else if(argColumn.equals(SingleRadiusModel.AZIMUTH)){
			return BigDecimal.class;
		}else if(argColumn.equals(SingleSampleModel.ELEMENT)){
			return TridasElement.class;
		}else if(argColumn.equals(SingleSampleModel.BOX)){
			return WSIBoxDictionary.class;
		}else if(argColumn.equals(SingleSampleModel.OBJECT)){
			return TridasObject.class;
		}
		return null;
	}
	
	/**
	 * @see edu.cornell.dendro.corina.bulkImport.model.AbstractBulkImportTableModel#setValueAt(java.lang.Object, java.lang.String, edu.cornell.dendro.corina.bulkImport.model.IBulkImportSingleRowModel, int)
	 */
	@Override
	public void setValueAt(Object argAValue, String argColumn, IBulkImportSingleRowModel argModel, int argRowIndex) {
		argModel.setProperty(argColumn, argAValue);
		
		// FIXME this all should be in a command!!!
		if(argColumn.equals(SingleSampleModel.OBJECT)){
			if(argAValue != null){
				final TridasObject o = (TridasObject) argAValue;
				final SingleSampleModel ssm = (SingleSampleModel) argModel;
				ssm.getPossibleElements().clear();
				
				Thread t = new Thread(new Runnable() {
					
					@Override
					public void run() {
						SearchParameters param = new SearchParameters(SearchReturnObject.ELEMENT);
				    	param.addSearchConstraint(SearchParameterName.ANYPARENTOBJECTID, SearchOperator.EQUALS, o.getIdentifier().getValue().toString());

				    	// we want an object return here, so we get a list of object->elements->samples when we use comprehensive
						EntitySearchResource<TridasElement> resource = new EntitySearchResource<TridasElement>(param, TridasElement.class);
						resource.setProperty(CorinaResourceProperties.ENTITY_REQUEST_FORMAT, CorinaRequestFormat.MINIMAL);
						
						CorinaResourceAccessDialog dialog = new CorinaResourceAccessDialog(resource);
						resource.query();	
						dialog.setVisible(true);
						
						if(!dialog.isSuccessful()) 
						{ 
							System.out.println("oopsey doopsey.  Error getting elements");
							return;
						}
						
						List<TridasElement> elList = resource.getAssociatedResult();
						ssm.getPossibleElements().addAll(elList);
					}
				}, "Elements Fetch Thread");
				
				t.start();
			}
		}
	}
}