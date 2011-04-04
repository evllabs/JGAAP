// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
package com.jgaap.eventCullers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventCuller;
import com.jgaap.generics.EventSet;

public class ExtremeCuller extends EventCuller {

	@Override
	public List<EventSet> cull(List<EventSet> eventSets) {
		List<Set<String>> eventSetsUnique = new ArrayList<Set<String>>();
		Set<String> uniqueEvents = new HashSet<String>();
		Set<String> extremeEvents = new HashSet<String>();
		for(EventSet eventSet : eventSets){
			Set<String> events = new HashSet<String>();
			for(Event event : eventSet){
				events.add(event.getEvent());
				uniqueEvents.add(event.getEvent());
			}
			eventSetsUnique.add(events);
		}
		for(String event : uniqueEvents){
			boolean extreme = true;
			for(Set<String> events : eventSetsUnique){
				if(!events.contains(event)){
					extreme = false;
					break;
				}
			}
			if(extreme){
				extremeEvents.add(event);
			}
		}
		for(EventSet eventSet : eventSets){
			Iterator<Event> eventIterator = eventSet.iterator();
			while(eventIterator.hasNext()){
				Event event = eventIterator.next();
				if(!extremeEvents.contains(event.getEvent())){
					eventIterator.remove();
				}
			}
		}
		return eventSets;
	}

	@Override
	public String displayName() {
		return "X-treme Culler";
	}

	@Override
	public String tooltipText() {
		return "All Events appear in all samples suggested by (Jockers, 2008)";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

}
