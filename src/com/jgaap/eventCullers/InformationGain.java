package com.jgaap.eventCullers;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventCuller;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Sort out the N most informative events across all documents. Uses smoothing
 * to make sure that a divide by 0 error doesn't occur. IG = log(i = 1 to n
 * Product(mi!)/((i=1 to n Sum(mi))!(i=1 to n Product(Pi^mi))))
 * 
 * @author Christine Gray
 */

public class InformationGain extends EventCuller {
	public InformationGain() {
		super();
		addParams("numEvents", "N", "50", new String[] { "1", "2", "3", "4",
				"5", "6", "7", "8", "9", "10", "15", "20", "25", "30", "40",
				"45", "50", "75", "100", "150", "200" }, true);
		addParams("informative", "I", "Most", new String[] { "Most", "Least" },
				false);
	}

	@Override
	public List<EventSet> cull(List<EventSet> eventSets) {
		List<EventSet> results = new ArrayList<EventSet>();
		int minPos, numEvents;
		String informative;

		if (!getParameter("minPos").equals("")) {
			minPos = Integer.parseInt(getParameter("minPos"));
		} else {
			minPos = 0;
		}

		if (!getParameter("numEvents").equals("")) {
			numEvents = Integer.parseInt(getParameter("numEvents"));
		} else {
			numEvents = 50;
		}

		if (!getParameter("informative").equals("")) {
			informative = getParameter("informative");
		} else {
			informative = "Most";
		}

		EventHistogram hist = new EventHistogram();

		for (EventSet oneSet : eventSets) {
			for (Event e : oneSet) {
				hist.add(e);
			}
		}
		List<Pair<Event, Double>> infoGain = new ArrayList<Pair<Event, Double>>(hist.getNTypes());
		BigDecimal percentage = new BigDecimal(0.0);
		BigDecimal numerator = new BigDecimal(1.0);
		BigDecimal denom1 = new BigDecimal(0.0);
		BigDecimal denom2 = new BigDecimal(1.0);

		/*
		 * The list count keeps track of the frequency of each event in the
		 * individual documents This is mi in the formula
		 */
		List<EventHistogram> eventHistograms = new ArrayList<EventHistogram>(eventSets.size());
		for (EventSet eventSet : eventSets) {
			eventHistograms.add(new EventHistogram(eventSet));
		}
		for (Event event : hist) {
			percentage = new BigDecimal(hist.getRelativeFrequency(event));
			for (EventHistogram eventHistogram : eventHistograms) {
				int mi = eventHistogram.getAbsoluteFrequency(event);
				/*
				 * Calculates numerator i = 0 to n Product of mi!
				 */
				numerator = numerator.multiply(factorial(mi));
				BigDecimal countK = new BigDecimal(mi);
				/*
				 * Calculates first term of the denominator i = 0 to n Sum of mi
				 */
				denom1 = denom1.add(countK);
				MathContext mc = new MathContext(300, RoundingMode.UP);
				BigDecimal power = percentage.pow(mi, mc);
				/*
				 * Calculates second term of the denominator i = 0 to n Product
				 * Pi^mi
				 */
				denom2 = denom2.multiply(power);
			}
			denom1 = factorial(denom1);
			denom1 = denom1.multiply(denom2);
			numerator = numerator.divide(denom1, RoundingMode.UP);
			Double res = Math.log(numerator.doubleValue());
			infoGain.add(new Pair<Event, Double>(event, res, 2));
			BigDecimal Temp1 = new BigDecimal(1.0);
			BigDecimal Temp0 = new BigDecimal(0.0);
			numerator = Temp1;
			denom1 = Temp0;
			denom2 = Temp1;
		}
		Collections.sort(infoGain);
		if (informative.equals("Most")) {
			Collections.reverse(infoGain);
		}
		/*
		 * IGSet holds the k first events in infoGain. infoGain is already
		 * sorted by most or least informative
		 */
		List<Event> IGSet = new ArrayList<Event>();
		for (int i = minPos; i < minPos + numEvents; i++) {
			IGSet.add(infoGain.get(i).getFirst());
		}
		for (EventSet oneSet : eventSets) {
			EventSet newSet = new EventSet();
			for (Event e : oneSet) {
				if (IGSet.contains(e)) {
					newSet.addEvent(e);
				}
			}
			results.add(newSet);
		}
		return results;
	}

	@Override
	public String displayName() {
		return "Information Gain";
	}

	@Override
	public String tooltipText() {
		return "Analyze only the N most or least informative events across all documents";
	}

	@Override
	public String longDescription() {
		return "Information Gain \n"
				+ "Analyze only the N most or least informative events across all documents\n"
				+ "IG = log(i = 1 to n \u03A0mi!/((i=1 to n \u03A3mi)!(i=1 to n \u03A0Pi^mi)))\n";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	/**
	 * @param n
	 * 
	 * @return n!
	 */
	public BigDecimal factorial(double n) {
		return factorial(new BigDecimal(n));
	}

	/**
	 * @param n
	 * 
	 * @return n!
	 */
	public BigDecimal factorial(BigDecimal n) {
		BigDecimal result = new BigDecimal(1.0);
		BigDecimal tmp = new BigDecimal(1.0);
		if (!n.equals(0)) {
			for (int i = n.intValue(); i > 0; i--) {
				result = result.multiply(n);
				n = n.subtract(tmp);
			}
		}
		return result;
	}
}
