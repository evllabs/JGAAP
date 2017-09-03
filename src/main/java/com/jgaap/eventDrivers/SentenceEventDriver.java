package com.jgaap.eventDrivers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

import com.jgaap.JGAAPConstants;
import com.jgaap.generics.EventDriver;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

/**
 * 
 * 
 * 
 * @author Michael Ryan
 *
 */

public class SentenceEventDriver extends EventDriver {

	private static Logger logger = Logger.getLogger(SentenceEventDriver.class);
	private static String regex;
	
	static {
		InputStream is = SentenceEventDriver.class.getResourceAsStream(JGAAPConstants.JGAAP_RESOURCE_PACKAGE + "abbreviation.list");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		Set<String> abbreviations = new HashSet<String>();
		try {
			for (String current; (current = reader.readLine()) != null;) {
				if (!current.isEmpty()) {
					abbreviations.add(current);
					abbreviations.add(current.toLowerCase());
					abbreviations.add(current.toUpperCase());
				}
			}
			reader.close();
		} catch (IOException e) {
			logger.warn("Problem while reading abbrivation list", e);
		}
		// Builds the regex String of the form
		// (abbreviation|abbreviation|...)\\s?[?!\\.]$
		// breaking on the space after the specified punctuation
		// except when preceded by an abbreviation
		StringBuilder regexBuilder = new StringBuilder();
		regexBuilder.append(".*(");
		Iterator<String> abbreviationIterator = abbreviations.iterator();
		regexBuilder.append(abbreviationIterator.next());
		while (abbreviationIterator.hasNext()) {
			String abbreviation = abbreviationIterator.next();
			regexBuilder.append("|").append(abbreviation);
		}
		regexBuilder.append(")\\s?[?!\\.]$");
		regex = regexBuilder.toString();
	}
	
	@Override
	public String displayName() {
		return "Sentences";
	}

	@Override
	public String tooltipText() {
		return "Full sentences including punctuation";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public EventSet createEventSet(char[] text) {
		logger.debug(regex);
		String textString = new String(text);
		String[] sentences = textString.split("(?<=[?!\\.])\\s+");
		EventSet eventSet = new EventSet(sentences.length);
		StringBuilder eventBuilder = new StringBuilder();
		for (String sentence : sentences) {
			eventBuilder.append(sentence);
			if(!sentence.matches(regex)){
				eventSet.addEvent(new Event(eventBuilder.toString(), this));
				eventBuilder = new StringBuilder();
			} else {
				eventBuilder.append(" ");
			}
		}
		String edgeCase = eventBuilder.toString();
		if(!edgeCase.isEmpty()){
			eventSet.addEvent(new Event(edgeCase, this));
		}
		return eventSet;
	}

}
