package com.jgaap.eventDrivers;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.ling.CoreLabel;

import java.io.IOException;
import java.util.List;

import com.jgaap.generics.Document;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Event;

public class StanfordNamedEntityRecognizer extends EventDriver {

	String serializedClassifier = "com.jgaap.generics.Document";

	@Override
	public String displayName() {
		return "Stanford Named Entity Recognizer";
	}

	@Override
	public String tooltipText() {
		return "A Named Entity Recognizer developed by the Stanford NLP Group http://nlp.stanford.edu";
	}

	@Override
	public boolean showInGUI() {
		return false;
	}


	@Override
	public EventSet createEventSet(Document doc) throws EventGenerationException {
		EventSet eventSet = new EventSet();
		String serializedClassifier = "com/jgaap/resources/models/ner/english.conll.4class.distsim.crf.ser.gz";
		AbstractSequenceClassifier<CoreLabel> classifier;
		try {
			classifier = CRFClassifier.getClassifier(com.jgaap.JGAAP.class.getResourceAsStream(serializedClassifier));
		} catch (Exception e) {
			throw new EventGenerationException("Classifier failed to load");
		}
		String fileContents = doc.stringify();
		List<List<CoreLabel>> out = classifier.classify(fileContents);
		for (List<CoreLabel> sentence : out) {
			for (CoreLabel word : sentence) {
				if (word.ner() != null) {
					eventSet.addEvent(new Event(word.word()));
					System.out.println(word.toString() + "\t" + word.word()
							+ "\t" + word.ner());
				}
			}
		}
		return eventSet;
	}

}
