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

/**
 * NOT WORKING YET. KendallCorrelationDistance : sequence-based distance for NN
 * algorithm suggested by (Wilson & Martinez 1997, JAIR). General theory:
 * Kendell's rank correlation measures how similar frequency rankings are
 * between two rank orderings; +1 is perfect agreement, -1 is perfect
 * disagreement. We subtract from 1 to get a distance measure.
 * 
 * TODO: Fix KendallCorrelationDistance
 * @author Juola
 * @version 1.0
 */
@Deprecated
public class KendallCorrelationDistance extends DistanceFunction {
	public String displayName(){
	    return "Kendall Correlation Distance";
	}

	public String tooltipText(){
	    return "Kendall Correlation Distance Nearest Neighbor Classifier";
	}

	public boolean showInGUI(){
	    return false;
	}
    /**
     * Returns KC distance between event sets es1 and es2
     * 
     * @param es1
     *            The first EventSet
     * @param es2
     *            The second EventSet
     * @return the KC distance between them
     */
    @Override
    public double distance(EventSet s, EventSet t) {

        System.out.println("Does not work yet!");

        return 1.0;
    }
}
