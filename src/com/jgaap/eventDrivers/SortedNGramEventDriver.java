package com.jgaap.eventDrivers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import com.jgaap.backend.EventDriverFactory;
import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;

public class SortedNGramEventDriver extends EventDriver {

	private static Logger logger = Logger.getLogger("com.jgaap.eventDrivers.SortedNGramEventDriver");
	
	public SortedNGramEventDriver(){
		addParams("N", "N", "2", new String[] { "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
				"18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
				"28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
				"38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
				"48", "49", "50" }, false);
	}
	
	@Override
	public String displayName() {
		return "Sorted NGram";
	}

	@Override
	public String tooltipText() {
		return "Sorts NGrams";
	}

	@Override
	public boolean showInGUI() {
		return false;
	}

	@Override
	public EventSet createEventSet(Document doc) throws EventGenerationException {
		String eventDriverString = getParameter("underlyingEventDriver", "Words");
		String nString = getParameter("N","2");
		int n = Integer.parseInt(nString);
		EventDriver underlyingEventDriver = null;
		try {
			underlyingEventDriver = EventDriverFactory.getEventDriver(eventDriverString);
		} catch (Exception e) {
			logger.error("Problem loading underlying EventDriver "+eventDriverString+".",e);
			throw new EventGenerationException("Problem loading underlying EventDriver "+eventDriverString+".");
		}
		EventSet underlyingEventSet = underlyingEventDriver.createEventSet(doc);
		EventSet eventSet = new EventSet(underlyingEventSet.size()-n);
		for(int i=0;i<underlyingEventSet.size()-n;i++){
			List<String> currentEvents = new ArrayList<String>(n);
			for(int j=0;j<n;j++){
				currentEvents.add(underlyingEventSet.eventAt(i+j).getEvent());
			}
			Collections.sort(currentEvents);
			eventSet.addEvent(new Event(currentEvents.toString()));
		}
		return eventSet;
	}

}
