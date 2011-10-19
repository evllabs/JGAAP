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

import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;

/**
 * Camberra distance, defined as D(x,y) = sum (| (xi -yi)/(xi + yi) |). This is
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
	 * @param es1
	 *            The first EventSet
	 * @param es2
	 *            The second EventSet
	 * @return the Canberra distance between them
	 */
	@Override
	public double distance(EventSet es1, EventSet es2) {

		EventHistogram h1 = new EventHistogram();
		EventHistogram h2 = new EventHistogram();
		double distance = 0.0, increment;

		for (int i = 0; i < es1.size(); i++) {
			h1.add(es1.eventAt(i));
		}

		for (int i = 0; i < es2.size(); i++) {
			h2.add(es2.eventAt(i));
		}

		for (Event event : h1) {
			increment = (h1.getRelativeFrequency(event) - h2
					.getRelativeFrequency(event))
					/ (h1.getRelativeFrequency(event) + h2
							.getRelativeFrequency(event));
			if (increment < 0) {
				increment = increment * -1;
			}
			distance += increment;
		}

		for (Event event: h2) {
			if (h1.getAbsoluteFrequency(event) == 0) {
				increment = (h1.getRelativeFrequency(event) - h2
						.getRelativeFrequency(event))
						/ (h1.getRelativeFrequency(event) + h2
								.getRelativeFrequency(event));
				if (increment < 0) {
					increment = increment * -1;
				}
				distance += increment;
			}
		}

		return distance;
	}
}
