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

import com.google.common.base.Objects;
import com.jgaap.generics.EventDriver;

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
public class Event {

    private String data;
    private EventDriver eventDriver;

    /** Create a new event given a character representation of the event **/
    public Event(char data, EventDriver eventDriver) {
        this.data = Character.toString(data);
        this.eventDriver = eventDriver;
    }
    
    /** Create a new event given a string representation of this event **/
    public Event(String data, EventDriver eventDriver) {
        this.data = data;
        this.eventDriver = eventDriver;
    }

    /**
     * Allows for equality comparison of two events. Two events are the same if
     * their string representations are the same
     **/
    @Override
    public boolean equals(Object o) {
        if(o instanceof Event) {
        	Event event = ((Event) o);
        	return Objects.equal(data, event.data) && Objects.equal(eventDriver, event.eventDriver);
        }
        return false;
    }

    /** Returns the String representation of this event **/
    public String getEvent() {
        return data+eventDriver;
    }
    
    public EventDriver getEventDriver() {
    	return eventDriver;
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
    	return Objects.hashCode(data, eventDriver);
    }

    @Override
    public String toString() {
        return data;
    }
}
