package com.jgaap.eventCullers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jgaap.backend.Utils;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventCuller;
import com.jgaap.generics.EventCullingException;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;
/**
 * Analyze N events with highest standard deviation
 * 
 * @author Christine Gray
 */
public class StandardDeviationCuller extends EventCuller {
	public StandardDeviationCuller() {
		super();
		addParams("numEvents", "N", "50", new String[] { "1", "2", "3", "4",
				"5", "6", "7", "8", "9", "10", "15", "20", "25", "30", "40",
				"45", "50", "75", "100", "150", "200" }, true);
		addParams("Informative", "I", "Most", new String[] { "Most","Least"}, false);
	}
	@Override
	public List<EventSet> cull(List<EventSet> eventSets)
			throws EventCullingException {
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
		if (!getParameter("Informative").equals("")) {
			informative = getParameter("Informative");
		} else {
			informative = "Most";
		}
		
		EventHistogram hist = new EventHistogram();

		for (EventSet oneSet : eventSets) {
			for (Event e : oneSet) {
				hist.add(e);
			}
		}
		List<Pair<Event,Double>> var = new ArrayList<Pair<Event,Double>>(); 
		List<EventHistogram> eventHistograms = new ArrayList<EventHistogram>(eventSets.size());
		for (EventSet eventSet : eventSets) {
			eventHistograms.add(new EventHistogram(eventSet));
		}
		for (Event event : hist) {
			List<Double>frequencies = new ArrayList<Double>();
			/*
			 * Create a list of frequencies of each event
			 * Then take the standard deviation of the list of frequencies
			 */
			for (EventHistogram eventHistogram : eventHistograms) {
				frequencies.add((double) eventHistogram.getAbsoluteFrequency(event));
			} 
			var.add(new Pair<Event,Double>(event, Utils.stddev(frequencies)));
		}
		
		Collections.sort(var, new SortByStdDev());
		if(informative.equals("Most")){
			Collections.reverse(var);
		}

		List<Event> varSet = new ArrayList<Event>();
		for (int i = minPos; i < minPos + numEvents; i++) {
			varSet.add(var.get(i).getFirst());
		}
		for (EventSet oneSet : eventSets) {
			EventSet newSet = new EventSet();
			for (Event e : oneSet) {
				if (varSet.contains(e)) {
					newSet.addEvent(e);
				}
			}
			results.add(newSet);
		}
		return results;
	}

	@Override
	public String displayName() {
		return "Standard Deviation Culler";
	}
	@Override
	public String tooltipText() {
		return "Analyze N events with highest standard deviation";
	}
	@Override
	public boolean showInGUI() {
		return true;
	}
	@Override
	public String longDescription(){
		return "Analyze N events with highest standard deviation";
	}
	class SortByStdDev implements Comparator<Pair<Event, Double>> {
		public int compare(Pair<Event, Double> p1, Pair<Event, Double> p2) {
			return p1.getSecond().compareTo(p2.getSecond());
		}
	}

}
