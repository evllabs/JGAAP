package com.jgaap.backend;

import java.util.HashMap;
import java.util.Map;

import com.jgaap.generics.EventDriver;

public class EventDriverFactory {

	private Map<String, EventDriver> eventDrivers;
	
	public EventDriverFactory() {
		// Load the event drivers dynamically
		eventDrivers = new HashMap<String, EventDriver>();
		for(EventDriver eventDriver : AutoPopulate.getEventDrivers()){
			eventDrivers.put(eventDriver.displayName().toLowerCase().trim(), eventDriver);
		}
	}
	
	public EventDriver getEventDriver(String action) throws Exception{
		EventDriver eventDriver;
		action = action.toLowerCase().trim();
		if(eventDrivers.containsKey(action)){
			eventDriver = eventDrivers.get(action).getClass().newInstance();
		}else{
			throw new Exception("Event Driver "+action+" not found!");
		}
		return eventDriver;
	}
}
