package com.jgaap.eventDrivers;

import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

/**
 * 
 * Gets the last letter from the last word in each line.
 * @author David Berdik
 *
 */
public class RhymeEventDriver extends EventDriver {

	@Override
	public String displayName() {
		return "Rhyme";
	}

	@Override
	public String tooltipText() {
		return "Gets the last letter from the last word in each line.";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public EventSet createEventSet(char[] text) throws EventGenerationException {
		EventSet rhymeEventSet = new EventSet();
		
		// Get lines from text.
		EventSet linesEventSet = new NewLineEventDriver().createEventSet(text);
		
		// For each line, get the last word and extract the last character from it.
		NaiveWordEventDriver wordDriver = new NaiveWordEventDriver();
		for(Event line : linesEventSet) {
			EventSet wordsEventSet = wordDriver.createEventSet(line.toString().toCharArray());
			if (wordsEventSet.size() > 0) {
				String lastWord = wordsEventSet.eventAt(wordsEventSet.size() - 1).toString();
				rhymeEventSet.addEvent(new Event(lastWord.charAt(lastWord.length() - 1), this));
			}
		}
		
		return rhymeEventSet;
	}

}
