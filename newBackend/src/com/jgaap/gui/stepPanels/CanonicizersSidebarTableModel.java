/**
 *   JGAAP -- Java Graphical Authorship Attribution Program
 *   Copyright (C) 2009 Patrick Juola
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation under version 3 of the License.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/
package com.jgaap.gui.stepPanels;

import com.jgaap.generics.DocType;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import com.jgaap.gui.dnd.CanonDropLocation;
import com.jgaap.gui.dnd.CanonicizerLabelPanel.ActionGroup;
import com.jgaap.gui.dnd.DnDManager;

/**
 * Class used to store and manipulate the data displayed in the
 * docTypesTable on the canonicizersStepPanel.
 * 
 * @author Chuck Liddell
 * @date 1/24/09
 *
 */
class CanonicizersSidebarTableModel extends AbstractTableModel {

	/**
	 * Version ID for possible serialization of this class.
	 */
	private static final long      serialVersionUID = 1L;
		
	/**
     * Enum containing the column names and their matching column index. This enum is
     * used to prevent possible errors when using index values to refer to specific
     * columns. 
     */
    /* As long as code in this class only refers to this enum structure when
     * referring to column order, then the order can be completely controlled by
     * the values used to construct these enums, and will be safe.
     */
    public enum Column {  // change these int values to change column order
    	ADD		( "Add To",		0 ),
    	REMOVE 	( "Remove From", 	1 );
    	
    	public final String name;
    	public final int index;
    	
    	Column( String name, int index ){
    		this.name = name;
    		this.index = index;
    	}
    }
        
    /**
     * Internal data array of CanonDropLocation instances, two for
     * each row in this table.
     */
    private ArrayList<CanonDropLocation[]> dropLocations = 
    								new ArrayList<CanonDropLocation[]>();
    
    /**
     * Simple constructor.
     * 
     * @param dndManager
     * 		DnDManager instance that will be given to new CanonicizerLabelPanels
     */
    public CanonicizersSidebarTableModel( DnDManager dndManager ){
    	CanonDropLocation[] row =
    	{ new CanonDropLocation( dndManager, "ALL", ActionGroup.ALL, null, CanonDropLocation.DropType.ADD ),
		  new CanonDropLocation( dndManager, "ALL", ActionGroup.ALL, null, CanonDropLocation.DropType.REMOVE) };
    	dropLocations.add( row );
    	for( DocType docType : DocType.values() )
    	{
    		row = new CanonDropLocation[2];
    		row[0] = new CanonDropLocation( dndManager, docType.toString(), ActionGroup.DOCTYPE, docType, CanonDropLocation.DropType.ADD );
    		row[1] = new CanonDropLocation( dndManager, docType.toString(), ActionGroup.DOCTYPE, docType, CanonDropLocation.DropType.REMOVE);
    		dropLocations.add( row );
    	}
    	this.fireTableDataChanged();
    }
      
    /**
     * Return the number of columns in the table.
     * 
     * @return
     * 		Returns the number of columns in this table.
     */
    public int getColumnCount() {
        return Column.values().length;
    }
    
    /**
     * Get the number of rows in the table.
     * 
     * @return
     * 		Returns the number of rows in this table.
     */
    public int getRowCount() {
        return dropLocations.size();
    }
    
    /**
     * JTable uses this method to determine the default renderer/ editor for
     * each cell.
     * 
     * @param c
     * 		The column to check
     * @return
     * 		Returns the Class of the corresponding column.
     */
    @Override
    public Class<?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /**
     * Get the string name of a given column.
     * 
     * @param col
     * 		The column index that will be used to find the correct column name
     * @return
     * 		Returns the column name matching the given index.
     */
    @Override
    public String getColumnName(int col) {
    	for( Column c : Column.values() )
    		if( c.index == col )
    			return c.name;
    	return null;
    }
    
    /**
     * Returns the value stored in a specific table cell.
     * 
     * @param row
     * 		The row of the cell to be checked.
     * @param col
     * 		The column of the cell to be checked.
     * @return
     * 		Returns the value stored in the table cell [row, col]. Null values will be returned as a blank String.
     */
    public Object getValueAt(int row, int col) {
        if (row < 0 || row >= dropLocations.size() || col < 0 || col >= getColumnCount() )
            return ""; // returning a null value will throw an error
        
        Object retVal = dropLocations.get(row)[col];
        if( retVal == null ) // returning a null value will throw an error
        	retVal = "";
        return retVal;
    }
       
    /**
     * Get a docType from a specific cell (i.e. the Document from that cell)
     * 
     * @param row
     * 		Row in the table that will be used to return the corresponding document's docType
     * @return
     * 		Returns the docType of the document at the given row
     */
    /*public Document.DocType getDocTypeFrom( int row ){
    	if( row < 1 || row > dropLocations.size() - 1 )
    		return null;
    	
    	return dropLocations.get( row )[0].getDocType();
    }*/

    /**
     * Tells callers whether a specific cell in the table is allowed to be edited.
     * 
     * @param row
     * 		The row of the cell.
     * @param col
     * 		The column of the cell.
     */
    @Override
    public boolean isCellEditable(int row, int col) {
        return false; // No other cells in this model are editable directly in the GUI
    }

    /**
     * Method to set the value of a specific table cell. This method is called
     * when the table is modified visually by a user.
     * 
     * @param value
     * 			The new value to be stored in the table cell
     * @param row
     * 			The row of the table cell to be modified
     * @param col
     * 			The column of the table cell to be modifieds
     */
    @Override
    public void setValueAt(Object value, int row, int col) {
        // Do nothing - no cells in this model are editable directly in the GUI
    }

	/* (non-Javadoc)
	 * @see com.jgaap.gui.stepPanels.DocumentsTableModel#removeAllRows()
	 */
	public void removeAllRows() {
		dropLocations.clear();
	}
    
    public String getCanonTooltipTextAt( int row, int col ){
    	if( row < 0 || row > dropLocations.size() - 1 )
    		return null;
    	return dropLocations.get(row)[col].getTooltipText();
    }
}
