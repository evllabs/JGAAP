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

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.InputEvent;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.TransferHandler;

/**
 * This class is responsible for handling the drag and drop functionality
 * of CanonicizerLabel components.
 * 
 * @author Chuck Liddell
 *
 */
public class LabelTransferHandler extends TransferHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 
	 * Called to see if this CanonicizerLabel can accept data from
	 * component type c of dataflavor df.
	 *
	 * @param c JComponent that is the source of the drag and drop
	 * @param df The type of data being dragged
	 * @see javax.swing.TransferHandler#canImport(javax.swing.JComponent, java.awt.datatransfer.DataFlavor[])
	 */
	@Override
	public boolean canImport(JComponent c, DataFlavor[] df) {
		return false; //CanonicizerLabel can never accept drops
	}

	/** 
	 * This method is called by Swing's built-in Drag and Drop functionality in order
	 * to obtain a Transferable, which is a bundle of data created by the CanonicizerLabel
	 * to be transferred elsewhere.
	 *
	 * @param c The JComponent that needs to have its data packaged up
	 * @see javax.swing.TransferHandler#createTransferable(javax.swing.JComponent)
	 */
	@Override
	protected Transferable createTransferable(JComponent c) {
		try {
			return ((CanonicizerLabel)c).createTransferable();
		}catch(ClassCastException cce){
			return null; // We weren't passed a CanonicizerLabel
		}
	}

	/** 
	 * This method is called by our own code to kick off the Drag and Drop functionality.
	 *
	 * @param c The JComponent that is the source of the Drag
	 * @param event The event representing the start of the drag
	 * @param type the ActionType of the drag (Move, Copy, Copy or Move, None)
	 * @see javax.swing.TransferHandler#exportAsDrag(javax.swing.JComponent, java.awt.event.InputEvent, int)
	 */
	@Override
	public void exportAsDrag(JComponent c, InputEvent event, int type) {
		super.exportAsDrag(c, event, type);
	}

	/** 
	 * Called by the built-in Swing drag and drop support, whether the drop was
	 * a success or a failure. This method is used to clean up anything that was
	 * going on during the drag, in this case glasspane updates.
	 * 
	 * @param c The JComponent that started the drag
	 * @param data The data created by the JComponent to be transferred
	 * @param type The ActionType of the transfer
	 * @see javax.swing.TransferHandler#exportDone(javax.swing.JComponent, java.awt.datatransfer.Transferable, int)
	 */
	@Override
	protected void exportDone(JComponent c, Transferable data, int type) {
		try {
			((CanonicizerLabel)c).getGlassPane().hideIcon();
		}catch(ClassCastException cce){
			cce.printStackTrace(); // We weren't passed a CanonicizerLabel
		}
		finally{ // Called regardless of whether the try succeeds or fails
			super.exportDone(c, data, type);
		}		
	}

	/** 
	 *
	 * @see javax.swing.TransferHandler#exportToClipboard(javax.swing.JComponent, java.awt.datatransfer.Clipboard, int)
	 */
	@Override
	public void exportToClipboard(JComponent arg0, Clipboard arg1, int arg2)
			throws IllegalStateException {
		super.exportToClipboard(arg0, arg1, arg2);
	}

	/** 
	 * Asks this TransferHandler what kinds of actions are supported by
	 * the component being passed as an argument.
	 *
	 * @param c The JComponent linked to this TransferHandler
	 * @see javax.swing.TransferHandler#getSourceActions(javax.swing.JComponent)
	 */
	@Override
	public int getSourceActions(JComponent c) {
		return TransferHandler.COPY_OR_MOVE; // CanonicizerLabel supports both Move and Copy
	}

	/** 
	 *
	 * @see javax.swing.TransferHandler#getVisualRepresentation(java.awt.datatransfer.Transferable)
	 */
	@Override
	public Icon getVisualRepresentation(Transferable arg0) {
		//System.out.println("getVisualRepresentation");
		return super.getVisualRepresentation(arg0);
	}

	/** 
	 *
	 * @see javax.swing.TransferHandler#importData(javax.swing.JComponent, java.awt.datatransfer.Transferable)
	 */
	@Override
	public boolean importData(JComponent arg0, Transferable arg1) {
		//System.out.println("importData");
		return super.importData(arg0, arg1);
	}

}
