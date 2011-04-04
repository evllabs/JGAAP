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
public class CamberraDistance extends DistanceFunction {

	public String displayName() {
		return "Camberra Distance";
	}

	public String tooltipText() {
		return "Camberra Distance Nearest Neighbor Classifier";
	}

	public boolean showInGUI() {
		return true;
	}

	/**
	 * Returns Camberra distance between event sets es1 and es2
	 * 
	 * @param es1
	 *            The first EventSet
	 * @param es2
	 *            The second EventSet
	 * @return the Camberra distance between them
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
