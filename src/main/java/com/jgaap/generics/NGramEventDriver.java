package com.jgaap.generics;

import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

public abstract class NGramEventDriver extends EventDriver {

	public NGramEventDriver() {
		addParams("N", "N", "2", new String[] { "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
				"18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
				"28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
				"38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
				"48", "49", "50" }, false);
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
