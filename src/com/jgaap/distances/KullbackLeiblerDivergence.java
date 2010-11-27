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

import com.jgaap.generics.DivergenceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;

/**
 * Kullback-Leibler divergence, to be treated as YA distance for
 * nearest-neighbor algorithms. This is technically a divergence instead of a
 * "distance" as it is noncommutative.
 * 
 * @author Ryan
 * @version 3.1
 */

public class KullbackLeiblerDivergence extends DivergenceFunction {
	public String displayName(){
	    return "Kullback Leibler Distance"+getDivergenceType();
	}

	public String tooltipText(){
	    return "Kullback Leibler Distance Nearest Neighbor Classifier";
	}

	public boolean showInGUI(){
	    return true;
	}
    /**
     * Returns KL-divergence between event sets es1 and es2. This is basically
     * the cross-entropy H(es1,es2) minus the pure entropy H(es1).
     * 
     * @param es1
     *            The first EventSet
     * @param es2
     *            The second EventSet
     * @return the KL divergence between them
     */

    @Override
    public double divergence(EventSet es1, EventSet es2) {
        EventHistogram h1 = new EventHistogram();
        EventHistogram h2 = new EventHistogram();
        double distance = 0;

        for (int i = 0; i < es1.size(); i++) {
            h1.add(es1.eventAt(i));
        }

        for (int i = 0; i < es2.size(); i++) {
            h2.add(es2.eventAt(i));
        }

        for(Event event : h1) {
            if(h2.getRelativeFrequency(event)!=0){
             distance += h1.getRelativeFrequency(event) * Math.log(h1.getRelativeFrequency(event)/h2.getRelativeFrequency(event)); 
            }
        }
        return distance;
    }
}
