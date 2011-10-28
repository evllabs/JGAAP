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
/**
 **/
package com.jgaap.generics;

/**
 * Class for canonicizers. As an abstract class, can only be instantiated
 * through subclasses. Legacy code inherited from WAY back.
 * 
 * @author unknown
 * @since 1.0
 */
public abstract class Canonicizer extends Parameterizable implements Comparable<Canonicizer>, Displayable {
	
	/**
	 * Simple method to return the display name of this Canonicizer, to be used in the GUI.
	 * 
	 * @return The human-readable name of this Canonicizer
	 */
	public abstract String displayName();
	
	/**
	 * Simple method to return the tooltip text of this Canonicizer, to be used in the GUI.
	 * 
	 * @return The human-readable tooltip text to display when this Canonicizer is moused over
	 */
	public abstract String tooltipText();

        public String longDescription() { return tooltipText(); }


	/**
	 * Simple method to indicate whether this Canonicizer should be displayed in the GUI.
	 * 
	 * @return Boolean flag indicating whether this Canonicizer should appear in the GUI
	 */
	public abstract boolean showInGUI();
	
	/**
	 * Overrides the equals method so that Canonicizer can be compared more easily. A
	 * canonicizer is equal to another Canonicizer if they share the same displayName.
	 * 
	 * @param o The object to compare this Canonicizer to. The object is cast using (Canonicizer)o.
	 */
	@Override
	public boolean equals( Object o ){
        if(o instanceof Canonicizer) {
    		return (this.displayName().equalsIgnoreCase(((Canonicizer)o).displayName()));
        }
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.displayName().toLowerCase().hashCode();
	}
	
    /**
     * Generic method for getting information. As yet used only by AddErrors,
     * and should use Parameterizable interface instead.
     */
    public String getExtraInfo() {
        return "";
    }

    /**
     * Generic canonicizer method (abstract). Process a document to convert it
     * from one form to another or to normalize/canonicize in some way, such as
     * stripping HTML, regularizing spelling, or normalizing whitespace. Legacy
     * code from WAY back.
     * 
     * @author unknown
     * @since 1.0
     * @param procText
     *            the text (array of characters) to be processed
     * @return an array of characters containing the processed text
     */

    abstract public char[] process(char[] procText) throws CanonicizationException;


    /**
     * Get a String representation of this Canonicizer.
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString(){
    	return displayName();
    }
    
    public int compareTo(Canonicizer o){
    	return displayName().compareTo(o.displayName());
    }
}
