/*******************************************************************************
 * Copyright (C) 2010 Daniel Murphy and Peter Brewer
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Contributors:
 *     Daniel Murphy
 *     Peter Brewer
 ******************************************************************************/
package org.tellervo.desktop.bulkdataentry.control;

import org.tellervo.desktop.bulkdataentry.command.AddRowCommand;
import org.tellervo.desktop.bulkdataentry.command.CopyRowCommand;
import org.tellervo.desktop.bulkdataentry.command.CopySelectedRowsCommand;
import org.tellervo.desktop.bulkdataentry.command.DeleteRowCommand;
import org.tellervo.desktop.bulkdataentry.command.GPXBrowseCommand;
import org.tellervo.desktop.bulkdataentry.command.HideColumnWindowCommand;
import org.tellervo.desktop.bulkdataentry.command.ImportSelectedElementsCommand;
import org.tellervo.desktop.bulkdataentry.command.ImportSelectedObjectsCommand;
import org.tellervo.desktop.bulkdataentry.command.ImportSelectedSamplesCommand;
import org.tellervo.desktop.bulkdataentry.command.PrintBarcodesCommand;
import org.tellervo.desktop.bulkdataentry.command.RemoveSelectedCommand;
import org.tellervo.desktop.bulkdataentry.command.ShowColumnWindowCommand;
import org.tellervo.desktop.bulkdataentry.model.BulkImportModel;
import org.tellervo.desktop.bulkdataentry.model.ColumnChooserModel;
import org.tellervo.desktop.bulkdataentry.model.SingleElementModel;
import org.tellervo.desktop.bulkdataentry.model.SingleObjectModel;
import org.tellervo.desktop.bulkdataentry.model.SingleSampleModel;
import org.tellervo.desktop.bulkdataentry.view.BulkDataEntryWindow;
import org.tellervo.desktop.core.App;
import org.tellervo.desktop.prefs.Prefs.PrefKey;

import com.dmurph.mvc.MVCEvent;
import com.dmurph.mvc.control.FrontController;


/**
 * @author daniel
 *
 */
public class BulkImportController extends FrontController {
	public static final String DISPLAY_COLUMN_CHOOSER = "BULK_IMPORT_DISPLAY_COLUMN_CHOOSER";
	public static final String HIDE_COLUMN_CHOOSER = "BULK_IMPORT_HIDE_COLUMN_CHOOSER";
	public static final String ADD_ROW = "BULK_IMPORT_ADD_ROW";
	public static final String COPY_ROW = "BULK_IMPORT_COPY_ROW";
	public static final String DELETE_ROW = "BULK_IMPORT_DELETE_ROW";
	public static final String DISPLAY_BULK_IMPORT = "BULK_IMPORT_DISPLAY_BULK_IMPORT";
	public static final String REMOVE_SELECTED = "BULK_IMPORT_REMOVE_SELECTED_ROW";
	public static final String COPY_SELECTED_ROWS = "BULK_IMPORT_COPY_ALL_SELECTED_ROWS";
	
	public static final String IMPORT_SELECTED_OBJECTS = "BULK_IMPORT_SELECTED_OBJECTS";
	public static final String IMPORT_SELECTED_ELEMENTS = "BULK_IMPORT_SELECTED_ELEMENTS";
	public static final String IMPORT_SELECTED_SAMPLES = "BULK_IMPORT_SELECTED_SAMPLES";
	
	public static final String SET_DYNAMIC_COMBO_BOX_OBJECTS = "BULK_IMPORT_SET_DYNAMIC_COMBO_BOX_OBJECTS";
	public static final String SET_DYNAMIC_COMBO_BOX_ELEMENTS = "BULK_IMPORT_SET_DYNAMIC_COMBO_BOX_ELEMENTS";
	public static final String SET_DYNAMIC_COMBO_BOX_SAMPLES = "BULK_IMPORT_SET_DYNAMIC_COMBO_BOX_SAMPLES";
	
	public static final String BROWSE_GPX_FILE = "BULK_IMPORT_BROWSE_GPX_FILE";
	public static final String PRINT_SAMPLE_BARCODES = "BULK_IMPORT_PRINT_BARCODES";

	
	public BulkImportController(){
		registerCommand(DISPLAY_COLUMN_CHOOSER, ShowColumnWindowCommand.class);
		registerCommand(IMPORT_SELECTED_OBJECTS, ImportSelectedObjectsCommand.class);
		registerCommand(HIDE_COLUMN_CHOOSER, HideColumnWindowCommand.class);
		registerCommand(ADD_ROW, AddRowCommand.class);
		registerCommand(COPY_ROW, CopyRowCommand.class);
		registerCommand(COPY_SELECTED_ROWS, CopySelectedRowsCommand.class);
		registerCommand(DELETE_ROW, DeleteRowCommand.class);
		registerCommand(REMOVE_SELECTED, RemoveSelectedCommand.class);
		registerCommand(DISPLAY_BULK_IMPORT, "display");
		registerCommand(IMPORT_SELECTED_ELEMENTS, ImportSelectedElementsCommand.class);
		registerCommand(IMPORT_SELECTED_SAMPLES, ImportSelectedSamplesCommand.class);
		registerCommand(BROWSE_GPX_FILE, GPXBrowseCommand.class);
		registerCommand(PRINT_SAMPLE_BARCODES, PrintBarcodesCommand.class);
	}
	
	public void display(MVCEvent argEvent){
		/*if(MVC.getTracker() == null){
			JGoogleAnalyticsTracker tracker = new JGoogleAnalyticsTracker(new AnalyticsConfigData("UA-17109202-7"), GoogleAnalyticsVersion.V_4_7_2);
			MVC.setTracker(tracker);
		}else{
			MVC.getTracker().resetSession();
		}*/
		//MVC.showEventMonitor();
		
		if(BulkImportModel.getInstance().getMainView() != null){
			BulkDataEntryWindow window = BulkImportModel.getInstance().getMainView();
			window.setVisible(true);
			window.toFront();
			return;
		}
		
		BulkImportModel model = BulkImportModel.getInstance();
		populateObjectDefaults(model.getObjectModel().getColumnModel());
		populateElementDefaults(model.getElementModel().getColumnModel());
		populateSampleDefaults(model.getSampleModel().getColumnModel());
		
		// just put inot the sample model for now, change if we want to add extra radius functionality
		populateRadiusDefaults(model.getSampleModel().getColumnModel());
		
		BulkDataEntryWindow frame = new BulkDataEntryWindow();
		BulkImportModel.getInstance().setMainView(frame);
		frame.pack();
		frame.setVisible(true);
		//MVC.showEventMonitor();
	}
	
	private void populateObjectDefaults(ColumnChooserModel ccmodel){
		
		
		// TODO adding choice of columns to preferences.  
		ccmodel.add(SingleObjectModel.IMPORTED);
		if (App.prefs.getBooleanPref(PrefKey.OBJECT_FIELD_VISIBLE_PARENT_OBJECT, false)) ccmodel.add(SingleObjectModel.PARENT_OBJECT);
		if (App.prefs.getBooleanPref(PrefKey.OBJECT_FIELD_VISIBLE_OBJECT_CODE, true)) ccmodel.add(SingleObjectModel.OBJECT_CODE);
		ccmodel.add(SingleObjectModel.TITLE);
		ccmodel.add(SingleObjectModel.TYPE);
		ccmodel.add(SingleObjectModel.COMMENTS);
		ccmodel.add(SingleObjectModel.DESCRIPTION);
		ccmodel.add(SingleObjectModel.LATITUDE);
		ccmodel.add(SingleObjectModel.LONGITUDE);
		ccmodel.add(SingleObjectModel.LOCATION_PRECISION);
		ccmodel.add(SingleObjectModel.LOCATION_COMMENT);
		//ccmodel.add(SingleObjectModel.WAYPOINT);
		ccmodel.add(SingleObjectModel.ADDRESSLINE1);
		ccmodel.add(SingleObjectModel.ADDRESSLINE2);
		ccmodel.add(SingleObjectModel.CITY_TOWN);
		ccmodel.add(SingleObjectModel.STATE_PROVINCE_REGION);
		ccmodel.add(SingleObjectModel.POSTCODE);
		ccmodel.add(SingleObjectModel.COUNTRY);
		ccmodel.add(SingleObjectModel.OWNER);
		ccmodel.add(SingleObjectModel.CREATOR);
	}
	
	private void populateElementDefaults(ColumnChooserModel ccmodel){
		ccmodel.add(SingleElementModel.IMPORTED);
		ccmodel.add(SingleElementModel.OBJECT);
		ccmodel.add(SingleElementModel.TITLE);
		ccmodel.add(SingleElementModel.TYPE);
		ccmodel.add(SingleElementModel.TAXON);
		
	}
	
	private void populateSampleDefaults(ColumnChooserModel ccmodel){
		ccmodel.add(SingleSampleModel.IMPORTED);
		ccmodel.add(SingleSampleModel.OBJECT);
		ccmodel.add(SingleSampleModel.ELEMENT);
		ccmodel.add(SingleSampleModel.TITLE);
		ccmodel.add(SingleSampleModel.TYPE);
		ccmodel.add(SingleSampleModel.BOX);
	}
	
	private void populateRadiusDefaults(ColumnChooserModel ccmodel){
		
	}
}
