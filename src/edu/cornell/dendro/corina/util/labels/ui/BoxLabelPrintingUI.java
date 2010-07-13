
/*
 * BoxLabelPrintingUI.java
 *
 * Created on August 26, 2009, 11:52 AM
 */

package edu.cornell.dendro.corina.util.labels.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import edu.cornell.dendro.corina.core.App;
import edu.cornell.dendro.corina.dictionary.Dictionary;
import edu.cornell.dendro.corina.gui.dbbrowse.ElementListTableSorter;
import edu.cornell.dendro.corina.gui.dbbrowse.SiteRenderer;
import edu.cornell.dendro.corina.schema.WSIBox;
import edu.cornell.dendro.corina.tridasv2.TridasComparator;
import org.tridas.util.TridasObjectEx;
import edu.cornell.dendro.corina.util.ArrayListModel;

/**
 *
 * @author  peterbrewer
 */
public class BoxLabelPrintingUI extends javax.swing.JPanel implements ActionListener{
	
	private static final long serialVersionUID = -7164959226567752919L;
	protected ArrayListModel<WSIBox> selModel = new ArrayListModel<WSIBox>();
	protected ArrayListModel<WSIBox> availModel = new ArrayListModel<WSIBox>();

	
    /** Creates new form BoxLabelPrintingUI */
    public BoxLabelPrintingUI() {
    	initComponents();
    	
        btnAdd.addActionListener(this);
        btnRemove.addActionListener(this);
    	
        populateBoxList();
        

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

        jScrollPane1.setViewportView(lstAvailable);

        btnAdd.setText(">");

        btnRemove.setText("<");

        jLabel1.setText("Available:");

        jLabel2.setText("Selected:");

        jScrollPane2.setViewportView(lstSelected);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(20, 20, 20)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
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
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 169, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, layout.createSequentialGroup()
                        .add(btnAdd)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 158, Short.MAX_VALUE)
                        .add(btnRemove)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    

    private void populateBoxList(){
	
    // Grab box dictionary
    List<WSIBox> boxlist = (List<WSIBox>) Dictionary.getDictionary("boxDictionary");
	
    // Set up available list
	availModel.addAll(boxlist);
	lstAvailable.setModel(availModel);
	lstAvailable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	lstAvailable.setCellRenderer(new TridasListCellRenderer());
	
	// Set up selected list
	lstSelected.setModel(selModel);
	lstSelected.setCellRenderer(new TridasListCellRenderer());
    }
	  
    private void sortAvailableBoxList(){
		// Sort list intelligently
		TridasComparator numSorter = new TridasComparator(TridasComparator.Type.SITE_CODES_THEN_TITLES, 
				TridasComparator.NullBehavior.NULLS_LAST, 
				TridasComparator.CompareBehavior.AS_NUMBERS_THEN_STRINGS);
		
		Collections.sort(availModel, numSorter);
		

    }
    
	public void actionPerformed(ActionEvent evt) {

	
		if(evt.getSource() == btnAdd){

			for (Object obj : lstAvailable.getSelectedValues())
			{
				WSIBox myobj = (WSIBox) obj;
				selModel.add(myobj);
				availModel.remove(myobj);
				sortAvailableBoxList();
			}
			
			
		}
			
		if(evt.getSource() == btnRemove)
		{
			for (Object obj : lstSelected.getSelectedValues())
			{
				WSIBox myobj = (WSIBox) obj;
				availModel.add(myobj);
				selModel.remove(myobj);
				sortAvailableBoxList();

			}
		}
		
	}
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton btnAdd;
    protected javax.swing.JButton btnRemove;
    protected javax.swing.JLabel jLabel1;
    protected javax.swing.JLabel jLabel2;
    protected javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JScrollPane jScrollPane2;
    protected javax.swing.JList lstAvailable;
    protected javax.swing.JList lstSelected;
    // End of variables declaration//GEN-END:variables

    
}
