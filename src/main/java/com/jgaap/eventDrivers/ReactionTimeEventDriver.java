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
 * Reaction times taken from English Lexicon Project. Converts each word (using
 * table) to the time it takes to perform lexical decision on that word in the
 * ELP database. Obviously English-only, and obviously incomplete; words that
 * are not in the database are silently removed.
 */

public class ReactionTimeEventDriver extends NumericTransformationEventDriver {

    @Override
    public String displayName(){
    	return "Lexical Decision Reaction Times";
    }
    
    @Override
    public String tooltipText(){
    	return "Reaction times from English Lexicon Project";
    }
    
    @Override
    public boolean showInGUI(){
    	return API.getInstance().getLanguage().getLanguage().equalsIgnoreCase("english");
    }

    private static ImmutableMap<String, String> reactionTimes = getTransformationMap("ELPrt.dat"); 
    private NaiveWordEventDriver wordEventDriver = new NaiveWordEventDriver();

    @Override
    public NumericEventSet createEventSet(char[] text) throws EventGenerationException {
        EventSet words = wordEventDriver.createEventSet(text);
        NumericEventSet eventSet = new NumericEventSet();
        for(Event event : words) {
        	String currentString = event.toString();
        	if(reactionTimes.containsKey(currentString)){
        		eventSet.addEvent(new Event(reactionTimes.get(currentString), this));
        	}
        }
        return eventSet;
    }

}
