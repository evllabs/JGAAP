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
import com.jgaap.generics.Document;
import java.util.ArrayList;
import java.util.Collection;
import java.awt.Color;
import com.jgaap.gui.dnd.CanonicizerLabelPanel;
import com.jgaap.gui.dnd.DnDManager;

/**
 * Class used to store and manipulate the data displayed in the
 * canonicizersTable on the canonicizersStepPanel.
 * 
 * @author Chuck Liddell
 * @date 1/24/09
 *
 */
class CanonicizersTableModel extends DocumentsTableModel {

	/**
	 * Version ID for possible serialization of this class.
	 */
	private static final long      serialVersionUID = 1L;
	
	/**
	 * Reference to the DnDManager so we can pass drag and drop
	 * event info to it for processing.
	 */
	private final DnDManager dndManager;
	
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
    	TITLE 			( "Title", 			0 ),
    	TYPE			( "Doc Type",		1 ),
    	CANONICIZERS 	( "Canonicizers", 	2 );
    	
    	public final String name;
    	public final int index;
    	
    	Column( String name, int index ){
    		this.name = name;
    		this.index = index;
    	}
    }
    
    /**
     * Internal data array of CanonicizerLabelPanel instances, one for
     * each document row in this table.
     */
    private ArrayList<CanonicizerLabelPanel> labelPanels = 
    								new ArrayList<CanonicizerLabelPanel>();
    
    /**
     * Simple constructor.
     * 
     * @param dndManager
     * 		DnDManager instance that will be given to new CanonicizerLabelPanels
     */
    public CanonicizersTableModel( DnDManager dndManager ){
    	this.dndManager = dndManager;
    }
        
    /**
     * Add a row to this table. This method should be passed a Document object
     * reference. The argument passed can be a new instance, or a reference
     * to an existing instance.
     * 
     * @param newRow
     *            A Document object to be added to the table
     */
    @Override
    public void addDocument(Document newDocument){
        data.add( newDocument );
        CanonicizerLabelPanel panel;
        labelPanels.add( panel = new CanonicizerLabelPanel( dndManager,
        													CanonicizerLabelPanel.ActionGroup.SINGLE,
        													true) );
        panel.setDocument( newDocument );
        panel.setBackground( Color.white );
        dndManager.addCanonChangeListener( panel );
        
        fireTableRowsInserted( data.size() - 2, data.size() - 1);
    }
    
    /**
     * Add multiple rows at once to this table.
     */
    @Override
    public void addDocuments( Collection<Document> documents ){
    	data.addAll( documents );
    	CanonicizerLabelPanel panel;
    	for( Document newDocument : documents ){
    		labelPanels.add( panel = new CanonicizerLabelPanel( dndManager,
    															CanonicizerLabelPanel.ActionGroup.SINGLE,
    															true));
    		panel.setDocument( newDocument );
    		panel.setBackground( Color.white );
    		dndManager.addCanonChangeListener( panel );
    	}
    	fireTableRowsInserted( data.size() - 1 - documents.size(), data.size() - 1 );
    }
    
    /**
     * Return the number of columns in the table.
     * 
     * @return
     * 		Returns the number of columns in this table.
     */
    @Override
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
     * Returns the value stored in a specific table cell.
     * 
     * @param row
     * 		The row of the cell to be checked.
     * @param col
     * 		The column of the cell to be checked.
     * @return
     * 		Returns the value stored in the table cell [row, col]. Null values will be returned as a blank String.
     */
    @Override
    public Object getValueAt(int row, int col) {
        if (row < 0 || row >= data.size() || col < 0 || col >= getColumnCount() )
            return ""; // returning a null value will throw an error
        
        Object retVal = "";
        if( col == Column.TITLE.index )
        	retVal = data.get( row ).getTitle();
        else if( col == Column.TYPE.index )
        	retVal = data.get( row ).getDocType();
        else if( col == Column.CANONICIZERS.index )
        	retVal = labelPanels.get( row );
        if( retVal == null ) // returning a null value will throw an error
        	retVal = "";
        return retVal;
    }
    
    /**
     * Get a filepath from a specific cell (i.e. the Document from that cell)
     * 
     * @param row
     * 		Row in the table that will be used to return the corresponding document's filepath
     * @return
     * 		Returns the filepath of the document at the given row.
     */
    public String getFilePathFrom( int row ){
    	if( row < 0 || row > data.size() - 1 )
    		return null;
    	
    	return data.get( row ).getFilePath();
    }
    
    /**
     * Get a docType from a specific cell (i.e. the Document from that cell)
     * 
     * @param row
     * 		Row in the table that will be used to return the corresponding document's docType
     * @return
     * 		Returns the docType of the document at the given row
     */
    public DocType getDocTypeFrom( int row ){
    	if( row < 0 || row > data.size() - 1 )
    		return null;
    	
    	return data.get( row ).getDocType();
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
        
    	// Can only edit the Canonicizers field
    	if( col == Column.CANONICIZERS.index )
        	return true; 
                
        return false; // No cells in this model are editable directly in the GUI
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
	@Override
	public void removeAllRows() {
		super.removeAllRows();
		labelPanels.clear();
	}
    
    public String getCanonTooltipTextAt( int row ){
    	if( row < 0 || row > labelPanels.size() - 1 )
    		return null;
    	return labelPanels.get(row).getTooltipText();
    }
}
