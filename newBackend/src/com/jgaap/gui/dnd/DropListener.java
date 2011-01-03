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
/**
 * 
 */
package com.jgaap.gui.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;

/**
 * This class is given a CanonicizerLabelUnpackager at
 * construction time, which it will call whenever a
 * CanonicizerPacket is dropped on that component.
 * 
 * @author Chuck Liddell
 *
 */
public class DropListener extends DropTargetAdapter {

	/**
	 * Reference to the listener that gets called when something
	 * is dropped.
	 */
	private final CanonicizerLabelUnpackager unpackager;
	
	/**
	 * Only constructor. The unpackager will have their UnpackageLabel()
	 * called whenever a CanonicizerPacket is dropped.
	 * 
	 * @param unpackager
	 * 		The recipient of a call to UnpackageLabel()
	 */
	public DropListener( CanonicizerLabelUnpackager unpackager ){
		this.unpackager = unpackager;
	}
	
	
	/**
	 * This method is called when something is dropped on
	 * a component we are listening to.
	 * 
	 * @see java.awt.dnd.DropTargetListener#drop(java.awt.dnd.DropTargetDropEvent)
	 * 
	 * @param dtde
	 * 		The DropTargetDropEvent that describes the drop
	 */
	public void drop(DropTargetDropEvent dtde) {
    	//System.out.println("Dropped on panel at " + dtde.getLocation());
        DataFlavor[] flavors = dtde.getCurrentDataFlavors();
        if (flavors == null) {
            return;
        }
        for (int i = 0; i < flavors.length; i++) {
            if (flavors[i].equals(CanonicizerPacket.getFlavor())) {
                
            	dtde.acceptDrop( dtde.getDropAction() );
                Transferable transferable = dtde.getTransferable();

                CanonicizerPacket packet;
                try {
                    packet = (CanonicizerPacket) transferable.getTransferData( CanonicizerPacket.getFlavor());
                } catch (Exception e) {
                	e.printStackTrace();
                	return;
                }
                
                CanonicizerLabel label = packet.getLabel();
                DnDManager dndManager = label.getDnDManager();
                
                // Stop displaying the drag icon
                label.getGlassPane().hideIcon();
                
                // Unpackage the label and process it
                unpackager.unpackageLabel( packet );
                
                // Remove the old Label if this was a MOVE operation
                if( dtde.getDropAction() == DnDConstants.ACTION_MOVE )
                {
	                try {
	            		switch( label.getOwner().getActionGroup() )
	            		{
	            		case ALL:
	            			dndManager.processRemove( label );
	            			break;
	            		case DOCTYPE:
	            			dndManager.processRemove( label,
	            					label.getOwner().getDocType() );
	            			break;
	            		case SINGLE:
	            			dndManager.processRemove( label,
	            					label.getOwner().getDocument() );
	            			break;
	            		case NONE:
	            			// Do nothing
	            			break; 
	            		}
	            		// Don't remove labels from ActionGroup = NONE panels
	            		if( !label.getOwner().getActionGroup().equals( CanonicizerLabelPanel.ActionGroup.NONE ) )
	            			((CanonicizerLabelPanel)label.getParent()).removeCanonicizerLabel( label );
	            	}catch( Exception e ){}
                }
                break; //break the loop now that we've dropped
            }
        }
    }
}
