/**
 *   JGAAP -- Java Graphical Authorship Attribution Program
 *   Copyright (C) 2009 Patrick Juola
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation under version 3 of the License.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/
package com.jgaap.distances;

import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;

/**
 * Given two event type-sets, A,B, calculate 1 - ||A intereset B|| // ||A union
 * B||
 * 
 * @author Juola
 * @version 4.1
 */
public class IntersectionDistance extends DistanceFunction {
	public String displayName() {
		return "Intersection Distance";
	}

	public String tooltipText() {
		return "Event type set intersection divided by event type set union";
	}

	public boolean showInGUI() {
		return true;
	}

	/**
	 * Returns intersection distance between event sets es1 and es2.
	 * 
	 * @param es1
	 *            The first EventSet
	 * @param es2
	 *            The second EventSet
	 * @return the intersection distance between them
	 */

	@Override
	public double distance(EventSet es1, EventSet es2) {
		EventHistogram h1 = new EventHistogram();
		EventHistogram h2 = new EventHistogram();

		long intersectioncount = 0;
		long unioncount;

		for (int i = 0; i < es1.size(); i++) {
			h1.add(es1.eventAt(i));
		}

		for (int i = 0; i < es2.size(); i++) {
			h2.add(es2.eventAt(i));
		}

		unioncount = h1.getNTypes();
		// unioncount now has the number of distinct types in h1

		for (Event event : h2) {
			if (h1.getAbsoluteFrequency(event) == 0) {
				// present in h2, not in h1, so add to union count
				unioncount++;
			} else {
				// present in h2 and in h1
				// already in union (from h1)
				intersectioncount++; // add to intersection

			}
		}
		/*
		 * FOR DEBUGGING ONLY System.out.println("intersection count is " +
		 * intersectioncount); System.out.println("unioncount is " +
		 * unioncount); System.out.println("ratio is " +
		 * (1.0*intersectioncount)/unioncount);
		 * System.out.println("distance is " + (1.0 -
		 * (1.0*intersectioncount)/unioncount) ); System.out.println("");
		 */
		return 1.0 - (1.0 * intersectioncount) / unioncount;
	}
}
