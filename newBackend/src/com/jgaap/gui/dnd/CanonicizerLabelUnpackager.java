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

/**
 * This interface defines a single method inpackageLabel(CanonicizerPacket).
 * Classes implementing this interface are responsible for unpackaging
 * a CanonicizerPacket containing a CanonicizerLabel, processing the
 * transfer data as appropriate and doing something with the label.
 * 
 * @author Chuck Liddell
 *
 */
public interface CanonicizerLabelUnpackager {
	
	/**
	 * Take a CanonicizerPacket, unpackage it, process the transfer
	 * data, and pull out the CanonicizerLabel.
	 * 
	 * @param packet
	 * 		A CanonicizerPacket to be unpackaged
	 */
	public void unpackageLabel( CanonicizerPacket packet );
	
}
