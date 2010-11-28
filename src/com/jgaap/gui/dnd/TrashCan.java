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

import java.awt.dnd.DropTarget;

import javax.swing.JLabel;
import javax.swing.ImageIcon;

/**
 * This label looks like a trash can, and allows
 * the user to dispose of Canonicizers by dragging
 * and dropping them onto this label.
 * 
 * @author Chuck Liddell
 *
 */
public class TrashCan extends JLabel implements CanonicizerLabelUnpackager {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TrashCan(){
		super();
		this.setIcon( createImageIcon("../resources/trash.gif",
				"/com/jgaap/gui/resources/trash.gif",
				"Drop Canonicizers here to remove them.") );
		setHorizontalAlignment( JLabel.CENTER );
		this.setDropTarget( new DropTarget(this, new DropListener(this)) );
	}
	
	/**
	 * We implement this method so that we can be passed CanonicizerPackets.
	 * Since this is a trash can we will simply throw them out.
	 * 
	 * @see com.jgaap.gui.dnd.CanonicizerLabelUnpackager#unpackageLabel(com.jgaap.gui.dnd.CanonicizerPacket)
	 */
	public void unpackageLabel(CanonicizerPacket packet) {
		; // TODO:  What on earth is the intent of this method?	
	}
	
	/** Returns an ImageIcon, or null if the path was invalid. */
	protected ImageIcon createImageIcon(String relativePath, String jarPath,
	                                           String description) {
	    java.net.URL imgURL = getClass().getResource(jarPath);
		if (imgURL == null) {
			imgURL = getClass().getResource(relativePath);
			if (imgURL == null) {
				System.err.println("Couldn't find file: " + jarPath);
				return null;
			}
		}
		return new ImageIcon(imgURL, description);
	}

}
