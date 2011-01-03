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
import com.jgaap.generics.EventSet;
import com.jgaap.generics.NumericEventSet;

/**
 * Mean Distance or normalized dot product. Average both EventSets and subtract.
 * 
 * @author Juola
 * @version 4.2
 */
public class MeanDistance extends DistanceFunction {
	public String displayName() {
		return "Mean Distance";
	}

	public String tooltipText() {
		return "Distance between arithmetic mean of events (numeric events only)";
	}

	public boolean showInGUI() {
		return true;
	}

	/**
	 * Returns mean distance between event sets es1 and es2
	 * 
	 * @param es1
	 *            The first EventSet
	 * @param es2
	 *            The second EventSet
	 * @return the mean distance between them
	 */
	@Override
	public double distance(EventSet es1, EventSet es2) {

		double sum1 = 0.0;
		double sum2 = 0.0;

		if (!(es1 instanceof NumericEventSet)
				|| !(es1 instanceof NumericEventSet)) {
			System.out
					.println("ERROR : MeanDistance: Attempting to take average of non-numeric set!");
			return Double.NaN;
		}

		for (int i = 0; i < es1.size(); i++) {
			sum1 += Double.valueOf((es1.eventAt(i).toString()));
		}

		for (int i = 0; i < es2.size(); i++) {
			sum2 += Double.valueOf((es2.eventAt(i).toString()));
		}

		return Math.abs((sum1 / es1.size()) - (sum2 / es2.size()));
	}
}
