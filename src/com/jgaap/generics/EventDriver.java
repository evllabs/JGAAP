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
package com.jgaap.generics;

/**
 * Class for EventSet factories. As an abstract class, can only be instantiated
 * through subclasses. Legacy code inherited from WAY back.
 * 
 * @author unknown
 * @since 1.0
 */
public abstract class EventDriver extends Parameterizable implements Comparable<EventDriver>, Displayable {
	
	public abstract String displayName();

	public abstract String tooltipText();

	public abstract boolean showInGUI();

    /**
     * Creates an EventSet from a given DocumentSet after preprocessing.
     * 
     * @since 1.0
     * @param doc
     *            the DocumentSet to be Event-ified
     * @return the EventSet containing the Events from the document(s)
     */

    abstract public EventSet createEventSet(Document doc);

    
    public int compareTo(EventDriver o){
    	return displayName().compareTo(o.displayName());
    }
}


