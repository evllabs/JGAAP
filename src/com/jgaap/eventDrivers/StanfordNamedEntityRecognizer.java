package com.jgaap.eventDrivers;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.ling.CoreAnnotations.AnswerAnnotation;
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

	@Override
	synchronized public EventSet createEventSet(Document doc)
			throws EventGenerationException {
		EventSet eventSet = new EventSet();
		//		String serializedClassifier = "/com/jgaap/resources/models/ner/english.all.3class.distsim.crf.ser.gz";  original classifier
		String serializedClassifier = "/com/jgaap/resources/models/ner/english.muc.7class.distsim.crf.ser.gz";  // Runs with this one too.  Still no output
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

		String fileContents = doc.stringify();
		List<List<CoreLabel>> out = classifier.classify(fileContents);
		for (List<CoreLabel> sentence : out) {
			for (CoreLabel word : sentence) {
				System.out.println(word.word()+"\t" + word.get(AnswerAnnotation.class));
				if (!word.get(AnswerAnnotation.class).equals("0") || !word.get(AnswerAnnotation.class).equals("O")) {
					eventSet.addEvent(new Event(word.word()));
				System.out.println(word.word()+"\t" + word.get(AnswerAnnotation.class));
				}
			}	
		}	
		return eventSet;
	}	

}
