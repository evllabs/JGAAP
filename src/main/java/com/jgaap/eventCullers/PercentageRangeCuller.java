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
package com.jgaap.eventCullers;

import java.util.List;

import com.jgaap.generics.EventCuller;
import com.jgaap.generics.EventCullingException;
import com.jgaap.util.Event;
import com.jgaap.util.EventHistogram;
import com.jgaap.util.EventSet;

/**
 * Analyze only events whose relative frequency is in a percentage range across all documents.
 * This function uses the total occurrences across the corpus. 
 * 
 * Parameters: minPercent (The lower bound of the percentage range in Double form)
 * 			   maxPercent (The upper bound of the percentage range in Double form)
 * 
 * If either parameter falls outside the 0.0 to 1.0 range, they are reduced or raised to either
 *   0.0 or 1.0 depending on where they fall out of the range.
 * If minPercent is greater than maxPercent, the two values are switched.
 * 
 * Default behavior: The words whose relative frequency fall in the 0.25% to 0.75% range.
 * 
 * @author Amanda Kroft
 */
public class PercentageRangeCuller extends EventCuller{
	
	private double minPercent;
	private double maxPercent;
	private EventHistogram hist;
	
	public PercentageRangeCuller() {
		super();
		addParams("minPercent", "min", "0.0025", new String[] { "0.00", "0.0025", "0.005", "0.0075", 
				"0.01", "0.05", "0.10"}, true);
		addParams("maxPercent", "max", "0.0075", new String[] { "0.0025", "0.005", "0.0075", "0.01", 
				"0.05", "0.10", "1.0"}, true);
	}
	
	@Override 
	public EventSet cull(EventSet eventSet) {
		EventSet newEventSet = new EventSet();
		for(Event event : eventSet) {
			if(hist.getRelativeFrequency(event) >= minPercent && hist.getRelativeFrequency(event) <= maxPercent){
				newEventSet.addEvent(event);
			}
		}
		return newEventSet;
	}
	
	@Override
    public void init(List<EventSet> eventSets) throws EventCullingException {
        
        //Get/set minimum percentage
        minPercent = Math.min(Math.max(getParameter("minPercent",0.0025),0.0),1.0);

        //Get/set maximum percentage
   		maxPercent = Math.max(Math.min(getParameter("maxPercent",0.0075),1.0),0.0);
        
        //Make sure percents are in correct order
        Double holder;
        if(minPercent > maxPercent){
        	holder = minPercent;
        	minPercent = maxPercent;
        	maxPercent = holder;
        }
        
        //Set global "variables" to new values
        this.setParameter("minPercent", minPercent);
        this.setParameter("maxPercent", maxPercent);
        
        //Create the histogram of frequencies of the events
        hist = new EventHistogram();
        for(EventSet oneSet : eventSets) {
            for(Event e : oneSet) {
                hist.add(e);
            }
        }
	}
	
    @Override
    public String displayName() {
        return "Percentage Range Event Culler";  //To change body of implemented methods use File | Settings | File Templates.
    }
    
    @Override
    public String tooltipText() {
        return "Analyze only the words whose relative frequency is between X% and Y%";  //To change body of implemented methods use File | Settings | File Templates.
    }
    
    @Override
    public String longDescription() {
        return "Analyze only events whose relative frequency is in a percentage range across all documents " +
          "(e.g., the words whose relative frequncy is between 0.10% and 0.15% in the corpus). " +
          "The parameter minPercent is the lower bound of the events to be included, in Double form, (e.g. 0.0010 in the example above), " +
          "while maxPercent is the upper bound of the events to be included, in Double form, (e.g. 0.0015).\n" +
          "Any floating point value can be entered which can be stored in the Java Double data type.\n" +
          "If either parameter falls outside the 0.0 to 1.0 range, they are reduced or raised to be either 0.0 or 1.0 depending on " +
          "where they fall out of the range. If minPercent is greater than maxPercent, the two values are switched.";
    }
    
    @Override
    public boolean showInGUI() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
    
    public String toString(){
    	return "Percentage Range Culler with parameter values:\nminPercent = " + getParameter("minPercent") + "\nmaxPercent = " + getParameter("maxPercent");
    }
}
