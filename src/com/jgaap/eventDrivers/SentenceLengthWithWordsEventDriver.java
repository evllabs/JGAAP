/*
 * JGAAP -- a graphical program for stylometric authorship attribution
 * Copyright (C) 2009,2011 by Patrick Juola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * @author Mike Mehok
 */
package com.jgaap.eventDrivers;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;


public class SentenceLengthWithWordsEventDriver extends EventDriver {
	
	@Override
	public String displayName() {
		return "Sentence Length";
	}
	
	@Override
	public String tooltipText() {
		return "Sentence length (With words)";
	}
	
	@Override
	public boolean showInGUI() {
		return true;
	}
	
	@Override
	public EventSet createEventSet(Document doc) {
		
		EventSet sentences = new SentenceEventDriver().createEventSet(doc);
		EventSet eventSet = new EventSet(sentences.size());
		for(Event sentence : sentences){
			String[] words = sentence.getEvent().split("\\s+");
			eventSet.addEvent(new Event(Integer.toString(words.length)));
		}
		return eventSet;
	}
}


