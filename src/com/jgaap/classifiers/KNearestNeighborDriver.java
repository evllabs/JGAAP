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
package com.jgaap.classifiers;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.jgaap.backend.Ballot;
import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.NeighborAnalysisDriver;
import com.jgaap.generics.Pair;

/**
 * Assigns authorship labels by using a nearest-neighbor approach on a given
 * distance/divergence function.
 * 
 */
public class KNearestNeighborDriver extends NeighborAnalysisDriver {

	static Logger logger = Logger.getLogger(KNearestNeighborDriver.class);
	
    private static final int DEFAULT_K = 5;
    private static final String DEFAULT_TIE = "lastPicked";

	public String displayName() {
		return "K-Nearest Neighbor Driver" + getDistanceName();
	}

	public String tooltipText() {
		return " ";
	}

	public boolean showInGUI() {
		return false;
	}

	@Override
	public List<Pair<String, Double>> analyze(EventSet unknown, List<EventSet> known) throws AnalyzeException {

        Ballot<String> ballot = new Ballot<String>();

        int k = DEFAULT_K; // Default
        // Set K
        if(getParameter("K") != null) {
            try {
                k = Integer.parseInt(getParameter("K"));
            } catch(Exception e) {
                k = DEFAULT_K; // Default
            }
        }

        String tieBreaker = DEFAULT_TIE; // Default
        if(getParameter("tieBreaker") != null && !getParameter("tieBreaker").trim().equals("")) {
            tieBreaker = getParameter("tieBreaker");
        }

		List<Pair<String, Double>> rawResults = new ArrayList<Pair<String,Double>>();

		for (int i = 0; i < known.size(); i++) {
			double current;
			try {
				current = distance.distance(unknown, known.get(i));
			} catch (DistanceCalculationException e) {
				logger.error("Distance "+distance.displayName()+" failed", e);
				throw new AnalyzeException("Distance "+distance.displayName()+" failed");
			}
            rawResults.add(new Pair<String, Double>(known.get(i).getAuthor(), current, 2));
			logger.debug(unknown.getDocumentName()+"(Unknown):"+known.get(i).getDocumentName()+"("+known.get(i).getAuthor()+") Distance:"+current);
		}
		Collections.sort(rawResults);
        for(int i = 0; i < Math.min(k, rawResults.size()); i++) {
            Pair<String, Double> p = rawResults.get(i);
            ballot.vote(p.getFirst(), (1 + Math.pow(2, (-1.0 * (i+1)))));
        }

        if(tieBreaker.equals("lastPicked")) {
            ballot.setComparator(new LastPickedComparator());
        }

        List<Pair<String, Double>> results = ballot.getResults();

		return results;
	}

    private static class LastPickedComparator implements Comparator<Pair<String, Double>>, Serializable {

		private static final long serialVersionUID = 1L;

		public int compare(Pair<String, Double> firstPair, Pair<String, Double> secondPair) {
            double first = firstPair.getSecond();
            double second = secondPair.getSecond();

            // If the overall rank was not the same, then return these according to rank.
            if((int)first != (int)second) {
                return (int)first - (int)second;
            }

            // Otherwise, we want to move the decimal point right until we have an integer.
            while(((int)first - first) > 0.0000001) {
                first *= 2;
                second *= 2;
            }
            // If first had fewer decimal places than second, this means the last first vote came BEFORE the last second vote.
            if(((int)second -second) > 0.0000001) {
                return 1;
            }
            // Otherwise, the last second vote came before the last first vote.
            else {
                return -1;
            }
        }
    }

}


