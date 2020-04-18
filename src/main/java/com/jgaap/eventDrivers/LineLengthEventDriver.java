package com.jgaap.eventDrivers;

import com.jgaap.generics.EventDriver;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

/**
 * 
 * Gets the number of words per line of text.
 * @author David Berdik
 *
 */
public class LineLengthEventDriver extends EventDriver {

	@Override
	public String displayName() {
		return "Line Length";
	}

	@Override
	public String tooltipText() {
		return "Gets the number of words per line of text.";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public EventSet createEventSet(char[] text) {
		// Get lines from text.
		EventSet linesEventSet = new NewLineEventDriver().createEventSet(text);
		
		// Create an EventSet that consists of length values for each line.
		EventSet lineLengths = new EventSet();
		for(Event line : linesEventSet)
			lineLengths.addEvent(new Event(Integer.toString(line.toString().trim().split("\\s+").length), this));
		
		return lineLengths;
	}

}
