/*******************************************************************************
 * Copyright (C) 2011 Peter Brewer.
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
 *     Peter Brewer
 *     Dan Girshovich
 ******************************************************************************/
package edu.cornell.dendro.corina.admin.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle;
import javax.swing.table.TableRowSorter;

import edu.cornell.dendro.corina.ui.Builder;
import edu.cornell.dendro.corina.ui.I18n;
import edu.cornell.dendro.corina.admin.SetPasswordUI;
import edu.cornell.dendro.corina.admin.control.CreateNewUserEvent;
import edu.cornell.dendro.corina.admin.control.UpdateUserEvent;
import edu.cornell.dendro.corina.admin.model.SecurityGroupTableModelB;
import edu.cornell.dendro.corina.admin.model.UserGroupAdminModel;
import edu.cornell.dendro.corina.schema.WSISecurityGroup;
import edu.cornell.dendro.corina.schema.WSISecurityUser;
import net.miginfocom.swing.MigLayout;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JButton;

/*
 * UserUI.java
 *
 * Created on January 8, 2010, 11:03 AM
 */

/**
 * GUI Class for viewing/editing a users details.
 * 
 * @author  peterbrewer
 */
public class UserUIView extends javax.swing.JDialog implements ActionListener, MouseListener{
    
	private static final long serialVersionUID = 1L;
	WSISecurityUser user = new WSISecurityUser();
	Boolean isNewUser;
	private UserGroupAdminModel mainModel = UserGroupAdminModel.getInstance();
	private SecurityGroupTableModelB groupsModel;
	private TableRowSorter<SecurityGroupTableModelB> groupsSorter;
	
    /** Creates new form UserUI 
     * @wbp.parser.constructor*/
    public UserUIView(JDialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
    	isNewUser = true;
        setupGUI();
        internationlizeComponents();
    }
    
    public UserUIView(JDialog parent, boolean modal, WSISecurityUser user) {
        super(parent, modal);
        this.user = user;
        initComponents();
    	isNewUser = false;
    	setupGUI();
    	internationlizeComponents();
    }
    
    private void internationlizeComponents() {
        lblUser.setText(I18n.getText("login.username")+":");
        lblName.setText(I18n.getText("admin.realName")+":");
        chkEnabled.setText(I18n.getText("general.enabled"));
        btnClose.setText(I18n.getText("general.close"));
        btnSetPwd.setText(I18n.getText("admin.setPassword"));
        lblPassword.setText(I18n.getText("login.password")+":");
        lblPassword2.setText(I18n.getText("admin.confirmPassword")+":");
	}

	/** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    @SuppressWarnings("serial")
	private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        btnDoIt = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        jLabel3.setText("jLabel3");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblUser.setText("User name:");

        lblName.setText("Real name:");

        btnDoIt.setText("Apply");
        
        btnDoIt.setActionCommand("doit");
        getContentPane().setLayout(new MigLayout("hidemode 3", "[81px][140px,grow][107px,grow][73px]", "[19px][19px][][100px,grow][0px:n,shrink 0][0px:n,shrink 0][25px]"));
        txtFirstname = new javax.swing.JTextField();
        getContentPane().add(txtFirstname, "cell 1 0,growx");
        
        lblIcon = new JLabel("");
        lblIcon.setIcon(Builder.getIcon("edit_user.png", 64));
        lblIcon.setSize(70, 70);
        
        getContentPane().add(lblIcon, "cell 3 0 1 3");
        txtLastname = new javax.swing.JTextField();
        getContentPane().add(txtLastname, "cell 2 0,growx");
        txtUsername = new javax.swing.JTextField();
        getContentPane().add(txtUsername, "cell 1 1 2 1,growx");
        chkEnabled = new javax.swing.JCheckBox();
        
                chkEnabled.setText("Account enabled");
                getContentPane().add(chkEnabled, "cell 1 2,alignx left,aligny top");
        btnSetPwd = new javax.swing.JButton();
        btnSetPwd.setIcon(Builder.getIcon("password.png", 16));
        
        btnSetPwd.setText("Reset Password");
        getContentPane().add(btnSetPwd, "cell 2 2,alignx right,aligny top");
        
        lblMemberOf = new JLabel("Member of:");
        getContentPane().add(lblMemberOf, "cell 0 3,alignx right,aligny top");
        scrollPane = new javax.swing.JScrollPane();
        scrollPane.setBackground(Color.WHITE);
        getContentPane().add(scrollPane, "cell 1 3 3 1,grow");
        tblGroups = new javax.swing.JTable();
        tblGroups.setBackground(Color.WHITE);
        
                tblGroups.setModel(new javax.swing.table.DefaultTableModel(
                    new Object [][] {
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                    },
                    new String [] {
                        "ID", "Group", "Description", "Member"
                    }
                ) {
                    @SuppressWarnings("unchecked")
        			Class[] types = new Class [] {
                        java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
                    };
                    boolean[] canEdit = new boolean [] {
                        false, false, false, true
                    };
        
                    @SuppressWarnings("unchecked")
        			public Class getColumnClass(int columnIndex) {
                        return types [columnIndex];
                    }
        
                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit [columnIndex];
                    }
                });
                scrollPane.setViewportView(tblGroups);
        lblPassword = new javax.swing.JLabel();
        
                lblPassword.setText("Password:");
                getContentPane().add(lblPassword, "hidemode 2,cell 0 4,alignx right,aligny center");
        txtPassword = new javax.swing.JPasswordField();
        getContentPane().add(txtPassword, "cell 1 4 3 1,growx");
        lblPassword2 = new javax.swing.JLabel();
        
                lblPassword2.setText("Confirm:");
                getContentPane().add(lblPassword2, "hidemode 2,cell 0 5,alignx right,aligny center");
        txtPassword2 = new javax.swing.JPasswordField();
        getContentPane().add(txtPassword2, "cell 1 5 3 1,growx");
        txtId = new javax.swing.JTextField();
        
                txtId.setEditable(false);
                getContentPane().add(txtId, "cell 0 6,growx,aligny top");
        getContentPane().add(btnDoIt, "flowx,cell 2 6 2 1,alignx right,aligny top");
        getContentPane().add(lblUser, "cell 0 1,alignx right,aligny center");
        getContentPane().add(lblName, "cell 0 0,alignx right,aligny center");
        
        btnClose = new JButton("Close");
        getContentPane().add(btnClose, "cell 2 6");

        pack();
    }// </editor-fold>//GEN-END:initComponents
    protected javax.swing.JButton btnDoIt;
    protected javax.swing.JButton btnSetPwd;
    protected javax.swing.JCheckBox chkEnabled;
    protected javax.swing.JLabel jLabel1;
    protected javax.swing.JLabel jLabel3;
    protected javax.swing.JLabel lblName;
    protected javax.swing.JLabel lblPassword;
    protected javax.swing.JLabel lblPassword2;
    protected javax.swing.JLabel lblUser;
    protected javax.swing.JScrollPane scrollPane;
    protected javax.swing.JTable tblGroups;
    protected javax.swing.JTextField txtFirstname;
    protected javax.swing.JTextField txtId;
    protected javax.swing.JTextField txtLastname;
    protected javax.swing.JPasswordField txtPassword;
    protected javax.swing.JPasswordField txtPassword2;
    protected javax.swing.JTextField txtUsername;
    private JLabel lblIcon;
    private JLabel lblMemberOf;
    private JButton btnClose;
    // End of variables declaration//GEN-END:variables
 
	private void setupGUI()
    {
    	this.setLocationRelativeTo(getRootPane());
    	this.txtId.setVisible(false);
    	    	
    	this.tblGroups.addMouseListener(this);

    	if(isNewUser)
    	{
        	this.setTitle("Create user");
        	btnDoIt.setText("Create");
        	btnSetPwd.setVisible(false);   	
        	chkEnabled.setSelected(true);
    	}
    	else
    	{
        	this.setTitle("Edit user");
        	btnDoIt.setText("Apply");
        	lblPassword.setVisible(false);
        	txtPassword.setVisible(false);
        	lblPassword2.setVisible(false);
        	txtPassword2.setVisible(false);
	    	if(user.isSetFirstName()) txtFirstname.setText(user.getFirstName());
	    	if(user.isSetLastName())  txtLastname.setText(user.getLastName());
	    	if(user.isSetId()) 		  txtId.setText(user.getId());
	    	if(user.isSetUsername())  txtUsername.setText(user.getUsername());
	    	if(user.isSetIsActive())  chkEnabled.setSelected(user.isIsActive());
    	}
    	
        // Populate groups list
        groupsModel = new SecurityGroupTableModelB(user);
        tblGroups.setModel(groupsModel);
        groupsSorter = new TableRowSorter<SecurityGroupTableModelB>(groupsModel);
        tblGroups.setRowSorter(groupsSorter);
        tblGroups.setEditingColumn(4);
    	
    	this.btnDoIt.addActionListener(this);
    	this.btnSetPwd.addActionListener(this);
    	
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getActionCommand().equals("doit"))
		{
			saveChangesToUser();
		}
		
	}
	
	private void saveChangesToUser()
	{
		user.setFirstName(txtFirstname.getText());
		user.setLastName(txtLastname.getText());
		user.setUsername(txtUsername.getText());
		user.setIsActive(this.chkEnabled.isSelected());
		
		ArrayList<WSISecurityGroup> newMemList = this.groupsModel.getNewGroupMembership();
		for(WSISecurityGroup g:newMemList){
			if(!user.getMemberOves().contains(g.getId())){
				user.getMemberOves().add(g.getId());
			}
		}
		
		if(isNewUser)
		{
		
			// Creating new user
	    	
			// Check passwords match
			String p1 = new String(this.txtPassword.getPassword());
			String p2 = new String(this.txtPassword2.getPassword());
			if(!p1.equals(p2))
			{
				JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			p1 = null;
			p2 = null;
			
			//TODO: check to be sure handling passwords like this is ok.
			new CreateNewUserEvent(user, new String(this.txtPassword.getPassword()), this).dispatch();
		}
		else 
		{
			//get original group membership list
			ArrayList<WSISecurityGroup> oldMemList = this.groupsModel.getOrigGroupMembership();
			// Editing existing user
			new UpdateUserEvent(user, oldMemList, newMemList, this).dispatch();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount()>1)
		{
			// Double clicked on groups table - change users membership accordingly
			Boolean isMember = (Boolean) this.groupsModel.getValueAt(this.tblGroups.getSelectedRow(), 4);
			
			if(isMember)
			{
				groupsModel.setMembershipAt(this.tblGroups.getSelectedRow(), false);
			}
			else
			{
				groupsModel.setMembershipAt(this.tblGroups.getSelectedRow(), true);

			}
			this.tblGroups.repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
    
}