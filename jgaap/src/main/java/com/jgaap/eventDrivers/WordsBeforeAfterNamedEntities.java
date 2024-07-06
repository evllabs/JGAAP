package com.jgaap.eventDrivers;

import java.util.List;
// Need to increase heap size to 2GB by using (java -Xmx2g -jar jgaap.jar) Insert this is Run Configurations, Arguments, VM Arguments
//  When inserted error appears in Console that says "Error: Could not find or load main class java"

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.ling.CoreAnnotations.AnswerAnnotation;
import edu.stanford.nlp.ling.CoreLabel;

import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

public class WordsBeforeAfterNamedEntities extends EventDriver {

	private volatile AbstractSequenceClassifier<CoreLabel> classifier;
	private static String serializedClassifier = "/com/jgaap/resources/models/ner/english.muc.7class.distsim.crf.ser.gz";


	@Override
	public String displayName() {
		return "Words Before and After Named Entities";
	}

	@Override
	public String tooltipText() {
		return "Counts the words used before and after named entities";
	}

	@Override
	public boolean showInGUI() {
		return true; // Make true once code can compile
	}

	@Override
	public EventSet createEventSet(char[] text) throws EventGenerationException {
		EventSet eventSet = new EventSet();

		if (classifier == null) {
			synchronized (this) {
				if (classifier == null) {
					try {
						classifier = CRFClassifier.getJarClassifier(serializedClassifier, null);
					} catch (Exception e) {
						e.printStackTrace();
						throw new EventGenerationException("Classifier failed to load");
					}
				}
			}
		}
		String fileContents = new String(text);
		List<List<CoreLabel>> out = classifier.classify(fileContents);
		for (List<CoreLabel> sentence : out) {
			for (int i = 0; i < sentence.size(); i++) {
				if (!sentence.get(i).get(AnswerAnnotation.class).equals("O")) {
					if (i > 0 && sentence.get(i - 1).get(AnswerAnnotation.class).equals("O")) {
						eventSet.addEvent(new Event("BEFORE " + sentence.get(i - 1).word(), this));
					}
					if (i < sentence.size() - 1 && sentence.get(i + 1).get(AnswerAnnotation.class).equals("O")) {
						eventSet.addEvent(new Event("AFTER " + sentence.get(i + 1).word(), this));
					}
				}
			}
		}

		return eventSet;

	}
}