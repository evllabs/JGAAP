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
package com.jgaap.eventDrivers;

import com.jgaap.util.Event;
import com.jgaap.util.EventSet;


/**
 * @author Mike Mehok
 */
public class FirstWordInSentenceEventDriver extends SentenceEventDriver {

	@Override
	public String displayName() {
		return "First Word In Sentence";
	}

	@Override
	public String tooltipText() {
		return "First Word In Sentence";
	}

	@Override
	public String longDescription() {
		return "First Word In Sentence";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	

	@Override
	public EventSet createEventSet(char[] text) {
		EventSet sentences = super.createEventSet(text);
		EventSet eventSet = new EventSet(sentences.size());
		for(Event sentence : sentences){
			String[] words = sentence.toString().split("\\s+");
			if(words.length>0)
				eventSet.addEvent(new Event(words[0], this));
		}
		return eventSet;
    }

}