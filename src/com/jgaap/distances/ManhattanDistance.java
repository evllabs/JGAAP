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
package com.jgaap.distances;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventMap;

/**
 * Histogram distance using L1 metric,(defined as D(x,y) = sum (|xi -yi|). This
 * is YA distance for Nearest Neighbor algorithms
 * 
 * @author Juola
 * @version 1.0
 */

public class ManhattanDistance extends DistanceFunction {
	public String displayName(){
	    return "Manhattan Distance";
	}

	public String tooltipText(){
	    return "Manhattan Distance (aka taxicab or Minkowski L1 distance)";
	}

	public boolean showInGUI(){
	    return true;
	}
    /**
     * Returns Manhattan or taxicab distance between event sets es1 and es2.
     * 
     * @param es1
     *            The first EventSet
     * @param es2
     *            The second EventSet
     * @return the Manhattan distance between them
     */
    @Override
    public double distance(EventMap unknownEventMap, EventMap knownEventMap) {
        double distance = 0.0;
        Set<Event> events = new HashSet<Event>(unknownEventMap.uniqueEvents());        
        events.addAll(knownEventMap.uniqueEvents());
        
        for(Event event : events){
        	distance += Math.abs(unknownEventMap.relativeFrequency(event)-knownEventMap.relativeFrequency(event));
        }

        return distance;
    }
    
    public double distance(List<Double> e1, List<Double> e2) {
    	double distance = 0.0;
    	for(int i = 0; i < e1.size();i++){
    		distance += Math.abs(e1.get(i)-e2.get(i));
    	}
    	return distance;
    }
}
