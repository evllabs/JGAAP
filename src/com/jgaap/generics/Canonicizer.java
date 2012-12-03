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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jgaap.backend.AutoPopulate;

/**
 * Class for canonicizers. As an abstract class, can only be instantiated
 * through subclasses. Legacy code inherited from WAY back.
 * 
 * @author unknown
 * @since 1.0
 */
public abstract class Canonicizer extends Parameterizable implements Comparable<Canonicizer>, Displayable {
	
	private static List<Canonicizer> CANONICIZERS;
	
    public String longDescription() { return tooltipText(); }
	
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
     * Generic canonicizer method (abstract). Process a document to convert it
     * from one form to another or to normalize/canonicize in some way, such as
     * stripping HTML, regularizing spelling, or normalizing whitespace. Legacy
     * code from WAY back.
     * 
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

	/**
	 * A read-only list of the Canonicizers
	 */
	public static List<Canonicizer> getCanonicizers() {
		if(CANONICIZERS==null){
			CANONICIZERS = Collections.unmodifiableList(loadCanonicizers());
		}
		return CANONICIZERS;
	}

	private static List<Canonicizer> loadCanonicizers() {
		List<Object> objects = AutoPopulate.findObjects("com.jgaap.canonicizers", Canonicizer.class);
		for(Object tmp : AutoPopulate.findClasses("com.jgaap.generics", Canonicizer.class)){
			objects.addAll(AutoPopulate.findObjects("com.jgaap.canonicizers", (Class<?>)tmp));
		}
		List<Canonicizer> canonicizers = new ArrayList<Canonicizer>(objects.size());
		for (Object tmp : objects) {
			canonicizers.add((Canonicizer)tmp);
		}
		Collections.sort(canonicizers);
		return canonicizers;
	}
}
