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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *  A set of Events
 *  In JGAAP, an "event" is a token or feature that will be extracted from the 
 *  document as an atomic unit.
 */
public class EventSet implements Iterable<Event> {

    /** Events are currently stored as a ArrayList of Events */
    private List<Event> events;
    /** The author, if any, is stored as a String. May be null. */
    private String       author;
    /** Name of document from which Events were derived. */
    private String       docName;
    /** ID for debugging purposes */
    private String       EventSetID;
    /** static ID number for the next EventSet created */
    private static int   gensym      = 0;
    
    private Map<AnalysisDriver, List<Pair<String,Double>>> results = new HashMap<AnalysisDriver, List<Pair<String,Double>>>();

    private EventHistogram histogram = new EventHistogram();
    
    /** Creates a new, empty list of events. Will also include unique ID */
    public EventSet() {
        events = new ArrayList<Event>();
        setAuthor("default");
        setNewEventSetID("default");
    }
    
    /**
     * Creates a empty list of event of the specified size
     * @param size
     */
    public EventSet(int size) {
        events = new ArrayList<Event>(size);
        setAuthor("default");
        setNewEventSetID("default");
    }

    /** Creates a new list of events given a previously created list of events **/
    public EventSet(List<Event> evts) {
        events = new ArrayList<Event>(evts);
        setAuthor("default");
        setNewEventSetID("default");
        for(Event event : events){
        	histogram.add(event);
        }
    }

    /**
     * Construct new Event set (with empty vector) of known author
     * 
     * @param Author
     *            the name of the author
     */
    public EventSet(String Author) {
        events = new ArrayList<Event>();
        setAuthor(Author);
        setNewEventSetID(Author);
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
    	histogram.add(event);
    }
    
    public void addEvents(List<Event> events){
    	this.events.addAll(events);
    	for(Event event : events){
    		histogram.add(event);
    	}
    }

    /** return the Author associated with any EventSet */
    public String getAuthor() {
        return author;
    }

    /** Return (document) name associated with EventSet */
    public String getDocumentName() {
        return docName;
    }

    /** return the ID associated with any EventSet */
    public String getEventSetID() {
        return EventSetID;
    }

    /**
     * Sets the author of the current event set. There should be a better way to
     * pass authors through the processing stages...
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /** Set (document) name associated with EventSet */
    public void setDocumentName(String s) {
        docName = s;
    }

    /** Duplicate EventSetID of a previous EventSet */
    public void setEventSetID(String EventSetID) {
        this.EventSetID = EventSetID;
    }

    /** Creates a new, unique EventSetID for this EventSet */
    public void setNewEventSetID(String EventSetID) {
        this.EventSetID = EventSetID + Integer.valueOf(gensym).toString();
        gensym++;
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
        return new EventSet(events.subList(start, length));
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
        return builder.substring(0, builder.length()-2);
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
    
    public Set<Event> uniqueEvents(){
    	return histogram.events();
    }
    
    public EventHistogram getHistogram(){
    	return histogram;
    }

	public Iterator<Event> iterator() {
		return events.iterator();
	}
	
	public void addResults(AnalysisDriver analysisDriver, List<Pair<String, Double>> result){
		results.put(analysisDriver, result);
	}
	
	public Map<AnalysisDriver, List<Pair<String, Double>>> getResults(){
		return results;
	}
	
	public List<Pair<String, Double>> getResult(AnalysisDriver analysisDriver){
		return results.get(analysisDriver);
	}
}
