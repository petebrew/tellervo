package org.tellervo.desktop.odk;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.AbstractListModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tellervo.desktop.odk.fields.AbstractODKField;
import org.tellervo.desktop.odk.fields.ODKFieldInterface;

public class ODKFieldListModel extends AbstractListModel {

	private static final long serialVersionUID = 1L;
	ArrayList<ODKFieldInterface> fields;
	private static final Logger log = LoggerFactory.getLogger(ODKFieldListModel.class);

	public ODKFieldListModel()
	{
		fields = new ArrayList<ODKFieldInterface>();
	}
	
	public ODKFieldListModel(ArrayList<ODKFieldInterface> fields)
	{
		this.fields = fields;
	}
	
	public void addField(ODKFieldInterface field)
	{
		fields.add(field);
		this.fireIntervalAdded(this, 0, fields.size()-1);
	}
	
	public void addAllFields(Collection<ODKFieldInterface> fields)
	{
		if(fields==null || fields.size()==0) return;
		
		this.fields.addAll(fields);
		this.fireIntervalAdded(this, 0, fields.size()-1);

	}
	
	public void removeField(AbstractODKField field)
	{
		int index = this.fields.indexOf(field);
		
		if(index==-1)
		{
			log.debug("Cannont remove field from list as it's not in the list");
			return;
		}
		
		this.fields.remove(field);
		try{
			this.fireIntervalRemoved(this, index, index);
		} catch (IndexOutOfBoundsException e)
		{
			log.debug("Index out of bounds exception.  Trying to remove index: "+index+" when list size is now "+getSize());
		}
		log.debug("Removed field at index "+index+ " the size of list is now "+getSize());

	
	}
	
	public void removeField(int index)
	{
		this.fields.remove(index);
		
		try{
			this.fireIntervalRemoved(this, index, index);
		} catch (IndexOutOfBoundsException e)
		{
			log.debug("Index out of bounds exception.  Trying to remove index: "+index+" when list size is now "+getSize());
		}
		
		log.debug("Removed field at index "+index+ " the size of list is now "+getSize());
		
		
	}
	
	public void removeFields(Collection<ODKFieldInterface> fields)
	{
		this.fields.removeAll(fields);
		if(fields.size()==0)
		{
			this.fireIntervalRemoved(this, 0, 0);
		}
		else
		{
			this.fireIntervalRemoved(this, 0, fields.size()-1);
		}
		log.debug("Removed a bunch of fields, the size of list is now "+getSize());

	}
	
	@Override
	public ODKFieldInterface getElementAt(int i) {
		try{
			return fields.get(i);
		} catch (IndexOutOfBoundsException e)
		{
			log.debug("IndexOutOfBoundsException fired when trying to get index "+i+ " when size = "+getSize());
		}
		return null;
	}

	@Override
	public int getSize() {
		return fields.size();
	}
	
	public ArrayList<ODKFieldInterface> getAllFields()
	{
		return fields;
	}

}