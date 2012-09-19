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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jgaap.backend.AutoPopulate;

/**
 * Class for EventSet factories. As an abstract class, can only be instantiated
 * through subclasses. Legacy code inherited from WAY back.
 * 
 * @author unknown
 * @since 1.0
 */
public abstract class EventDriver extends Parameterizable implements Comparable<EventDriver>, Displayable {
	
	private static final List<EventDriver> EVENT_DRIVERS = Collections.unmodifiableList(loadEventDrivers());
	
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

    abstract public EventSet createEventSet(Document doc) throws EventGenerationException;

    
    public int compareTo(EventDriver o){
    	return displayName().compareTo(o.displayName());
    }

	/**
	 * A read-only list of the EventDrivers
	 */
	public static List<EventDriver> getEventDrivers() {
		return EVENT_DRIVERS;
	}

	private static List<EventDriver> loadEventDrivers() {
		List<Object> objects = AutoPopulate.findObjects("com.jgaap.eventDrivers", EventDriver.class);
		for(Object tmp : AutoPopulate.findClasses("com.jgaap.generics", EventDriver.class)){
			objects.addAll(AutoPopulate.findObjects("com.jgaap.eventDrivers", (Class<?>) tmp));
		}
		List<EventDriver> eventDrivers = new ArrayList<EventDriver>(objects.size());
		for (Object tmp : objects) {
			eventDrivers.add((EventDriver) tmp);
		}
		Collections.sort(eventDrivers);
		return eventDrivers;
	}
}


