package com.jgaap.eventDrivers;

import com.jgaap.generics.EventDriver;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

public class PunctuationEventDriver extends EventDriver {

	@Override
	public String displayName() {
		return "Punctuation";
	}

	@Override
	public String tooltipText() {
		return "Non alphanumeric nor whitspace";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public EventSet createEventSet(char[] text) {
		EventSet eventSet = new EventSet();
		for(char character: text){
			if(!(Character.isLetterOrDigit(character)||Character.isWhitespace(character))){
				eventSet.addEvent(new Event(character, this));
			}
		}
		return eventSet;
	}

	
}
