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

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;
import com.knowledgebooks.nlp.fasttag.*;
import java.util.*;


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
		return true;
	}

	@Override
	public EventSet createEventSet(Document doc) {

		EventSet es = new EventSet(doc.getAuthor());

		String current = doc.stringify();

		FastTag tagger = new FastTag();

		List<String> tmp = new ArrayList<String>();

		String[] tmpArray = current.split("\\s");

		for (int j = 0; j < tmpArray.length; j++) {
			tmp.add(tmpArray[j]);
		}

		List<String> tagged = tagger.tag(tmp);

		for (int j = 0; j < tagged.size(); j++) {
			es.addEvent(new Event(tagged.get(j)));
		}

		return es;

	}

}
