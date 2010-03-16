/*
 * Subsite.java
 *
 * Created on June 2, 2008, 3:38 PM
 */

package edu.cornell.dendro.corina.gui.datawizard;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import edu.cornell.dendro.corina.tridas.Subsite;
import edu.cornell.dendro.corina.tridas.TridasObject;
import edu.cornell.dendro.corina.util.Center;
import edu.cornell.dendro.corina.webdbi.IntermediateResource;
import edu.cornell.dendro.corina.webdbi.PrototypeLoadDialog;

/**
 *
 * @author  peterbrewer
 */
public class SubsiteEditorPanel extends BaseEditorPanel<Subsite> {
    
    /** Creates new form Subsite */
    public SubsiteEditorPanel() {
        initComponents();

        initialize();
        validateForm();
    }
    
    private void initialize() {
    	// When the name is changed, listen and ensure length
    	// We use this as one condition to allow the Apply button to be enabled
    	setFieldValidateButtons(txtSubsiteName);
    	
    	// select all on focus
    	setSelectAllOnFocus(txtSubsiteName);
    	
    	// force focus
    	txtSubsiteName.requestFocusInWindow();
    	
    	// apply button
    	//getRootPane().setDefaultButton(btnApply);
    	btnApply.setEnabled(false);
       	btnApply.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent ae) {
    			commit();
    		}
    	});
       	
    	// cancel button
       	btnCancel.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent ae) {
    			dispose();
    		}
    	});
       	
    }
    
    public void populate() {
       	// neat site name thingy
       	txtSite.setText(getParentObject().toString());    	
    }
    
    /*
    public void commit() {
    	Subsite subsite = new Subsite(Subsite.ID_NEW, txtSubsiteName.getText());
    	assimilateUpdateObject(subsite);
    	IntermediateResource ir = new IntermediateResource(getParentObject(), subsite);

    	if(!createOrUpdateObject(ir))
    		return;
    	
		// add the subsite to the parent site, sort, and 
		if(ir.getObject().get(0) instanceof Subsite) {
			setNewObject((Subsite) ir.getObject().get(0));
			if(getParentObject() != null) {
				((TridasObject)getParentObject()).addSubsite(getNewObject());
				((TridasObject)getParentObject()).sortSubsites();
			}
		}
		
    	dispose();
    }
    */
        
    protected void validateForm() {
    	boolean nameOk;

    	// then, site name
		int len = txtSubsiteName.getText().length();

		if(len > 2 && !txtSubsiteName.getText().equals("Name of this subsite"))
			nameOk = true;
		else
			nameOk = false;
		
		colorField(txtSubsiteName, nameOk);

		setFormValidated(nameOk);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtSubsiteName = new javax.swing.JTextField();
        lblSite = new javax.swing.JLabel();
        lblSubsiteName = new javax.swing.JLabel();
        txtSite = new javax.swing.JTextField();
        panelButtons = new javax.swing.JPanel();
        btnCancel = new javax.swing.JButton();
        btnApply = new javax.swing.JButton();
        seperatorButtons = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sub Site details");

        txtSubsiteName.setText("Name of this subsite");
        txtSubsiteName.setToolTipText("Name of this subsite");

        lblSite.setLabelFor(txtSite);
        lblSite.setText("Site:");

        lblSubsiteName.setLabelFor(lblSubsiteName);
        lblSubsiteName.setText("Sub site name:");

        txtSite.setEditable(false);
        txtSite.setText("ABC - Site name");
        txtSite.setToolTipText("Site to which this sub site belongs");

        btnCancel.setText("Cancel");

        btnApply.setText("Apply");

        seperatorButtons.setBackground(new java.awt.Color(153, 153, 153));
        seperatorButtons.setOpaque(true);

        /*
        org.jdesktop.layout.GroupLayout panelButtonsLayout = new org.jdesktop.layout.GroupLayout(panelButtons);
        panelButtons.setLayout(panelButtonsLayout);
        panelButtonsLayout.setHorizontalGroup(
            panelButtonsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, panelButtonsLayout.createSequentialGroup()
                .addContainerGap(239, Short.MAX_VALUE)
                .add(btnApply)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnCancel)
                .add(5, 5, 5))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, seperatorButtons, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        panelButtonsLayout.setVerticalGroup(
            panelButtonsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelButtonsLayout.createSequentialGroup()
                .add(seperatorButtons, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelButtonsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(btnCancel)
                    .add(btnApply))
                .addContainerGap(30, Short.MAX_VALUE))
        );*/

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(lblSubsiteName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(lblSite, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(txtSite, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                    .add(txtSubsiteName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
                .addContainerGap())
            //.add(panelButtons, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblSite)
                    .add(txtSite, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblSubsiteName)
                    .add(txtSubsiteName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(7, 7, 7)
                )//.add(panelButtons, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnApply;
    private javax.swing.JButton btnCancel;
    private javax.swing.JLabel lblSite;
    private javax.swing.JLabel lblSubsiteName;
    private javax.swing.JPanel panelButtons;
    private javax.swing.JSeparator seperatorButtons;
    private javax.swing.JTextField txtSite;
    private javax.swing.JTextField txtSubsiteName;
    // End of variables declaration//GEN-END:variables
    
}