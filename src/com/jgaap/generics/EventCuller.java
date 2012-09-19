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
 * Event Culling abstract parent class.
 * Event Culling is any transformation on a List<EventSet> that results in
 * a List<EventSet>
 * 
 * @author John Noecker
 * @since 5.0.0
 */
public abstract class EventCuller extends Parameterizable implements Comparable<EventCuller>, Displayable {

	private static final List<EventCuller> EVENT_CULLERS = Collections.unmodifiableList(loadEventCullers());

    public abstract List<EventSet> cull(List<EventSet> eventSets) throws EventCullingException; 

    public abstract String displayName();
    public abstract String tooltipText();
    public abstract boolean showInGUI();
    public String longDescription() { return tooltipText(); }

    public int compareTo(EventCuller o){
    	return displayName().compareTo(o.displayName());
    }
    
	/**
	 * A read-only list of the EventCullers
	 */
	public static List<EventCuller> getEventCullers() {
		return EVENT_CULLERS;
	}

	private static List<EventCuller> loadEventCullers() {
		List<Object> objects = AutoPopulate.findObjects("com.jgaap.eventCullers", EventCuller.class);
		for(Object tmp : AutoPopulate.findClasses("com.jgaap.generics", EventCuller.class)){
			objects.addAll(AutoPopulate.findObjects("com.jgaap.eventCullers", (Class<?>)tmp));
		}
		List<EventCuller> cullers = new ArrayList<EventCuller>(objects.size());
		for (Object tmp : objects) {
			cullers.add((EventCuller) tmp);
		}
		Collections.sort(cullers);
		return cullers;
	}
}
