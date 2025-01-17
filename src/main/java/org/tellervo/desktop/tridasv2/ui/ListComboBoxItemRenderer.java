/*******************************************************************************
 * Copyright (C) 2010 Lucas Madar and Peter Brewer
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
 *     Lucas Madar
 *     Peter Brewer
 ******************************************************************************/
/**
 * 
 */
package org.tellervo.desktop.tridasv2.ui;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;

import org.tellervo.schema.WSISecurityUser;
import org.tridas.schema.ControlledVoc;
import org.tridas.schema.TridasProject;


/**
 * @author Lucas Madar
 *
 */
public class ListComboBoxItemRenderer extends DefaultListCellRenderer implements ComboBoxItemRenderer {
	private static final long serialVersionUID = 1L;

	public ListComboBoxItemRenderer() {
		setHorizontalAlignment(LEFT);
		setVerticalAlignment(CENTER);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		// set backgrounds and such
		if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

		modifyComponent(value);
		
		return this;
	}
	
	public void modifyComponent(Object value) {
		if(value == null) {
			setText("");
		} 
		else {
			if(value instanceof ControlledVoc) {
				ControlledVoc cv = (ControlledVoc) value;
				
				if(cv.isSetNormal())
					setText(cv.getNormal());
				else if(cv.isSetValue())
					setText(cv.getValue());
				else
					setText("<invalid controlled vocabulary>");
			}
			else if(value instanceof WSISecurityUser) {
				WSISecurityUser u = (WSISecurityUser) value;
				
				setText(u.getLastName() + ", " + u.getFirstName());
			}
			else if (value instanceof TridasProject){
				TridasProject p = (TridasProject) value;
				setText(p.getTitle());
			}
			else
				setText(value.toString());
		}		
	}

}
