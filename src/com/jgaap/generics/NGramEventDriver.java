package com.jgaap.generics;

import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

public abstract class NGramEventDriver extends EventDriver {

	public NGramEventDriver() {
		addParams("N", "N", "2", new String[] { "2" }, false);
	}
	
	protected EventSet transformToNgram(EventSet eventSet) {
		int n = getParameter("n", 2);
		EventSet ngramEventSet = new EventSet(eventSet.size());
		for(int i = 0; i+n <= eventSet.size(); i++){
			Event event = new Event(eventSet.subList(i, i+n).toString(), this);
			ngramEventSet.addEvent(event);
		}
		return ngramEventSet;
	}
}
