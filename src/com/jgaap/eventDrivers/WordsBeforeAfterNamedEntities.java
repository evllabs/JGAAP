package com.jgaap.eventDrivers;

import java.util.List;
// Need to increase heap size to 2GB by using (java -Xmx2g -jar jgaap.jar) Insert this is Run Configurations, Arguments, VM Arguments
//  When inserted error appears in Console that says "Error: Could not find or load main class java"

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.ling.CoreAnnotations.AnswerAnnotation;
import edu.stanford.nlp.ling.CoreLabel;

import com.jgaap.generics.Document;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Event;

public class WordsBeforeAfterNamedEntities extends EventDriver {

	private volatile AbstractSequenceClassifier<CoreLabel> classifier;

	String serializedClassifier = "com.jgaap.generics.Document";

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
	public EventSet createEventSet(Document doc)
			throws EventGenerationException {
		EventSet eventSet = new EventSet();
		// String serializedClassifier =
		// "/com/jgaap/resources/models/ner/english.all.3class.distsim.crf.ser.gz";
		// original classifier
		String serializedClassifier = "/com/jgaap/resources/models/ner/english.muc.7class.distsim.crf.ser.gz"; 
		// Runs with this one too. Decide which to keep?!
		
		if (classifier == null){
			synchronized (this) {
				if (classifier == null) {
					try {
						classifier = CRFClassifier.getJarClassifier(
								serializedClassifier, null);
					} catch (Exception e) {
						e.printStackTrace();
						throw new EventGenerationException(
								"Classifier failed to load");
					}
				}
			}
		}
		String fileContents = doc.stringify();
		List<List<CoreLabel>> out = classifier.classify(fileContents);
		for (List<CoreLabel> sentence : out) {
			for (int i = 0; i < sentence.size(); i++) {
				if (!sentence.get(i).get(AnswerAnnotation.class).equals("O")) {
					if (i > 0 && sentence.get(i-1).get(AnswerAnnotation.class).equals("O")) {
						eventSet.addEvent(new Event("BEFORE "
								+ sentence.get(i - 1).word()));
					}
					if (i < sentence.size() - 1 && sentence.get(i+1).get(AnswerAnnotation.class).equals("O")) {
						eventSet.addEvent(new Event("AFTER "
								+ sentence.get(i + 1).word()));
					}
				}
			}
		}

		return eventSet;

	}
}