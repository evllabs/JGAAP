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

import com.google.common.collect.ImmutableMap;
import com.jgaap.backend.API;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.NumericTransformationEventDriver;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;
import com.jgaap.util.NumericEventSet;

/**
 * Corpus frequencies taken from English Lexicon Project. Converts each word
 * (using table) to the the (log-scaled) frequency in which that word appears in
 * the general purpose HAL corpus as recorded in the ELP database. Obviously
 * English-only, and obviously incomplete; words that are not in the database
 * are silently removed.
 */

public class FreqEventDriver extends NumericTransformationEventDriver {

    @Override
    public String displayName(){
    	return "Lexical Frequencies";
    }
    
    @Override
    public String tooltipText(){
    	return "(Log Scaled) HAL frequencies from English Lexicon Project";
    }
    
    @Override
    public boolean showInGUI(){
    	return API.getInstance().getLanguage().getLanguage().equalsIgnoreCase("english");
    }
    
    private static ImmutableMap<String, String> halFrequencies = getTransformationMap("ELPfreq.dat");
    private NaiveWordEventDriver wordEventDriver = new NaiveWordEventDriver();


    @Override
    public NumericEventSet createEventSet(char[] text) throws EventGenerationException {
        EventSet words = wordEventDriver.createEventSet(text);
        NumericEventSet eventSet = new NumericEventSet();
        for(Event event : words) {
        	String current = event.toString();
        	if(halFrequencies.containsKey(current)) {
        		eventSet.addEvent(new Event(halFrequencies.get(current), this));
        	}
        }
        return eventSet;
    }

}
