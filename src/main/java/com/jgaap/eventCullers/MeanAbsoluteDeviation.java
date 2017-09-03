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
 * Analyze N events with the highest Mean Absolute Deviation
 * MAD = 1/n sum for i = 1 to n |xi - mean|
 * 
 * @author Christine Gray
 */
public class MeanAbsoluteDeviation extends FilterEventCuller {
	public MeanAbsoluteDeviation() {
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
		List<Pair<Event,Double>> MAD = new ArrayList<Pair<Event,Double>>(); 
		List<EventHistogram> eventHistograms = new ArrayList<EventHistogram>(eventSets.size());
		for (EventSet eventSet : eventSets) {
			eventHistograms.add(new EventHistogram(eventSet));
		}
		
		for (Event event : hist) {
			double mean;
			List<Integer>frequencies = new  ArrayList<Integer>();
			for (EventHistogram eventHistogram : eventHistograms) {
				frequencies.add(eventHistogram.getAbsoluteFrequency(event));
			}
			double total = frequencies.size();
			List<Integer> tmp = new ArrayList<Integer>();
			tmp.addAll(frequencies);
			mean = Mean(tmp,0.0,0.0);
			MAD.add(new Pair<Event, Double>(event, (1/total) * MAD(frequencies, mean, 0.0),2));
		}

		
		Collections.sort(MAD);
		if(informative.equals("Most")){
			Collections.reverse(MAD);
		}

		int counter = 0;
		Set<Event> events = new HashSet<Event>(numEvents);
		for(Pair<Event, Double> event : MAD){
			counter++;
			events.add(event.getFirst());
			if(counter == numEvents)
				break;
		}
		return events;
	}

	@Override
	public String displayName() {
		return "Mean Absolute Deviation";
	}

	@Override
	public String tooltipText() {
		return "Analyze N events with the highest Mean Absolute Deviation";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	@Override
	public String longDescription(){
		return "Analyze N events with the highest Mean Absolute Deviation\n"+
				"MAD = 1/n \u03A3 for i = 1 to n |xi - \u03BC|";
	}
	double Mean(List<Integer>frequencies, double count, double mean){
		if(frequencies.isEmpty()){
			return mean/count;
		}		
		mean+=frequencies.remove(0);
		count++;
		return Mean(frequencies,count, mean);
	}
	/*
	 * sum of |xi-mean| 
	 */
	double MAD(List<Integer> frequencies, double mean, double sum){
		if(frequencies.isEmpty()){
			return sum;
		}
		sum+=Math.abs(frequencies.remove(0) - mean);
		return MAD(frequencies, mean, sum);
	}

}
