/**
 **/
package com.jgaap.distances;

import java.util.*;

import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.Event;
import com.jgaap.generics.Pair;

/**
 * KendallCorrelationDistance : sequence-based distance for NN
 * algorithm suggested by (Wilson & Martinez 1997, JAIR). General theory:
 * Kendell's rank correlation measures how similar frequency rankings are
 * between two rank orderings; +1 is perfect agreement, -1 is perfect
 * disagreement. We subtract from 1 to get a distance measure.
 * 
 * @author Juola
 * @version 5.0
 */
public class KendallCorrelationDistance extends DistanceFunction {
	@Override
	public String displayName(){
	    return "Kendall Correlation Distance";
	}

	@Override
	public String tooltipText(){
	    return "Kendall Correlation Distance Nearest Neighbor Classifier";
	}

	@Override
	public boolean showInGUI(){
	    return true;
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
    public double distance(EventSet es1, EventSet es2) {

		EventHistogram h1 = new EventHistogram();
		EventHistogram h2 = new EventHistogram();

		Set<Event> s = new HashSet<Event>();

		List<Pair<Event,Double>> l1 = new ArrayList<Pair<Event,Double>>();
		List<Pair<Event,Double>> l2 = new ArrayList<Pair<Event,Double>>();

		HashMap<Event,Integer> hm1 = new HashMap<Event,Integer>();
		HashMap<Event,Integer> hm2 = new HashMap<Event,Integer>();

		double oldfreq = Double.POSITIVE_INFINITY;

		double correlation = 0.0;

		/* make two histograms */
		/* also make set for union of two histograms */
		for (int i = 0; i < es1.size(); i++) {
			h1.add(es1.eventAt(i));
			s.add(es1.eventAt(i));
		}

		for (int i = 0; i < es2.size(); i++) {
			h2.add(es2.eventAt(i));
			s.add(es2.eventAt(i));
		}

		//System.out.println(h1.toString());
		//System.out.println(h2.toString());

		/* make lists of the histograms */
		for (Event e: h1) {
			l1.add(new Pair<Event, Double>(e,h1.getRelativeFrequency(e),2) );
		}
		for (Event e: h2) {
			l2.add(new Pair<Event, Double>(e,h2.getRelativeFrequency(e),2) );
		}

		/* sort the list so the most frequent items are at the top */
		/* NOTE : THIS MAY BE USEFUL ELSEWHERE : SAVE THIS CODE */
		Collections.sort(l1);
		Collections.reverse(l1);
		Collections.sort(l2);
		Collections.reverse(l2);

		/* DEBUGGING STUFF 
		for (Pair <Event,Double> p : l1) {
			System.out.println("L1: " + p.toString());
		}
		for (Pair <Event,Double> p : l1) {
			System.out.println("L2: " + p.toString());
		}
		*/

	
		/* Convert lists into a hashmap of event:rank pairs */
		int rank = 0;
		int count = 0;
		for (Pair<Event,Double> p : l1) {
			Event e = (Event) (p.getFirst());
			double f = (Double) (p.getSecond());
			count++;
			if (f != oldfreq) {
				rank = count;
				oldfreq = f;
			}
			hm1.put(e,rank);
		}

		/* reset and do second list */
		rank = 0;
		count = 0;
		for (Pair<Event,Double> p : l2) {
			Event e = (Event) (p.getFirst());
			double f = (Double) (p.getSecond());
			count++;
			if (f != oldfreq) {
				rank = count;
				oldfreq = f;
			}
			hm2.put(e,rank);
		}

		/* More debugging stuff 
		System.out.println(hm1.toString());
		System.out.println(hm2.toString());
		System.out.println(s.toString());
		*/

		
		Integer x1, x2, y1, y2;
		for (Event e1 : s) {
			for (Event e2: s) {

				if (e1.equals(e2)) continue;

				/* get ranks of events e1 and e2 in both x and y distributions */
				x1 = hm1.get(e1);
				/* if not present, rank is size + 1 */
				if (x1 == null) x1 = hm1.size()+1;

				x2 = hm2.get(e1);
				if (x2 == null) x2 = hm2.size()+1;

				y1 = hm1.get(e2);
				/* if not present, rank is size + 1 */
				if (y1 == null) x1 = hm1.size()+1;

				y2 = hm2.get(e2);
				if (y2 == null) y2 = hm2.size()+1;


				/* more debugging stuff 
				System.out.println(e1.toString() + " is ("+x1+","+x2+")");
				System.out.println(e2.toString() + " is ("+y1+","+y2+")");
				System.out.println(sgn(x1.compareTo(y1)) + " " +
						   sgn(x2.compareTo(y2)) );
				System.out.println("");
				*/

				correlation += (sgn(x1.compareTo(y1)) * sgn(x2.compareTo(y2)));
				//System.out.println(correlation);
			}
		}
	

		//System.out.println(correlation);
		correlation /= (hm1.size() * (hm2.size()-1));
		//System.out.println(correlation);
		//System.out.println("---");

		return 1.0 - correlation;
				
    }

    private int sgn(Integer i) {
	if (i<0) return -1;
	else if (i==0) return 0;
	else /* i > 0 */ return 1;
    }

}
