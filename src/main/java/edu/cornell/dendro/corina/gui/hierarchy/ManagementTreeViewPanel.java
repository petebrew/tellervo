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
package edu.cornell.dendro.corina.gui.hierarchy;

import java.awt.Window;
import java.awt.event.ActionEvent;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.tridas.interfaces.ITridas;
import org.tridas.io.util.TridasUtils.TreeDepth;
import org.tridas.schema.TridasDerivedSeries;
import org.tridas.schema.TridasElement;
import org.tridas.schema.TridasMeasurementSeries;
import org.tridas.schema.TridasObject;
import org.tridas.schema.TridasRadius;
import org.tridas.schema.TridasSample;

import edu.cornell.dendro.corina.admin.view.PermissionByEntityDialog;
import edu.cornell.dendro.corina.core.App;
import edu.cornell.dendro.corina.gui.TridasEntityChooser;
import edu.cornell.dendro.corina.gui.TridasEntityChooser.EntitiesAccepted;
import edu.cornell.dendro.corina.schema.EntityType;
import edu.cornell.dendro.corina.ui.Alert;
import edu.cornell.dendro.corina.ui.Builder;
import edu.cornell.dendro.corina.wsi.corina.CorinaResourceAccessDialog;
import edu.cornell.dendro.corina.wsi.corina.resources.EntityResource;

/**
 * Extension of the standard TridasTreeViewPanel with the addition of 
 * a reassign right click menu for moving entities to different parents
 * 
 * @author peterbrewer
 *
 */
public class ManagementTreeViewPanel extends TridasTreeViewPanel {

	private static final long serialVersionUID = -7973400038586992025L;
    private Window parent;
	/**
	 * Basic constructor for tree view panel used in the context of searching
	 * for series.  Defaults are:
	 * 
	 * TreeDepth - Depth beyond which the tree will not expand 
	 * @see #setTreeDepth(TreeDepth) 
	 *   default = radius 
	 * 
	 * ListenersAreCheap - Are the attached listeners computationally cheap?
	 * @see #setListenersAreCheap(Boolean)
	 *   default = false
	 *  
	 * TextForSelectPopup - Text to display in popup menu when selecting entity
	 * @see #setTextForSelectPopup(String)
	 *   default = Search for associated series
	 * 
	 * BaseObjectListMode - Object list mode to use for base objects
	 * @see edu.cornell.dendro.corina.gui.CorinaCodePanel.ObjectListMode
	 *   default = Top level only
	 */
	public ManagementTreeViewPanel()
	{
		super();
			
	}
	
	/**
	 * Complete constructor for tree view panel.  
	 * 
	 * @param depth - @see #setTreeDepth(TreeDepth)
	 * @param listenersAreCheap - @see #setListenersAreCheap(Boolean)
	 * @param textForSelectPopup - @see #setTextForSelectPopup(String)
	 */
	public ManagementTreeViewPanel(Window parent, TreeDepth depth, Boolean listenersAreCheap, String textForSelectPopup)
	{
		super(parent, depth, listenersAreCheap, textForSelectPopup);
		this.parent = parent;
	}
	
	
	/**
	 * Set up the popup menu 
	 */
	@Override
	protected JPopupMenu initPopupMenu(boolean expandEnabled, Class<?> clazz)
	{
		String className = ManagementTreeViewPanel.getFriendlyClassName(clazz);
		Boolean isTridas = false;
		if(clazz.getSimpleName().startsWith("Tridas"))
		{
			isTridas = true;
		}
		
        // define the popup
        JPopupMenu popup = new JPopupMenu();
        JMenuItem menuItem;
        
        if(isTridas)
        {
	        // Expand 
	        menuItem = new JMenuItem("Expand branch");
	        menuItem.addActionListener(this);
	        menuItem.setActionCommand("expand");
	       	menuItem.setEnabled(expandEnabled);
	        menuItem.setIcon(Builder.getIcon("view_tree.png", 16));
	        popup.add(menuItem);
	        
	        // Select
	        menuItem = new JMenuItem(this.textForSelectPopup);
	        menuItem.addActionListener(this);
	        menuItem.setActionCommand("select");
	        menuItem.setIcon(Builder.getIcon("select.png", 16));
	        popup.add(menuItem);
	        popup.addSeparator();
	        
	        // Delete
	        menuItem = new JMenuItem("Delete this "+className.toLowerCase());
	        menuItem.addActionListener(this);
	        menuItem.setActionCommand("delete");
	        menuItem.setIcon(Builder.getIcon("cancel.png", 16));
	        popup.add(menuItem);
	        
		  	Boolean adm = App.isAdmin;
	        
	        // Reassign
	        menuItem = new JMenuItem("Reassign to another parent");
	        menuItem.addActionListener(this);
	        menuItem.setActionCommand("reassign");
	        menuItem.setEnabled(adm);
	        menuItem.setIcon(Builder.getIcon("newparent.png", 16));
	        popup.add(menuItem);
	        
	        // Merge
	        menuItem = new JMenuItem("Merge with another record");
	        menuItem.addActionListener(this);
	        menuItem.setActionCommand("merge");
	        menuItem.setEnabled(adm);
	        menuItem.setIcon(Builder.getIcon("merge.png", 16));
	        popup.add(menuItem);

	        popup.addSeparator();   
	        
	        menuItem = new JMenuItem("View permissions");
	        menuItem.addActionListener(this);
	        menuItem.setActionCommand("permissions");
	        menuItem.setEnabled(adm);
	        menuItem.setIcon(Builder.getIcon("trafficlight.png", 16));
	        popup.add(menuItem);
	        
	        popup.addSeparator();  
	        
        }
        
        // Refresh
        menuItem = new JMenuItem("Refresh");
        menuItem.addActionListener(this);
        menuItem.setActionCommand("refresh");
        menuItem.setIcon(Builder.getIcon("reload.png", 16));
        popup.add(menuItem);
        
        popup.setOpaque(true);
        popup.setLightWeightPopupEnabled(false);
		return popup;

	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("expand"))
		{
			// Request to expand the current node of tree
			expandEntity((DefaultMutableTreeNode) tree.getSelectionPath().getLastPathComponent());
		}
		else if (e.getActionCommand().equals("select"))
		{
			doSelectEntity((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent());
		}
		else if (e.getActionCommand().equals("refresh"))
		{
			DefaultMutableTreeNode node = ((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent());
			this.refreshNode(node);
		}
		else if (e.getActionCommand().equals("delete"))
		{
			// Delete this entity
			Object[] options = {"OK",
            "Cancel"};
			int ret = JOptionPane.showOptionDialog(getParent(), 
					"Are you sure you want to permanently delete this entity?", 
					"Confirm delete", 
					JOptionPane.YES_NO_OPTION, 
					JOptionPane.WARNING_MESSAGE, null, options, options[1]);
			
			if(ret == JOptionPane.YES_OPTION)
			{
				deleteEntity((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent());
			}			
		}
		else if (e.getActionCommand().equals("reassign"))
		{
			Object[] options = {"OK",
            "Cancel"};
			int ret = JOptionPane.showOptionDialog(getParent(), 
					"Are you sure you want to move this to another parent?\n"+
					"Changes will also impact entities subordinate to this one\n"+
					"so only continue if you know what you're doing!", 
					"Confirm move", 
					JOptionPane.YES_NO_OPTION, 
					JOptionPane.WARNING_MESSAGE, null, options, options[1]);
			
			if(ret != JOptionPane.YES_OPTION)
			{
				return;
			}	
			
			ITridas selected = (ITridas) ((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent()).getUserObject();
			Class<? extends ITridas> expectedClass = ITridas.class;
			
			if((selected.getClass().equals(TridasMeasurementSeries.class)) || 
					(selected.getClass().equals(TridasDerivedSeries.class)))
			{
				expectedClass = TridasRadius.class;
			}
			else if (selected.getClass().equals(TridasRadius.class))
			{
				expectedClass = TridasSample.class;
			}
			else if (selected.getClass().equals(TridasSample.class))
			{
				expectedClass = TridasElement.class;
			}
			else if ((selected.getClass().equals(TridasElement.class)) || 
					(selected.getClass().equals(TridasObject.class)))
			{
				expectedClass = TridasObject.class;
			}
			
			
			ITridas newParent = TridasEntityChooser.showDialog(parent, 
					"Select new parent", 
					expectedClass, 
					EntitiesAccepted.SPECIFIED_ENTITY_ONLY);

			// Actually do the reassign
			reassignEntity((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent(), newParent);
			
			
		}
		
		else if (e.getActionCommand().equals("merge"))
		{
			Object[] options = {"OK",
            "Cancel"};
			int ret = JOptionPane.showOptionDialog(getParent(), 
					"Are you sure you want to merge this with another record?\n"+
					"Changes will also impact entities subordinate to this one\n"+
					"so only continue if you know what you're doing!", 
					"Confirm merge", 
					JOptionPane.YES_NO_OPTION, 
					JOptionPane.WARNING_MESSAGE, null, options, options[1]);
			
			if(ret != JOptionPane.YES_OPTION)
			{
				return;
			}	
			
			ITridas selected = (ITridas) ((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent()).getUserObject();
			Class<? extends ITridas> expectedClass = selected.getClass();
						
			ITridas correctEntity = TridasEntityChooser.showDialog(null, 
					"Select correct entity", 
					expectedClass, 
					EntitiesAccepted.SPECIFIED_ENTITY_ONLY);

			// Actually do the merge
			mergeEntity((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent(), correctEntity);
			
			
		}
		
		else if (e.getActionCommand().equals("permissions"))
		{
			ITridas selected = (ITridas) ((DefaultMutableTreeNode)tree.getSelectionPath().getLastPathComponent()).getUserObject();

			PermissionByEntityDialog.showDialog(selected);
		}
	}
	
	/**
	 * Remove the specified node from the tree
	 * 
	 * @param node
	 */
	@SuppressWarnings("unchecked")
	private void reassignEntity(DefaultMutableTreeNode node, ITridas newParent)
	{	
		ITridas entity = null;
		EntityResource rsrc = null;

		if(newParent==null)
		{
			return;
		}
		
		String newParentEntityID = newParent.getIdentifier().getValue();
		
		if(node.getUserObject() instanceof TridasMeasurementSeries)
		{
			entity = (TridasMeasurementSeries) node.getUserObject();
			rsrc = new EntityResource<TridasMeasurementSeries>(entity, newParentEntityID, TridasMeasurementSeries.class);
			
		}
		else if(node.getUserObject() instanceof TridasRadius)
		{
			entity = (TridasRadius) node.getUserObject();
			rsrc = new EntityResource<TridasRadius>(entity, newParentEntityID, TridasRadius.class);
			
		}
		else if(node.getUserObject() instanceof TridasSample)
		{
			entity = (TridasSample) node.getUserObject();
			rsrc = new EntityResource<TridasSample>(entity, newParentEntityID, TridasSample.class);	
		}
		else if(node.getUserObject() instanceof TridasElement)
		{
			entity = (TridasElement) node.getUserObject();
			rsrc = new EntityResource<TridasElement>(entity, newParentEntityID, TridasElement.class);	
		}
		else if(node.getUserObject() instanceof TridasObject)
		{
			Alert.message("Not implemented", "Moving sub-objects is not yet supported");
		}
		else
		{
			Alert.message("Not implemented", "You shouldn't have been able to get here!");
		}
		
		// Do query
		CorinaResourceAccessDialog accdialog = new CorinaResourceAccessDialog(rsrc);
		rsrc.query();
		accdialog.setVisible(true);
		
		if(accdialog.isSuccessful())
		{
			rsrc.getAssociatedResult();
			((DefaultTreeModel)tree.getModel()).removeNodeFromParent(node);
			return;
		}

		
		Exception exception = accdialog.getFailException();
		
		if(exception.getLocalizedMessage().contains("duplicate key value"))
		{
			JOptionPane.showMessageDialog(this, "Operation aborted:\nReassigning to this parent would result in "
					+getFriendlyPluralClassName(node.getUserObject().getClass()).toLowerCase()+" with duplicate titles.", 
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(this, "There was a problem reassigning this entity\n"+exception, 
					"Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return;
	}
	
	@SuppressWarnings("unchecked")
	private void mergeEntity(DefaultMutableTreeNode node, ITridas correctEntity)
	{
		ITridas entity = null;
		EntityResource rsrc = null;

		if(correctEntity==null)
		{
			return;
		}
		
		String correctEntityID = correctEntity.getIdentifier().getValue();
		
		if(node.getUserObject() instanceof TridasMeasurementSeries)
		{
			entity = (TridasMeasurementSeries) node.getUserObject();
			rsrc = new EntityResource<TridasMeasurementSeries>(EntityType.MEASUREMENT_SERIES, 
					TridasMeasurementSeries.class, entity, correctEntityID );
			
		}
		else if(node.getUserObject() instanceof TridasRadius)
		{
			entity = (TridasRadius) node.getUserObject();
			rsrc = new EntityResource<TridasRadius>(EntityType.RADIUS,
					TridasRadius.class, entity, correctEntityID);
			
		}
		else if(node.getUserObject() instanceof TridasSample)
		{
			entity = (TridasSample) node.getUserObject();
			rsrc = new EntityResource<TridasSample>(EntityType.SAMPLE,
					TridasSample.class, entity, correctEntityID);	
		}
		else if(node.getUserObject() instanceof TridasElement)
		{
			entity = (TridasElement) node.getUserObject();
			rsrc = new EntityResource<TridasElement>(EntityType.ELEMENT,
					TridasElement.class, entity, correctEntityID);	
		}
		else if(node.getUserObject() instanceof TridasObject)
		{
			entity = (TridasObject) node.getUserObject();
			rsrc = new EntityResource<TridasObject>(EntityType.OBJECT,
					TridasObject.class, entity, correctEntityID);	
		}
		else
		{
			Alert.message("Not implemented", "You shouldn't have been able to get here!");
		}
		
		// Do query
		CorinaResourceAccessDialog accdialog = new CorinaResourceAccessDialog(rsrc);
		rsrc.query();
		accdialog.setVisible(true);
		
		if(accdialog.isSuccessful())
		{
			rsrc.getAssociatedResult();
			((DefaultTreeModel)tree.getModel()).removeNodeFromParent(node);
			return;
		}

		
		Exception exception = accdialog.getFailException();
		

		JOptionPane.showMessageDialog(this, "There was a problem merging entities\n"+exception, 
				"Error", JOptionPane.ERROR_MESSAGE);
		
		
		return;
		
		
	}
		
}