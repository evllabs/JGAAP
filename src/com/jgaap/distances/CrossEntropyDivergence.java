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
package com.jgaap.distances;

import com.jgaap.generics.DivergenceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventMap;

/**
 * Cross-entropy "divergence" for Nearest Neighbor. It's actually a rather poor
 * distance as it is not only non-commutative, but it isn't zero on identical
 * histograms.
 * 
 * Note: This is a faster implementation of Cross Entropy than the built-in version.
 * 
 * @author Michael Ryan
 * @version 1.0
 */
public class CrossEntropyDivergence extends DivergenceFunction {
	public String displayName(){
	    return "RN Cross Entropy"+getDivergenceType();
	}

	public String tooltipText(){
	    return "Ryan-Noecker Cross Entropy (Faster)";
	}

	public boolean showInGUI(){
	    return true;
	}
    /**
     * Returns cross-entropy between event sets es1 and es2
     * 
     * @param es1
     *            The first EventSet
     * @param es2
     *            The second EventSet
     * @return the cross-entropy H(es1,es2)
     */
    @Override
    public double divergence(EventMap unknownEventMap, EventMap knownEventMap) {
        double distance = 0.0;

       for(Event event : knownEventMap.uniqueEvents()) {
    	   distance += -1 * (unknownEventMap.relativeFrequency(event) * Math.log(knownEventMap.relativeFrequency(event))) ;            		
        }
        return distance;
    }

	
}
