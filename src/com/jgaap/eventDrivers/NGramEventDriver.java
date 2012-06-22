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
package com.jgaap.eventDrivers;

import com.jgaap.backend.EventDriverFactory;
import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;


/**
 * This event set is N-grams (parameterized as N) of an underlying event model
 * (parameterized as underlyingevents)
 **/
public class NGramEventDriver extends EventDriver {


    @Override
    public String displayName(){
    	return "Generic Event N-gram";
    }
    
    @Override
    public String tooltipText(){
    	return "(Should never be user-visible)";
    }

    @Override
    public String longDescription(){
    	return "Sliding window N-gram (N is a parameter) of underlying events (also a parameter).  Used as a generic N-gram event driver";
    }
    
    @Override
    public boolean showInGUI(){
    	return false;
    }

  /** Underlying EventSets from which Events are drawn. */
    public EventDriver underlyingevents = new NaiveWordEventDriver();
    /** Number of events per N-gram. */
    public int         N                = 2;
    /** Opening delimeter marking beginning of Event within N-gram. */
    public String      opendelim        = "(";
    /** Closing delimeter marking end of Event within N-gram. */
    public String      closedelim       = ")";

    /** Separator between Events. */
    public String      separator        = "-";                              ;

    /**
     * Put together the events in groups of N, using string concatenation For
     * now, the format is "(event1)-(event2)-(event3)-...(eventN) This may be
     * inefficient (high overhead), but it should be easily parsable.
     * @throws EventGenerationException 
     */
    @Override
    public EventSet createEventSet(Document ds) throws EventGenerationException {

        // Extract local field values based on parameter settings
        String param;
        // string parameters need no error checking
	param = getParameter("opendelim");
        if (!(param.equals("null")) && !(param.equals("")) ) {
            setopendelim(param);
        } else if (param.equals("null")) {
            setopendelim("");
        }  
	param = getParameter("closedelim");
        if (!(param.equals("null")) && !(param.equals("")) ) {
            setclosedelim(param);
        } else if (param.equals("null")) {
            setclosedelim("");
        }  
	param = getParameter("separator");
        if (!(param.equals("null")) && !(param.equals("")) ) {
            setseparator(param);
        } else if (param.equals("null")) {
            setseparator("");
        }  
        // but numeric and object ones do
        if (!(param = (getParameter("N"))).equals("")) {
            try {
                int value = Integer.parseInt(param);
                setN(value);
            } catch (NumberFormatException e) {
                System.out.println("Warning: cannot parse N:" + param
                        + " as int");
                System.out.println(" -- Using default value (2)");
                setN(2);
            }
        }

        if (!(param = (getParameter("underlyingEvents"))).equals("")) {
            try {
				setEvents(EventDriverFactory.getEventDriver(param));
            } catch (Exception e) {
                System.out.println("Error: cannot create EventDriver " + param);
                System.out.println(" -- Using NaiveWordEventSet");
                setEvents(new NaiveWordEventDriver());
            }
        }
        EventSet es = underlyingevents.createEventSet(ds);
        EventSet newEs = new EventSet();
        newEs.setAuthor(es.getAuthor());
        newEs.setNewEventSetID(es.getAuthor());
        String s;

        /**
         * Start at event N-1, then put the previous N together as a string. s
         * holds each subevent, using theEvent to put them together
         */
        // watch off-by-one error at end of event stream
        for (int i = N; i <= es.size(); i++) {

            StringBuilder stringBuilder = new StringBuilder();
            // watch off-by-one error at end of event stream
            for (int j = i - N; j < i; j++) {
                s = es.eventAt(j).getEvent();
                stringBuilder.append(opendelim).append(s).append(closedelim);
                if (j != i - 1) {
                    stringBuilder.append(separator);
                }
            }
            newEs.addEvent(new Event(stringBuilder.toString()));
        }
        return newEs;
    }

    /** Returns closing delimeter */
    public String getclosedelim() {
        return closedelim;
    }

    /**
     * Get EventDriver for relevant Events *
     * 
     * @return underlying EventDriver
     */
    public EventDriver getEvents() {
        return underlyingevents;
    }

    /* Parameter settings */
    /**
     * Get number of Events per N-gram.
     * 
     * @return number of Events
     */
    public int getN() {
        return N;
    };

    /** Returns opening delimeter */
    public String getopendelim() {
        return opendelim;
    }

    /** Returns separating delimeter */
    public String getseparator() {
        return separator;
    };


    /** Sets opening delimeter */
    public void setclosedelim(String closedelim) {
        this.closedelim = closedelim;
    }

    /**
     * Set EventDriver for relevant Events *
     * 
     * @param underlyingevents
     *            underlying EventDriver
     */
    public void setEvents(EventDriver underlyingevents) {
        this.underlyingevents = underlyingevents;
    }

    /**
     * Set number of Events per N-gram.
     * 
     * @param N
     *            number of Events
     */
    public void setN(int N) {
        this.N = N;
    }

    /** Sets opening delimeter */
    public void setopendelim(String opendelim) {
        this.opendelim = opendelim;
    }

    /** Sets opening delimeter */
    public void setseparator(String separator) {
        this.separator = separator;
    }

}
