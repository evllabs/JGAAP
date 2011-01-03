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

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;

/**
 * Extension of the JTable class to allow for the
 * custom cells, drag and drop support, and visual
 * tweaks needed for this table.
 * 
 * 
 * @author Chuck Liddell
 *
 */
public class CanonicizersSidebarTable extends JTable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CanonicizersSidebarTable( CanonicizersSidebarTableModel model ){
		super( model );
		setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		this.setCellSelectionEnabled( false );
		this.setColumnSelectionAllowed(false);
	    this.setRowSelectionAllowed(false);

		configureDropThrough();
		//configureFocusFollow();
	}
	
	/**
     * This method adds a DropListener to make the table essentially
     * transparent to drops. Any drop events will be passed down
     * to whatever table cell element is below the mouse. 
     */
	private void configureDropThrough(){
		this.setDropTarget( new DropTarget( this, new DropTargetAdapter(){
			
		    public void drop(DropTargetDropEvent dtde) {
		    	//System.out.println("Dropped on table at " + dtde.getLocation());
		    	Point p = dtde.getLocation();
		    	//System.out.println("Table location: " + p + " -> (" + 
		    	//		CanonicizersSidebarTable.this.rowAtPoint(p) + "," +
	    		//		CanonicizersSidebarTable.this.columnAtPoint(p) + ")");
		    	// Get the table element under the mouse pointer
		    	//   and attempt to cast it to a DropTargetListener
		    	try{
			    	Component component =
			    		(Component) CanonicizersSidebarTable.this.getValueAt(
			    				CanonicizersSidebarTable.this.rowAtPoint(p),
			    				CanonicizersSidebarTable.this.columnAtPoint(p));
			    	//System.out.println("pre - gL: " + dtde.getLocation() + " p: " + p);
			    	SwingUtilities.convertPointToScreen(p, CanonicizersSidebarTable.this);
			    	//System.out.println("scr - gL: " + dtde.getLocation() + " p: " + p);
			    	SwingUtilities.convertPointFromScreen(p, component);
			    	//System.out.println("cel - gL: " + dtde.getLocation() + " p: " + p);
			    	// Pass along the event if it is a valid listener
			    	DropTarget dropTarget = component.getDropTarget();
			    	if( dropTarget != null ) 
			    		dropTarget.drop( dtde );
		    	}
		    	catch( ClassCastException cce ){
		    		System.out.println(" -- Failed Component cast --");
		    		cce.printStackTrace();
		    	}
		    }
		}));
	}
	
	/**
	 * Sets up Mouse listeners to request focus on the table cell
	 * that the mouse is over, as the mouse moves around.
	 */
	public void configureFocusFollow(){
		this.addMouseMotionListener( new MouseMotionAdapter() {
			private int row = -1, col = -1;
			
			@Override
			public void mouseMoved( MouseEvent me ){
				Point p = me.getPoint();
				int curRow = CanonicizersSidebarTable.this.rowAtPoint(p),
				    curCol = CanonicizersSidebarTable.this.columnAtPoint(p);
				if(  curRow != row || curCol != col ){
					row = curRow; col = curCol;
					CanonicizersSidebarTable.this.changeSelection(row, col, false, false);
					Object o = CanonicizersSidebarTable.this.getValueAt(row, col);
					if( o != null )
						try{
						((Component)o).requestFocusInWindow();
						}catch(Exception e){}
				}
			}
			
		});
	}
	
	// Overriding this method stretches the table vertically
	public boolean getScrollableTracksViewportHeight() {
		// fetch the table's parent
		Container viewport = getParent();

		// if the parent is not a viewport, calling this isn't useful
		if (!(viewport instanceof JViewport)) {
			return false;
		}

		// return true if the table's preferred height is smaller
		// than the viewport height, else false
		return getPreferredSize().height < viewport.getHeight();
	}

	//Implement table cell tool tips.
	public String getToolTipText(MouseEvent e) {
		String tip;
		java.awt.Point p = e.getPoint();
		int rowIndex = rowAtPoint(p);
		int colIndex = columnAtPoint(p);
		int realColumnIndex = convertColumnIndexToModel(colIndex);

		tip = ((CanonicizersSidebarTableModel)this.getModel()).getCanonTooltipTextAt( rowIndex, realColumnIndex );
				
        return tip;
    }
}