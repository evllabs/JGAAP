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

import java.util.Set;

import com.google.common.collect.Sets;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.util.Event;
import com.jgaap.util.Histogram;

/**
 * Cosine Distance or normalized dot product. This is YA distance for Nearest
 * Neighbor algorithms, based on John's research at JHU. NOTE: The cosine
 * distance was modified slightly as we need to make it nonnegative and we want
 * smaller distances to imply similarity.
 * 
 * @author Noecker
 * @version 1.0
 */
public class CosineDistance extends DistanceFunction {
 	public String displayName(){
	    return "Cosine Distance";
	}

	public String tooltipText(){
	    return "Normalized Dot-Product Nearest Neighbor Classifier";
	}

	public boolean showInGUI(){
	    return true;
	}
	/**
     * Returns cosine distance between event sets es1 and es2
     * 
     * @param es1
     *            The first EventSet
     * @param es2
     *            The second EventSet
     * @return the cosine distance between them
     */
    @Override    
    public double distance(Histogram unknownHistogram, Histogram knownHistogram) {
        
        double distance = 0.0;
        double h1Magnitude = 0.0;
        double h2Magnitude = 0.0;

        Set<Event> events = Sets.union(unknownHistogram.uniqueEvents(), knownHistogram.uniqueEvents());
        
        for(Event event : events){
        	double unknownNormalizedFrequencey = unknownHistogram.normalizedFrequency(event);
        	double knownNormalizedFrequencey = knownHistogram.normalizedFrequency(event);
        	
        	distance += unknownNormalizedFrequencey * knownNormalizedFrequencey;
            h1Magnitude += unknownNormalizedFrequencey * unknownNormalizedFrequencey;
            h2Magnitude += knownNormalizedFrequencey * knownNormalizedFrequencey;
        }

        return Math.abs((distance / (Math.sqrt(h1Magnitude * h2Magnitude))) - 1);
    }
}
