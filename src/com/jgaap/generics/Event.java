// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.generics;

/*
 * Event.java Caleb Astey - 2007
 */

/**
 *  In JGAAP, an "event" is a token or feature that will be extracted from the 
 *  document as an atomic unit.
 * 
 * @author Astey
 * @since 1.0
 */
public class Event implements Comparable<Object> {

    private String data;

    public Event() {
        data = "";
    }

    /** Create a new event given a character representation of the event **/
    public Event(Character data) {
        char[] c = new char[1];
        c[0] = data.charValue();
        this.data = new String(c);
    }

    /** Create a new event given a string representation of this event **/
    public Event(String data) {
        this.data = new String(data);
    }

    /**
     * Overridden - from Comparable interface. Allows for comparison of two
     * events.
     **/
    public int compareTo(Object o) {
        return data.compareTo(((Event) o).data);
    }

    /**
     * Allows for equality comparison of two events. Two events are the same if
     * their string representations are the same
     **/
    @Override
    public boolean equals(Object o) {
        if(o instanceof Event) {
            return data.equals(((Event) o).data);
        }
        return false;
    }

    /** Returns the String representation of this event **/
    public String getEvent() {
        return data;
    }

    /**
     * When overriding equals(), the hashCode() function must also be
     * overridden. Since two events are equal if their string representations
     * are equal, then it is sufficient to say that two events are equal if the
     * hash of their string representations are equal. This comment is longer
     * than the code itself
     **/
    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public String toString() {
        return data;
    }
}
