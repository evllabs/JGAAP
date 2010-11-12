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
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 * This class essentially encapsulates a single CanonicizerLabel and any
 * relevant transfer information as it is moved from one location in the GUI
 * to another.
 * 
 * @author Chuck Liddell
 *
 */
public class CanonicizerPacket implements Transferable {
	
	/**
	 * Convenience reference to the DataFlavor for a CanonicizerPacket
	 */
	private static DataFlavor dataFlavor = null;
	
	/**
	 * Reference to a CanonicizerLabel that is stored inside this packet.
	 */
	private final CanonicizerLabel label;
		
	/**
	 * Reference to the source action group of the stored label.
	 */
	private final CanonicizerLabelPanel.ActionGroup actionGroup;
	
	public CanonicizerPacket( CanonicizerLabel label,
							  CanonicizerLabelPanel.ActionGroup actionGroup){
		this.label = label;
		this.actionGroup = actionGroup;
	}
	
	/**
	 * Retrieve the transfer data stored in this packet.
	 * 
	 * @see java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer.DataFlavor)
	 * @param flavor
	 * 		The DataFlavor to expect the information to come in
	 * @return
	 * 		The data stored in this packet.
	 */
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		//System.out.println("getTransferData(" + flavor + ")");

		if( !CanonicizerPacket.getFlavor().equals(flavor) )
			throw new UnsupportedFlavorException( flavor );
		return this;
	}

	/**
	 * Get an array of DataFlavors this Transferable supports
	 * 
	 * @see java.awt.datatransfer.Transferable#getTransferDataFlavors()
	 * @return
	 * 		An array of supported DataFlavors, in order of preference
	 */
	public DataFlavor[] getTransferDataFlavors() {
		//System.out.println("getTransferDataFlavors()");
		DataFlavor[] flavors = { getFlavor() };
		return flavors;
	}

	/**
	 * Convenience methods for other classes to get the DataFlavor
	 * reference for a CanonicizerPacket
	 * @return
	 * 		A custom DataFlavor reference
	 */
	public static DataFlavor getFlavor() {
		if(dataFlavor == null)
		{
			try {
				dataFlavor = new DataFlavor(
					DataFlavor.javaJVMLocalObjectMimeType + 
					";class=\"com.jgaap.gui.dnd.CanonicizerPacket\"");
			}
			catch(ClassNotFoundException cnfe){
				cnfe.printStackTrace();
				return null;
			}
		}
		return dataFlavor;
	}
	
	/**
	 * Checks to see if a specific DataFlavor is supported
	 * 
	 * @see java.awt.datatransfer.Transferable#isDataFlavorSupported(java.awt.datatransfer.DataFlavor)
	 * @param flavor
	 * 		The DataFlavor to check
	 * @return
	 * 		Boolean value indicating whether the flavor is supported
	 */
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		//System.out.println("isDataFlavorSupported(" + flavor + ")");
		return getFlavor().equals( flavor );
	}

	/**
	 * @return the label
	 */
	public CanonicizerLabel getLabel() {
		return label;
	}

	/**
	 * @return the actionGroup
	 */
	public CanonicizerLabelPanel.ActionGroup getActionGroup() {
		return actionGroup;
	}

}
