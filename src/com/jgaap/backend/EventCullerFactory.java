package com.jgaap.backend;

import com.jgaap.generics.EventCuller;

import java.util.HashMap;
import java.util.Map;

public class EventCullerFactory {

	private Map<String, EventCuller> eventCullers;

	public EventCullerFactory() {
		// Load the classifiers dynamically
		eventCullers = new HashMap<String, EventCuller>();
		for(EventCuller eventCuller: AutoPopulate.getEventCullers()){
			eventCullers.put(eventCuller.displayName().toLowerCase().trim(), eventCuller);
		}
	}

	public EventCuller getEventCuller(String action) throws Exception{
		EventCuller eventCuller;
		action = action.toLowerCase().trim();
		if(eventCullers.containsKey(action)){
			eventCuller = eventCullers.get(action).getClass().newInstance();
		}else{
			throw new Exception("Event culler "+action+" not found!");
		}
		return eventCuller;
	}
}
