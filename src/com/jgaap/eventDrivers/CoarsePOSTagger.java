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

import java.util.HashMap;
import java.util.Map;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;


public class CoarsePOSTagger extends EventDriver {

	static Map<String, String> translationTable;
	static {
		translationTable = new HashMap<String, String>();

		translationTable.put("CC", "C");
		translationTable.put("CD", "CD");
		translationTable.put("DT", "DT");
		translationTable.put("EX", "EX");
		translationTable.put("FW", "FW");
		translationTable.put("IN", "C");
		translationTable.put("JJ", "J");
		translationTable.put("JJR", "J");
		translationTable.put("JJS", "J");
		translationTable.put("LS", "LS");
		translationTable.put("MD", "MD");
		translationTable.put("NN", "N");
		translationTable.put("NNS", "N");
		translationTable.put("NNP", "N");
		translationTable.put("NNPS", "N");
		translationTable.put("PDT", "PDT");
		translationTable.put("POS", "PDT");
		translationTable.put("PRP", "P");
		translationTable.put("PRP$", "P");
		translationTable.put("RB", "R");
		translationTable.put("RBR", "R");
		translationTable.put("RBS", "R");
		translationTable.put("RP", "R");
		translationTable.put("SYM", "Punc");
		translationTable.put("TO", "TO");
		translationTable.put("UH", "UH");
		translationTable.put("VB", "V");
		translationTable.put("VBD", "V");
		translationTable.put("VBG", "V");
		translationTable.put("VBN", "V");
		translationTable.put("VBP", "V");
		translationTable.put("VBZ", "V");
		translationTable.put("WDT", "W");
		translationTable.put("WP", "W");
		translationTable.put("WP$", "W");
		translationTable.put("WRB", "W");
		translationTable.put("#", "Punc");
		translationTable.put("$", "Punc");
		translationTable.put(".", "Punc");
		translationTable.put(",", "Punc");
		translationTable.put(":", "Punc");
		translationTable.put("(", "Punc");
		translationTable.put(")", "Punc");
		translationTable.put("\"", "Punc");
		translationTable.put("`", "Punc");
		translationTable.put("\"", "Punc");
		translationTable.put("'", "Punc");
		translationTable.put("\"", "Punc");

	}

	@Override
	public String displayName() {
		return "Coarse POS Tagger";
	}

	@Override
	public String tooltipText() {
		return "A simplification of the normal part of speech tagger";
	}

	@Override
	public String longDescription() {
		return "A simplification of the normal part of speech tagger, neutralizing minor variations such as plural inflection; for example, all noun types (proper/common, singular/plural) are grouped.";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public EventSet createEventSet(Document doc) {
		EventSet preprocessEventSet = new PartOfSpeechEventDriver().createEventSet(doc);
		EventSet eventSet = new EventSet(doc.getAuthor());
		for (Event event : preprocessEventSet) {
			if(translationTable.containsKey(event.getEvent()))
				eventSet.addEvent(new Event(translationTable.get(event.getEvent())));
			else
				eventSet.addEvent(event);
		}
		return eventSet;
	}
}
