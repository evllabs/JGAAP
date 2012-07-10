package com.jgaap.eventCullers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventCuller;
import com.jgaap.generics.EventCullingException;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;
/**
 * Ananlyze N events with the highest variance\n
 * 1/n sum\u03A3 for i = 1 to n (xi - mean)^2
 * 
 * @author Christine Gray
 */
public class VarianceCuller extends EventCuller{
	public VarianceCuller() {
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
		Collections.sort(VAR, new SortByVAR());
		if(informative.equals("Most")){
			Collections.reverse(VAR);
		}
		List<Event> Set = new ArrayList<Event>();
		for (int i = minPos; i < minPos + numEvents; i++) {
			Set.add(VAR.get(i).getFirst());
		}
		for (EventSet oneSet : eventSets) {
			EventSet newSet = new EventSet();
			for (Event e : oneSet) {
				if (Set.contains(e)) {
					newSet.addEvent(e);
				}
			}
			results.add(newSet);
		}
		return results;
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
class SortByVAR implements Comparator<Pair<Event, Double>> {
	public int compare(Pair<Event, Double> p1, Pair<Event, Double> p2) {
		return p1.getSecond().compareTo(p2.getSecond());
	}
}
