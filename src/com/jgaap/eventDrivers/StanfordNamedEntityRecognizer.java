package com.jgaap.eventDrivers;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.ling.CoreLabel;

import java.util.List;

import com.jgaap.generics.Document;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Event;

public class StanfordNamedEntityRecognizer extends EventDriver {

	private volatile AbstractSequenceClassifier<CoreLabel> classifier;

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
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	synchronized public EventSet createEventSet(Document doc)
			throws EventGenerationException {
		EventSet eventSet = new EventSet();
		String serializedClassifier = "/com/jgaap/resources/models/ner/english.all.3class.distsim.crf.ser.gz";
		if (classifier == null)
			synchronized (this) {
				if (classifier == null) {
					try {
						classifier = CRFClassifier.getClassifier(com.jgaap.JGAAP.class.getResourceAsStream(serializedClassifier));
					} catch (Exception e) {
						e.printStackTrace();
						throw new EventGenerationException(
								"Classifier failed to load");
					}
				}
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
