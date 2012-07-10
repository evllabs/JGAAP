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
 * Analyze N events with the highest frequency range
 * 
 * @author Christine Gray
 */
public class RangeCuller extends EventCuller {
	public RangeCuller() {
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
		List<Pair<Event,Integer>> rangeList = new ArrayList<Pair<Event,Integer>>(); 
		List<EventHistogram> eventHistograms = new ArrayList<EventHistogram>(eventSets.size());
		for (EventSet eventSet : eventSets) {
			eventHistograms.add(new EventHistogram(eventSet));
		}
		for (Event event : hist) {
			List<Integer>frequencies = new ArrayList<Integer>();
			int range = 0;
			/*
			 * Create a list of frequencies of each event
			 * Then sort list and subtract lowest frequency from highest frequency
			 */
			for (EventHistogram eventHistogram : eventHistograms) {
				frequencies.add(eventHistogram.getAbsoluteFrequency(event));
			}
			Collections.sort(frequencies);
			range = frequencies.get(frequencies.size()-1) - frequencies.get(0);
			rangeList.add(new Pair<Event, Integer>(event, range));
		}
		Collections.sort(rangeList, new SortByRange());
		if(informative.equals("Most")){
			Collections.reverse(rangeList);
		}
		
		List<Event> Set = new ArrayList<Event>();
		for (int i = minPos; i < minPos + numEvents; i++) {
			Set.add(rangeList.get(i).getFirst());
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
		return "Range Culler";
	}

	@Override
	public String tooltipText() {
		return "Analyze N events with the highest ranges";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	@Override
	public String longDescription(){
		return "Analyze N events with the highest frequency range";
	}
}
class SortByRange implements Comparator<Pair<Event, Integer>> {
	public int compare(Pair<Event, Integer> p1, Pair<Event, Integer> p2) {
		return p1.getSecond().compareTo(p2.getSecond());
	}
}
