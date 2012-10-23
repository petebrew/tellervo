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
 ******************************************************************************/
package org.tellervo.desktop.gui;


import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;

import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.tellervo.desktop.core.Build;
import org.tellervo.desktop.ui.Builder;
import org.tellervo.desktop.ui.I18n;
import net.miginfocom.swing.MigLayout;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.border.EtchedBorder;
import javax.swing.JTabbedPane;


/*
 * AboutBox2.java
 *
 * Created on October 16, 2008, 9:50 AM
 */



/**
 *
 * @author  peterbrewer
 */
public class AboutBox extends javax.swing.JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static AboutBox instance = null;

	
    /** Creates new form AboutBox */
    public AboutBox() {  	
    	
        initComponents();
        setIconImage(Builder.getApplicationIcon());

        addDetails();
        pack();

        // can't resize -- does this get rid of minimize/maximize buttons?
        setResizable(false);
        
        // center it
        setLocationRelativeTo(null);
        
    }
    
    public static synchronized AboutBox getInstance() {
        if (instance == null) {
          instance = new AboutBox();
        } 
        return instance;
      }
    

    
    private void addDetails(){
    
    	// Set icon
	    Icon icon = Builder.getIcon("tellervo-application.png", 128);
	    if (icon != null) {
	    	  lblIcon.setIcon(icon);
	    }

        // Build strings
        String strVersion = MessageFormat.format(I18n.getText("about.version"),
                new Object[] {Build.getVersion() });
        String strTimestamp = MessageFormat.format(I18n.getText("about.timestamp"),
                new Object[] { Build.TIMESTAMP });
        String strRevision = MessageFormat.format(I18n.getText("about.revision"),
                    new Object[] { Build.REVISION_NUMBER }); 
        String strCopyright = MessageFormat.format(I18n.getText("about.copyright"),
        		new Object[] { Build.YEAR,
              Build.AUTHOR });
        
        String strDescription = I18n.getText("about.description");
        String strLicense = fileResourceToString("Licenses/license.txt");
        
        // Set text in dialog
        txtCopyright.setText(strCopyright);
        lblVersion.setText(strVersion);
        lblCompiledAt.setText(strTimestamp);   
        lblRevision.setText(strRevision);
        txtLicense.setText(strLicense);
        txtLicense.setCaretPosition(0);
        
        tabbedPane.setTitleAt(0, I18n.getText("about"));
        tabbedPane.setTitleAt(1, I18n.getText("about.license"));
        
        
    }
    
    public String fileResourceToString(String filename){
    	
        StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;
        InputStream is = getClass().getClassLoader().getResourceAsStream("Licenses/license.txt"); 
        
        try {
        	reader = new BufferedReader(new InputStreamReader(is));
        	String text = null;
        	while((text = reader.readLine()) != null)
        	{
        		contents.append(text)
        		.append(System.getProperty("line.separator"));
         	}
      	
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        return contents.toString();
    	
    }
    
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane = new javax.swing.JTabbedPane();
        tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
        panelAbout = new javax.swing.JPanel();
        panelSummary = new javax.swing.JPanel();
        lblTellervo = new javax.swing.JLabel();
        lblVersion = new javax.swing.JLabel();
        lblCompiledAt = new javax.swing.JLabel();
        txtCopyright = new javax.swing.JTextPane();
        lblIcon = new javax.swing.JLabel();
        panelLicense = new javax.swing.JPanel();
        scrollPane = new javax.swing.JScrollPane();
        txtLicense = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lblTellervo.setFont(new Font("Dialog", Font.BOLD, 20));
        lblTellervo.setText("Tellervo");

        lblVersion.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        lblVersion.setText("Version : 2.01");

        lblCompiledAt.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        lblCompiledAt.setText("Compiled at: 1231231");
        
        lblRevision = new JLabel("Revision:");
        lblRevision.setFont(new Font("Lucida Grande", Font.PLAIN, 10));

        txtCopyright.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        txtCopyright.setEditable(false);
        txtCopyright.setFont(new java.awt.Font("Lucida Grande", 0, 8));
        txtCopyright.setOpaque(false);

        tabbedPane.addTab("About", panelAbout);
        panelAbout.setLayout(new MigLayout("", "[128px][48.00px][274px]", "[25.00][128px][26.00][100px,fill]"));
        seperator = new javax.swing.JSeparator();
        
                seperator.setBackground(new java.awt.Color(255, 255, 255));
                panelAbout.add(seperator, "cell 0 0 3 1,growx,aligny bottom");
        
        separator = new JSeparator();
        separator.setBackground(Color.WHITE);
        panelAbout.add(separator, "cell 0 2 3 1,growx,aligny top");
        panelAbout.add(txtCopyright, "cell 0 3 3 1,growx,aligny top");
        panelAbout.add(lblIcon, "cell 0 1 2 1,grow");
        panelAbout.add(panelSummary, "cell 2 1,grow");
        panelSummary.setLayout(new MigLayout("", "[][85px,grow,fill]", "[][22px][13px][13px][17.00][13px][grow]"));
        panelSummary.add(lblRevision, "cell 1 3,alignx left,aligny top");
        panelSummary.add(lblCompiledAt, "cell 1 5,alignx left,aligny top");
        panelSummary.add(lblVersion, "cell 1 2,alignx left,aligny top");
        panelSummary.add(lblTellervo, "cell 1 1,alignx right,aligny top");

        scrollPane.setFont(new java.awt.Font("Courier", 0, 9));

        txtLicense.setEditable(false);
        txtLicense.setFont(new java.awt.Font("Courier", 0, 9));
        txtLicense.setDragEnabled(false);
        txtLicense.setFocusable(false);
        txtLicense.setOpaque(false);
        scrollPane.setViewportView(txtLicense);

        org.jdesktop.layout.GroupLayout gl_panelLicense = new org.jdesktop.layout.GroupLayout(panelLicense);
        panelLicense.setLayout(gl_panelLicense);
        gl_panelLicense.setHorizontalGroup(
            gl_panelLicense.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(gl_panelLicense.createSequentialGroup()
                .addContainerGap()
                .add(scrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                .addContainerGap())
        );
        gl_panelLicense.setVerticalGroup(
            gl_panelLicense.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(gl_panelLicense.createSequentialGroup()
                .addContainerGap()
                .add(scrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedPane.addTab("License", panelLicense);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabbedPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(tabbedPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 373, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AboutBox dialog = new AboutBox();
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JLabel lblCompiledAt;
    protected javax.swing.JLabel lblTellervo;
    protected javax.swing.JLabel lblIcon;
    protected javax.swing.JLabel lblVersion;
    protected javax.swing.JLabel lblRevision;
    protected javax.swing.JPanel panelAbout;
    protected javax.swing.JPanel panelLicense;
    protected javax.swing.JPanel panelSummary;
    protected javax.swing.JScrollPane scrollPane;
    protected javax.swing.JSeparator seperator;
    protected javax.swing.JTabbedPane tabbedPane;
    protected javax.swing.JTextPane txtCopyright;
    protected javax.swing.JTextPane txtLicense;
    private JSeparator separator;
}
