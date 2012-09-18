package com.jgaap.eventCullers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventCuller;
import com.jgaap.generics.EventCullingException;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;
/**
 * Analyze N events with the the highest interquartile range where the interquartile range is the third quartile - the first quartile.
 * IQR = Q3 - Q1
 * 
 * @author Christine Gray
 */
public class IQRCuller extends EventCuller {
	public IQRCuller() {
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
		List<Pair<Event,Double>> rangeList = new ArrayList<Pair<Event,Double>>(); 
		List<EventHistogram> eventHistograms = new ArrayList<EventHistogram>(eventSets.size());
		for (EventSet eventSet : eventSets) {
			eventHistograms.add(new EventHistogram(eventSet));
		}
		for (Event event : hist) {
			List<Double>frequencies = new ArrayList<Double>();
			double IQR = 0;
			double med= 0;
			double Q1 = 0;  /*The value of the first quartile*/
			double Q3 = 0;	/*The value of the third quartile*/
			double Q1Index = 0;	/*The index where the first quartile is located*/
			double Q3Index = 0;	/*The index where the second quartile is located*/
			
			/*
			 * Create a list of the frequency of each event across all documents
			 * Then sort by frequency
			 */
			for (EventHistogram eventHistogram : eventHistograms) {
				frequencies.add((double) eventHistogram.getAbsoluteFrequency(event));
			}			
			Collections.sort(frequencies);
			med = frequencies.size()/2;
			Q1Index = med/2;
			/*
			 * Calculate the indexes of Q1 and Q3
			 * If the index is between two numbers, take the average of the two numbers
			 */
			if(Math.round(med)==med && Math.round(Q1Index)>Q1Index){
				Q1Index = Math.round(Q1Index) - 1 ;
				Q3Index = med + Q1Index;
				Q1 = frequencies.get((int) Q1Index);
				Q3 = frequencies.get((int) Q3Index);
			}
			else if(Math.round(med)==med && Math.round(Q1Index)==Q1Index){
				Q3Index = med + Q1Index;
				Q1 = (frequencies.get((int) Q1Index) + frequencies.get((int) (Q1Index-1)))/2;
				Q3 = (frequencies.get((int) Q3Index) + frequencies.get((int) (Q3Index-1)))/2;
			}
			IQR = Q3 - Q1;  //Calculate the Interquartile Range
			rangeList.add(new Pair<Event, Double>(event, IQR,2));
		}
		Collections.sort(rangeList);
		if(informative.equals("Most")){
			Collections.reverse(rangeList);
		}
		List<Event> rangeSet = new ArrayList<Event>();
		for (int i = minPos; i < minPos + numEvents; i++) {
			rangeSet.add(rangeList.get(i).getFirst());
		}
		for (EventSet oneSet : eventSets) {
			EventSet newSet = new EventSet();
			for (Event e : oneSet) {
				if (rangeSet.contains(e)) {
					newSet.addEvent(e);
				}
			}
			results.add(newSet);
		}
		return results;
	}

	@Override
	public String displayName() {
		return "Interquartile Range";
	}

	@Override
	public String tooltipText() {
		return "Ananlze N events with the highest Interquartile Range";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	@Override
	public String longDescription(){
		return "Analyze N events with the highest interquartile range where the interquartile range is the third quartile - the first quartile."
				+ "\nIQR = Q3 - Q1";
	}

}
