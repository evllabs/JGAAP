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
 * Canberra distance, defined as D(x,y) = sum (| (xi -yi)/(xi + yi) |). This is
 * YA distance for Nearest Neighbor algorithms, based on (Wilson & Martinez
 * 1997, JAIR).
 * 
 * @author Juola
 * @version 1.0
 */
public class CanberraDistance extends DistanceFunction {

	public String displayName() {
		return "Canberra Distance";
	}

	public String tooltipText() {
		return "Canberra Distance Nearest Neighbor Classifier";
	}

	public boolean showInGUI() {
		return true;
	}

	/**
	 * Returns Canberra distance between event sets es1 and es2
	 * 
	 * @param unknownHistogram
	 *            The first EventSet
	 * @param knownHistogram
	 *            The second EventSet
	 * @return the Canberra distance between them
	 */
	@Override
	public double distance(Histogram unknownHistogram, Histogram knownHistogram) {
		double distance = 0.0;
		Set<Event> events = Sets.union(unknownHistogram.uniqueEvents(), knownHistogram.uniqueEvents());

		for (Event event : events) {
			distance += Math.abs((unknownHistogram.relativeFrequency(event) - knownHistogram.relativeFrequency(event))
					/ (unknownHistogram.relativeFrequency(event) + knownHistogram.relativeFrequency(event)));
		}

		return distance;
	}
}
