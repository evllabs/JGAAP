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

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

/**
 * Event Culling abstract parent class.
 * Event Culling is any transformation on a List<EventSet> that results in
 * a List<EventSet>
 * 
 * @author John Noecker
 * @since 5.0.0
 */
public abstract class EventCuller extends Parameterizable implements Comparable<EventCuller>, Displayable {

	private ImmutableSet<Event> events;
	
    public abstract Set<Event> train(List<EventSet> eventSets) throws EventCullingException; 

    public Set<Event> init(List<EventSet> eventSets) throws EventCullingException {
    	events = ImmutableSet.copyOf(train(eventSets));
    	return events;
    }
    
    public EventSet cull(EventSet eventSet) {
    	EventSet reducedEventSet = new EventSet();
    	for(Event event : eventSet){
    		if(events.contains(event)){
    			reducedEventSet.addEvent(event);
    		}
    	}
    	return reducedEventSet;
    }
    
    public String longDescription() { return tooltipText(); }

    public int compareTo(EventCuller o){
    	return displayName().compareTo(o.displayName());
    }
    
}
