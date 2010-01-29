/*
 * SampleLabelPrintingUI.java
 *
 * Created on August 26, 2009, 11:46 AM
 */

package edu.cornell.dendro.corina.util.labels.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.tridas.schema.TridasElement;
import org.tridas.schema.TridasObject;
import org.tridas.schema.TridasSample;

import edu.cornell.dendro.corina.core.App;
import edu.cornell.dendro.corina.gui.dbbrowse.SiteRenderer;
import edu.cornell.dendro.corina.schema.CorinaRequestFormat;
import edu.cornell.dendro.corina.schema.SearchOperator;
import edu.cornell.dendro.corina.schema.SearchParameterName;
import edu.cornell.dendro.corina.schema.SearchReturnObject;
import edu.cornell.dendro.corina.tridasv2.GenericFieldUtils;
import edu.cornell.dendro.corina.tridasv2.LabCode;
import edu.cornell.dendro.corina.tridasv2.LabCodeFormatter;
import edu.cornell.dendro.corina.tridasv2.TridasComparator;
import edu.cornell.dendro.corina.tridasv2.TridasObjectEx;
import edu.cornell.dendro.corina.util.ArrayListModel;
import edu.cornell.dendro.corina.wsi.corina.CorinaResourceAccessDialog;
import edu.cornell.dendro.corina.wsi.corina.CorinaResourceProperties;
import edu.cornell.dendro.corina.wsi.corina.SearchParameters;
import edu.cornell.dendro.corina.wsi.corina.resources.EntitySearchResource;

/**
 *
 * @author  peterbrewer
 */
public class SampleLabelPrintingUI extends javax.swing.JPanel implements ActionListener{
    
	protected ArrayListModel<TridasSample> selModel = new ArrayListModel<TridasSample>();
	protected ArrayListModel<TridasSample> availModel = new ArrayListModel<TridasSample>();
	protected ArrayListModel<TridasObject> objModel = new ArrayListModel<TridasObject>();
	
    /** Creates new form SampleLabelPrintingUI */
    public SampleLabelPrintingUI() {
        initComponents();
        setupDialog();
        populateObjectList();
        cboObjects.addActionListener(this);
        btnSelectObject.addActionListener(this);
        btnAdd.addActionListener(this);
        btnRemove.addActionListener(this);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lstAvailable = new javax.swing.JList();
        btnAdd = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstSelected = new javax.swing.JList();
        cboObjects = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        btnSelectObject = new javax.swing.JButton();

        jScrollPane1.setViewportView(lstAvailable);

        btnAdd.setText(">");

        btnRemove.setText("<");

        jLabel1.setText("Available:");

        jLabel2.setText("Selected:");

        jScrollPane2.setViewportView(lstSelected);

        cboObjects.setModel(objModel);

        jLabel3.setText("Samples from object:");

        btnSelectObject.setText("Select");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jLabel3)
                        .add(18, 18, 18)
                        .add(cboObjects, 0, 219, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnSelectObject))
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                    .add(btnAdd)
                                    .add(btnRemove))
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                            .add(layout.createSequentialGroup()
                                .add(jLabel1)
                                .add(198, 198, 198)))
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel2)
                            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel3)
                    .add(btnSelectObject)
                    .add(cboObjects, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(btnAdd)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 154, Short.MAX_VALUE)
                        .add(btnRemove)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    private void setupDialog()
    {
		lstAvailable.setModel(availModel);
		lstAvailable.setCellRenderer(new TridasListCellRenderer());
		
		lstSelected.setModel(selModel);
		lstSelected.setCellRenderer(new TridasListCellRenderer());
		
		cboObjects.setRenderer(new SiteRenderer());	
    }
    
    private void populateObjectList()
    {
		// Find all objects 
    	/*
    	SearchParameters objparam = new SearchParameters(SearchReturnObject.OBJECT);
		objparam.addSearchForAll();
		EntitySearchResource<TridasObject> objresource = new EntitySearchResource<TridasObject>(objparam);
		CorinaResourceAccessDialog dialog = new CorinaResourceAccessDialog(objresource);
		objresource.query();	
		dialog.setVisible(true);
		if(!dialog.isSuccessful()) 
		{ 
			System.out.println("oopsey doopsey.  Error getting objects");
			return;
		}
		List<TridasObject> objlist = objresource.getAssociatedResult();

		// Sort list intelligently
		TridasComparator numSorter = new TridasComparator(TridasComparator.Type.SITE_CODES_THEN_TITLES, 
				TridasComparator.NullBehavior.NULLS_LAST, 
				TridasComparator.CompareBehavior.AS_NUMBERS_THEN_STRINGS);
		
		Collections.sort(objlist, numSorter);
		
		objModel.addAll(objlist);
		*/
    	objModel.replaceContents(App.tridasObjects.getObjectList());
    	objModel.setSelectedItem(null);
    }

    /**
     * Stupid function to get a list of samples from a list of objects
     * 
     * Has the side effect of setting corina.internal.labcode generic field ;)
     * Hack to get lab code!
     * 
     * @param objects
     * @param objsThisLevel
     * @param returns
     * @return
     */
    private List<TridasSample> getSamplesList(List<TridasObject> objects, 
    		TridasObject[] objsThisLevel, List<TridasSample> returns) {
    	
    	// create this on the fly
    	if(returns == null)
    		returns = new ArrayList<TridasSample>();
    	
    	for(TridasObject obj : objects) {
    		
    		// handle stupid recursive objects
    		List<TridasObject> currentObjects;    		
    		if(objsThisLevel == null)
    			currentObjects = new ArrayList<TridasObject>();
    		else 
    			currentObjects = new ArrayList<TridasObject>(Arrays.asList(objsThisLevel));
    		
			currentObjects.add(obj);
			
			// grar...
			for(TridasObject obj2 : obj.getObjects()) {
				getSamplesList(obj.getObjects(), currentObjects.toArray(new TridasObject[0]), returns);
			}
			
			for(TridasElement ele : obj.getElements()) {
				for(TridasSample samp : ele.getSamples()) {
					
					// make lab code
					LabCode labcode = new LabCode();
					
					
					
					// objects first...
					labcode.appendSiteCode(((TridasObjectEx) currentObjects.get(0)).getLabCode());
					
					// Cornell only wants top level object in lab code. 
					// Make this client selectable before releasing to the world
					
					/*for(TridasObject obj2 : currentObjects) {
						if(obj2 instanceof TridasObjectEx)
							labcode.appendSiteCode(((TridasObjectEx) obj2).getLabCode());
						else
							labcode.appendSiteCode(obj2.getTitle());
						labcode.appendSiteTitle(obj2.getTitle());
					}
					*/
					
					labcode.setElementCode(ele.getTitle());
					labcode.setSampleCode(samp.getTitle());
					
					// set the lab code kludgily on the sample
					GenericFieldUtils.setField(samp, "corina.internal.labcodeText", 
							LabCodeFormatter.getRadiusPrefixFormatter().format(labcode));
					
					// add the sample to the returns list
					returns.add(samp);
				}
			}
    	}
    	
    	return returns;
    }
    
    private void populateAvailableSamples()
    {
    	// not necessary w/ replaceall
    	// lstAvailable.removeAll();
    	
    	TridasObject obj = (TridasObject) cboObjects.getSelectedItem();
    	
		// Find all samples for an object 
    	SearchParameters sampparam = new SearchParameters(SearchReturnObject.SAMPLE);
    	sampparam.addSearchConstraint(SearchParameterName.ANYPARENTOBJECTID, SearchOperator.EQUALS, obj.getIdentifier().getValue().toString());

    	// we want an object return here, so we get a list of object->elements->samples when we use comprehensive
		EntitySearchResource<TridasObject> sampresource = new EntitySearchResource<TridasObject>(sampparam, TridasObject.class);
		sampresource.setProperty(CorinaResourceProperties.ENTITY_REQUEST_FORMAT, CorinaRequestFormat.COMPREHENSIVE);
		
		CorinaResourceAccessDialog dialog = new CorinaResourceAccessDialog(sampresource);
		sampresource.query();	
		dialog.setVisible(true);
		
		if(!dialog.isSuccessful()) 
		{ 
			System.out.println("oopsey doopsey.  Error getting samples");
			return;
		}
		
		List<TridasObject> objList = sampresource.getAssociatedResult();
		List<TridasSample> sampList = getSamplesList(objList, null, null);
				
		availModel.replaceContents(sampList);	
		availModel.setSelectedItem(null);
		sortAvailableSamples();
    }
    
    private void sortAvailableSamples(){
		// Sort list intelligently
		TridasComparator numSorter = new TridasComparator(TridasComparator.Type.LAB_CODE_THEN_TITLES, 
				TridasComparator.NullBehavior.NULLS_LAST, 
				TridasComparator.CompareBehavior.AS_NUMBERS_THEN_STRINGS);
		
		Collections.sort(availModel, numSorter);
		
		
		

    }
    
	public void actionPerformed(ActionEvent evt) {
		// TODO Auto-generated method stub
		
		
		if (evt.getSource()==cboObjects)
		{
			
		}
		if (evt.getSource()==btnSelectObject)
		{
			populateAvailableSamples();
			
		}
		
		
		if(evt.getSource() == btnAdd){

			for (Object obj : lstAvailable.getSelectedValues())
			{
				TridasSample myobj = (TridasSample) obj;
				selModel.add(myobj);
				availModel.remove(myobj);
				sortAvailableSamples();
			}
			
			
		}
			
		if(evt.getSource() == btnRemove)
		{
			for (Object obj : lstSelected.getSelectedValues())
			{
				TridasSample myobj = (TridasSample) obj;
				availModel.add(myobj);
				selModel.remove(myobj);
				sortAvailableSamples();

			}
		}
		
		
	}
    
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton btnAdd;
    protected javax.swing.JButton btnRemove;
    protected javax.swing.JButton btnSelectObject;
    protected javax.swing.JComboBox cboObjects;
    protected javax.swing.JLabel jLabel1;
    protected javax.swing.JLabel jLabel2;
    protected javax.swing.JLabel jLabel3;
    protected javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JScrollPane jScrollPane2;
    protected javax.swing.JList lstAvailable;
    protected javax.swing.JList lstSelected;
    // End of variables declaration//GEN-END:variables




    
}
