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

import java.util.*;

import javax.swing.table.AbstractTableModel;

import com.jgaap.jgaap;
import com.jgaap.generics.Document;

/**
 * Class used to store and manipulate the data displayed in the
 * documentsTable on the documentsStepPanel.
 * 
 * @author Chuck Liddell
 * @date 1/24/09
 */
class DocumentsTableModel extends AbstractTableModel {
    /**
     *
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
    private enum Column {  // change these int values to change column order
    	TITLE 			( "Title", 			0 ), 
    	FILEPATH 		( "Filepath", 		1 ),
    	AUTHOR_KNOWN 	( "Author Known", 	2 ),
    	AUTHOR_NAME 	( "Author Name", 	3 );
    	
    	public final String name;
    	public final int index;
    	
    	Column( String name, int index ){
    		this.name = name;
    		this.index = index;
    	}
    }
    
    /**
     * Data structure to store the Document information.
     */
    protected Vector<Document> data = new Vector<Document>();

    /**
     * Add a row to this table. This method should be passed a Document object
     * reference. The argument passed can be a new instance, or a reference
     * to an existing instance.
     * 
     * @param newRow
     *            A Document object to be added to the table
     */
    public void addDocument(Document newDocument){
        data.add( newDocument );
        fireTableRowsInserted( data.size() - 2, data.size() - 1);
    }
    
    /**
     * Add multiple rows at once to this table.
     */
    public void addDocuments( Collection<Document> documents ){
    	data.addAll( documents );
    	fireTableRowsInserted( data.size() - 1 - documents.size(), data.size() - 1 );
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
     * Return the number of columns in the table.
     * 
     * @return
     * 		Returns the number of columns in this table.
     */
    public int getColumnCount() {
        return Column.values().length;
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
     * Get the number of rows in the table.
     * 
     * @return
     * 		Returns the number of rows in this table.
     */
    public int getRowCount() {
        return data.size();
    }
    
    /**
     * Returns a reference to the internal data structure containing this table's Documents.
     * 
     * @return 
     * 		A collection of Documents stored in this table model
     */
    public Collection<Document> getDocuments(){
    	return data;
    }
    
    /**
     * Returns a new instance of Vector<Document> that contains the Documents stored in this table model.
     * 
     * @return 
     * 		A copy (new instance) of the list of Documents stored in this table model
     */
    public Collection<Document> getDocumentsCopy(){
    	return new Vector<Document>( data );
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
        if (row < 0 || row >= data.size() || col < 0 || col >= getColumnCount() )
            return ""; // returning a null value will throw an error
        
        Object retVal = "";
        if( col == Column.TITLE.index )
        	retVal = data.get( row ).getTitle();
        else if( col == Column.FILEPATH.index )
        	retVal = data.get( row ).getFilePath();
        else if( col == Column.AUTHOR_KNOWN.index )
        	retVal = data.get( row ).isAuthorKnown();
        else if( col == Column.AUTHOR_NAME.index )
        	retVal = data.get( row ).getAuthor();
        if( retVal == null ) // returning a null value will throw an error
        	retVal = "";
        return retVal;
    }

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
        
    	// Cannot edit the file path or author status
    	if( col == Column.FILEPATH.index || 
        	col == Column.AUTHOR_KNOWN.index )
        	return false; 
        
        // can't edit author names if author is unknown
        if( (col == Column.AUTHOR_NAME.index) && 
        	((Boolean) getValueAt(row, Column.AUTHOR_KNOWN.index) == false))
            return false;
        
        return true;        
    }

    /**
     * Print useful information about the contents of this table model.
     */
    protected void printDebugData() {
        int numRows = getRowCount();
        int numCols = getColumnCount();

        for (int i = 0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j = 0; j < numCols; j++) {
                System.out.print("  " + getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }

    /**
     * Remove all rows from the table.
     */
    public void removeAllRows() {
    	int lastIndex = data.size() - 1;
        data.clear();
        if( lastIndex >= 0)
        	fireTableRowsDeleted(0, lastIndex);
    }

    /**
     * Remove a specific row from the table.
     * 
     * @param row
     *            the index of the table row to be removed (model space)
     */
    public void removeRow(int row) {
        data.remove(row);
        fireTableRowsDeleted( row, row );
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
        if (jgaap.DEBUG) {
            System.out.println("Setting value at " + row + "," + col
                    + " to " + value + " (an instance of "
                    + value.getClass() + ")");
        }
        if (row < 0 || row >= data.size() || col < 0 || col >= getColumnCount() )
            return; // return early if cell is out-of-bounds
        
        if( col == Column.TITLE.index )
        	data.get(row).setTitle( (String)value );
        if( col == Column.AUTHOR_NAME.index )
        	data.get(row).setAuthor( (String)value );
        // Column.FILEPATH can only be set by making a new Document
        // Column.AUTHOR_KNOWN is derived from AUTHOR_NAME
        fireTableCellUpdated(row, col);

        if (jgaap.DEBUG) {
            System.out.println("New value of data:");
            printDebugData();
        }
    }
}