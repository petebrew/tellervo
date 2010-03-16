//
// This file is part of Corina.
// 
// Corina is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
// 
// Corina is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with Corina; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
// Copyright 2001 Ken Harris <kbh7@cornell.edu>
//

package edu.cornell.dendro.corina.graph;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import edu.cornell.dendro.corina.editor.Editor;
import edu.cornell.dendro.corina.sample.Sample;
import edu.cornell.dendro.corina.ui.Builder;

// used by: GraphWindow, BargraphFrame
public class GraphBGPopupMenu extends JPopupMenu {

    private JMenuItem titleItem, rangeItem, scaleItem;

    public GraphBGPopupMenu() {
        
        // add
        JMenuItem addSeries = new JMenuItem("Add series...");
        addSeries.addActionListener(new AbstractAction(){
        	public void actionPerformed(ActionEvent ae){
        		// FIXME: implement!
        		
        	}
        });
        
        // gridlines
        JMenuItem gridLines = new JMenuItem("Gridlines");
        gridLines.addActionListener(new AbstractAction(){
        	public void actionPerformed(ActionEvent ae){
        		// FIXME: implement!
        		
        	}
        });
        
        // axis
        JMenuItem axis = new JMenuItem("Vertical axis");
        axis.addActionListener(new AbstractAction(){
        	public void actionPerformed(ActionEvent ae){
        		// FIXME: implement!
        		
        	}
        });        
        
        addSeries.setEnabled(false);
        axis.setEnabled(false);
        gridLines.setEnabled(false);
        
        add(gridLines);
        add(axis);
        addSeparator(); 
        add(addSeries);

    }

}