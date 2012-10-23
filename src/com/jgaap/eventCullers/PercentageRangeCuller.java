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

import com.jgaap.generics.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Analyze only events whose relative frequency is in a percentage range across all documents.
 * This function uses the total occurrences across the corpus. 
 * 
 * Paramaters: minPercent (The lower bound of the percentage range in Double form)
 * 			   maxPercent (The upper bound of the percentage range in Double form)
 * 
 * If either parameter falls outside the 0.0 to 1.0 range, they are reduced or raised to either
 *   0.0 or 1.0 depending on where they fall out of the range.
 * If minPercent is greater than maxPercent, the two values are switched.
 * 
 * Default behaviour: The words whose relative frequency fall in the 25% to 75% range.
 * 
 * @author Amanda Kroft
 */
public class PercentageRangeCuller extends EventCuller{
	
	public PercentageRangeCuller() {
		super();
		addParams("minPercent", "min", "0.25", new String[] { "0.00", "0.01", "0.05", "0.10",
				"0.25", "0.50", "0.75"}, true);
		addParams("maxPercent", "max", "0.75", new String[] { "0.01", "0.05", "0.10",
				"0.25", "0.50", "0.75", "1.00"}, true);
	}
	
	@Override
    public List<EventSet> cull(List<EventSet> eventSets) throws EventCullingException {
        List<EventSet> results = new ArrayList<EventSet>();
        double minPercent, maxPercent;
        
        //Get/set minimum percentage
        if(!getParameter("minPercent").equals("")) {
        	try{
        		minPercent = Math.min(Math.max(Double.parseDouble(getParameter("minPercent")),0.0),1.0);
        		this.setParameter("minPercent", minPercent);
        	}catch(NumberFormatException e){
        		throw new EventCullingException("Paramater minPercent could not be parsed to a Double.");
        	}
        }
        else {
            minPercent = 0.25;
            this.setParameter("minPercent", minPercent);
        }

        //Get/set maximum percentage
        if(!getParameter("maxPercent").equals("")) {
        	try{
        		maxPercent = Math.max(Math.min(Double.parseDouble(getParameter("maxPercent")),1.0),0.0);
        		this.setParameter("maxPercent", maxPercent);
        	}catch(NumberFormatException e){
        		throw new EventCullingException("Paramater maxPercent could not be parsed to a Double.");
        	}
        }
        else {
            maxPercent = 0.75;
            this.setParameter("maxPercent",maxPercent);
        }
        
        //Make sure percents are in correct order
        Double holder;
        if(minPercent > maxPercent){
        	holder = minPercent;
        	minPercent = maxPercent;
        	maxPercent = holder;
        	this.setParameter("minPercent",minPercent);
        	this.setParameter("maxPercent",maxPercent);
        }
        
        //Create the histogram of frequencies of the events
        EventHistogram hist = new EventHistogram();
        for(EventSet oneSet : eventSets) {
            for(Event e : oneSet) {
                hist.add(e);
            }
        }
        
        //Run through all events and keep only the ones that fall within the percentage range
        for(EventSet oneSet : eventSets) {
            EventSet newSet = new EventSet();
            for(Event e : oneSet) {
            	if(hist.getRelativeFrequency(e) >= minPercent && hist.getRelativeFrequency(e) <= maxPercent){
            		newSet.addEvent(e);
            	}
            }
            results.add(newSet);
        }
        
        return results;
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
          "(e.g., the words whose relative frequncy is between 10% and 15% in the corpus). " +
          "The parameter minPercent is the lower bound of the events to be included, in Double form, (e.g. 0.10 in the example above), " +
          "while maxPercent is the upper bound of the events to be included, in Double form, (e.g. 0.15). " +
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
