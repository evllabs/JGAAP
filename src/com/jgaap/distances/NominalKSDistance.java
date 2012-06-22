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

import java.util.HashSet;
import java.util.Set;

import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;

/**
 * Nominal Kolmogorov-Smirnov distance for Nearest Neighbor algorithm
 */
public class NominalKSDistance extends DistanceFunction {
	public String displayName(){
	    return "KS Distance";
	}

	public String tooltipText(){
	    return "Nominal Kolmogorov-Smirnov distance (also known as the Minkowski L-infinity metric)";
	}

	public boolean showInGUI(){
	    return true;
	}
	/**
	 * Calculate the Kolmogorov-Smirnov distance between two event sets.
	 * @return The nominal KS distance between two event sets.
	 */
	@Override
    public double distance(EventSet es1, EventSet es2) {
        EventHistogram h1 = es1.getHistogram();
        EventHistogram h2 = es2.getHistogram();
        double distance = 0.0;
        Set<Event> events = new HashSet<Event>();
        
        events.addAll(es1.uniqueEvents());
        events.addAll(es2.uniqueEvents());

        for (Event event : events) {
            distance += Math.abs(h1.getRelativeFrequency(event)
                    - h2.getRelativeFrequency(event));
        }

        return (1.0 / 2.0) * distance;
    }
}
