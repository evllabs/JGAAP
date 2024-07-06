package com.jgaap.generics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jgaap.util.Event;
import com.jgaap.util.EventSet;


public abstract class SortedNGramEventDriver extends EventDriver {
	
	public SortedNGramEventDriver(){
		addParams("N", "N", "2", new String[] { "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
				"18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
				"28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
				"38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
				"48", "49", "50" }, false);
	}

	protected EventSet sortEventSet(EventSet eventSet) {
		int n = getParameter("n", 2);
		EventSet sortedEventSet = new EventSet(eventSet.size()-n);
		for(int i=0;i<eventSet.size()-n;i++){
			List<String> currentEvents = new ArrayList<String>(n);
			for(int j=0;j<n;j++){
				currentEvents.add(eventSet.eventAt(i+j).toString());
			}
			Collections.sort(currentEvents);
			sortedEventSet.addEvent(new Event(currentEvents.toString(), this));
		}
		return sortedEventSet;
	}

}
