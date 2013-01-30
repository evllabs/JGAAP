package com.jgaap.generics;

import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

public abstract class TruncatedEventDriver extends EventDriver {

	protected EventSet truncate(EventSet eventSet, int length) {
		EventSet truncatedEventSet = new EventSet(eventSet.size());
		for(Event event : eventSet) {
			String current = event.toString();
	    	   if(current.length()>length)
	    		   truncatedEventSet.addEvent(new Event(current.substring(0, length), this));
	    	   else 
	    		   truncatedEventSet.addEvent(new Event(current, this));
		}
		return truncatedEventSet;
	}
}
