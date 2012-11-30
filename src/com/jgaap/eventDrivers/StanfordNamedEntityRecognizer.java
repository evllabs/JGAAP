package com.jgaap.eventDrivers;

import java.util.List;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;

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

	@Override
	synchronized public EventSet createEventSet(char[] text)
			throws EventGenerationException {
		EventSet eventSet = new EventSet();
		String serializedClassifier = "/com/jgaap/resources/models/ner/english.all.3class.distsim.crf.ser.gz";
		if (classifier == null)
			synchronized (this) {
				if (classifier == null) {
					try {
						classifier = CRFClassifier.getJarClassifier(serializedClassifier, null);
					} catch (Exception e) {
						e.printStackTrace();
						throw new EventGenerationException(
								"Classifier failed to load");
					}
				}
			}
		String fileContents = new String(text);
		List<List<CoreLabel>> out = classifier.classify(fileContents);
		for (List<CoreLabel> sentence : out) {
			for (CoreLabel word : sentence) {
				System.out.println(word.originalText()+" ner:"+word.ner()+" word:"+word.word()+" tag:"+word.tag());
				if (word.ner() != null) {
					eventSet.addEvent(new Event(word.word(), this));
					System.out.println(word.toString() + "\t" + word.word()
							+ "\t" + word.ner());
				}
			}
		}
		return eventSet;
	}

}
