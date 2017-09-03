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
package com.jgaap.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *  A set of Events
 *  In JGAAP, an "event" is a token or feature that will be extracted from the 
 *  document as an atomic unit.
 */
public class EventSet implements Iterable<Event> {

    /** Events are currently stored as a ArrayList of Events */
    private List<Event> events;
        
    /** Creates a new, empty list of events. Will also include unique ID */
    public EventSet() {
        events = new ArrayList<Event>();
    }
    
    /**
     * Creates a empty list of event of the specified size
     * @param size
     */
    public EventSet(int size) {
        events = new ArrayList<Event>(size);
    }

    /** Creates a new list of events given a previously created list of events **/
    public EventSet(List<Event> evts) {
        events = new ArrayList<Event>(evts);
    }

    /**
     * Returns the event at a given index
     * 
     * @param index
     *            the index of interest
     * @return the Event at that index
     */
    public Event eventAt(int index) {
        return events.get(index);
    }
    
    public void addEvent(Event event){
    	events.add(event);
    }
    
    public void addEvents(List<Event> events){
    	this.events.addAll(events);
    }
    
    public void addEvents(EventSet eventSet) {
    	this.events.addAll(eventSet.events);
    }

    /** Returns the total number of events in the set **/
    public int size() {
        return events.size();
    }

    /**
     * Returns a subset of events given a starting index of an event and the
     * number of events wanted in the returned list.
     **/
    public EventSet subset(int start, int length) {
        return new EventSet(subList(start, length));
    }

    /**
     * Returns a sublist of events given a starting index of an event and the
     * number of events wanted in the returned list.
     **/
    public List<Event> subList(int start, int length) {
        /*
         * JIN - 07/31/2008 : Added array bounds checking. If the requested
         * subset would run beyond the end of the current list, only elements
         * from start until the end of the list are returned. If start is beyond
         * the end of the list, an empty list is returned If start > length, an
         * empty list will be returned
         */
        if (length > events.size()) {
            length = events.size();
        }
        if (start > events.size()) {
            start = length; // subList() will now return an empty list
        }
        if (start > length) {
            start = length; // subList() will now return an empty list
        }
        return events.subList(start, length);
    }
    
    /**
     * Returns the string representation of this event set, which is just a
     * comma separated list of each individual event
     **/
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for(Event event : events){
        	builder.append(event).append(", ");
        }
        if(builder.length() > 1)
        	return builder.substring(0, builder.length()-2);
        else
        	return builder.toString();
    }
    
    @Override
    public boolean equals(Object o) {
    	if(o instanceof EventSet){
    		return this.events.equals(((EventSet)o).events);
    	}
    	return false;
    }
    
    @Override
    public int hashCode(){
    	return events.hashCode();
    }

	public Iterator<Event> iterator() {
		return events.iterator();
	}
}
