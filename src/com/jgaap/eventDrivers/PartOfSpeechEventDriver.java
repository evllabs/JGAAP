package com.jgaap.eventDrivers;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;
import com.knowledgebooks.nlp.fasttag.*;
import java.util.*;
import javax.swing.*;


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
        public GroupLayout getGUILayout(JPanel panel){
            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
            return layout;
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
