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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.common.collect.ImmutableSet;
import com.jgaap.JGAAPConstants;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;


/**
 * Uses function words as defined by Mosteller-Wallace in their Federalist
 * papers study.
 */
public class MWFunctionWordsEventDriver extends NaiveWordEventDriver {


    @Override
    public String displayName(){
    	return "MW Function Words";
    }
    
    @Override
    public String tooltipText(){
    	return "Function Words from Mosteller-Wallace";
    }

    @Override
    public String longDescription(){
    	return "Function Words from Mosteller-Wallace's study of the Federalist Papers";
    }
    
    @Override
    public boolean showInGUI(){
    	return true;
    }

    private static ImmutableSet<String> functionWords;

    static {
    	ImmutableSet.Builder<String> builder = ImmutableSet.builder();
    	BufferedReader reader = new BufferedReader(new InputStreamReader(MWFunctionWordsEventDriver.class.getResourceAsStream(JGAAPConstants.JGAAP_RESOURCE_PACKAGE+"MWfunctionwords.dat")));
    	String current;
    	try {
			while((current = reader.readLine())!=null) {
				builder.add(current.trim());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	functionWords = builder.build();
    }

    /** Creates EventSet using M-W function word list 
     * @throws EventGenerationException */
    @Override
    public EventSet createEventSet(char[] text) {
        EventSet words = super.createEventSet(text);
        EventSet eventSet = new EventSet();
        for(Event event : words){
        	String current = event.toString();
        	if(functionWords.contains(current)){
        		eventSet.addEvent(event);
        	}
        }
        return eventSet;
    }

}
