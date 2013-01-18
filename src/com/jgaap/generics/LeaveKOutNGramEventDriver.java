package com.jgaap.generics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public abstract class LeaveKOutNGramEventDriver extends EventDriver {

	protected EventSet transformEventSet(EventSet underlyingEventSet, int k, int n) {
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
