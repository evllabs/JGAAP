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

import java.util.*;

import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.Event;

/**
 * PearsonCorrelationDistance : Parametric equivalent of
 * KendallCorrelationDistance, based on Pearson's product-moment correlation 
 * General theory:
 * Pearson's correlation measures how similar frequency rankings are
 * between two rank orderings; +1 is perfect agreement, -1 is perfect
 * disagreement. We subtract from 1 to get a distance measure.  The main
 * difference is that Pearson measures only linear association, so the
 * Kendall correlation between x^2 and x^3 is perfect, but the PPMC is not.
 * 
 * @author Juola
 * @version 5.0.1
 */
public class PearsonCorrelationDistance extends DistanceFunction {
	@Override
	public String displayName(){
	    return "Pearson Correlation Distance";
	}

	@Override
	public String tooltipText(){
	    return "Pearson Correlation Distance Nearest Neighbor Classifier";
	}

	@Override
	public boolean showInGUI(){
	    return true;
	}
    /**
     * Returns PPMCC distance between event sets es1 and es2
     * 
     * @param es1
     *            The first EventSet
     * @param es2
     *            The second EventSet
     * @return the PPMCC distance (1 - Pearson's r) between them.
     * 		  Returns 0 if sd of both es1 and es2 are zero (point mass),
     *            otherwise returns 1 if only one is 0.  
     */
    @Override
    public double distance(EventSet es1, EventSet es2) {

		EventHistogram h1 = es1.getHistogram();
		EventHistogram h2 = es2.getHistogram();

		Set<Event> s = new HashSet<Event>();

		int n; // number of elements
	
		double sigX; // sum of relative frequencies in h1;
		double sigY; // sum of relative frequencies in h2;
		double sigXY; // sum of products of relative frequencies
		double sigX2; // sum of squared relative frequencies in h1;
		double sigY2; // sum of squared relative frequencies in h2;

		double denom1, denom2; // factors of denominator

		double correlation = 0.0;

		s.addAll(es1.uniqueEvents());
		s.addAll(es2.uniqueEvents());

		//System.out.println(h1.toString());
		//System.out.println(h2.toString());
		//System.out.println(s.toString());

		n = s.size();
		sigX = 0.0; 
		sigY = 0.0; 
		sigXY = 0.0; 
		sigX2 = 0.0; 
		sigY2 = 0.0; 
		for (Event e: s) {
			double x = h1.getRelativeFrequency(e);
			double y = h2.getRelativeFrequency(e);
			sigX += x;
			sigY += y;
			sigX2 += x * x;
			sigY2 += y * y;
			sigXY += x * y;
		};

		//System.out.println("n = " + n);
		//System.out.println("sigX = " + sigX);
		//System.out.println("sigY = " + sigY);
		//System.out.println("sigXY = " + sigXY);
		//System.out.println("sigX2 = " + sigX2);
		//System.out.println("sigY2 = " + sigY2);

		// formula from http://davidmlane.com/hyperstat/A56626.html
		// as well as lots of other places

		denom1 = sigX2 - (sigX * sigX)/n;
		denom2 = sigY2 - (sigY * sigY)/n;

		//System.out.println("denom1 = " + denom1);
		//System.out.println("denom2 = " + denom2);

		// check for edge cases
		if (Math.abs(denom1) < 0.000001 &&
		    Math.abs(denom2) < 0.000001) return 0;

		if (Math.abs(denom1) < 0.000001 ||
		    Math.abs(denom2) < 0.000001) return 1;

		correlation = (sigXY-(sigX*sigY)/n) /
			Math.sqrt(denom1 * denom2);

		//System.out.println("correlation = "+correlation);
		//System.out.println("---");

		return 1.0 - correlation;
				
    }
}
