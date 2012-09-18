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

import com.jgaap.generics.*;

import java.util.List;

/**
 * Given two event type-sets, A,B, calculate 1 - ||A intereset B|| // ||A union B||
 * 
 * @author Juola
 * @version 4.1
 */
public class IntersectionDistance extends DistanceFunction{
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
		EventHistogram h1 = es1.getHistogram();
		EventHistogram h2 = es2.getHistogram();

		double intersectioncount = 0;
		double unioncount;

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
		return 1.0 - intersectioncount / unioncount;
	}

    public double distance(List<Double> v1, List<Double> v2) {
        double intersectionCount = 0.0;
        double unionCount = v1.size();
        for(int i = 0; i < unionCount; i++) {
            if(v1.get(i) > 0 && v2.get(i) > 0) {
                intersectionCount++;
            }
        }

        return 1.0 - intersectionCount / unionCount;
    }

}
