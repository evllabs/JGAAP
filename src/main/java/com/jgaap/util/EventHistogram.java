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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Class supporting basic histogram operations on Events. Uses a Hashtable to
 * keep track of events and their counts, and provides standard access methods.
 * 
 * @author Juola
 * @since 1.0
 */
public class EventHistogram implements Iterable<Event> {

	/** The Hashtable storing the histogram of interest */
	private Map<Event, Integer> theHist;
	/**
	 * The current number of entries in the histogram. Zero is, of course, an
	 * empty histogram with no data.
	 */
	private int numTokens;

	/** Construct a new (empty) histogram */
	public EventHistogram() {
		theHist = new HashMap<Event, Integer>(1001);
		numTokens = 0;
	}

	/**
	 * Construct a new (empty) histogram of named capacity
	 * 
	 * @param initialCapacity
	 *            the initial capacity
	 */
	public EventHistogram(int initialCapacity) {
		theHist = new HashMap<Event, Integer>(initialCapacity);
		numTokens = 0;
	}

	/**
	 * Construct a histogram from an EventSet
	 * 
	 * @param eventSet the Event set to populate the histogram
	 */
	 public EventHistogram(EventSet eventSet) {
		 this();
		 for(Event event : eventSet) {
			 this.add(event);
		 }
	 }
	
	/**
	 * Add an Event to the histogram
	 * 
	 * @param e
	 *            the Event to add
	 */
	public void add(Event e) {
		Integer v = theHist.get(e);
		if (v == null) {
			theHist.put(e, Integer.valueOf(1));
		} else {
			theHist.put(e, Integer.valueOf(v.intValue() + 1));
		}
		numTokens++;
	}

	/** Clear the histogram of all data */
	public void clear() {
		theHist.clear();
		numTokens = 0;
	}

	/** Return all Events in a histogram */
	public Set<Event> events() {
		return theHist.keySet();
	}

	/**
	 * Calculate the absolute frequency of a given Event
	 * 
	 * @param e
	 *            the Event to calculate
	 * @return the exact number of times that Event has been added
	 */
	public int getAbsoluteFrequency(Event e) {
		Integer v = theHist.get(e);
		if (v == null) {
			return 0;
		} else {
			return v.intValue();
		}
	}

	/**
	 * Calculate the relative frequency of a given Event times 100000
	 * 
	 * @param e
	 *            the Event to calculate
	 * @return 100000 times the relative frequency of the named Event
	 */
	public double getNormalizedFrequency(Event e) {
		Integer v = theHist.get(e);
		if (v == null) {
			return 0.0;
		} else {
			return ((v.doubleValue() * 100000.0) / (numTokens));
		}
	}

	/**
	 * Calculate the relative frequency of a given Event
	 * 
	 * @param e
	 *            the Event to calculate
	 * @return the proportion of Events in the histogram of this type
	 */
	public double getRelativeFrequency(Event e) {
		Integer v = theHist.get(e);
		if (v == null) {
			return 0.0;
		} else {
			return v.doubleValue() / numTokens;
		}
	}

	/**
	 * Calculate the token size of a histogram
	 * 
	 * @return the number of Events stored in a histogram
	 */
	public int getNTokens() {
		return numTokens;
	}

	/**
	 * Calculate the type size of a histogram
	 * 
	 * @return the number of distinct Event types stored in a histogram
	 */
	public int getNTypes() {
		return theHist.size();
	}

	@Override
	public String toString() {
		return theHist.toString();
		/*
		 * StringBuffer out = new StringBuffer(); for(Enumeration<Event> keys =
		 * theHist.keys();keys.hasMoreElements();){ Event e =
		 * keys.nextElement(); out.append(e.toString()+" = "+theHist.get(e)); }
		 * return out.toString();
		 */
	}

	public Iterator<Event> iterator() {
		return theHist.keySet().iterator();
	}

    /**
     * Return a list of pairs of the form [event, frequency] sorted in descending order
     * by the frequency of the event.  Hence, list[0] is the most frequent event, while
     * list[size-1] is the least.
     * @return The above-described list of events.
     */
	public List<Pair<Event, Integer>> getSortedHistogram() {
		List<Entry<Event, Integer>> list = new ArrayList<Entry<Event, Integer>>(theHist.entrySet());
		Collections.sort(list, new Comparator<Entry<Event, Integer>>() {
			public int compare(Entry<Event, Integer> o1, Entry<Event, Integer> o2) {
				return -(o1.getValue().compareTo(o2.getValue()));
			}
		});

		List<Pair<Event, Integer>> result = new ArrayList<Pair<Event, Integer>>(list.size());
		for (Entry<Event, Integer>entry : list) {
			result.add(new Pair<Event, Integer>(entry.getKey(), entry.getValue(), 2));
		}
		return result;
	}
}
