// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 * @author Mike Mehok
 */
package com.jgaap.eventDrivers;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;
import javax.swing.*;


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
				es.addEvent(new Event(String.valueOf(wordsPerSent.length)));
			}
		//}
        return es;
    }
}


