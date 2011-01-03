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

import java.util.*;

/**
 * Class supporting basic histogram operations on Events. Uses a Hashtable to
 * keep track of events and their counts, and provides standard access methods.
 * 
 * @author Juola
 * @since 1.0
 */
public class EventHistogram implements Iterable<Event> {

	/** The Hashtable storing the histogram of interest */
	private Hashtable<Event, Integer> theHist;
	/**
	 * The current number of entries in the histogram. Zero is, of course, an
	 * empty histogram with no data.
	 */
	private long numTokens;

	/** Construct a new (empty) histogram */
	public EventHistogram() {
		theHist = new Hashtable<Event, Integer>(1001);
		numTokens = 0;
	}

	/**
	 * Construct a new (empty) histogram of named capacity
	 * 
	 * @param initialCapacity
	 *            the initial capacity
	 */
	public EventHistogram(int initialCapacity) {
		theHist = new Hashtable<Event, Integer>(initialCapacity);
		numTokens = 0;
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
			theHist.put(e, new Integer(1));
		} else {
			theHist.put(e, new Integer(v.intValue() + 1));
		}
		numTokens++;
	}

	/** Clear the histogram of all data */
	public void clear() {
		theHist.clear();
		numTokens = 0;
	}

	/** Return all Events in a histogram */
	public Enumeration<Event> events() {
		return theHist.keys();
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
	 * Revised 9/5/09 by PMJ Some distances (esp KL-distance) can't handle zero
	 * probability well; they return infinite distances. So we cheat : how
	 * infrequent an event would have been ROUNDED DOWN to zero if we were
	 * sampling from a much larger sample?
	 * 
	 * Answer : half of the relative frequency of an event with absolute
	 * frequency 1. So we simply return 1.0 / (numTokens*2)
	 */
	public double getRelativeFrequencySmoothing() {
		double result = 0.000001;
		int i = 1;
		if (numTokens != 0) {
			while ((result = (i / numTokens * 2)) == 0.0) {
				i *= 10;
			}
		}
		return result;
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
	public long getNTokens() {
		return numTokens;
	}

	/**
	 * Calculate the type size of a histogram
	 * 
	 * @return the number of distinct Event types stored in a histogram
	 */
	public long getNTypes() {
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
    @SuppressWarnings({"rawtypes", "unchecked"})
    public List<Pair<Event, Integer> > getSortedHistogram() {
        List list = new LinkedList(theHist.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
            return -((Comparable)((Map.Entry)(o1)).getValue()).compareTo(((Map.Entry)(o2)).getValue());
        }
        });

        List<Pair<Event, Integer> > result = new ArrayList<Pair<Event, Integer> >();
        for(Iterator it = list.iterator() ; it.hasNext() ; ) {
            Map.Entry entry = (Map.Entry)it.next();
            result.add(new Pair(entry.getKey(), entry.getValue(), 2));
        }
        return result;
    }
}
