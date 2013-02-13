/*
 * JGAAP -- a graphical program for stylometric authorship attribution
 * Copyright (C) 2009,2011 by Patrick Juola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jgaap.generics;
/**
 * @author Michael Ryan
 * @since 5.0.0
 */

public interface Displayable {

	/**
	 * Simple method to return the display name of this Displayable, to be used in the GUI.
	 * 
	 * @return The human-readable name of this Displayable
	 */
	public String displayName();
	
	/**
	 * Simple method to return the tooltip text of this Displayable, to be used in the GUI.
	 * 
	 * @return The human-readable tooltip text to display when this Displayable is moused over
	 */
	public String tooltipText();
	
	/**
	 * Simple method to indicate whether this Displayable should be displayed in the GUI.
	 * 
	 * @return Boolean flag indicating whether this Displayable should appear in the GUI
	 */
	public boolean showInGUI();
}
