package edu.cornell.dendro.corina.io;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.apache.commons.lang.WordUtils;

import com.l2fprod.common.swing.ComponentFactory;

import edu.cornell.dendro.corina.core.App;
import edu.cornell.dendro.corina.formats.Filetype;
import edu.cornell.dendro.corina.formats.PackedFileType;
import edu.cornell.dendro.corina.gui.Bug;
import edu.cornell.dendro.corina.gui.FileDialog;
import edu.cornell.dendro.corina.gui.Help;
import edu.cornell.dendro.corina.gui.FileDialog.ExtensionFilter;
import edu.cornell.dendro.corina.io.Exporter.EncodingType;
import edu.cornell.dendro.corina.sample.Element;
import edu.cornell.dendro.corina.sample.ElementList;
import edu.cornell.dendro.corina.sample.Sample;
import edu.cornell.dendro.corina.ui.Alert;
import edu.cornell.dendro.corina.ui.Builder;
import edu.cornell.dendro.corina.ui.I18n;
import edu.cornell.dendro.corina.util.ArrayListModel;
import edu.cornell.dendro.corina.util.PureStringWriter;
import edu.cornell.dendro.corina.util.TextClipboard;
import edu.cornell.dendro.corina.util.openrecent.OpenRecent;
import edu.cornell.dendro.corina.util.openrecent.SeriesDescriptor;

/*
 * ExportUI.java
 *
 * Created on September 7, 2009, 12:27 PM
 */

/**
 *
 * @author  peterbrewer
 */
public class ExportUI extends javax.swing.JPanel{
    	
	private static final String EXPORTERS[] = new String[] {
		"edu.cornell.dendro.corina.formats.Corina", 
		"edu.cornell.dendro.corina.formats.Heidelberg",
		"edu.cornell.dendro.corina.formats.Hohenheim",
		"edu.cornell.dendro.corina.formats.RangesOnly",
		"edu.cornell.dendro.corina.formats.Sheffield",
		"edu.cornell.dendro.corina.formats.Spreadsheet",
		"edu.cornell.dendro.corina.formats.Tucson", 
		"edu.cornell.dendro.corina.formats.PackedTucson", 
		"edu.cornell.dendro.corina.formats.TucsonSimple",
		"edu.cornell.dendro.corina.formats.TSAPMatrix"
		 };
	
	private StringWriter writer = new PureStringWriter(10240); // 10K	
	private ElementList elements;
	private String exportDirectory;
	private boolean rememberExportDirectory;
	private Boolean exportMulti = true;
	protected DefaultComboBoxModel howModel = new DefaultComboBoxModel();
	protected DefaultComboBoxModel whatModel = new DefaultComboBoxModel();
	protected ArrayListModel<Filetype> formatModel = new ArrayListModel<Filetype>();
	protected ArrayListModel<EncodingType> encodingModel = new ArrayListModel<EncodingType>();
	protected ExportDialog parent;
	final JFileChooser fc = new JFileChooser();
	
    /** Creates new form ExportUI */
    public ExportUI(ExportDialog p, ElementList els) {
    	parent = p;
    	elements = els;
    	
		rememberExportDirectory = true;

		// load the last export directory. If it doesn't exist, make a nice default.
		exportDirectory = App.prefs.getPref("corina.dir.export");
		if(exportDirectory == null)
			exportDirectory = App.prefs.getPref("corina.dir.data");
		if(exportDirectory == null)
			exportDirectory = "";
		
		// now, keep going back until it exists and is a directory.
		File exdf = new File(exportDirectory).getAbsoluteFile();
		
		while(!exdf.isDirectory() && exdf.toString().length() > 0)
			exdf = exdf.getParentFile();
		
		exportDirectory = exdf.getAbsolutePath();
    	
    	
        initComponents();
        setupGui();
        internationalizeComponents();
    }
    
    private void internationalizeComponents()    
    {
    	lblWhat.setText(I18n.getText("export.what")+":");
    	lblHow.setText(I18n.getText("export.format")+":");
    	lblEncoding.setText(I18n.getText("export.encoding")+":");
    	lblOutput.setText(I18n.getText("export.outputFolder")+":");
    	btnHelp.setText(I18n.getText("menus.help"));
    	btnBrowse.setText(I18n.getText("general.browse"));
    	btnCancel.setText(I18n.getText("general.cancel"));
    	btnOK.setText(I18n.getText("general.ok"));
    }
    
    private void setupGui()
    {
    	// Hide preview button - not sure we want it any more
    	btnPreview.setVisible(false);
    	
        // Set icon        
        lblIcon.setIcon(Builder.getIcon("fileexport.png", 128));
    	
    	// Disable ok button until file/folder has been selected
    	btnOK.setEnabled(false);
        
		Help.assignHelpPageToButton(btnHelp, "File_formats");
    	
    	// Hide how form items
    	lblHow.setVisible(false);
    	cboHow.setVisible(false);

      	// setup combo boxes
      	cboWhat.setModel(whatModel);
      	setWhatModel();
      	cboExportFormat.setModel(formatModel);
      	setupFormatList();
      	cboEncoding.setModel(encodingModel);
      	setupEncodingList();
      	cboEncoding.setSelectedIndex(0);
      	
    	// Add action listeners
		btnOK.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				doExport();
			}
		});		
		btnCancel.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				// just close
				parent.dispose();
			}
		});
		btnPreview.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				// Hide/Show preview panel
				if(panelPreview.isVisible())
					panelPreview.setVisible(false);
				else
					panelPreview.setVisible(true);
				parent.pack();
				updatePreview();
			}
		});
		btnBrowse.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				
				// Are we exporting multiple files... 
				if (exportMulti)
				{
					// ... if so choose directory not file
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);	
					fc.setDialogTitle(I18n.getText("export.selectAFolder"));
				}
				else
				{
					// ... otherwise set the file filter 
					Filetype ft =  (Filetype) formatModel.getSelectedItem();
					ExtensionFilter filter = new FileDialog.ExtensionFilter(ft.getDefaultExtension().substring(1), ft.toString());
					fc.setFileFilter(filter);
				}
			
				// Open at last used directory
				if(exportDirectory!=null)
				{
					fc.setCurrentDirectory(new File(exportDirectory));					
				}

				// Show dialog
				int returnVal;
				returnVal = fc.showSaveDialog(parent);
		
				// Set file/folder in text box
		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = fc.getSelectedFile();
		            txtOutput.setText(file.getAbsoluteFile().toString());

		        } else {
		            System.out.println("Save command cancelled by user.");
		        }
		        
		        // If file/folder selected enable OK button
				if(txtOutput.getText()==null || txtOutput.getText().equals(""))
				{
					btnOK.setEnabled(false);
				}
				else
				{
					btnOK.setEnabled(true);
				}
		        
			}
		});
		
		txtOutput.addActionListener(new AbstractAction(){
			public void actionPerformed(ActionEvent e){
				if(txtOutput.getText()==null || txtOutput.getText().equals(""))
				{
					btnOK.setEnabled(false);
				}
				else
				{
					btnOK.setEnabled(true);
				}
			}
		});
		
		
		cboExportFormat.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				
				Filetype ft =  (Filetype) formatModel.getSelectedItem();	
				if(ft.isPackedFileCapable()){				
					exportMulti=false;
				 	setGuiForPacked(true);
				} else {
					exportMulti=true;
					setGuiForPacked(false);
				}
				updatePreview();
			}
		});
        
		// Set up preview pane
        Font oldFont = txtPreview.getFont();
		txtPreview.setFont(new Font("monospaced", oldFont.getStyle(), oldFont
				.getSize()));
		txtPreview.setEditable(false);
		btnCopy.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				TextClipboard.copy(txtPreview.getText());
			}
		});

		// initial view
		updatePreview();
    }

    /**
     * Set the comboboxmodel for the 'what' combo box.  
     * @param plural if there are more than one series being converted
     */
    public void setWhatModel()
    {
    	if(elements.size()>1)
    	{
        	String whatOptionsPl[] = new String[] {
        		"The selected " + Integer.toString(elements.size()) + " series",
        		//"These series and their associated raw measurement series", 
        		//"These series and all associated series", 
        		};
			int n = whatOptionsPl.length;
			for (int i = 0; i < n; i++) 
			{
				whatModel.addElement(whatOptionsPl[i]);
			}
    	}
    	else
    	{
	    	String whatOptions[] = new String[] {
				I18n.getText("export.justThisSeries"),
				//I18n.getText("export.thisSeriesAndRaws"),
				//I18n.getText("export.thisSeriesAndAll"),
				};
			int n = whatOptions.length;
			for (int i = 0; i < n; i++) 
			{
				whatModel.addElement(whatOptions[i]);
			}  
    	}
	
    }
     
    /**
     * Set up the aspects of the gui that change
     * depending on whether the output is packed 
     * into a single file or not.
     * 
     * @param packed
     */
    public void setGuiForPacked(Boolean packed)
    {
    	if(packed)
    	{
    		lblOutput.setText(I18n.getText("export.outputFile"));
    		txtOutput.setText("");
    	}
    	else
    	{
    		lblOutput.setText(I18n.getText("export.outputFolder"));
    		txtOutput.setText("");
    	}
    }
    
    
    public void setupEncodingList()
    {  	
    	for (EncodingType enc : EncodingType.values())
    	{
    		encodingModel.add(enc);
    	}
    }
    
    /**
     * Set up the combo box containing the output file formats
     */
    public void setupFormatList()
    {
    	
    	if (formatModel.size()>0) formatModel.clear();
    	int n = EXPORTERS.length;
    	for (int i = 0; i < n; i++)
    	{
    		try {
				Filetype f = (Filetype) Class.forName(EXPORTERS[i])
						.newInstance();
				formatModel.add(f);
			} catch (Exception e) {
				Bug.bug(e);
			}
    	}

		cboExportFormat.setSelectedIndex(0);
		

    }
    

    /**
     * Actually do the export
     */
    private void doExport(){
		
    	Filetype ft =  (Filetype) formatModel.getSelectedItem();
    	String message = null;
    	Integer wraplength = 65;
    	
    	// Caution user if using a lossy file format
    	if(!(ft.isLossless()))
    	{
       		message = WordUtils.wrap("Caution: " + ft.getDeficiencyDescription(), wraplength);
    		message+= "\n\n";
    		
    	}
    	
		// Warn about inferior character encodings if applicable
		EncodingType selEnc = (EncodingType) cboEncoding.getSelectedItem();
		if(!selEnc.equals(EncodingType.UTF8) && (!selEnc.equals(EncodingType.UTF16)))
		{
			String also = " ";
			if (message!=null) also = " also ";
			message+= WordUtils.wrap("The character encoding that you have chosen is"+also+"incapable of " +
					"representing all the international characters that the Corina database is capable " +
					"of using.  The resulting output file may therefore contain incorrect glyphs.", wraplength);
			message+= "\n\n";
		}
		
		// Do we need to show the user the warnings?
		if (message!=null)
		{
    		message+= WordUtils.wrap("Please be aware that the exported files will therefore " +
    				"not be a complete representation of the data currently " +
    				"stored in the Corina database.", wraplength);
    		message+= "\n\n";
    		message+= WordUtils.wrap("Would you like to continue with the export?", wraplength); 
    				
    		Object[] options = {"Yes", "No"};
    		int n = JOptionPane.showOptionDialog(parent, 
    				message, "File format caution", 
    				JOptionPane.YES_OPTION,
    				JOptionPane.QUESTION_MESSAGE, 
    				null, 
    				options, 
    				options[0]);
    		if (n==JOptionPane.YES_OPTION){ } else { return; }
		}
    	
    	
    	// Actually do export
    	try {
			Boolean success;
								
			File outfile = new File(txtOutput.getText());
			Exporter exp = new Exporter();
			exp.setEncodingType((EncodingType) cboEncoding.getSelectedItem());
			
			if(ft.isPackedFileCapable() && elements.size()>1)
			{
				// Do 'packed' save
				exp.savePackedSample2(elements, ft, outfile);				
			}
			else
			{
				// Do standard save	
				exp.saveMultiSample2(elements, ft, outfile);
			}
			
			if(success=false)
				Alert.message("Export failed", "Something went wrong with the export");
			
			// close dialog
			parent.dispose();

		} catch (Exception ex) {
			Bug.bug(ex);
		}
    }
    
    
	// FIXME: sometimes the preview takes a while to create.  for example,
	// a spreadsheet view of a 100-element master needs to load all 100
	// elements.  yeowch.  i'll need to run this in a separate thread for that!
	private void updatePreview() {
		
		// Don't run if the preview panel is hidden
		if(panelPreview.isVisible()!=true)	return;
		
		// Kludge to intercept when the combo and exporters list is out of sync
		if (cboExportFormat.getModel().getSize()!=EXPORTERS.length) return;
		
		int i = cboExportFormat.getSelectedIndex();

		// Don't run if an export format hasn't been selected
		if(i<0) return;
	
		/*
		try {
			// save to buffer
			setCursor(new Cursor(Cursor.WAIT_CURSOR)); // this could take a second...
			
			// If exporters list is smaller than the combo box - quit.
			if(EXPORTERS.length<i) return;
			
			Filetype f =  (Filetype) formatModel.getSelectedItem();	
			StringBuffer buf = writer.getBuffer();
			buf.delete(0, buf.length()); // clear it
			BufferedWriter b = new BufferedWriter(writer);
			
			// if the sample list has multi samples in it and the file type is 
			// capable of writing multiple files we want a packed file. 
			if (f.isMultiFileCapable() && elements.size()>1)
			{
				System.out.println("Saving packed samples as " + f.toString() + "format");
				((PackedFileType)f).saveSamples(sampleList, b);
				lblPreview.setText("Preview:");
			}
			else if (elements != null)
			{	
				f.save(sampleList.get(0), b);
				lblPreview.setText("Previewing file 1 of "+ Integer.toString(sampleList.size()) ); 
			}
			else
			{
				return;
			}
			b.close();
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR)); // ok, done with the slow part

			// update views
			txtPreview.setText(buf.toString());

			// move cursor to start -- this scrolls to the top, as well
			txtPreview.setCaretPosition(0);
		} catch (IOException ioe) {
			// problem saving it -- bug
			Bug.bug(ioe);
		} catch (Exception e) {
			// problem creating the filetype -- bug
			Bug.bug(e);
		}
		*/
	}
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelOptions = new javax.swing.JPanel();
        lblExportFormat = new javax.swing.JLabel();
        txtOutput = new javax.swing.JTextField();
        btnBrowse = new javax.swing.JButton();
        cboExportFormat = new javax.swing.JComboBox();
        lblOutput = new javax.swing.JLabel();
        cboWhat = new javax.swing.JComboBox();
        lblWhat = new javax.swing.JLabel();
        cboHow = new javax.swing.JComboBox();
        lblHow = new javax.swing.JLabel();
        panelSpacer = new javax.swing.JPanel();
        lblEncoding = new javax.swing.JLabel();
        cboEncoding = new javax.swing.JComboBox();
        panelPreview = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtPreview = new javax.swing.JTextPane();
        lblPreview = new javax.swing.JLabel();
        btnCopy = new javax.swing.JButton();
        panelBottom = new javax.swing.JPanel();
        btnPreview = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        btnOK = new javax.swing.JButton();
        btnHelp = new javax.swing.JButton();
        panelIcon = new javax.swing.JPanel();
        lblIcon = new javax.swing.JLabel();
        panelPadding = new javax.swing.JPanel();
        separator = new javax.swing.JSeparator();

        setMinimumSize(new java.awt.Dimension(400, 200));

        lblExportFormat.setText("Export format:");

        btnBrowse.setText("Browse");

        lblOutput.setText("Output folder:");

        lblWhat.setText("What to export:");

        lblHow.setText("Grouping:");

        panelSpacer.setPreferredSize(new java.awt.Dimension(100, 0));

        org.jdesktop.layout.GroupLayout panelSpacerLayout = new org.jdesktop.layout.GroupLayout(panelSpacer);
        panelSpacer.setLayout(panelSpacerLayout);
        panelSpacerLayout.setHorizontalGroup(
            panelSpacerLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 318, Short.MAX_VALUE)
        );
        panelSpacerLayout.setVerticalGroup(
            panelSpacerLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 0, Short.MAX_VALUE)
        );

        lblEncoding.setText("Encoding:");

        cboEncoding.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Automatic", "UTF-8", "UTF-16", "Latin 1", "Mac Roman" }));

        org.jdesktop.layout.GroupLayout panelOptionsLayout = new org.jdesktop.layout.GroupLayout(panelOptions);
        panelOptions.setLayout(panelOptionsLayout);
        panelOptionsLayout.setHorizontalGroup(
            panelOptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelOptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(panelSpacer, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                    .add(panelOptionsLayout.createSequentialGroup()
                        .add(panelOptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(panelOptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                                .add(lblOutput, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(lblExportFormat, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(lblHow, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(lblWhat, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .add(lblEncoding))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(panelOptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, panelOptionsLayout.createSequentialGroup()
                                .add(txtOutput, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(btnBrowse))
                            .add(cboExportFormat, 0, 215, Short.MAX_VALUE)
                            .add(cboHow, 0, 215, Short.MAX_VALUE)
                            .add(cboWhat, 0, 215, Short.MAX_VALUE)
                            .add(cboEncoding, 0, 215, Short.MAX_VALUE)))))
        );
        panelOptionsLayout.setVerticalGroup(
            panelOptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelOptionsLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelOptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblWhat)
                    .add(cboWhat, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelOptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblHow)
                    .add(cboHow, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelOptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblExportFormat)
                    .add(cboExportFormat, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelOptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblEncoding)
                    .add(cboEncoding, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelOptionsLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblOutput)
                    .add(btnBrowse)
                    .add(txtOutput, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelSpacer, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jScrollPane1.setViewportView(txtPreview);

        lblPreview.setText("Preview:");

        btnCopy.setText("Copy");

        org.jdesktop.layout.GroupLayout panelPreviewLayout = new org.jdesktop.layout.GroupLayout(panelPreview);
        panelPreview.setLayout(panelPreviewLayout);
        panelPreviewLayout.setHorizontalGroup(
            panelPreviewLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelPreviewLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelPreviewLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, panelPreviewLayout.createSequentialGroup()
                        .add(lblPreview)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 343, Short.MAX_VALUE)
                        .add(btnCopy)))
                .addContainerGap())
        );
        panelPreviewLayout.setVerticalGroup(
            panelPreviewLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelPreviewLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelPreviewLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(lblPreview)
                    .add(btnCopy))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 29, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnPreview.setText("Preview");

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnOK.setText("OK");

        btnHelp.setText("Help");

        org.jdesktop.layout.GroupLayout panelBottomLayout = new org.jdesktop.layout.GroupLayout(panelBottom);
        panelBottom.setLayout(panelBottomLayout);
        panelBottomLayout.setHorizontalGroup(
            panelBottomLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelBottomLayout.createSequentialGroup()
                .addContainerGap()
                .add(btnHelp)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnPreview)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 143, Short.MAX_VALUE)
                .add(btnCancel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(btnOK)
                .addContainerGap())
        );
        panelBottomLayout.setVerticalGroup(
            panelBottomLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelBottomLayout.createSequentialGroup()
                .addContainerGap()
                .add(panelBottomLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnOK)
                    .add(btnCancel)
                    .add(btnHelp)
                    .add(btnPreview))
                .addContainerGap())
        );

        lblIcon.setMaximumSize(new java.awt.Dimension(128, 128));
        lblIcon.setMinimumSize(new java.awt.Dimension(128, 128));

        org.jdesktop.layout.GroupLayout panelIconLayout = new org.jdesktop.layout.GroupLayout(panelIcon);
        panelIcon.setLayout(panelIconLayout);
        panelIconLayout.setHorizontalGroup(
            panelIconLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelIconLayout.createSequentialGroup()
                .addContainerGap()
                .add(lblIcon, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 128, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelIconLayout.setVerticalGroup(
            panelIconLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(panelIconLayout.createSequentialGroup()
                .addContainerGap()
                .add(lblIcon, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 128, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout panelPaddingLayout = new org.jdesktop.layout.GroupLayout(panelPadding);
        panelPadding.setLayout(panelPaddingLayout);
        panelPaddingLayout.setHorizontalGroup(
            panelPaddingLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 482, Short.MAX_VALUE)
        );
        panelPaddingLayout.setVerticalGroup(
            panelPaddingLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 13, Short.MAX_VALUE)
        );

        separator.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        separator.setMaximumSize(new java.awt.Dimension(32767, 2));
        separator.setMinimumSize(new java.awt.Dimension(0, 2));
        separator.setPreferredSize(new java.awt.Dimension(50, 2));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(panelIcon, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(18, 18, 18)
                        .add(panelOptions, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .add(panelPreview, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(panelPadding, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(separator, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE))
                .addContainerGap())
            .add(org.jdesktop.layout.GroupLayout.TRAILING, panelBottom, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(6, 6, 6)
                        .add(panelIcon, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(panelOptions, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 168, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelPreview, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(panelPadding, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(separator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(12, 12, 12)
                .add(panelBottom, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelActionPerformed
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JButton btnBrowse;
    protected javax.swing.JButton btnCancel;
    protected javax.swing.JButton btnCopy;
    protected javax.swing.JButton btnHelp;
    protected javax.swing.JButton btnOK;
    protected javax.swing.JButton btnPreview;
    protected javax.swing.JComboBox cboEncoding;
    protected javax.swing.JComboBox cboExportFormat;
    protected javax.swing.JComboBox cboHow;
    protected javax.swing.JComboBox cboWhat;
    protected javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JLabel lblEncoding;
    protected javax.swing.JLabel lblExportFormat;
    protected javax.swing.JLabel lblHow;
    protected javax.swing.JLabel lblIcon;
    protected javax.swing.JLabel lblOutput;
    protected javax.swing.JLabel lblPreview;
    protected javax.swing.JLabel lblWhat;
    protected javax.swing.JPanel panelBottom;
    protected javax.swing.JPanel panelIcon;
    protected javax.swing.JPanel panelOptions;
    protected javax.swing.JPanel panelPadding;
    protected javax.swing.JPanel panelPreview;
    protected javax.swing.JPanel panelSpacer;
    protected javax.swing.JSeparator separator;
    protected javax.swing.JTextField txtOutput;
    protected javax.swing.JTextPane txtPreview;
    // End of variables declaration//GEN-END:variables
    
}