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
package com.jgaap.gui.dnd;

import java.awt.dnd.DropTarget;

import javax.swing.JLabel;

import com.jgaap.generics.DocType;
import com.jgaap.gui.dnd.CanonicizerLabelPanel.ActionGroup;

/**
 * This class provides a landing zone for dropping CanonicizerLabels.
 * It is used on the CanonicizeStepPanel to affect Documents en-masse. 
 * 
 * @author Chuck Liddell
 *
 */
public class CanonDropLocation extends JLabel implements CanonicizerLabelUnpackager {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Defines the possible actions that result when a label is dropped on this component.
	 */
	public enum DropType { ADD, REMOVE };
	
	/**
	 * Reference to the DnDManager so we can pass drag and drop
	 * event info to it for processing.
	 */
	private final DnDManager dndManager;
	
	/**
	 * Tracks what set of documents actions will be applied to.
	 */
	private final ActionGroup actionGroup;
	
	/**
	 * Reference to a Document.DocType, which is needed if this
	 * panel is used in the sidebar table.
	 */
	private final DocType docType;
	
	/**
	 * What happens when a CanonicizerLabel is dropped here.
	 */
	private final DropType dropType;
	
	/**
	 * Default constructor.
	 * 
	 * @param text The text displayed on this label
	 * @param actionGroup The ActionGroup this component belongs to
	 * @param docType The DocType drops on this component should affect
	 * @param dndManager Used to pass drop information to the manager
	 * @param dropType Should drops result in adding canonicizers or removing them
	 */	
	public CanonDropLocation( DnDManager dndManager, String text, ActionGroup actionGroup, 
							  DocType docType, DropType dropType )
	{
		super( text );
		this.dndManager = dndManager;
		this.actionGroup = actionGroup;
		this.docType = docType;		
		this.dropType = dropType;
		this.setDropTarget( new DropTarget(this, new DropListener(this)) );
	}
	
	/**
	 * Take a CanonicizerPacket, unpackage it, process the transfer
	 * data, and pull out the CanonicizerLabel.
	 * 
	 * @param packet
	 * 		A CanonicizerPacket to be unpackaged
	 */
	public void unpackageLabel( CanonicizerPacket packet ){
		//System.out.println("unpackageLabel(" + packet.getLabel() + ")");
				
		// Notify the DnDManager of the new canonicizer(s)
		switch( actionGroup )
		{
		case ALL:
			if( dropType == DropType.ADD )
				dndManager.processAdd( packet.getLabel() );
			else
				dndManager.processRemove( packet.getLabel() );
			break;
		case DOCTYPE:
			if( dropType == DropType.ADD )
				dndManager.processAdd( packet.getLabel(), docType );
			else
				dndManager.processRemove( packet.getLabel(), docType );
			break;
		}		
	}
	
	/**
	 * Generates some helpful information to be displayed.
	 * 
	 * @return
	 * 		String of helpful information
	 */
	public String getTooltipText(){
		return "Drop a Canonicizer here to " + (dropType==DropType.ADD ? "add it to " : "remove it from " ) +
		       "all documents" + (docType==null ? "." : " of type " + docType + "." );
		
	}
	
	/**
	 * Create a String representation of this object.
	 */
	public String toString(){
		return docType == null ? "ALL" : docType.toString();
	}
}
