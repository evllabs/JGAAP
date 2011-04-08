// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.classifiers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

import com.jgaap.backend.Ballot;
import com.jgaap.jgaapConstants;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.NeighborAnalysisDriver;
import com.jgaap.generics.Pair;

/**
 * Assigns authorship labels by using a nearest-neighbor approach on a given
 * distance/divergence function.
 * 
 */
public class KNearestNeighborDriver extends NeighborAnalysisDriver {

    private final int DEFAULT_K = 5;
    private final String DEFAULT_TIE = "lastPicked";

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
	public List<Pair<String, Double>> analyze(EventSet unknown, List<EventSet> known) {

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
			double current = distance.distance(unknown, known.get(i));

            rawResults.add(new Pair<String, Double>(known.get(i).getAuthor(), current, 2));

			if (jgaapConstants.JGAAP_DEBUG_VERBOSITY) {
				System.out.print(unknown.getDocumentName() + "(Unknown)");
				System.out.print(":");
				System.out.print(known.get(i).getDocumentName() + "("
						+ known.get(i).getAuthor() + ")\t");
				System.out.println("Distance is " + current);
			}
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

    private class LastPickedComparator implements Comparator<Pair<String, Double>> {

        public int compare(Pair<String, Double> firstPair, Pair<String, Double> secondPair) {
            double first = firstPair.getSecond();
            double second = secondPair.getSecond();

            // If the overall rank was not the same, then return these according to rank.
            if((int)first != (int)second) {
                return (int)first - (int)second;
            }

            // Otherwise, we want to move the decimal point right until we have an integer.
            while((int)first != first) {
                first *= 2;
                second *= 2;
            }
            // If first had fewer decimal places than second, this means the last first vote came BEFORE the last second vote.
            if((int)second != second) {
                return 1;
            }
            // Otherwise, the last second vote came before the last first vote.
            else {
                return -1;
            }
        }
    }

}


