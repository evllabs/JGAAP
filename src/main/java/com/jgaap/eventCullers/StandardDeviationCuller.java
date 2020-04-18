package com.jgaap.eventCullers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jgaap.backend.Utils;
import com.jgaap.generics.EventCullingException;
import com.jgaap.generics.FilterEventCuller;
import com.jgaap.util.Event;
import com.jgaap.util.EventHistogram;
import com.jgaap.util.EventSet;
import com.jgaap.util.Pair;
/**
 * Analyze N events with highest standard deviation
 * 
 * @author Christine Gray
 */
public class StandardDeviationCuller extends FilterEventCuller {
	public StandardDeviationCuller() {
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
			var.add(new Pair<Event,Double>(event, Utils.stddev(frequencies),2));
		}
		
		Collections.sort(var);
		if(informative.equals("Most")){
			Collections.reverse(var);
		}

		int counter = 0;
		Set<Event> events = new HashSet<Event>(numEvents);
		for(Pair<Event, Double> event : var){
			counter++;
			events.add(event.getFirst());
			if(counter == numEvents)
				break;
		}
		return events;
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

}
