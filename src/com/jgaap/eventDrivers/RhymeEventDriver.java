package com.jgaap.eventDrivers;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jgaap.generics.EventDriver;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

/**
 * 
 * Gets the last consonant cluster from the last word in each line.
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
		return "Gets the last consonant cluster from the last word in each line.";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public EventSet createEventSet(char[] text) {
		EventSet rhymeEventSet = new EventSet();
		Pattern consClusterPattern = Pattern.compile("(?i)[bcdfghjklmnpqrstvwxyz]{2,}");
		
		// Get lines from text.
		EventSet linesEventSet = new NewLineEventDriver().createEventSet(text);
		
		// For each line, get the last word and extract the last consonant cluster from it.
		NaiveWordEventDriver wordDriver = new NaiveWordEventDriver();
		for(Event line : linesEventSet) {
			EventSet wordsEventSet = wordDriver.createEventSet(line.toString().toCharArray());
			if (wordsEventSet.size() > 0) {
				String lastWord = wordsEventSet.eventAt(wordsEventSet.size() - 1).toString();
				
				// Get all consonant clusters from the last word.
				Matcher consClusterMatcher = consClusterPattern.matcher(lastWord);
				ArrayList<String> clusters = new ArrayList<>();
				while(consClusterMatcher.find())
					clusters.add(consClusterMatcher.group());
				
				// Get the last consonant cluster and add it to the EventSet.
				if (clusters.size() > 0)
					rhymeEventSet.addEvent(new Event(clusters.get(clusters.size() - 1), this));
			}
		}
		
		return rhymeEventSet;
	}

}
