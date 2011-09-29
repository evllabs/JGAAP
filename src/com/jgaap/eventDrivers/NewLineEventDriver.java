package com.jgaap.eventDrivers;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;
/**
 * 
 * Split text on contiguous groups of new lines
 * 
 * @author Michael Ryan
 *
 */
public class NewLineEventDriver extends EventDriver {

	@Override
	public String displayName() {
		return "New Lines";
	}

	@Override
	public String tooltipText() {
		return "Events are split on contiguous groups of \n";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public EventSet createEventSet(Document doc) {
		
		String text = doc.stringify();
		String [] events = text.split("[\n]+");
		EventSet eventSet = new EventSet(events.length);
		for(String event : events){
			eventSet.addEvent(new Event(event));
		}
		
		return eventSet;
	}

	
	
}
