package org.tellervo.desktop.gui.menus;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.tellervo.desktop.core.App;
import org.tellervo.desktop.editor.FullEditor;
import org.tellervo.desktop.gui.seriesidentity.IdentifySeriesPanel;
import org.tellervo.desktop.io.view.ImportView;
import org.tellervo.desktop.prefs.Prefs.PrefKey;
import org.tellervo.desktop.ui.Builder;
import org.tellervo.desktop.util.openrecent.OpenRecent;
import org.tridas.io.AbstractDendroFileReader;
import org.tridas.io.DendroFileFilter;
import org.tridas.io.TridasIO;

public class FullEditorMenuBar extends EditorMenuBar{

	private static final long serialVersionUID = 1L;
	private JMenuItem miOpenMulti;
	private JMenuItem miLogoff;
	private JMenuItem miLogon;
	private JMenuItem miExportData;
	private JMenuItem miBulkDataEntry;
	private JMenuItem miDesignODKForm;

	private Window parent;

	
	private JMenu mnAdministration;
	private JMenuItem miUsersAndGroups;
	private JMenuItem miEditViewPermissions;
	private JMenuItem miChangePassword;
	private JMenuItem miForgetPassword;
	private JMenuItem miReports;
	private JMenu miLabels;
	private JMenuItem miBoxLabel;
	private JMenuItem miBasicBoxLabel;
	private JMenuItem miSampleBoxLabel;
	private JMenuItem miDatabaseStatistics;
	private JMenu miCurationMenu;
	private JMenuItem miCurationMenuFindSample;
	private JMenuItem miCurationMenuSampleStatus;
	private JMenuItem miCurationMenuBoxDetails;
	private JMenuItem miCurationMenuNewLoan;
	private JMenuItem miCurationMenuLoanDialog;
	private JMenuItem miMetaDB;
	private JMenuItem miSiteMap;
	private JMenuItem miReportBugOnLastTransaction;
	private JMenuItem miXMLCommunicationsViewer;
	private JMenu mnView;
	private JMenu mnTools;
	JMenuItem miComponentSeries;
	private JMenuItem miToolsCrossdate;
	private JMenuItem miToolsTruncate;
	private JMenuItem miNew;
	private JMenuItem miOpen;
	private JMenu openrecent;
	private JMenuItem miSave;
	private JMenuItem miPrint;
	
	private JMenuItem miMapCompass;

	
	public FullEditorMenuBar(FullEditorActions actions, FullEditor editor)
	{
        super(actions, editor);
        
		// FILE MENU
		JMenu mnFile = new JMenu("File");

		miNew = new JMenuItem(actions.fileNewAction);
		mnFile.add(miNew);

		miOpen = new JMenuItem(actions.fileOpenAction);
		mnFile.add(miOpen);

		miOpenMulti = new JMenuItem(actions.fileOpenMultiAction);
		mnFile.add(miOpenMulti);
		
		openrecent = OpenRecent.makeOpenRecentMenu();
		mnFile.add(openrecent);
		
		mnFile.addSeparator();
		
		mnFile.add(getImportDataOnlyMenu());
		//mnFile.add(getImportDataAndMetadataMenu());

		miExportData = new JMenuItem(actions.fileExportDataAction);
		mnFile.add(miExportData);
		
		miBulkDataEntry = new JMenuItem(actions.fileBulkDataEntryAction);
		mnFile.add(miBulkDataEntry);

		miDesignODKForm = new JMenuItem(actions.fileDesignODKFormAction);
		mnFile.add(miDesignODKForm);
		
		mnFile.addSeparator();


		miSave = new JMenuItem(actions.fileSaveAction);
		mnFile.add(miSave);
		
		try{
			JMenuItem miSaveAll = new JMenuItem(actions.fileSaveAllAction);
			mnFile.add(miSaveAll);
		} catch (Exception e)
		{
			
		}

		mnFile.addSeparator();

		miPrint = new JMenuItem(actions.filePrintAction);
		mnFile.add(miPrint);

		mnFile.addSeparator();

		miLogoff = new JMenuItem(actions.fileLogoffAction);
		mnFile.add(miLogoff);

		miLogon = new JMenuItem(actions.fileLogonAction);
		mnFile.add(miLogon);

		mnFile.addSeparator();

		
		JMenuItem miExit = new JMenuItem(actions.fileExitAction);
		mnFile.add(miExit);

		add(mnFile);

		
		// EDIT MENU
		
		JMenu mnEdit = new JMenu("Edit");
		add(mnEdit);

		JMenuItem miCopy = new JMenuItem(actions.editCopyAction);
		mnEdit.add(miCopy);

		mnEdit.addSeparator();

		JMenuItem miSelectAll = new JMenuItem(actions.editSelectAllAction);
		mnEdit.add(miSelectAll);

		mnEdit.addSeparator();

		JMenuItem miInsertYearPushForwards = new JMenuItem(actions.editInsertYearPushForwardsAction);
		mnEdit.add(miInsertYearPushForwards);

		JMenuItem miInsertYearPushBackwards = new JMenuItem(actions.editInsertYearPushBackwardsAction);
		mnEdit.add(miInsertYearPushBackwards);

		JMenuItem miInsertMissingRingPushForwards = new JMenuItem(actions.editInsertMissingRingPushForwardsAction);
		mnEdit.add(miInsertMissingRingPushForwards);

		JMenuItem miInsertMissingRingPushBackwards = new JMenuItem(actions.editInsertMissingRingPushBackwardsAction);
		mnEdit.add(miInsertMissingRingPushBackwards);

		JMenuItem miInsertYears = new JMenuItem(actions.editInsertYearsAction);
		mnEdit.add(miInsertYears);

		
		JMenuItem miDeleteYear = new JMenuItem(actions.editDeleteAction);
		mnEdit.add(miDeleteYear);
		
		mnEdit.addSeparator();

		JMenuItem miInitializeDataGrid = new JMenuItem(actions.editInitGridAction);
		mnEdit.add(miInitializeDataGrid);

		mnEdit.add(this.getMeasureModeMenu());
		
		JMenuItem miStartMeasuring = new JMenuItem(actions.editMeasureAction);
		mnEdit.add(miStartMeasuring);

		mnEdit.addSeparator();

		JMenuItem miPreferences = new JMenuItem(actions.editPreferencesAction);
		mnEdit.add(miPreferences);

		add(mnEdit);
		
		
		// ADMIN MENU

		mnAdministration = new JMenu("Administration");
		
		miUsersAndGroups = new JMenuItem(actions.adminUserAndGroupsAction);
		mnAdministration.add(miUsersAndGroups);
		
		miEditViewPermissions = new JMenuItem(actions.adminEditViewPermissionsAction);
		mnAdministration.add(miEditViewPermissions);
		
		mnAdministration.addSeparator();
		
		miChangePassword = new JMenuItem(actions.adminChangePasswordAction);
		mnAdministration.add(miChangePassword);
		
		miForgetPassword = new JMenuItem(actions.adminForgetPasswordAction);
		mnAdministration.add(miForgetPassword);
		
		miReports = new JMenuItem(actions.adminReportsAction);
		mnAdministration.add(miReports);
		
		miLabels = new JMenu(actions.adminLabelAction);
		miBoxLabel = new JMenuItem(actions.adminBoxLabelAction);
		miBasicBoxLabel = new JMenuItem(actions.adminBasicBoxLabelAction);
		miSampleBoxLabel = new JMenuItem(actions.adminSampleLabelAction);
		miLabels.add(miBoxLabel);
		miLabels.add(miBasicBoxLabel);
		miLabels.add(miSampleBoxLabel);
		mnAdministration.add(miLabels);
		
		
		mnAdministration.addSeparator();
		
		miDatabaseStatistics = new JMenuItem(actions.adminDatabaseStatisticsAction);
		mnAdministration.add(miDatabaseStatistics);
		
		miCurationMenu = new JMenu("Curation...");
		miCurationMenu.setIcon(Builder.getIcon("curation.png", 22));
		miCurationMenuFindSample = new JMenuItem(actions.adminCurationMenuFindSampleAction);
		miCurationMenuSampleStatus = new JMenuItem(actions.adminCurationMenuSampleStatusAction);
		miCurationMenuBoxDetails = new JMenuItem(actions.adminCurationMenuBoxDetailsAction);
		miCurationMenuLoanDialog = new JMenuItem(actions.adminCurationMenuLoanDialogAction);
		miCurationMenuNewLoan = new JMenuItem(actions.adminCurationMenuNewLoanAction);
		miCurationMenu.add(miCurationMenuFindSample);
		miCurationMenu.add(miCurationMenuSampleStatus);
		miCurationMenu.add(miCurationMenuBoxDetails);
		miCurationMenu.add(miCurationMenuLoanDialog);
		miCurationMenu.add(miCurationMenuNewLoan);
		mnAdministration.add(miCurationMenu);
		
		mnAdministration.addSeparator();
		
		miMetaDB = new JMenuItem(actions.adminMetaDBAction);
		mnAdministration.add(miMetaDB);
		
		miSiteMap = new JMenuItem(actions.adminSiteMapAction);
		mnAdministration.add(miSiteMap);		
		
		add(mnAdministration);

		
		// VIEW MENU
		
		mnView = new JMenu("View");
		add(mnView);
		
		JMenuItem mnViewExtent = new JMenuItem(actions.viewZoomToExtent);
		mnView.add(mnViewExtent);

		miMapCompass = new JMenuItem(actions.mapCompassToggleAction);
		mnView.add(miMapCompass);
		
		
		// TOOLS MENU
		
		mnTools = new JMenu("Tools");
		add(mnTools);
		
		miToolsTruncate = new JMenuItem(actions.toolsTruncateAction);
		mnTools.add(miToolsTruncate);
		
		JMenuItem miToolsReverse = new JMenuItem(actions.toolsReverseAction);
		mnTools.add(miToolsReverse);
		
		JMenuItem miToolsReconcile = new JMenuItem(actions.toolsReconcileAction);
		mnTools.add(miToolsReconcile);
		
		JMenuItem miToolsIndex = new JMenuItem(actions.toolsIndexAction);
		mnTools.add(miToolsIndex);
		
		JMenuItem miToolsSum = new JMenuItem(actions.toolsSumAction);
		mnTools.add(miToolsSum);
		
		JMenuItem miToolsRedate = new JMenuItem(actions.toolsRedateAction);
		mnTools.add(miToolsRedate);
		
		miToolsCrossdate = new JMenuItem(actions.toolsCrossdateAction);
		mnTools.add(miToolsCrossdate);
		

		
		// GRAPH MENU
		
		JMenu mnGraph = new JMenu("Graph");
		
		JMenuItem miCurrentSeries = new JMenuItem(actions.graphCurrentSeriesAction);
		mnGraph.add(miCurrentSeries);
		
	    miComponentSeries = new JMenuItem(actions.graphComponentSeriesAction);
	    mnGraph.add(miComponentSeries);
	    
	    JMenuItem miAllSeries = new JMenuItem(actions.graphAllSeriesAction);
	    mnGraph.add(miAllSeries);
	    
	    JMenuItem miCreateFileHistoryPlot = new JMenuItem(actions.graphCreateFileHistoryPlotAction);
	    mnGraph.add(miCreateFileHistoryPlot);
	    
		add(mnGraph);

		// MAP MENU
		
		JMenu mnMap = new JMenu("Map");
		
		JMenu mnControls = new JMenu("Controls");
		
		JCheckBoxMenuItem miCompass = new JCheckBoxMenuItem(actions.mapCompassToggleAction);
		mnControls.add(miCompass);
		
		JCheckBoxMenuItem miWorldMapLayer = new JCheckBoxMenuItem(actions.mapWorldMapLayerToggleAction);
		mnControls.add(miWorldMapLayer);
		
		JCheckBoxMenuItem miControlLayer = new JCheckBoxMenuItem(actions.mapControlLayerToggleAction);
		mnControls.add(miControlLayer);
		
		JCheckBoxMenuItem miScaleBarLayer = new JCheckBoxMenuItem(actions.mapScaleBarLayerToggleAction);
		mnControls.add(miScaleBarLayer);
		
		JCheckBoxMenuItem miUTMGraticuleLayer = new JCheckBoxMenuItem(actions.mapUTMGraticuleLayerToggleAction);
		mnControls.add(miUTMGraticuleLayer);
		
		JCheckBoxMenuItem miMGRSGraticuleLayer = new JCheckBoxMenuItem(actions.mapMGRSGraticuleLayerToggleAction);
		mnControls.add(miMGRSGraticuleLayer);
		
		JCheckBoxMenuItem miNASAWFSPlaceName = new JCheckBoxMenuItem(actions.mapNASAWFSPlaceNameLayerToggleAction);
		mnControls.add(miNASAWFSPlaceName);
		
		JCheckBoxMenuItem miCountryBoundaries = new JCheckBoxMenuItem(actions.mapCountryBoundariesLayerToggleAction);
		mnControls.add(miCountryBoundaries);
		
		mnMap.add(mnControls);
		mnMap.addSeparator();
				
		JMenuItem miStereo = new JMenuItem(actions.mapStereoModeAction);
		mnMap.add(miStereo);
		
		JMenuItem miSave = new JMenuItem(actions.mapSaveCurrentMapAsImagesAction);
		mnMap.add(miSave);
		mnMap.addSeparator();
		
		JMenu mnAddLayers = new JMenu("Add Layers");
		
		JMenuItem miShapefileLayer = new JMenuItem(actions.mapShapefileLayerAction);
		mnAddLayers.add(miShapefileLayer);
		
		JMenuItem miKMLLayer = new JMenuItem(actions.mapKMLLayerAction);
		mnAddLayers.add(miKMLLayer);
		
		JMenuItem miGISImage = new JMenuItem(actions.mapGISImageAction);
		mnAddLayers.add(miGISImage);
		
		JMenuItem miWMSLayer = new JMenuItem(actions.mapWMSLayerAction);
		mnAddLayers.add(miWMSLayer);
		
		mnMap.add(mnAddLayers);
		add(mnMap);
		
		
		// HELP MENU
		
		JMenu mnHelp = new JMenu("Help");
		
		JMenuItem miHelpContents = new JMenuItem(actions.helpHelpContentsAction);
		mnHelp.add(miHelpContents);
		
		JMenuItem miSetupWizard = new JMenuItem(actions.helpSetupWizardAction);
		mnHelp.add(miSetupWizard);
		
		JMenu miVideoTutorials = new JMenu("Video tutorials... ");
		miVideoTutorials.setIcon(Builder.getIcon("video.png", 16));
				
		JMenuItem miVideoIntro = new JMenuItem(actions.helpVideoIntroAction);
		miVideoTutorials.add(miVideoIntro);
		
		JMenuItem miVideoGettingStarted = new JMenuItem(actions.helpVideoGettingStartedAction);
		miVideoTutorials.add(miVideoGettingStarted);
		
		JMenuItem miVideoServerInstallation = new JMenuItem(actions.helpVideoServerInstallationAction);
		miVideoTutorials.add(miVideoServerInstallation);
		
		JMenuItem miEnteringMetadata = new JMenuItem(actions.helpVideoEnteringMetadataAction);
		miVideoTutorials.add(miEnteringMetadata);
		
		JMenuItem miMeasuringSamples = new JMenuItem(actions.helpVideoMeasuringSamplesAction);
		miVideoTutorials.add(miMeasuringSamples);
		
		JMenuItem miMapping = new JMenuItem(actions.helpVideoMappingAction);
		miVideoTutorials.add(miMapping);
		
		JMenuItem miAdministeringUsersAndGroups = new JMenuItem(actions.helpVideoAdminsteringUsersAndGroupsAction);
		miVideoTutorials.add(miAdministeringUsersAndGroups);
		
		JMenuItem miCuratingYourCollection = new JMenuItem(actions.helpVideoCuratingYourCollectionAction);
		miVideoTutorials.add(miCuratingYourCollection);
		
		JMenuItem miExportingData = new JMenuItem(actions.helpVideoExportingDataAction);
		miVideoTutorials.add(miExportingData);
		
		JMenuItem miImporting = new JMenuItem(actions.helpVideoImportingAction);
		miVideoTutorials.add(miImporting);
		
		JMenuItem miGraphing = new JMenuItem(actions.helpVideoGraphingAction);
		miVideoTutorials.add(miGraphing);
		
		JMenuItem miDataManipulation = new JMenuItem(actions.helpVideoDataManipulationAction);
		miVideoTutorials.add(miDataManipulation);
				
		mnHelp.add(miVideoTutorials);
		
		mnHelp.addSeparator();
		
		JMenuItem miCheckForUpdates = new JMenuItem(actions.helpCheckForUpdatesAction);
		mnHelp.add(miCheckForUpdates);
		
		JMenuItem miEmailDevelopers = new JMenuItem(actions.helpEmailDeveloperAction);
		mnHelp.add(miEmailDevelopers);
		
		miReportBugOnLastTransaction = new JMenuItem(actions.helpReportBugOnLastTransactionAction);
		mnHelp.add(miReportBugOnLastTransaction);
		
		mnHelp.addSeparator();
		
		JMenu miDebugInfo = new JMenu("Debug Information");
		
		JMenuItem miErrorLogViewer = new JMenuItem(actions.helpErrorLogViewerAction);
		miDebugInfo.add(miErrorLogViewer);
				
		miXMLCommunicationsViewer = new JMenuItem(actions.helpXMLCommunicationsViewerAction);
		miDebugInfo.add(miXMLCommunicationsViewer);
		
		JMenuItem miMVCMonitor = new JMenuItem(actions.helpMVCMonitorAction);
		miDebugInfo.add(miMVCMonitor);
		
		JMenuItem miSystemsInformation = new JMenuItem(actions.helpSystemsInformationAction);
		miDebugInfo.add(miSystemsInformation);
		
		mnHelp.add(miDebugInfo);
		mnHelp.addSeparator();
		
		JMenuItem miAboutTellervo = new JMenuItem(actions.helpAboutTellervoAction);
		mnHelp.add(miAboutTellervo);
		
		
		add(mnHelp);


		

	}
	
	
	/**
	 * Get a submenu containing menu items for importing data and metadata from legacy data file
	 * 
	 * @return
	 */
	private JMenu getImportDataAndMetadataMenu()
	{
		
		JMenu fileimport = Builder.makeMenu("menus.file.import", "fileimport.png");
				
		for (final String s : TridasIO.getSupportedReadingFormats()) 
		{
			
			JMenuItem importitem = new JMenuItem(s);

			importitem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// Set up file chooser and filters
					AbstractDendroFileReader reader = TridasIO.getFileReader(s);
					DendroFileFilter filter = reader.getDendroFileFilter();
					File lastFolder = null;
					try{
						lastFolder = new File(App.prefs.getPref(PrefKey.FOLDER_LAST_READ, null));
					} catch (Exception e){}
					
					JFileChooser fc = new JFileChooser(lastFolder);
					fc.addChoosableFileFilter(filter);
					fc.setFileFilter(filter);
					
					int returnVal = fc.showOpenDialog(null);
						
					// Get details from user
				    if (returnVal == JFileChooser.APPROVE_OPTION) {
				        File file = fc.getSelectedFile();
				        ImportView importDialog = new ImportView(file, s);
						importDialog.setVisible(true);
				        
						// Remember this folder for next time
						App.prefs.setPref(PrefKey.FOLDER_LAST_READ, file.getPath());
					    
				    } else {
				    	return;
				    }

					
				}
				
			});
			
			fileimport.add(importitem);
			
			
		}
	
		return fileimport;
		
		
	}
	
	
	/**
	 * Get a submenu containing menu items for importing data only from legacy data file
	 * 
	 * @return
	 */
	private JMenu getImportDataOnlyMenu()
	{

		JMenu fileimportdataonly = Builder.makeMenu("menus.file.importdataonly", "fileimport.png");
		
		for (final String s : TridasIO.getSupportedReadingFormats()) {
			
			JMenuItem importitem = new JMenuItem(s);

			importitem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// Set up file chooser and filters
					AbstractDendroFileReader reader = TridasIO.getFileReader(s);
					DendroFileFilter filter = reader.getDendroFileFilter();
					File lastFolder = null;
					try{
						lastFolder = new File(App.prefs.getPref(PrefKey.FOLDER_LAST_READ, null));
					} catch (Exception e){}
					
					JFileChooser fc = new JFileChooser(lastFolder);
					fc.addChoosableFileFilter(filter);
					fc.setFileFilter(filter);
					fc.setMultiSelectionEnabled(true);
					int returnVal = fc.showOpenDialog(null);
						
					// Get details from user
				    if (returnVal == JFileChooser.APPROVE_OPTION) {
				        
				    	File[] files = fc.getSelectedFiles();
				    	
				    	/*File file = fc.getSelectedFile();
				        ImportDataOnly importDialog = new ImportDataOnly(parent, file, s);
				        importDialog.openEditors();*/
				    	
				    	

				        
						// Remember this folder for next time
						App.prefs.setPref(PrefKey.FOLDER_LAST_READ, files[0].getPath());
						
				    	IdentifySeriesPanel.show(files, s);

					    
				    } else {
				    	return;
				    }

					
				}
				
			});
			
			fileimportdataonly.add(importitem);
		}
		return fileimportdataonly;
	}
	

}
