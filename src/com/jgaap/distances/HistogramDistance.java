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

import java.util.Enumeration;

import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;

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
		return "Histogram Distance (L2 Norm) Nearest Neighbor Classifier";
	}

	public boolean showInGUI() {
		return true;
	}

	/**
	 * Returns histogram distance between event sets es1 and es2.
	 * 
	 * @param es1
	 *            The first EventSet
	 * @param es2
	 *            The second EventSet
	 * @return the (Euclidean) histogram distance between them
	 */

	@Override
	public double distance(EventSet es1, EventSet es2) {
		EventHistogram h1 = new EventHistogram();
		EventHistogram h2 = new EventHistogram();
		double distance = 0.0;
		Enumeration<Event> EventList;
		Event theEvent;

		for (int i = 0; i < es1.size(); i++) {
			h1.add(es1.eventAt(i));
		}

		for (int i = 0; i < es2.size(); i++) {
			h2.add(es2.eventAt(i));
		}

		for (EventList = h1.events(); EventList.hasMoreElements();
		/* nothing -- handled in function */) {

			theEvent = EventList.nextElement();
			distance += Math.pow((h1.getRelativeFrequency(theEvent) - h2
					.getRelativeFrequency(theEvent)), 2);
			// * (h1.getRelativeFrequency(theEvent) - h2
			// .getRelativeFrequency(theEvent));
			//System.out.println(theEvent);
		}

		for (EventList = h2.events(); EventList.hasMoreElements();
		/* nothing -- handled in function */) {

			theEvent = EventList.nextElement();
			if (h1.getAbsoluteFrequency(theEvent) == 0) {
				distance += Math.pow((h1.getRelativeFrequency(theEvent) - h2
						.getRelativeFrequency(theEvent)), 2);
				// * (h1.getRelativeFrequency(theEvent) - h2
				// .getRelativeFrequency(theEvent));
				//System.out.println(theEvent);
			}
		}
		return distance;
	}
}
