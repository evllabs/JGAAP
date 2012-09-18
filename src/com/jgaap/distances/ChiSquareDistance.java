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
 * Chi-Square Distance, defined as
 * sum( (xi - yi)^2/(xi + yi) )
 * 
 * @author John Noecker Jr.
 */
public class ChiSquareDistance extends DistanceFunction {

	public String displayName() {
		return "Chi Square Distance";
	}

	public String tooltipText() {
		return "Chi Square Distance";
	}

	public boolean showInGUI() {
		return true;
	}

	/**
	 * Returns Chi-Square distance between event sets es1 and es2
	 * 
	 * @param es1
	 *            The first EventSet
	 * @param es2
	 *            The second EventSet
	 * @return the Canberra distance between them
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
			double x = h1.getRelativeFrequency(event);
			double y = h2.getRelativeFrequency(event);
			distance += (x - y) * (x - y) / (x + y);			
		}

		return distance;
	}
}
