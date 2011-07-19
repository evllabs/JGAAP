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


/**
 * @author Mike Mehok
 */
public class FirstWordInSentenceEventDriver extends EventDriver {

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
	public EventSet createEventSet(Document doc) {
		EventSet es = new EventSet(doc.getAuthor());
		//for (int i = 0; i < ds.documentCount(); i++) {
			String current = doc.stringify();
			String[] result = current.split("(((?<![A-Z])[?][\\s]*)|((?<![0-9])[?][\\s]*))|(((?<![A-Z])[!][\\s]*)|((?<![0-9])[!][\\s]*))|(((?<![A-Z])[a-z][.][\\s]++)|((?<=[0-9])[.][\\s]*))");
        //regex splits at last letter of a word on a sentence ending with a period.
        //EX: Im going to the mall. -- Im going to the mal
			String splitSentence;
			for (int j = 0; j < result.length; j++) {
				splitSentence = result[j];
				String[] wordsPerSent = splitSentence.split("[\\s++]|[,.][\\s++]");
				es.addEvent(new Event(wordsPerSent[0]));
			}
		//}
        return es;
    }

}

