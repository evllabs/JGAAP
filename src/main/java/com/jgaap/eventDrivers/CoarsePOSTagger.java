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

import com.google.common.collect.ImmutableMap;
import com.jgaap.backend.API;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

public class CoarsePOSTagger extends PartOfSpeechEventDriver {

	static ImmutableMap<String, String> translationTable = ImmutableMap.<String, String> builder().put("CC", "C")
			.put("CD", "CD").put("DT", "DT").put("EX", "EX").put("FW", "FW").put("IN", "C").put("JJ", "J")
			.put("JJR", "J").put("JJS", "J").put("LS", "LS").put("MD", "MD").put("NN", "N").put("NNS", "N")
			.put("NNP", "N").put("NNPS", "N").put("PDT", "PDT").put("POS", "PDT").put("PRP", "P").put("PRP$", "P")
			.put("RB", "R").put("RBR", "R").put("RBS", "R").put("RP", "R").put("SYM", "Punc").put("TO", "TO")
			.put("UH", "UH").put("VB", "V").put("VBD", "V").put("VBG", "V").put("VBN", "V").put("VBP", "V")
			.put("VBZ", "V").put("WDT", "W").put("WP", "W").put("WP$", "W").put("WRB", "W").put("#", "Punc")
			.put("$", "Punc").put(".", "Punc").put(",", "Punc").put(":", "Punc").put("(", "Punc").put(")", "Punc")
			.put("`", "Punc").put("'", "Punc").put("\"", "Punc").build();

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
		return API.getInstance().getLanguage().getLanguage().equalsIgnoreCase("English");
	}

	@Override
	public EventSet createEventSet(char[] text) {
		EventSet preprocessEventSet = super.createEventSet(text);
		EventSet eventSet = new EventSet();
		for (Event event : preprocessEventSet) {
			if (translationTable.containsKey(event.toString()))
				eventSet.addEvent(new Event(translationTable.get(event.toString()), this));
			else
				eventSet.addEvent(event);
		}
		return eventSet;
	}
}
