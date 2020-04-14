package com.jgaap.generics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jgaap.util.Event;
import com.jgaap.util.EventSet;


public abstract class LeaveKOutNGramEventDriver extends EventDriver {

	public LeaveKOutNGramEventDriver() {
		addParams("K", "K", "1", new String[] { "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
				"18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
				"28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
				"38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
				"48", "49", "50" }, false);
		addParams("N", "N", "3", new String[] { "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
				"18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
				"28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
				"38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
				"48", "49", "50" }, false);
	}
	
	protected EventSet transformEventSet(EventSet underlyingEventSet) {
		int k = getParameter("k", 1);
		int n = getParameter("n", 3);
		EventSet eventSet = new EventSet();
		for (int i = 0; i < underlyingEventSet.size() - n; i++) {
			List<String> currentEvents = new ArrayList<String>(n);
			for (int j = 0; j < n; j++) {
				currentEvents.add(underlyingEventSet.eventAt(i + j).toString());
			}
			Set<List<String>> reducedEvents = getSubList(currentEvents, k);
			for (List<String> current : reducedEvents) {
				eventSet.addEvent(new Event(current.toString(), this));
			}
		}
		return eventSet;
	}

	private Set<List<String>> getSubList(List<String> list, int k) {
		Set<List<String>> results = new HashSet<List<String>>();
		if (k == 1) {
			results = reduceList(list);
		} else {
			Set<List<String>> tmp = getSubList(list, k - 1);
			for (List<String> current : tmp) {
				results.addAll(reduceList(current));
			}
		}
		return results;
	}

	private Set<List<String>> reduceList(List<String> list) {
		Set<List<String>> results = new HashSet<List<String>>();
		for (int i = 0; i < list.size(); i++) {
			List<String> current = new ArrayList<String>(list.size() - 1);
			for (int j = 0; j < i; j++) {
				current.add(list.get(j));
			}
			for (int j = i + 1; j < list.size(); j++) {
				current.add(list.get(j));
			}
			results.add(current);
		}
		return results;
	}

}
