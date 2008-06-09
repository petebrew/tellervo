/*
 * CreateSample.java
 *
 * Created on June 2, 2008, 3:30 PM
 */

package edu.cornell.dendro.corina.gui.newui;

/**
 *
 * @author  peterbrewer
 */
public class CreateSample extends javax.swing.JPanel {
    
    /** Creates new form CreateSample */
    public CreateSample() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelNewValues = new javax.swing.JPanel();
        cboSite = new javax.swing.JComboBox();
        lblSite = new javax.swing.JLabel();
        lblSubsite = new javax.swing.JLabel();
        lblTree = new javax.swing.JLabel();
        tblSpecimen = new javax.swing.JLabel();
        lblRadius = new javax.swing.JLabel();
        cboSubsite = new javax.swing.JComboBox();
        cboTree = new javax.swing.JComboBox();
        cboSpecimen = new javax.swing.JComboBox();
        cboRadius = new javax.swing.JComboBox();
        btnNewSite = new javax.swing.JButton();
        btnNewSubsite = new javax.swing.JButton();
        btnNewTree = new javax.swing.JButton();
        btnNewSpecimen = new javax.swing.JButton();
        btnNewRadius = new javax.swing.JButton();

        cboSite.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ADN - Adana, Pos Ormani", "AFY - Afyon - Ulu Camii", "VEZ - Bilicek, Vezirhan" }));

        lblSite.setText("Site:");

        lblSubsite.setText("Subsite:");

        lblTree.setText("Tree:");

        tblSpecimen.setText("Specimen:");

        lblRadius.setText("Radius:");

        cboSubsite.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Main" }));
        cboSubsite.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSubsiteActionPerformed(evt);
            }
        });

        cboTree.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1" }));

        cboSpecimen.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1" }));

        cboRadius.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1" }));

        btnNewSite.setText("New");

        btnNewSubsite.setText("New");

        btnNewTree.setText("New");

        btnNewSpecimen.setText("New");

        btnNewRadius.setText("New");

        org.jdesktop.layout.GroupLayout panelNewValuesLayout = new org.jdesktop.layout.GroupLayout(panelNewValues);
        panelNewValues.setLayout(panelNewValuesLayout);
        panelNewValuesLayout.setHorizontalGroup(
            panelNewValuesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelNewValuesLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelNewValuesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, lblSubsite, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(lblSite, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(tblSpecimen, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, lblTree, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, lblRadius, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .add(18, 18, 18)
                .add(panelNewValuesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(panelNewValuesLayout.createSequentialGroup()
                        .add(cboSite, 0, 248, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnNewSite))
                    .add(panelNewValuesLayout.createSequentialGroup()
                        .add(cboSubsite, 0, 248, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnNewSubsite))
                    .add(panelNewValuesLayout.createSequentialGroup()
                        .add(cboTree, 0, 248, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnNewTree))
                    .add(panelNewValuesLayout.createSequentialGroup()
                        .add(cboSpecimen, 0, 248, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnNewSpecimen))
                    .add(panelNewValuesLayout.createSequentialGroup()
                        .add(cboRadius, 0, 248, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnNewRadius)))
                .addContainerGap())
        );
        panelNewValuesLayout.setVerticalGroup(
            panelNewValuesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelNewValuesLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelNewValuesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblSite)
                    .add(cboSite, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btnNewSite))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelNewValuesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblSubsite)
                    .add(cboSubsite, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btnNewSubsite))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelNewValuesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblTree)
                    .add(cboTree, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btnNewTree))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelNewValuesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(tblSpecimen)
                    .add(cboSpecimen, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btnNewSpecimen))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelNewValuesLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblRadius)
                    .add(cboRadius, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(btnNewRadius))
                .addContainerGap(27, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelNewValues, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelNewValues, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cboSubsiteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSubsiteActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_cboSubsiteActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNewRadius;
    private javax.swing.JButton btnNewSite;
    private javax.swing.JButton btnNewSpecimen;
    private javax.swing.JButton btnNewSubsite;
    private javax.swing.JButton btnNewTree;
    private javax.swing.JComboBox cboRadius;
    private javax.swing.JComboBox cboSite;
    private javax.swing.JComboBox cboSpecimen;
    private javax.swing.JComboBox cboSubsite;
    private javax.swing.JComboBox cboTree;
    private javax.swing.JLabel lblRadius;
    private javax.swing.JLabel lblSite;
    private javax.swing.JLabel lblSubsite;
    private javax.swing.JLabel lblTree;
    private javax.swing.JPanel panelNewValues;
    private javax.swing.JLabel tblSpecimen;
    // End of variables declaration//GEN-END:variables
    
}
