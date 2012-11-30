package com.jgaap.eventCullers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jgaap.backend.Utils;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventCuller;
import com.jgaap.generics.EventCullingException;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;
/**
 *Analyze N events with the highest index of dispersion
 *D = stdev^2/mean
 * 
 * @author Christine Gray
 */
public class IndexOfDispersion extends EventCuller {
	public IndexOfDispersion() {
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
		int numEvents = getParameter("numEvents", 50);
		String informative = getParameter("Informative", "Most");

		EventHistogram hist = new EventHistogram();

		for (EventSet oneSet : eventSets) {
			for (Event e : oneSet) {
				hist.add(e);
			}
		}
		
		List<Pair<Event,Double>> IoD = new ArrayList<Pair<Event,Double>>(); 
		List<EventHistogram> eventHistograms = new ArrayList<EventHistogram>(eventSets.size());
		for (EventSet eventSet : eventSets) {
			eventHistograms.add(new EventHistogram(eventSet));
		}
		
		for (Event event : hist) {
			double mean;
			double stddev;
			List<Double>frequencies = new  ArrayList<Double>();
			/*
			 * Add all the frequencies of each event to a list
			 * Then take the standard deviation of the list
			 */
			for (EventHistogram eventHistogram : eventHistograms) {
				frequencies.add((double) eventHistogram.getAbsoluteFrequency(event));
			}
			stddev = Utils.stddev(frequencies);
			mean = mean(frequencies,0.0,0.0);
			/*
			 * Add Standard deviation^2 / mean to the list of Index of Dispersion
			 */
			IoD.add(new Pair<Event,Double>(event, Math.pow(stddev,2)/mean,2));
		}
		Collections.sort(IoD);
		if(informative.equals("Most")){
			Collections.reverse(IoD);
		}
		int counter = 0;
		Set<Event> events = new HashSet<Event>(numEvents);
		for(Pair<Event, Double> event : IoD){
			counter++;
			events.add(event.getFirst());
			if(counter == numEvents)
				break;
		}
		for (EventSet oneSet : eventSets) {
			EventSet newSet = new EventSet();
			for (Event e : oneSet) {
				if (events.contains(e)) {
					newSet.addEvent(e);
				}
			}
			results.add(newSet);
		}		
		return results;
	}

	@Override
	public String displayName() {
		return "Index of Dispersion";
	}

	@Override
	public String tooltipText() {
		return "Analyze N events with the highest index of dispersion";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	@Override
	public String longDescription(){
		return "Analyze N events with the highest index of dispersion\n"
				+"D = \u03C3\u00B2/\u03BC";
	}
	/*
	 * Recursively find the mean of a list of frequencies 
	 */
	double mean(List<Double>frequencies, double count, double mean){
		if(frequencies.isEmpty()){
			return mean/count;
		}		
		mean+=frequencies.remove(0);
		count++;
		return mean(frequencies,count, mean);
	}
}
