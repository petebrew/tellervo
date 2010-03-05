package edu.cornell.dendro.corina.admin;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import edu.cornell.dendro.corina.dictionary.Dictionary;
import edu.cornell.dendro.corina.dictionary.User;
import edu.cornell.dendro.corina.schema.SecurityGroup;
import edu.cornell.dendro.corina.schema.SecurityUser;
import edu.cornell.dendro.corina.tridasv2.TridasComparator;
import edu.cornell.dendro.corina.ui.Builder;
import edu.cornell.dendro.corina.ui.I18n;

/**
 * GUI class for administering users and groups.  Allows user with the correct
 * privileges to create and edit users and groups.  Also allows editing of which
 * groups a user is in.
 *
 * @author  peterbrewer
 */
public class UserGroupAdmin extends javax.swing.JDialog implements ActionListener {
    
	private static final long serialVersionUID = -7039984838996355038L;
	private SecurityUserTableModel utm;
	
    /** Creates new form UserGroupAdmin */
    public UserGroupAdmin(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setupGui();
        internationlizeComponents();
    }
    
    @SuppressWarnings("unchecked")
	private void setupGui(){
    	// Set up basic dialog 
        setLocationRelativeTo(null);
                
        // Populate user list
        List<SecurityUser> lstofUsers = (List<SecurityUser>) Dictionary.getDictionary("securityUserDictionary");  
        
        utm = new SecurityUserTableModel(lstofUsers);
        tblUsers.setModel(utm);
        
        btnOk.addActionListener(this);
        
        setIconImage(Builder.getApplicationIcon());
    }
    
    private void internationlizeComponents()
    {
    	this.setTitle(I18n.getText("admin.usersAndGroups"));
    	chkShowDisabledUsers.setText(I18n.getText("admin.showDisabledAccounts"));
    	chkShowDisabledGroups.setText(I18n.getText("admin.showDisabledGroups"));
    	btnOk.setText(I18n.getText("general.ok"));
    	btnEditUser444.setText(I18n.getText("menus.edit"));
    	btnNewUser.setText(I18n.getText("menus.file.new"));
    	btnDeleteUser.setText(I18n.getText("general.delete"));
    	btnEditGroup.setText(I18n.getText("menus.edit"));
    	btnNewGroup.setText(I18n.getText("menus.file.new"));
    	btnDeleteGroup.setText(I18n.getText("general.delete"));
    	accountsTabPane.setTitleAt(0, I18n.getText("admin.users"));
    	accountsTabPane.setTitleAt(1, I18n.getText("admin.groups"));
    	
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        accountsTabPane = new javax.swing.JTabbedPane();
        userPanel = new javax.swing.JPanel();
        scrollUsers = new javax.swing.JScrollPane();
        tblUsers = new javax.swing.JTable();
        btnEditUser444 = new javax.swing.JButton();
        btnNewUser = new javax.swing.JButton();
        btnDeleteUser = new javax.swing.JButton();
        chkShowDisabledUsers = new javax.swing.JCheckBox();
        groupPanel = new javax.swing.JPanel();
        scrollGroups = new javax.swing.JScrollPane();
        tblGroups = new javax.swing.JTable();
        chkShowDisabledGroups = new javax.swing.JCheckBox();
        btnEditGroup = new javax.swing.JButton();
        btnNewGroup = new javax.swing.JButton();
        btnDeleteGroup = new javax.swing.JButton();
        scrollMembers = new javax.swing.JScrollPane();
        tblMembers = new javax.swing.JTable();
        lblGroupMembers = new javax.swing.JLabel();
        btnOk = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "peter", "Peter", "Brewer", "Admin, Staff", new Boolean(true)},
                {"2", "lucas", "Lucas", "Madar", "Admin, Staff", new Boolean(true)}
            },
            new String [] {
                "ID", "User", "First name", "Last name", "Groups", "Enabled"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblUsers.setShowVerticalLines(false);
        scrollUsers.setViewportView(tblUsers);

        btnEditUser444.setText("Edit");
        btnEditUser444.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditUser444ActionPerformed(evt);
            }
        });

        btnNewUser.setText("New");

        btnDeleteUser.setText("Delete");

        chkShowDisabledUsers.setSelected(true);
        chkShowDisabledUsers.setText("Show disabled accounts");
        chkShowDisabledUsers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowDisabledUsersActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout userPanelLayout = new org.jdesktop.layout.GroupLayout(userPanel);
        userPanel.setLayout(userPanelLayout);
        userPanelLayout.setHorizontalGroup(
            userPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(userPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(userPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, scrollUsers, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                    .add(userPanelLayout.createSequentialGroup()
                        .add(chkShowDisabledUsers)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 109, Short.MAX_VALUE)
                        .add(btnEditUser444)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnNewUser)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnDeleteUser)))
                .addContainerGap())
        );
        userPanelLayout.setVerticalGroup(
            userPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(userPanelLayout.createSequentialGroup()
                .add(userPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(chkShowDisabledUsers)
                    .add(btnDeleteUser)
                    .add(btnNewUser)
                    .add(btnEditUser444))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(scrollUsers, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                .addContainerGap())
        );

        accountsTabPane.addTab("Users", userPanel);

        tblGroups.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "Admin", new Boolean(true)},
                {"2", "Staff", new Boolean(true)},
                {"3", "Students", new Boolean(true)},
                {"4", "Guests", new Boolean(true)}
            },
            new String [] {
                "ID", "Group", "Enabled"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        scrollGroups.setViewportView(tblGroups);

        chkShowDisabledGroups.setSelected(true);
        chkShowDisabledGroups.setText("Show disabled groups");
        chkShowDisabledGroups.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkShowDisabledGroupsActionPerformed(evt);
            }
        });

        btnEditGroup.setText("Edit");

        btnNewGroup.setText("New");

        btnDeleteGroup.setText("Delete");

        tblMembers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "peter", "Peter", "Brewer", "Admin, Staff", new Boolean(true)},
                {"2", "lucas", "Lucas", "Madar", "Admin, Staff", new Boolean(true)}
            },
            new String [] {
                "ID", "User", "First name", "Last name", "Groups", "Enabled"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        scrollMembers.setViewportView(tblMembers);

        lblGroupMembers.setText("Group members:");

        org.jdesktop.layout.GroupLayout groupPanelLayout = new org.jdesktop.layout.GroupLayout(groupPanel);
        groupPanel.setLayout(groupPanelLayout);
        groupPanelLayout.setHorizontalGroup(
            groupPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, groupPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(groupPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, scrollMembers, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, scrollGroups, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, groupPanelLayout.createSequentialGroup()
                        .add(chkShowDisabledGroups)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 122, Short.MAX_VALUE)
                        .add(btnEditGroup)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnNewGroup)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnDeleteGroup))
                    .add(org.jdesktop.layout.GroupLayout.LEADING, lblGroupMembers))
                .addContainerGap())
        );
        groupPanelLayout.setVerticalGroup(
            groupPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(groupPanelLayout.createSequentialGroup()
                .add(groupPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(chkShowDisabledGroups)
                    .add(btnDeleteGroup)
                    .add(btnNewGroup)
                    .add(btnEditGroup))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(scrollGroups, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(lblGroupMembers)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(scrollMembers, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                .addContainerGap())
        );

        accountsTabPane.addTab("Groups", groupPanel);

        btnOk.setText("Ok");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, btnOk)
                    .add(accountsTabPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(accountsTabPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnOk)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditUser444ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditUser444ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEditUser444ActionPerformed

    private void chkShowDisabledUsersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkShowDisabledUsersActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkShowDisabledUsersActionPerformed

    private void chkShowDisabledGroupsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkShowDisabledGroupsActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkShowDisabledGroupsActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main() {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UserGroupAdmin dialog = new UserGroupAdmin(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {

                    }
                });
                dialog.setVisible(true);
            }
        });
    }
  
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JTabbedPane accountsTabPane;
    protected javax.swing.JButton btnDeleteGroup;
    protected javax.swing.JButton btnDeleteUser;
    protected javax.swing.JButton btnEditGroup;
    protected javax.swing.JButton btnEditUser444;
    protected javax.swing.JButton btnNewGroup;
    protected javax.swing.JButton btnNewUser;
    protected javax.swing.JButton btnOk;
    protected javax.swing.JCheckBox chkShowDisabledGroups;
    protected javax.swing.JCheckBox chkShowDisabledUsers;
    protected javax.swing.JPanel groupPanel;
    protected javax.swing.JLabel lblGroupMembers;
    protected javax.swing.JScrollPane scrollGroups;
    protected javax.swing.JScrollPane scrollMembers;
    protected javax.swing.JScrollPane scrollUsers;
    protected javax.swing.JTable tblGroups;
    protected javax.swing.JTable tblMembers;
    protected javax.swing.JTable tblUsers;
    protected javax.swing.JPanel userPanel;
    // End of variables declaration//GEN-END:variables

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnOk) this.dispose();
	}
    
}
