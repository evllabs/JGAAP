package com.jgaap.eventDrivers;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;
import javax.swing.*;


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
	public boolean showInGUI() {
		return true;
	}

        @Override
        public GroupLayout getGUILayout(JPanel panel){
            return null;
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

