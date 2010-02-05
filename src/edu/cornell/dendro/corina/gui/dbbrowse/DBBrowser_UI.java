package edu.cornell.dendro.corina.gui.dbbrowse;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JButton;
import javax.swing.JTable;



/**
 *
 * @author  peterbrewer
 */
public class DBBrowser_UI extends javax.swing.JDialog {
	private static final long serialVersionUID = 1L;
	
	/** A return status code - returned if Cancel button has been pressed */
	public static final int RET_CANCEL = 0;
	/** A return status code - returned if OK button has been pressed */
	public static final int RET_OK = 1;
	protected int returnStatus = RET_CANCEL;
	protected javax.swing.JTable tblAvailMeas;
	protected javax.swing.JTable tblChosenMeas;
	protected JButton btnAdd;
	protected JButton btnRemove;
	
    /** Creates new form DBBrowser_UI */
    public DBBrowser_UI(javax.swing.JDialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    protected void doInitComponents(){
    	// Dynamic components
	    btnAdd = new javax.swing.JButton();
	    btnRemove = new javax.swing.JButton();
	    tblAvailMeas = new javax.swing.JTable();
	    tblChosenMeas = new javax.swing.JTable();
	    // Standard components
    	initComponents();
    	
    	// Hide ribbon 
	    panelRibbon.setVisible(false);
    }
    
	/** @return the return status of this dialog - one of RET_OK or RET_CANCEL */
	public int getReturnStatus() {
	    return returnStatus;
	}
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton7 = new javax.swing.JButton();
        panelBottomButtons = new javax.swing.JPanel();
        btnOk = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnOptions = new javax.swing.JButton();
        panelRibbon = new javax.swing.JPanel();
        cboSeriesType = new javax.swing.JComboBox();
        lblSeriesType = new javax.swing.JLabel();
        btnTogByMe = new javax.swing.JToggleButton();
        btnTogMostRecent = new javax.swing.JToggleButton();
        lblSeriesType1 = new javax.swing.JLabel();
        mainPanel = new javax.swing.JPanel();
        listTableSplit = new javax.swing.JSplitPane();
        browseSearchPane = new javax.swing.JTabbedPane();
        browsePanel = new javax.swing.JPanel();
        cboBrowseBy = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstSites = new javax.swing.JList();
        txtFilterInput = new javax.swing.JTextField();
        searchPanel = new javax.swing.JPanel();
        workArea = new javax.swing.JPanel();
        extraButtonPanel = new javax.swing.JPanel();
        btnSelectAll = new javax.swing.JButton();
        btnSelectNone = new javax.swing.JButton();
        btnInvertSelect = new javax.swing.JButton();

        jButton7.setText("jButton7");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btnOk.setText("OK");

        btnCancel.setText("Cancel");

        btnOptions.setText("Show options");

        org.jdesktop.layout.GroupLayout panelBottomButtonsLayout = new org.jdesktop.layout.GroupLayout(panelBottomButtons);
        panelBottomButtons.setLayout(panelBottomButtonsLayout);
        panelBottomButtonsLayout.setHorizontalGroup(
            panelBottomButtonsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, panelBottomButtonsLayout.createSequentialGroup()
                .add(btnOptions)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 471, Short.MAX_VALUE)
                .add(btnCancel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnOk, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 75, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelBottomButtonsLayout.setVerticalGroup(
            panelBottomButtonsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelBottomButtonsLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelBottomButtonsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnCancel)
                    .add(btnOk)
                    .add(btnOptions))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        cboSeriesType.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        cboSeriesType.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "All", "Raw only", "Derived only" }));
        cboSeriesType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSeriesTypeActionPerformed(evt);
            }
        });

        lblSeriesType.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        lblSeriesType.setText("Series types:");

        btnTogByMe.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        btnTogByMe.setText("by me");

        btnTogMostRecent.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        btnTogMostRecent.setText("Most recent versions only");
        btnTogMostRecent.setEnabled(false);
        btnTogMostRecent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTogMostRecentActionPerformed(evt);
            }
        });

        lblSeriesType1.setFont(new java.awt.Font("Lucida Grande", 0, 10));
        lblSeriesType1.setText("Authored:");

        org.jdesktop.layout.GroupLayout panelRibbonLayout = new org.jdesktop.layout.GroupLayout(panelRibbon);
        panelRibbon.setLayout(panelRibbonLayout);
        panelRibbonLayout.setHorizontalGroup(
            panelRibbonLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelRibbonLayout.createSequentialGroup()
                .addContainerGap()
                .add(btnTogMostRecent)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 406, Short.MAX_VALUE)
                .add(panelRibbonLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, lblSeriesType1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, lblSeriesType, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelRibbonLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                    .add(btnTogByMe, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                    .add(cboSeriesType, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelRibbonLayout.setVerticalGroup(
            panelRibbonLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelRibbonLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelRibbonLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(cboSeriesType, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblSeriesType)
                    .add(btnTogMostRecent))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelRibbonLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnTogByMe)
                    .add(lblSeriesType1))
                .addContainerGap())
        );

        mainPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        listTableSplit.setBorder(null);
        listTableSplit.setDividerLocation(300);
        listTableSplit.setDividerSize(11);
        listTableSplit.setOneTouchExpandable(true);

        cboBrowseBy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jScrollPane1.setViewportView(lstSites);

        org.jdesktop.layout.GroupLayout browsePanelLayout = new org.jdesktop.layout.GroupLayout(browsePanel);
        browsePanel.setLayout(browsePanelLayout);
        browsePanelLayout.setHorizontalGroup(
            browsePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, browsePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(browsePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, cboBrowseBy, 0, 267, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, txtFilterInput, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE))
                .addContainerGap())
        );
        browsePanelLayout.setVerticalGroup(
            browsePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(browsePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(cboBrowseBy, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 252, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(txtFilterInput, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        browseSearchPane.addTab("Browse", browsePanel);

        org.jdesktop.layout.GroupLayout searchPanelLayout = new org.jdesktop.layout.GroupLayout(searchPanel);
        searchPanel.setLayout(searchPanelLayout);
        searchPanelLayout.setHorizontalGroup(
            searchPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 279, Short.MAX_VALUE)
        );
        searchPanelLayout.setVerticalGroup(
            searchPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 331, Short.MAX_VALUE)
        );

        browseSearchPane.addTab("Search", searchPanel);

        listTableSplit.setLeftComponent(browseSearchPane);

        org.jdesktop.layout.GroupLayout workAreaLayout = new org.jdesktop.layout.GroupLayout(workArea);
        workArea.setLayout(workAreaLayout);
        workAreaLayout.setHorizontalGroup(
            workAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 408, Short.MAX_VALUE)
        );
        workAreaLayout.setVerticalGroup(
            workAreaLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 377, Short.MAX_VALUE)
        );

        listTableSplit.setRightComponent(workArea);

        extraButtonPanel.setMaximumSize(new java.awt.Dimension(25, 32767));
        extraButtonPanel.setMinimumSize(new java.awt.Dimension(25, 0));
        extraButtonPanel.setPreferredSize(new java.awt.Dimension(25, 0));

        btnSelectAll.setToolTipText("Select all");
        btnSelectAll.setPreferredSize(null);

        btnSelectNone.setToolTipText("Select none");

        btnInvertSelect.setToolTipText("Invert selection");

        org.jdesktop.layout.GroupLayout extraButtonPanelLayout = new org.jdesktop.layout.GroupLayout(extraButtonPanel);
        extraButtonPanel.setLayout(extraButtonPanelLayout);
        extraButtonPanelLayout.setHorizontalGroup(
            extraButtonPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(btnInvertSelect, 0, 0, Short.MAX_VALUE)
            .add(btnSelectNone, 0, 0, Short.MAX_VALUE)
            .add(btnSelectAll, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 50, Short.MAX_VALUE)
        );
        extraButtonPanelLayout.setVerticalGroup(
            extraButtonPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(extraButtonPanelLayout.createSequentialGroup()
                .add(btnSelectAll, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnSelectNone)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnInvertSelect)
                .addContainerGap(278, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(listTableSplit, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 719, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(extraButtonPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 48, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, listTableSplit, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, extraButtonPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE))
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(panelBottomButtons, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(mainPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .add(panelRibbon, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 779, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .add(panelRibbon, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(mainPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(2, 2, 2)
                .add(panelBottomButtons, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cboSeriesTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSeriesTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSeriesTypeActionPerformed

    private void btnTogMostRecentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTogMostRecentActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnTogMostRecentActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DBBrowser_UI dialog = new DBBrowser_UI(new javax.swing.JFrame(), true);
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
    protected javax.swing.JPanel browsePanel;
    protected javax.swing.JTabbedPane browseSearchPane;
    protected javax.swing.JButton btnCancel;
    protected javax.swing.JButton btnInvertSelect;
    protected javax.swing.JButton btnOk;
    protected javax.swing.JButton btnOptions;
    protected javax.swing.JButton btnSelectAll;
    protected javax.swing.JButton btnSelectNone;
    protected javax.swing.JToggleButton btnTogByMe;
    protected javax.swing.JToggleButton btnTogMostRecent;
    protected javax.swing.JComboBox cboBrowseBy;
    protected javax.swing.JComboBox cboSeriesType;
    protected javax.swing.JPanel extraButtonPanel;
    protected javax.swing.JButton jButton7;
    protected javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JLabel lblSeriesType;
    protected javax.swing.JLabel lblSeriesType1;
    protected javax.swing.JSplitPane listTableSplit;
    protected javax.swing.JList lstSites;
    protected javax.swing.JPanel mainPanel;
    protected javax.swing.JPanel panelBottomButtons;
    protected javax.swing.JPanel panelRibbon;
    protected javax.swing.JPanel searchPanel;
    protected javax.swing.JTextField txtFilterInput;
    protected javax.swing.JPanel workArea;
    // End of variables declaration//GEN-END:variables
    
	public DBBrowser_UI() throws HeadlessException {
		super();
	}

	public DBBrowser_UI(Frame owner) throws HeadlessException {
		super(owner);
	}

	public DBBrowser_UI(Dialog owner) throws HeadlessException {
		super(owner);
	}

	public DBBrowser_UI(Frame owner, boolean modal) throws HeadlessException {
		super(owner, modal);
	}

	public DBBrowser_UI(Frame owner, String title) throws HeadlessException {
		super(owner, title);
	}

	public DBBrowser_UI(Dialog owner, boolean modal) throws HeadlessException {
		super(owner, modal);
	}

	public DBBrowser_UI(Dialog owner, String title) throws HeadlessException {
		super(owner, title);
	}

	public DBBrowser_UI(Frame owner, String title, boolean modal)
			throws HeadlessException {
		super(owner, title, modal);
	}

	public DBBrowser_UI(Dialog owner, String title, boolean modal)
			throws HeadlessException {
		super(owner, title, modal);
	}

	public DBBrowser_UI(Frame owner, String title, boolean modal,
			GraphicsConfiguration gc) {
		super(owner, title, modal, gc);
	}

	public DBBrowser_UI(Dialog owner, String title, boolean modal,
			GraphicsConfiguration gc) throws HeadlessException {
		super(owner, title, modal, gc);
	}


    
}
