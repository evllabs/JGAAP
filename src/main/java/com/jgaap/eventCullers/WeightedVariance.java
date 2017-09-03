package com.jgaap.eventCullers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jgaap.generics.EventCullingException;
import com.jgaap.generics.FilterEventCuller;
import com.jgaap.util.Event;
import com.jgaap.util.EventHistogram;
import com.jgaap.util.EventSet;
import com.jgaap.util.Pair;
/**
 * Analyze N events with highest weighted variance
 * Var(x) = sum for i = 1 to n Pi*(xi-mean)^2
 * where
 * mean = sum for i = 1 to n Pi*xi"
 * 
 * @author Christine Gray
 */
public class WeightedVariance extends FilterEventCuller {
	public WeightedVariance() {
		super();
		addParams("numEvents", "N", "50", new String[] { "1", "2", "3", "4",
				"5", "6", "7", "8", "9", "10", "15", "20", "25", "30", "40",
				"45", "50", "75", "100", "150", "200" }, true);
		addParams("Informative", "I", "Most", new String[] { "Most","Least"}, false);
	}
	@Override
	public Set<Event> train(List<EventSet> eventSets)
			throws EventCullingException {
		int numEvents = getParameter("numEvents", 50);
		String informative = getParameter("Informative", "Most");
		
		EventHistogram hist = new EventHistogram();

		for (EventSet oneSet : eventSets) {
			for (Event e : oneSet) {
				hist.add(e);
			}
		}
		List<Pair<Event,Double>> WVar = new ArrayList<Pair<Event,Double>>(); 
		List<EventHistogram> eventHistograms = new ArrayList<EventHistogram>(eventSets.size());
		for (EventSet eventSet : eventSets) {
			eventHistograms.add(new EventHistogram(eventSet));
		}
		for (Event event : hist) {
			double mean = 0.0;
			double var = 0.0;
			double percentage = hist.getRelativeFrequency(event);
			List<Integer> frequencies = new ArrayList<Integer>();
			/*
			 * Calculate the mean 
			 * sum i=1 to n Pi*xi
			 */
			for (EventHistogram eventHistogram : eventHistograms) {
				int tmp = eventHistogram.getAbsoluteFrequency(event);
				frequencies.add(tmp);
				mean+= percentage*tmp;
			}
			/*
			 * Calculate the weighted variance
			 * Sum i=1 to n Pi(xi-mean)^2
			 */
			for(int i : frequencies){
				var += percentage * Math.pow(i - mean, 2);
			}
			WVar.add(new Pair<Event, Double>(event, var, 2));
		}
				
		Collections.sort(WVar);
		if(informative.equals("Most")){
			Collections.reverse(WVar);
		}
		int counter = 0;
		Set<Event> events = new HashSet<Event>(numEvents);
		for(Pair<Event, Double> event : WVar){
			counter++;
			events.add(event.getFirst());
			if(counter == numEvents)
				break;
		}
		return events;
	}

	@Override
	public String displayName() {
		return "Weighted Variance";
	}

	@Override
	public String tooltipText() {
		return "Analyze N events with highest weighted variance";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	@Override
	public String longDescription(){
		return "Analyze N events with highest weighted variance\n"+
				"Var(x) = \u03A3 for i = 1 to n Pi*(xi-\u03BC)\u00B2\n"+
				"where\n"+
				"\u03BC = \u03A3 for i = 1 to n Pi*xi";
	}

}
