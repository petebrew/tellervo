/**
 * Created on Jul 22, 2010, 2:15:56 AM
 */
package edu.cornell.dendro.corina.view.bulkImport;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.Box;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.tridas.schema.ControlledVoc;

import com.dmurph.mvc.model.MVCArrayList;

import edu.cornell.dendro.corina.components.table.ControlledVocDictionaryEditor;
import edu.cornell.dendro.corina.components.table.TridasObjectTypeEditor;
import edu.cornell.dendro.corina.control.bulkImport.*;
import edu.cornell.dendro.corina.dictionary.Dictionary;
import edu.cornell.dendro.corina.model.bulkImport.ColumnChooserModel;
import edu.cornell.dendro.corina.model.bulkImport.ObjectModel;
import edu.cornell.dendro.corina.model.bulkImport.ObjectTableModel;
import edu.cornell.dendro.corina.model.bulkImport.SingleObjectModel;
import edu.cornell.dendro.corina.schema.WSIObjectTypeDictionary;
import edu.cornell.dendro.corina.tridasv2.ui.ControlledVocRenderer;

/**
 * @author Daniel Murphy
 *
 */
public class ObjectView extends JPanel{
	
	private ObjectModel model;

	private JTable table;
	private JButton addRow;
	private JButton showHideColumns;
	private JButton removeSelected;
	private JButton selectAll;
	private JButton selectNone;
	private JButton importSelected;
	
	public ObjectView(ObjectModel argModel){
		model = argModel;
		initComponents();
		linkModel();
		addListeners();
		populateLocale();
	}

	private void initComponents(){
		table = new JTable(0,3);
		addRow = new JButton();
		showHideColumns = new JButton();
		removeSelected = new JButton();
		selectAll = new JButton();
		selectNone = new JButton();
		importSelected = new JButton();
		
		setLayout(new BorderLayout());
		
		Box box = Box.createHorizontalBox();
		box.add(addRow);
		box.add( Box.createHorizontalGlue());
		box.add(showHideColumns);
		add(box, "North");

		JScrollPane panel = new JScrollPane(table);
		panel.setPreferredSize(new Dimension(500, 400));
		table.setAutoCreateRowSorter(true);
		table.setFillsViewportHeight(true); 
		
		// editors for combo box stuff
		table.setDefaultEditor(WSIObjectTypeDictionary.class, new ControlledVocDictionaryEditor("objectTypeDictionary"));
		table.setDefaultRenderer(WSIObjectTypeDictionary.class, new ControlledVocRenderer());
		add(panel, "Center");
		
		box = Box.createHorizontalBox();
		box.add(Box.createRigidArea(new Dimension(30, 1)));
		box.add(selectAll);
		box.add(selectNone);
		box.add(Box.createHorizontalGlue());
		box.add(removeSelected);
		box.add(Box.createHorizontalGlue());
		box.add(importSelected);
		add(box, "South");
	}
	
	private void linkModel() {
		table.setModel(model.getTableModel());
	}
	
	private void addListeners() {
		showHideColumns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DisplayColumnChooserEvent event = new DisplayColumnChooserEvent(model);
				event.dispatch();
			}
		});
		
		addRow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent argE) {
				AddRowEvent event = new AddRowEvent(model);
				event.dispatch();
			}
		});
		
		importSelected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent argE) {
				ImportSelectedEvent event = new ImportSelectedEvent(BulkImportController.IMPORT_SELECTED_OBJECTS);
				event.dispatch();
			}
		});
		
		selectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent argE) {
				// skip controller
				ObjectTableModel tmodel = (ObjectTableModel) model.getProperty(ObjectModel.TABLE_MODEL);
				tmodel.selectAll();
			}
		});
		
		selectNone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent argE) {
				// skip controller
				ObjectTableModel tmodel = (ObjectTableModel) model.getProperty(ObjectModel.TABLE_MODEL);
				tmodel.selectNone();
			}
		});
		
		removeSelected.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent argE) {
				RemoveSelectedEvent event = new RemoveSelectedEvent(model);
				event.dispatch();
			}
		});
	}

	private void populateLocale() {
		addRow.setText("Add Row");
		showHideColumns.setText("Show/Hide Columns");
		removeSelected.setText("Remove Selected");
		selectAll.setText("Select All");
		selectNone.setText("Select None");
		importSelected.setText("Import Selected");
	}
}