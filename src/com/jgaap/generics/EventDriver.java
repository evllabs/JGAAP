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

import java.util.Vector;
import java.util.Arrays;
import com.jgaap.gui.jgaapGUI;

/**
 * Class for EventSet factories. As an abstract class, can only be instantiated
 * through subclasses. Legacy code inherited from WAY back.
 * 
 * @author unknown
 * @since 1.0
 */
public abstract class EventDriver extends Parameterizable implements Comparable<EventDriver> {
	
	public abstract String displayName();

	public abstract String tooltipText();

	public abstract boolean showInGUI();

    /**
     * Creates an EventSet from a given DocumentSet after preprocessing.
     * 
     * @since 1.0
     * @param ds
     *            the DocumentSet to be Event-ified
     * @return the EventSet containing the Events from the document(s)
     */

    abstract public EventSet createEventSet(DocumentSet ds);

    /**
     * EventSet creation method for multiple DocumentSets. Creates a vector of
     * EventSets from a Vector of DocumentSets. By default, simply creates one
     * EventSet independently per DocumentSet, but this can be overridden as
     * necessary if Events are not independent (for instance, if we need
     * frequency information across all documents.
     * 
     * TODO: Make this method "final", as it is only a wrapper for the method
     * which takes a single DocumentSet, and many of the JGAAP features
     * depend on this fact (but right now, MostCommonEventSet has extended it)
     * 
     * @since 1.0
     * @param ds
     *            the DocumentSet Vector to be Event-ified
     * @return the EventSet Vector containing the Events from the document(s)
     */
    public Vector<EventSet> createEventSet(Vector<DocumentSet> ds) {

		EventSet v[] = new EventSet[ds.size()];

		// DELETEME
		/* System.out.println("createEventSet() called on ds " + ds.size());
		
        for (int i = 0; i < ds.size(); i++) {
            EventDriverThread edt = new EventDriverThread(this, ds.elementAt(i), i);
            (new Thread(edt)).start();
        }

        while(vsize < ds.size()) // Busy wait on the threads
			Thread.yield(); // FIXME: PJR 09/14/2009 This is a bad solution to the threading problem; */
		
		for (int i=0; i < ds.size(); i++) {
			EventSet es = createEventSet(ds.elementAt(i));
			v[i] = es;
			// events.addEventSet(i, es);
			jgaapGUI.incProgress();
		}
        
        return new Vector<EventSet>(Arrays.asList(v));
    }

    
    /**
     * Add an event set to the list of event sets being
     * stored by this event driver.  This is used to 
     * assist with threading.
     * @param i The position to add the new event set
     * @param e The event set to be added
     */
   /* public void addEventSet(int i, EventSet e) {
    	v[i] = e;
    	vsize++;
    }*/
    
    public int compareTo(EventDriver o){
    	return displayName().compareTo(o.displayName());
    }
}


