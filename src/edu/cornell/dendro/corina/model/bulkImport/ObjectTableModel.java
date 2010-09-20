/**
 * Created on Jul 17, 2010, 1:35:12 AM
 */
package edu.cornell.dendro.corina.model.bulkImport;

import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import com.dmurph.mvc.model.HashModel.PropertyType;
import com.dmurph.mvc.model.HashModel;
import com.dmurph.mvc.model.MVCArrayList;

import edu.cornell.dendro.corina.gis.GPXParser.GPXWaypoint;
import edu.cornell.dendro.corina.schema.WSIObjectTypeDictionary;

/**
 * @author Daniel Murphy
 *
 */
public class ObjectTableModel extends AbstractTableModel implements PropertyChangeListener, IBulkImportTableModel {
	private static final long serialVersionUID = 1L;
	
	private ObjectModel model;
	private MVCArrayList<SingleObjectModel> models;
	
	private  MVCArrayList<String> columns;
	private final HashMap<SingleObjectModel, Boolean> selected = new HashMap<SingleObjectModel, Boolean>();
	
	public ObjectTableModel(ObjectModel argModel){
		setModel(argModel);
	}
	
	public ArrayList<String> getColumns(){
		return columns;
	}
	
	@SuppressWarnings("unchecked")
	public void setModel(ObjectModel model) {
		if(this.model != null){
			models.removePropertyChangeListener(this);
			columns.removePropertyChangeListener(this);
		}
		this.model = model;
		this.models = (MVCArrayList<SingleObjectModel>) this.model.getProperty(ObjectModel.ROWS);
		this.columns = (MVCArrayList<String>) this.model.getProperty(ObjectModel.COLUMN_MODEL);
		models.addPropertyChangeListener(this);
		columns.addPropertyChangeListener(this);
		recreateSelected();
	}
	
	public void getSelected(ArrayList<SingleObjectModel> argModels){
		for(SingleObjectModel key : selected.keySet()){
			if(selected.get(key)){
				argModels.add(key);
			}
		}
	}
	
	public void setSelected(SingleObjectModel argSOM, boolean argSelected){
		if(!models.contains(argSOM)){
			throw new IllegalArgumentException("The provided model is not in this list.");
		}
		selected.put(argSOM, argSelected);
	}
	
	public void removeSelected( ArrayList<SingleObjectModel> argRemovedObjects) {
		Iterator<SingleObjectModel> it = selected.keySet().iterator();
		while(it.hasNext()){
			SingleObjectModel som = it.next();
			if(! selected.get(som)){
				continue; // if it's not selected
			}
			if(som.getImported() != null){
				int response = JOptionPane.showConfirmDialog(BulkImportModel.getInstance().getMainView(),
						"The object you are removing has been imported.  If any elements reference this object" +
						" as a parent, then they will no longer be able to be imported.  Still remove?", "Warning",
						JOptionPane.OK_CANCEL_OPTION);
				if( response != JOptionPane.OK_OPTION){
					continue;
				}
			}
			// careful, as changing the models list causes recreateSelected to be called, so we want to make sure
			// that we remove from the selected list first.
			it.remove();
			models.remove(som);
			argRemovedObjects.add(som);
		}
	}
	
	public void selectAll(){
		selected.clear();
		for(SingleObjectModel som : models){
			selected.put(som, true);
		}
		fireTableDataChanged();
	}
	
	public void selectNone(){
		selected.clear();
		fireTableDataChanged();
	}
	
	/**
	 * @see edu.cornell.dendro.corina.model.bulkImport.IBulkImportTableModel#getSelectedRows()
	 */
	@Override
	public HashModel[] getSelectedRows() {
		ArrayList<HashModel> sel = new ArrayList<HashModel>();
		for(HashModel s : selected.keySet()){
			if(selected.get(s)){
				sel.add(s);
			}
		}
		return sel.toArray(new HashModel[0]);
	}
	
	private void recreateSelected() {
		for(SingleObjectModel som : models){
			if(!selected.containsKey(som)){
				selected.put(som, true);
			}
		}
	}
	
	public ObjectModel getModel() {
		return model;
	}
	
	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		if(column == 0){
			return "Selected";
		}
		return columns.get(column-1);
	}
	
	/**
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		return columns.size()+1;
	}
	
	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if(columnIndex == 0){
			return Boolean.class;
		}
		columnIndex--;
		if(models.size() == 0){
			return String.class;
		}else{
			String column = columns.get(columnIndex);
			
			// for combo box stuff
			if(column.equals(SingleObjectModel.TYPE)){
				return WSIObjectTypeDictionary.class;
			}else if(column.equals(SingleObjectModel.IMPORTED)){
				return Boolean.class;
			}else if(column.equals(SingleObjectModel.LATITUDE)){
				return Double.class;
			}else if(column.equals(SingleObjectModel.LONGTITUDE)){
				return Double.class;
			}else if(column.equals(SingleObjectModel.WAYPOINT)){
				return GPXWaypoint.class;
			}
			
			
			SingleObjectModel som = models.get(0);
			if(som == null){
				return Object.class;
			}
			Object o = som.getProperty(column);
			if(o == null){
				return Object.class;
			}
			return o.getClass();
		}
	}
	
	/**
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return models.size();
	}
	
	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == 0){
			SingleObjectModel som = models.get(rowIndex);
			return selected.get(som);
		}
		columnIndex--;
		String column = columns.get(columnIndex);
		SingleObjectModel som = models.get(rowIndex);
		
		// make imported t/f
		if(column.equals(SingleObjectModel.IMPORTED)){
			return som.getProperty(column) != null;
		}
		return som.getProperty(column);
	}
	
	/**
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
	 */
	@Override
	public void setValueAt(Object argAValue, int argRowIndex, int argColumnIndex) {
		if(argColumnIndex == 0){
			SingleObjectModel som = models.get(argRowIndex);
			selected.put(som, (Boolean) argAValue);
			return;
		}
		argColumnIndex--;
		
		// TODO: this all should go to a command, as it's modifying the model.
		String column = columns.get(argColumnIndex);
		if(argAValue != null && argAValue.toString().equals("")){
			argAValue = null;
		}
		
		SingleObjectModel som = models.get(argRowIndex);
		
		// If it's a waypoint set the lat and long
		if(column.equals(SingleObjectModel.WAYPOINT))
		{
			GPXWaypoint wp = (GPXWaypoint) argAValue;
			som.setProperty(SingleObjectModel.LATITUDE, wp.getLatitude());
			som.setProperty(SingleObjectModel.LONGTITUDE, wp.getLongitude());
		}
		// If it's lat/long data, remove the waypoint
		if(column.equals(SingleObjectModel.LATITUDE) || column.equals(SingleObjectModel.LONGTITUDE)){
			som.setProperty(SingleObjectModel.WAYPOINT, null);
		}
				
		som.setProperty(column, argAValue);
	}
	
	/**
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if(columnIndex == 0){
			return true;
		}
		columnIndex--;
		
		String column = columns.get(columnIndex);
		SingleObjectModel som = models.get(rowIndex);
		if(som.getPropertyType(column) == PropertyType.READ_WRITE){
			return true;
		}
		return false;
	}

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		String prop = evt.getPropertyName();
		Object source = evt.getSource();
		if(source == models){
			if(prop.equals(MVCArrayList.REMOVED)){
				fireTableStructureChanged();
				recreateSelected();
			}
			else if(prop.equals(MVCArrayList.CHANGED)){
				IndexedPropertyChangeEvent event = (IndexedPropertyChangeEvent) evt;
				fireTableCellUpdated(event.getIndex(), event.getIndex());
			}
			else if(prop.equals(MVCArrayList.ADDED)){
				IndexedPropertyChangeEvent event = (IndexedPropertyChangeEvent) evt;
				fireTableRowsInserted(event.getIndex(), event.getIndex());
				recreateSelected();
			}
		}
		else if(source == columns){
			if(prop.equals(MVCArrayList.CHANGED)){
				fireTableStructureChanged();
			}
			else if(prop.equals(MVCArrayList.SIZE)){
				fireTableStructureChanged();
			}
		}
	}
	
	/**
	 * Return the count of rows that are selected
	 * 
	 * @return
	 */
	public Integer getSelectedCount()
	{
	
		Integer count = 0;
		
		Iterator<SingleObjectModel> it = selected.keySet().iterator();
		while(it.hasNext()){
			SingleObjectModel som = it.next();
			if(selected.get(som)){
				count++;
			}
		}
		
		return count;
	}
}
