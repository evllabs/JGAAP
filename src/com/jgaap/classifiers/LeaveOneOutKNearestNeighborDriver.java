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

package com.jgaap.classifiers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


import com.google.common.collect.ImmutableList;
import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.ValidationDriver;
import com.jgaap.util.Ballot;
import com.jgaap.util.Document;
import com.jgaap.util.EventMap;
import com.jgaap.util.Pair;

/*KNN LOOCV implementation by @Alejandro Jorge Napolitano Jawerbaum*/

public class LeaveOneOutKNearestNeighborDriver extends ValidationDriver {

	private java.util.logging.Logger logger =  java.util.logging.Logger.getLogger(LeaveOneOutKNearestNeighborDriver.class.getName());
	
	private ImmutableList<Pair<Document, EventMap>> knowns;
	
    private static final int DEFAULT_K = 5;
    private static final String DEFAULT_TIE = "lastPicked";
    
    public LeaveOneOutKNearestNeighborDriver() {
		addParams("k", "K: Number of Neighbors", "5", new String[] { "1", "2",
				"3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13",
				"14", "15", "16", "17", "18", "19", "20", "21", "22", "23",
				"24", "25" }, false);
    }
	@Override
	public String displayName() {
		return "Leave One Out K-Nearest Neighbor driver" + this.getDistanceName();
	}
	@Override
	public String tooltipText() {
		return " ";
	}
	@Override
	public boolean showInGUI() {
		return true;
	}
	@Override
	public void train(List<Document> knowns){
		ImmutableList.Builder<Pair<Document, EventMap>> builder = ImmutableList.builder();
		for(Document known : knowns) {
			builder.add(new Pair<Document, EventMap>(known, new EventMap(known)));
		}
		this.knowns = builder.build();
	}

	@Override
	public List<Pair<String, Double>> analyze(Document unknown) throws AnalyzeException {

        Ballot<String> ballot = new Ballot<String>();

        int k = getParameter("k", DEFAULT_K);

        String tieBreaker = getParameter("tieBreaker", DEFAULT_TIE);

		List<Pair<String, Double>> rawResults = new ArrayList<Pair<String,Double>>();

		for (int i = 0; i < knowns.size(); i++) {
			if(!knowns.get(i).getFirst().equals(unknown)) {
				double current;
				try {
					current = distance.distance(new EventMap(unknown), knowns.get(i).getSecond());
				} catch (DistanceCalculationException e) {
					throw new AnalyzeException("Distance "+distance.displayName()+" failed");
				}
				rawResults.add(new Pair<String, Double>(knowns.get(i).getFirst().getAuthor(), current, 2));
				}
			else
				logger.info("Excluded document that's being tested.");
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
        Comparator<Pair<String, Double>> compareByScore = (Pair<String, Double> r1, Pair<String, Double> r2) -> r2.getSecond().compareTo(r1.getSecond());
        Collections.sort(results, compareByScore);

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