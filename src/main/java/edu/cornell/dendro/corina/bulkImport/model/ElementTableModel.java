/*******************************************************************************
 * Copyright (C) 2010 Daniel Murphy and Peter Brewer
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
 *     Daniel Murphy
 *     Peter Brewer
 ******************************************************************************/
/**
 * Created on Aug 18, 2010, 1:12:40 PM
 */
package edu.cornell.dendro.corina.bulkImport.model;

import java.math.BigDecimal;

import org.tridas.schema.TridasObject;
import org.tridas.schema.TridasShape;
import org.tridas.schema.TridasUnit;

import edu.cornell.dendro.corina.gis.GPXParser.GPXWaypoint;
import edu.cornell.dendro.corina.schema.WSIElementTypeDictionary;
import edu.cornell.dendro.corina.schema.WSITaxonDictionary;

/**
 * @author Daniel Murphy
 *
 */
public class ElementTableModel extends AbstractBulkImportTableModel {
	private static final long serialVersionUID = 2L;
	
	public ElementTableModel(ElementModel argModel){
		super(argModel);
	}
	
	/**
	 * @see edu.cornell.dendro.corina.bulkImport.model.AbstractBulkImportTableModel#getColumnClass(java.lang.String)
	 */
	public Class<?> getColumnClass(String argColumn){
		if(argColumn.equals(SingleElementModel.TYPE)){
			return WSIElementTypeDictionary.class;
		}else if(argColumn.equals(SingleElementModel.IMPORTED)){
			return Boolean.class;
		}else if(argColumn.equals(SingleElementModel.OBJECT)){
			return TridasObject.class;
		}else if(argColumn.equals(SingleElementModel.DEPTH)){
			return BigDecimal.class;
		}else if(argColumn.equals(SingleElementModel.WIDTH)){
			return BigDecimal.class;
		}else if(argColumn.equals(SingleElementModel.DIAMETER)){
			return BigDecimal.class;
		}else if(argColumn.equals(SingleElementModel.HEIGHT)){
			return BigDecimal.class;
		}else if(argColumn.equals(SingleElementModel.LATITUDE)){
			return Double.class;
		}else if(argColumn.equals(SingleElementModel.LONGTITUDE)){
			return Double.class;
		}else if(argColumn.equals(SingleElementModel.SLOPE_ANGLE)){
			return Integer.class;
		}else if(argColumn.equals(SingleElementModel.SLOPE_AZIMUTH)){
			return Integer.class;
		}else if(argColumn.equals(SingleElementModel.SOIL_DEPTH)){
			return Double.class;
		}else if(argColumn.equals(SingleElementModel.SHAPE)){
			return TridasShape.class;
		}else if(argColumn.equals(SingleElementModel.TAXON)){
			return WSITaxonDictionary.class;
		}else if(argColumn.equals(SingleElementModel.UNIT)){
			return TridasUnit.class;
		}else if(argColumn.equals(SingleElementModel.WAYPOINT)){
			return GPXWaypoint.class;
		}
		return null;
	}
	
	/**
	 * @see edu.cornell.dendro.corina.bulkImport.model.AbstractBulkImportTableModel#setValueAt(java.lang.Object, java.lang.String, edu.cornell.dendro.corina.bulkImport.model.IBulkImportSingleRowModel, int)
	 */
	@Override
	public void setValueAt(Object argAValue, String argColumn, IBulkImportSingleRowModel argModel, int argRowIndex) {
		// TODO tie this into a command, only commands can modify the model!
		
		// If it's a waypoint set the lat and long
		if(argColumn.equals(SingleElementModel.WAYPOINT))
		{
			GPXWaypoint wp = (GPXWaypoint) argAValue;
			argModel.setProperty(SingleElementModel.LATITUDE, wp.getLatitude());
			argModel.setProperty(SingleElementModel.LONGTITUDE, wp.getLongitude());
		}
		// If it's lat/long data, remove the waypoint
		if(argColumn.equals(SingleElementModel.LATITUDE) || argColumn.equals(SingleElementModel.LONGTITUDE)){
			argModel.setProperty(SingleElementModel.WAYPOINT, null);
		}
		
		argModel.setProperty(argColumn, argAValue);
	}
}