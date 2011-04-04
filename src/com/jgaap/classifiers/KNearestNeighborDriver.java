/**
 **/
package com.jgaap.classifiers;

import java.util.Collections;
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

    private final int DEFAULT_K = 3;

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
            ballot.vote(p.getFirst());
        }

        List<Pair<String, Double>> results = ballot.getResults();

		return results;
	}

}
