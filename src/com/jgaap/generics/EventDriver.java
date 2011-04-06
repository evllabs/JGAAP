// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.generics;
import javax.swing.*;

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

        public String longDescription() { return tooltipText(); }

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
    
    abstract public GroupLayout getGUILayout(JPanel panel);

}


