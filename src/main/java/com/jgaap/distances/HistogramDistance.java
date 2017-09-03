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
 * Histogram distance using L2 metric,(defined as D(x,y) = sum ((xi -yi)^2) This
 * is YA distance for Nearest Neighbor algorithms
 * 
 * @author Juola
 * @version 1.0
 */
public class HistogramDistance extends DistanceFunction {
	public String displayName() {
		return "Histogram Distance";
	}

	public String tooltipText() {
		return "Histogram Distance (also known as Euclidean or L2 Norm)";
	}

	public boolean showInGUI() {
		return true;
	}

	/**
	 * Returns histogram distance between event sets es1 and es2.
	 * 
	 * @param unknownEventSet
	 *            The first EventSet
	 * @param knownEventSet
	 *            The second EventSet
	 * @return the (Euclidean) histogram distance between them
	 */

	@Override
	public double distance(Histogram unknownHistogram, Histogram knownHistogram) {
		double distance = 0.0;

		Set<Event> events = Sets.union(unknownHistogram.uniqueEvents(), knownHistogram.uniqueEvents());
		
		for(Event event : events){
			distance += Math.pow(unknownHistogram.relativeFrequency(event) - knownHistogram.relativeFrequency(event), 2);
		}

		return distance;
	}
}
