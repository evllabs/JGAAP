package com.jgaap.eventDrivers;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * 
 * @author Michael Ryan
 * 
 */
public class StanfordPartOfSpeechEventDriver extends EventDriver {

	static private Logger logger = Logger.getLogger(com.jgaap.eventDrivers.StanfordPartOfSpeechEventDriver.class);

	private volatile MaxentTagger tagger = null;

	public StanfordPartOfSpeechEventDriver() {
		addParams("taggingModel", "Model", "english-left3words-distsim", new String[] { "arabic-fast", "chinese",
				"english-left3words-distsim", "french", "german-fast" }, false);
	}

	@Override
	public String displayName() {
		return "Stanford Part of Speech";
	}

	@Override
	public String tooltipText() {
		return "A Part of Speech Tagger using the MaxentTagger developed by the Stanford NLP Group http://nlp.stanford.edu";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public EventSet createEventSet(char[] text) throws EventGenerationException {
		if (tagger == null)
			synchronized (this) {
				if (tagger == null) {
					String taggingModel = getParameter("taggingModel", "english-left3words-distsim");
					taggingModel = "com/jgaap/resources/models/postagger/" + taggingModel + ".tagger";
					try {
						tagger = new MaxentTagger(taggingModel);
					} catch (Exception e) {
						logger.error("Could Not instance Maxent Tagger " + taggingModel, e);
						throw new EventGenerationException("Could not instance Maxent Tagger with model located at " + taggingModel);
					}
				}
			}
		List<ArrayList<TaggedWord>> taggedSentences = tagger.process(MaxentTagger.tokenizeText(new StringReader(
				new String(text))));
		EventSet eventSet = new EventSet(taggedSentences.size());
		for (ArrayList<TaggedWord> sentence : taggedSentences) {
			for (TaggedWord taggedWord : sentence) {
				eventSet.addEvent(new Event(taggedWord.tag(), this));
			}
		}
		return eventSet;
	}

}
