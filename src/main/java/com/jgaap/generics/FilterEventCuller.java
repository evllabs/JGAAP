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
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

/**
 * Filter Event Culling abstract parent class.
 * A filter on a EventSet that results in a EventSet
 * containing only Events found in the Set<Event> returned
 * by train(List<EventSet>)
 * 
 * @author Michael Ryan
 * @since 7.0.0
 */
public abstract class FilterEventCuller extends EventCuller {

	private ImmutableSet<Event> events;
	
    public abstract Set<Event> train(List<EventSet> eventSets) throws EventCullingException; 

    public void init(List<EventSet> eventSets) throws EventCullingException {
    	events = ImmutableSet.copyOf(train(eventSets));
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

    public int compareTo(FilterEventCuller o){
    	return displayName().compareTo(o.displayName());
    }
    
}
