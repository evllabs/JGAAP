package com.jgaap.eventDrivers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.jgaap.backend.EventDriverFactory;
import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;

public class LeaveKOutNGramEventDriver extends EventDriver {

	private static Logger logger = Logger.getLogger("com.jgaap.eventDrivers.LeaveKOutNGramEventDriver");
	
	@Override
	public String displayName() {
		return "Leave KOut NGrams";
	}

	@Override
	public String tooltipText() {
		return "Leave K events out of grams of size N";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public EventSet createEventSet(Document doc)
			throws EventGenerationException {
		String eventDriverString = getParameter("underlyingEventDriver", "Words");
		int n = getParameter("N", 3);
		int k = getParameter("K", 1);
		EventDriver underlyingEventDriver = null;
		try {
			underlyingEventDriver = EventDriverFactory.getEventDriver(eventDriverString);
		} catch (Exception e) {
			logger.error("Problem loading underlying EventDriver "+eventDriverString+".",e);
			throw new EventGenerationException("Problem loading underlying EventDriver "+eventDriverString+".");
		}
		EventSet underlyingEventSet = underlyingEventDriver.createEventSet(doc);
		EventSet eventSet = new EventSet();
		for (int i = 0; i < underlyingEventSet.size() - n; i++) {
			List<String> currentEvents = new ArrayList<String>(n);
			for (int j = 0; j < n; j++) {
				currentEvents.add(underlyingEventSet.eventAt(i + j).getEvent());
			}
			Set<List<String>> reducedEvents = getSubList(currentEvents, k);
			for (List<String> current : reducedEvents) {
				eventSet.addEvent(new Event(current.toString()));
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
