package org.tellervo.desktop.odk;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;

import net.miginfocom.swing.MigLayout;

import org.codehaus.plexus.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tellervo.desktop.core.App;
import org.tellervo.desktop.odk.fields.AbstractODKChoiceField;
import org.tellervo.desktop.odk.fields.AbstractODKField;
import org.tellervo.desktop.odk.fields.ODKDataType;
import org.tellervo.desktop.odk.fields.ODKFieldInterface;
import org.tellervo.desktop.odk.fields.ODKFields;
import org.tellervo.desktop.prefs.Prefs.PrefKey;
import org.tellervo.desktop.ui.Builder;
import org.tellervo.desktop.util.ExtensionFileFilter;
import org.tridas.schema.TridasElement;
import org.tridas.schema.TridasObject;
import org.tridas.schema.TridasRadius;
import org.tridas.schema.TridasSample;

import com.jidesoft.swing.CheckBoxList;
import com.jidesoft.swing.SearchableUtils;



public class ODKFormDesignPanel extends JPanel implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JTextField txtFieldName;
	private JCheckBox chkRequired;
	private JTextArea txtDescription;
	private JList lstAvailableFields;
	private JList lstSelectedFields;
	private ODKFieldListModel availableFieldsModel;
	private ODKFieldListModel selectedFieldsModel;
	private static final Logger log = LoggerFactory.getLogger(ODKFormDesignPanel.class);
	private JComboBox cboDefault;
    private CheckBoxList cbxlstChoices;
    final private JDialog parent;
    private JScrollPane choicesScrollPane;
    private ODKFieldInterface selectedField;
    private JTextField txtFormName;
    private JTextField txtDefault;
    private DefaultComboBoxModel defModel;
    private JLabel lblOptionsToInclude;
    private JButton btnAll;
    private JButton btnNone;
    private JLabel lblDefaultValue;
    private JComboBox cboFormType;
    private JCheckBox chkHideField;
    private boolean quietFieldChangeFlag = false;
    
	/**
	 * Create the panel.
	 */
	public ODKFormDesignPanel(JDialog parent) {
		setLayout(new BorderLayout(0, 0));
		this.parent = parent;
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setOneTouchExpandable(true);
		add(splitPane);
		
		JPanel panelMain = new JPanel();
		splitPane.setLeftComponent(panelMain);
		panelMain.setLayout(new MigLayout("", "[][grow,fill]", "[][][grow]"));
		
		JLabel lblFormName = new JLabel("Form name:");
		panelMain.add(lblFormName, "cell 0 0,alignx trailing");
		
		txtFormName = new JTextField();
		txtFormName.setText("My form name");
		panelMain.add(txtFormName, "cell 1 0,growx");
		txtFormName.setColumns(10);
		
		JLabel lblFormType = new JLabel("Build form for:");
		panelMain.add(lblFormType, "cell 0 1");
		
		cboFormType = new JComboBox();
		cboFormType.setModel(new DefaultComboBoxModel(new String[] {"Objects", "Elements and samples"}));
		cboFormType.setActionCommand("FormTypeChanged");
		cboFormType.addActionListener(this);
		panelMain.add(cboFormType, "cell 1 1");
		
		JPanel panelFieldPicker = new JPanel();
		panelMain.add(panelFieldPicker, "cell 0 2 2 1,grow");
		panelFieldPicker.setLayout(new MigLayout("", "[300px,grow,fill][70px:70px:70px][300px,grow,fill]", "[][grow,center]"));
		
		JLabel lblAvailableFields = new JLabel("Available fields:");
		panelFieldPicker.add(lblAvailableFields, "cell 0 0");
		
		JLabel lblSelectedFields = new JLabel("Selected fields:");
		panelFieldPicker.add(lblSelectedFields, "cell 2 0");
		
		JScrollPane scrollPaneAvailable = new JScrollPane();
		panelFieldPicker.add(scrollPaneAvailable, "cell 0 1,grow");
		
		lstAvailableFields = new JList();
		lstAvailableFields.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		availableFieldsModel = new ODKFieldListModel(ODKFields.getFields(TridasObject.class));
		lstAvailableFields.setModel(availableFieldsModel);

		lstAvailableFields.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent evt) {
				
				if(evt.getValueIsAdjusting()) return;
				
				selectedField = (AbstractODKField) lstAvailableFields.getSelectedValue();
				setDetailsPanel();
			}
			
		});
		
		scrollPaneAvailable.setViewportView(lstAvailableFields);
		
		JPanel panelAddRemove = new JPanel();
		panelFieldPicker.add(panelAddRemove, "cell 1 1,growx,aligny center");
		panelAddRemove.setLayout(new MigLayout("", "[fill]", "[25px][][][]"));
		
		JButton btnAddAll = new JButton("");
		btnAddAll.setIcon(Builder.getIcon("2rightarrow.png", 22));
		btnAddAll.setActionCommand("AddAll");
		btnAddAll.addActionListener(this);
		panelAddRemove.add(btnAddAll, "cell 0 0,growx,aligny top");
		
		JButton btnAddOne = new JButton("");
		btnAddOne.setIcon(Builder.getIcon("1rightarrow.png", 22));
		btnAddOne.setActionCommand("AddOne");
		btnAddOne.addActionListener(this);
		panelAddRemove.add(btnAddOne, "cell 0 1");
		
		JButton btnRemoveOne = new JButton("");
		btnRemoveOne.setActionCommand("RemoveOne");
		btnRemoveOne.addActionListener(this);
		btnRemoveOne.setIcon(Builder.getIcon("1leftarrow.png", 22));
		panelAddRemove.add(btnRemoveOne, "cell 0 2");
		
		JButton btnRemoveAll = new JButton("");
		btnRemoveAll.setActionCommand("RemoveAll");
		btnRemoveAll.addActionListener(this);
		btnRemoveAll.setIcon(Builder.getIcon("2leftarrow.png", 22));
		panelAddRemove.add(btnRemoveAll, "cell 0 3");
		
		JScrollPane scrollPaneSelected = new JScrollPane();
		panelFieldPicker.add(scrollPaneSelected, "cell 2 1,grow");
		
		lstSelectedFields = new JList();
		lstSelectedFields.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		selectedFieldsModel = new ODKFieldListModel();
		lstSelectedFields.setModel(selectedFieldsModel);
		
		lstSelectedFields.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent evt) {

				if(evt.getValueIsAdjusting()) return;
				
				log.debug("Selected fields list item selected");

				selectedField = (AbstractODKField) lstSelectedFields.getSelectedValue();
				setDetailsPanel();

			}
			
		});
		scrollPaneSelected.setViewportView(lstSelectedFields);
		
		JScrollPane fieldOptionsScrollPane = new JScrollPane();
		fieldOptionsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JPanel panelFieldOptions = new JPanel();
		fieldOptionsScrollPane.setViewportView(panelFieldOptions);
		panelFieldOptions.setBorder(new TitledBorder(UIManager.getBorder("EditorPane.border"), "Field details", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		splitPane.setRightComponent(fieldOptionsScrollPane);
		panelFieldOptions.setLayout(new MigLayout("hidemode 2", "[160px:160px,right][grow][120.00][]", "[][][][59.00:59.00:59.00][center][grow]"));
		
		JLabel lblFieldNameDisplayed = new JLabel("Name:");
		panelFieldOptions.add(lblFieldNameDisplayed, "cell 0 0,alignx trailing");
		
		txtFieldName = new JTextField();
		txtFieldName.setActionCommand("FieldNameChanged");
		//txtFieldName.addActionListener(this);
		
		txtFieldName.addFocusListener(new FocusListener(){

			@Override
			public void focusGained(FocusEvent arg0) {
								
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				fieldNameChanged();
				
			}
			
		});
		
		panelFieldOptions.add(txtFieldName, "cell 1 0,growx");
		txtFieldName.setColumns(10);
		
		chkRequired = new JCheckBox("Required");
		chkRequired.setEnabled(false);
		panelFieldOptions.add(chkRequired, "cell 1 1");
		
		chkHideField = new JCheckBox("Hide field from user (default value required if ticked)");
		chkHideField.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				commitFieldChanges();
				
			}
			
		});
		panelFieldOptions.add(chkHideField, "cell 1 2 3 1");
		
		JLabel lblDescription = new JLabel("Description:");
		panelFieldOptions.add(lblDescription, "cell 0 3,alignx right,aligny top");
		
		JScrollPane scrollPane = new JScrollPane();
		panelFieldOptions.add(scrollPane, "cell 1 3 3 1,grow");
		
		txtDescription = new JTextArea();
		txtDescription.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
		txtDescription.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
		scrollPane.setViewportView(txtDescription);
		txtDescription.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent evt) {
				commitFieldChanges();
				
			}

			@Override
			public void insertUpdate(DocumentEvent evt) {
				commitFieldChanges();
				
			}

			@Override
			public void removeUpdate(DocumentEvent evt) {
				commitFieldChanges();
				
			}
			
		});
		txtDescription.setWrapStyleWord(true);
		txtDescription.setLineWrap(true);
						
		lblDefaultValue = new JLabel("Default value:");
		panelFieldOptions.add(lblDefaultValue, "cell 0 4,alignx trailing,aligny center");
		defModel = new DefaultComboBoxModel();
		
		JPanel panel_1 = new JPanel();
		panelFieldOptions.add(panel_1, "cell 1 4 3 1,grow");
		panel_1.setLayout(new MigLayout("hidemode 3, insets 0", "[grow,fill]", "[center][center]"));
		
		txtDefault = new JTextField();
		txtDefault.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent evt) {
				commitFieldChanges();
				
			}

			@Override
			public void insertUpdate(DocumentEvent evt) {
				commitFieldChanges();
				
			}

			@Override
			public void removeUpdate(DocumentEvent evt) {
				commitFieldChanges();
				
			}
			
		});
		panel_1.add(txtDefault, "cell 0 0,alignx left,aligny top");
		txtDefault.setColumns(10);
		
		cboDefault = new JComboBox();
		cboDefault.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				commitFieldChanges();
				
			}
			
		});
		panel_1.add(cboDefault, "flowy,cell 0 1");
		cboDefault.setVisible(false);
		//cboDefault.addActionListener(this);
		cboDefault.setActionCommand("DefaultChosen");
		
		lblOptionsToInclude = new JLabel("Options to include:");
		panelFieldOptions.add(lblOptionsToInclude, "cell 0 5,aligny top");
		
		choicesScrollPane = new JScrollPane();
		choicesScrollPane.setOpaque(false);
		
		cbxlstChoices = new CheckBoxList();

		choicesScrollPane.setViewportView(cbxlstChoices);
		panelFieldOptions.add(choicesScrollPane, "cell 1 5 2 1,grow");
		
		btnAll = new JButton("");
		btnAll.setIcon(Builder.getIcon("selectall.png", 16));
		btnAll.setActionCommand("SelectAllChoices");
		btnAll.addActionListener(this);
		panelFieldOptions.add(btnAll, "flowy,cell 3 5,aligny top");
		
		btnNone = new JButton("");
		btnNone.setActionCommand("SelectNoChoices");
		btnNone.addActionListener(this);
		btnNone.setIcon(Builder.getIcon("selectnone.png", 16));

		panelFieldOptions.add(btnNone, "cell 3 5");
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		add(panel, BorderLayout.SOUTH);
		
		JButton btnSave = new JButton("Generate Form");
		btnSave.setActionCommand("Save");
		btnSave.addActionListener(this);
		panel.add(btnSave);
		
		JButton btnCancel = new JButton("Close");
		btnCancel.setActionCommand("Cancel");
		btnCancel.addActionListener(this);
		panel.add(btnCancel);
		
		// Make sure all mandatory fields are selected
		this.addAllFields();
		this.removeAllNonMandatoryFields();
		setChoiceGUIVisible(false);


	}
	
	private void commitFieldChanges()
	{
		log.debug("commitFieldChanges called");
		if(selectedField==null) {
			log.debug("selectedField is null so not committing changes");
			return;
		}

		if(quietFieldChangeFlag==true) {
			log.debug("quietFieldChangeFlag is on so not committing changes");

			return;
		}
		
		
		selectedField.setDescription(this.txtDescription.getText());
		
		
		log.debug("Is txtDefault visible? "+txtDefault.isVisible());
		log.debug("Is cboDefault visible? "+cboDefault.isVisible());

		if(this.txtDefault.isVisible())
		{
			selectedField.setDefaultValue(txtDefault.getText());
		}
		else if (this.cboDefault.isVisible())
		{
			selectedField.setDefaultValue(cboDefault.getSelectedItem());
		}
		
		selectedField.setIsFieldHidden(this.chkHideField.isSelected());
		
		
	}
	
	private void setDetailsPanel()
	{
		quietFieldChangeFlag = true;

		// Handle null fields first
		if(selectedField==null)
		{
			this.txtFieldName.setText("");
			this.txtDescription.setText("");
			this.chkRequired.setSelected(false);
			this.txtDefault.setText("");
			this.chkHideField.setSelected(false);
			this.cboDefault.setSelectedIndex(-1);
			quietFieldChangeFlag = false;
			return;
		}
		
		log.debug("Field is hidden status: "+selectedField.isFieldHidden());
		
		// Handle fields that are the same regardless of data type
		this.txtFieldName.setText(selectedField.getFieldName());
		this.txtDescription.setText(selectedField.getFieldDescription());
		this.chkRequired.setSelected(selectedField.isFieldRequired());
		this.chkHideField.setSelected(selectedField.isFieldHidden());		

		if(selectedField.getFieldType().equals(ODKDataType.SELECT_ONE))
		{
			ArrayList<SelectableChoice> choices = ((AbstractODKChoiceField)selectedField).getAvailableChoices();
			this.cbxlstChoices = new CheckBoxList(choices.toArray(new SelectableChoice[choices.size()]));
			this.choicesScrollPane.setViewportView(cbxlstChoices);
			cbxlstChoices.setCheckBoxListSelectedIndices(((AbstractODKChoiceField)selectedField).getSelectedChoicesIndices());
			
			cbxlstChoices.getCheckBoxListSelectionModel().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

	        SearchableUtils.installSearchable(cbxlstChoices);
	        cbxlstChoices.getCheckBoxListSelectionModel().addListSelectionListener(new ListSelectionListener() {
	            public void valueChanged(ListSelectionEvent e) {
	                if (!e.getValueIsAdjusting()) {
	                                 	
	                	if(selectedField instanceof AbstractODKChoiceField)
	                	{
	                		((AbstractODKChoiceField)selectedField).setSelectedChoices(cbxlstChoices.getCheckBoxListSelectedValues());
	                		updateDefaultCombo();
	                	}
	                    
	                }
	            }
	        });
			
			cbxlstChoices.repaint();
			choicesScrollPane.revalidate();

			updateDefaultCombo();

			cboDefault.setVisible(true);
			lblDefaultValue.setVisible(true);
			txtDefault.setVisible(false);

			setChoiceGUIVisible(true);	
		}
		else if(selectedField.getFieldType().equals(ODKDataType.STRING))
		{
			if(selectedField.getDefaultValue()!=null)
			{
				this.txtDefault.setText(selectedField.getDefaultValue().toString());
			}
			else
			{
				this.txtDefault.setText("");
			}

			cboDefault.setVisible(false);
			txtDefault.setVisible(true);
			lblDefaultValue.setVisible(true);

			setChoiceGUIVisible(false);
		}
		else if(selectedField.getFieldType().equals(ODKDataType.IMAGE) || 
				selectedField.getFieldType().equals(ODKDataType.AUDIO) ||
				selectedField.getFieldType().equals(ODKDataType.VIDEO) ||
				selectedField.getFieldType().equals(ODKDataType.LOCATION))
		{
			
			txtDefault.setVisible(false);
			cboDefault.setVisible(false);
			lblDefaultValue.setVisible(false);
			setChoiceGUIVisible(false);
		}
		else if(selectedField.getFieldType().equals(ODKDataType.INTEGER) || 
				selectedField.getFieldType().equals(ODKDataType.DECIMAL))
		{
			txtDefault.setVisible(false);
			cboDefault.setVisible(false);
			lblDefaultValue.setVisible(false);
			setChoiceGUIVisible(false);
		}
		else
		{
			log.error("Fields of data type "+selectedField.getFieldType()+" are not yet supported");
			setChoiceGUIVisible(false);

		}
			
		quietFieldChangeFlag = false;

	}
	
	private void updateDefaultCombo()
	{
		if(selectedField.getFieldType().equals(ODKDataType.SELECT_ONE) || 
				selectedField.getFieldType().equals(ODKDataType.SELECT_MANY))
		{
			AbstractODKChoiceField field = (AbstractODKChoiceField)selectedField;
			defModel.removeAllElements();
			for(SelectableChoice choice : field.getSelectedChoices())
			{
				defModel.addElement(choice);
			}
			cboDefault.setModel(defModel);
			
			if(field.getDefaultValue()!=null)
			{
				cboDefault.setSelectedItem(field.getDefaultValue());
			}
			else
			{
				cboDefault.setSelectedIndex(-1);
			}
		}

	}
	
	/**
	 * Show or hide all the choice gui components 
	 * 
	 * @param b
	 */
	private void setChoiceGUIVisible(boolean b)
	{
		this.choicesScrollPane.setVisible(b);
		this.lblOptionsToInclude.setVisible(b);
		this.btnAll.setVisible(b);
		this.btnNone.setVisible(b);
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {

		App.init();

		JDialog dialog = new JDialog();
		ODKFormDesignPanel panel = new ODKFormDesignPanel(dialog);

		dialog.getContentPane().setLayout(new BorderLayout());
		dialog.getContentPane().add(panel, BorderLayout.CENTER);
		dialog.pack();
		dialog.setIconImage(Builder.getApplicationIcon());
		dialog.setTitle("ODK Form Builder");
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);

	}
	
	private void addAllFields()
	{
		ArrayList<ODKFieldInterface> fields =  availableFieldsModel.getAllFields();
		if(fields==null || fields.size()==0) return;
		selectedFieldsModel.addAllFields(fields);
		availableFieldsModel.removeFields(fields);
	}
	
	private void fieldNameChanged()
	{
		if(selectedField==null) return;
		selectedField.setName(this.txtFieldName.getText());
		
		for(ODKFieldInterface field : this.selectedFieldsModel.getAllFields())
		{
			if(selectedField.getFieldCode().equals(field.getFieldCode()))
			{
				field.setName(this.txtFieldName.getText());
				this.lstSelectedFields.repaint();
			}
		}
		
	}
	
	private void removeAllNonMandatoryFields()
	{
		ArrayList<ODKFieldInterface> fields =  selectedFieldsModel.getAllFields();
		ArrayList<ODKFieldInterface> fields2 =  new ArrayList<ODKFieldInterface>();

		if(fields==null || fields.size()==0) return;
		
		for(ODKFieldInterface field : fields)
		{
			if(!field.isFieldRequired())
			{
				fields2.add(field);
			}
		}
		
		availableFieldsModel.addAllFields(fields2);
		selectedFieldsModel.removeFields(fields2);
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getActionCommand().equals("AddOne"))
		{
			AbstractODKField field =  (AbstractODKField) lstAvailableFields.getSelectedValue();
			if(field==null) return;
			selectedFieldsModel.addField(field);
			availableFieldsModel.removeField(field);
		}
		else if(evt.getActionCommand().equals("AddAll"))
		{
			addAllFields();
		}
		else if(evt.getActionCommand().equals("RemoveOne"))
		{
			AbstractODKField field =  (AbstractODKField) lstSelectedFields.getSelectedValue();
			if(field==null) return;
			if(field.isFieldRequired()) return;
			availableFieldsModel.addField(field);
			selectedFieldsModel.removeField(field);
		}
		else if(evt.getActionCommand().equals("RemoveAll"))
		{
			removeAllNonMandatoryFields();
		}
		else if(evt.getActionCommand().equals("FieldNameChanged"))
		{
			fieldNameChanged();
		}
		else if(evt.getActionCommand().equals("Save"))
		{
			doSave();
			
		}
		else if(evt.getActionCommand().equals("Cancel"))
		{
			parent.dispose();
		}
		else if(evt.getActionCommand().equals("SelectAllChoices"))
		{
			cbxlstChoices.selectAll();
		}
		else if(evt.getActionCommand().equals("SelectNoChoices"))
		{
			cbxlstChoices.selectNone();

		}
		else if (evt.getActionCommand().equals("DefaultChosen"))
		{
			Object item = cboDefault.getSelectedItem();
			if(item!=null) selectedField.setDefaultValue(item);
		}
		else if (evt.getActionCommand().equals("FormTypeChanged"))
		{

			if(this.cboFormType.getSelectedIndex()==0)
			{
				log.debug("Setting gui for object form creation");
				availableFieldsModel = new ODKFieldListModel(ODKFields.getFields(TridasObject.class));
				selectedFieldsModel = new ODKFieldListModel();
				this.lstAvailableFields.setModel(availableFieldsModel);
				this.lstSelectedFields.setModel(selectedFieldsModel);
				this.addAllFields();
				this.removeAllNonMandatoryFields();
			}
			else
			{
				log.debug("Setting gui for element form creation");
				ArrayList<ODKFieldInterface> fields = ODKFields.getFields(TridasElement.class);
				fields.addAll(ODKFields.getFields(TridasSample.class));
				fields.addAll(ODKFields.getFields(TridasRadius.class));
				availableFieldsModel = new ODKFieldListModel(fields);
				selectedFieldsModel = new ODKFieldListModel();
				this.lstAvailableFields.setModel(availableFieldsModel);
				this.lstSelectedFields.setModel(selectedFieldsModel);

				this.addAllFields();
				this.removeAllNonMandatoryFields();
			}
		}
	}
	
    private void doSave()
    {
    	
    	File file = getOutputFile(new ExtensionFileFilter("Open Data Kit (ODK) form file (*.xml)", new String[] { "xml"}), this);
    	
    	if(file!=null) 
    	{
    		if(this.cboFormType.getSelectedIndex()==0)
    		{
    			// Generate Objects form
    			ODKFormGenerator.generate(file, txtFormName.getText(), this.selectedFieldsModel.getAllFields(), null);
    		}
    		else
    		{
    			// Generate Elements/Samples form			
    			ArrayList<ODKFieldInterface> mainFields = new ArrayList<ODKFieldInterface>();
    			ArrayList<ODKFieldInterface> secondaryFields = new ArrayList<ODKFieldInterface>();
    			for(ODKFieldInterface field : this.selectedFieldsModel.getAllFields())
    			{
    				if(field.getTridasClass().equals(TridasElement.class))
    				{
    					mainFields.add(field);
    				}
    				
    				if(field.getTridasClass().equals(TridasSample.class) || 
    						field.getTridasClass().equals(TridasRadius.class)) 
    				{
    					secondaryFields.add(field);
    				}

    			}
    			ODKFormGenerator.generate(file, txtFormName.getText(), mainFields, secondaryFields);

    		}
    	}
    	
    }
	
	
	/**
	 * Prompt the user for an output filename
	 * 
	 * @param filter
	 * @return
	 */
	public static File getOutputFile(FileFilter filter, Component parent)
	{
		String lastVisitedFolder = App.prefs.getPref(PrefKey.FOLDER_LAST_SAVE, null);
		File outputFile;
		
		//Create a file chooser
		final JFileChooser fc = new JFileChooser(lastVisitedFolder);
		
		
		fc.setAcceptAllFileFilterUsed(true);

		if(filter !=null)
		{
			fc.addChoosableFileFilter(filter);
			fc.setFileFilter(filter);
		}
		
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fc.setMultiSelectionEnabled(false);
		fc.setDialogTitle("Save as...");
		
		//In response to a button click:
		int returnVal = fc.showOpenDialog(parent);
		
		if(returnVal==JFileChooser.APPROVE_OPTION)
		{
			outputFile = fc.getSelectedFile();
			
			if(FileUtils.getExtension(outputFile.getAbsolutePath())== "")
			{
				log.debug("Output file extension not set by user");

				if(fc.getFileFilter().getDescription().equals(new ExtensionFileFilter("Open Data Kit (ODK) form file (*.xml)", new String[] { "xml"})))
				{
					log.debug("Adding odk extension to output file name");
					outputFile = new File(outputFile.getAbsolutePath()+".xml");
				}
				
			}
			else
			{
				log.debug("Output file extension set by user to '"+FileUtils.getExtension(outputFile.getAbsolutePath())+"'");
			}
			
			
			App.prefs.setPref(PrefKey.FOLDER_LAST_SAVE, outputFile.getAbsolutePath());
		}
		else
		{
			return null;
		}
		
		if(outputFile.exists())
		{
			Object[] options = {"Overwrite",
			        "No", "Cancel"};
			int response = JOptionPane.showOptionDialog(parent,
			"The file '"+outputFile.getName()+"' already exists.  Are you sure you want to overwrite?",
			"Confirm",
			JOptionPane.YES_NO_CANCEL_OPTION,
			JOptionPane.QUESTION_MESSAGE,
			null,     //do not use a custom Icon
			options,  //the titles of buttons
			options[0]); //default button title
	
			if(response != JOptionPane.YES_OPTION)
			{
				return null;				
			}
		}
		
		
		return outputFile;
	}

}



