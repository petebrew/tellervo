/**
 * Created at Jul 24, 2010, 3:48:12 PM
 */
package edu.cornell.dendro.corina.command.bulkImport;

import com.dmurph.mvc.IllegalThreadException;
import com.dmurph.mvc.IncorrectThreadException;
import com.dmurph.mvc.MVC;
import com.dmurph.mvc.MVCEvent;
import com.dmurph.mvc.control.ICommand;

import edu.cornell.dendro.corina.control.bulkImport.DisplayColumnChooserEvent;
import edu.cornell.dendro.corina.model.bulkImport.BulkImportModel;
import edu.cornell.dendro.corina.model.bulkImport.ColumnChooserModel;
import edu.cornell.dendro.corina.model.bulkImport.IBulkImportSectionModel;
import edu.cornell.dendro.corina.model.bulkImport.ObjectModel;
import edu.cornell.dendro.corina.model.bulkImport.SingleObjectModel;
import edu.cornell.dendro.corina.view.bulkImport.ColumnChooserView;

/**
 * @author daniel
 *
 */
public class ShowColumnWindowCommand implements ICommand {
	
	/**
	 * @see com.dmurph.mvc.control.ICommand#execute(com.dmurph.mvc.MVCEvent)
	 */
	@Override
	public void execute(MVCEvent argEvent) {
		DisplayColumnChooserEvent event = (DisplayColumnChooserEvent) argEvent;
		
		BulkImportModel biModel = BulkImportModel.getInstance();
		
		if(biModel.getCurrColumnChooser() != null){
			return;
		}
	
		ColumnChooserModel model = (ColumnChooserModel) event.model.getProperty(IBulkImportSectionModel.COLUMN_MODEL);
		ColumnChooserView view = new ColumnChooserView(model, biModel.getMainView());
		biModel.setCurrColumnChooser(view);
		
		try {
			MVC.splitOff();
		}catch (IllegalThreadException e) { e.printStackTrace();}
		catch (IncorrectThreadException e) { e.printStackTrace();}
		
		view.pack();
		view.setVisible(true);
	}
	
}
