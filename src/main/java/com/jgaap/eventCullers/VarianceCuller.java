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
 * Ananlyze N events with the highest variance\n
 * 1/n sum\u03A3 for i = 1 to n (xi - mean)^2
 * 
 * @author Christine Gray
 */
public class VarianceCuller extends FilterEventCuller{
	public VarianceCuller() {
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
		List<Pair<Event,Double>> VAR = new ArrayList<Pair<Event,Double>>(); 
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
			VAR.add(new Pair<Event, Double>(event, (1/(total))*VAR(frequencies, mean, 0.0), 2));
		}		
		Collections.sort(VAR);
		if(informative.equals("Most")){
			Collections.reverse(VAR);
		}
		int counter = 0;
		Set<Event> events = new HashSet<Event>(numEvents);
		for(Pair<Event, Double> event : VAR){
			counter++;
			events.add(event.getFirst());
			if(counter == numEvents)
				break;
		}
		return events;
	}

	@Override
	public String displayName() {
		return "Variance Culler";
	}

	@Override
	public String tooltipText() {
		return null;
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	@Override 
	public String longDescription(){
		return "Ananlyze N events with the highest variance\n"+
				"1/n \u03A3 for i = 1 to n (xi - \u03BC)\u00B2";
	}
	double Mean(List<Integer>frequencies, double count, double mean){
		if(frequencies.isEmpty()){
			return mean/count;
		}		
		mean+=frequencies.remove(0);
		count++;
		return Mean(frequencies,count, mean);
	}
	double VAR(List<Integer> frequencies, double mean, double sum){
		if(frequencies.isEmpty()){
			return sum;
		}
		sum+=Math.pow(frequencies.remove(0)-mean,2);
		return VAR(frequencies, mean, sum);
	}

}
