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

import java.util.Arrays;
import java.util.List;

import com.jgaap.backend.API;
import com.jgaap.canonicizers.PunctuationSeparator;
import com.jgaap.generics.EventDriver;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * This changes words into their parts of speech in a document. This does not
 * handle punctuation well changes all words with punctuation at the end into
 * the tag CD.
 * 
 */

public class PartOfSpeechEventDriver extends EventDriver {

	@Override
	public String displayName() {
		return "POS";
	}

	@Override
	public String tooltipText() {
		return "Parts of Speech";
	}

	@Override
	public boolean showInGUI() {
		return API.getInstance().getLanguage().getLanguage().equalsIgnoreCase("English");
	}

	private static PunctuationSeparator punctuationSeparator = new PunctuationSeparator();
	
	@Override
	public EventSet createEventSet(char[] text) {

		MaxentTagger tagger = null;
		try {
			tagger = new MaxentTagger(getClass().getResource("/com/jgaap/resources/models/postagger/english-left3words-distsim.tagger").toString());
		} catch (Exception e) {}
		
		EventSet es = new EventSet();

		text = punctuationSeparator.process(text);

		String stringText = new String(text);

		for (String current : stringText.split("(?<=[?!\\.])\\s+")) {

			String[] tmpArray = current.split("\\s");

			List<String> tmp = Arrays.asList(tmpArray);

			for (int j = 0; j < tmp.size(); j++) {
				String tagged = tagger.tagString(tmp.get(j));
				
				// The Stanford POS tagger's tagString() method returns the passed-in string with an underscore followed by the tag and a
				// space character appended to it. Since we only want the tag, we must strip off all extra data if the returned string
				// is not empty.
				if(!tagged.equals(""))
					tagged = tagged.substring(tagged.lastIndexOf('_') + 1, tagged.length() - 1);
				
				es.addEvent(new Event(tagged.substring(0, tagged.length()), this));
			}
		}
		return es;

	}

}
