package com.jgaap.generics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jgaap.util.Event;
import com.jgaap.util.EventSet;


public abstract class SortedNGramEventDriver extends EventDriver {
	
	public SortedNGramEventDriver(){
		addParams("N", "N", "4", new String[] { "4" }, false);
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
