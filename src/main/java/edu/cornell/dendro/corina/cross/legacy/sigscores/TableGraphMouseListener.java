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
package edu.cornell.dendro.corina.cross.legacy.sigscores;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

final class TableGraphMouseListener extends MouseAdapter {
  private final SignificantScoresView view;

  TableGraphMouseListener(SignificantScoresView view) {
    this.view = view;
  }

  @Override
public void mouseClicked(MouseEvent e) {
    if (e.getClickCount() == 2) // double-clicks only
      this.view.graphSelectedCrossdate();
  }
}