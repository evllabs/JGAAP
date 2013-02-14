package com.jgaap.eventDrivers;

import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.NGramEventDriver;
import com.jgaap.util.EventSet;

/**
 * 
 * @author Michael Ryan
 *
 */
public class StanfordPartOfSpeechNGramEventDriver extends NGramEventDriver {

	private EventDriver stanfordPOS = new StanfordPartOfSpeechEventDriver();
	
	public StanfordPartOfSpeechNGramEventDriver() {
		super();
		addParams("taggingModel", "Model", "english-left3words-distsim", new String[] { "arabic-fast", "chinese",
				"english-left3words-distsim", "french", "german-fast" }, false);
	}
	
	@Override
	public String displayName() {
		return "Stanford Part of Speech NGrams";
	}

	@Override
	public String tooltipText() {
		return "A sliding window of N parts of speech as tagged by the Stanford PoS Tagger.";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public EventSet createEventSet(char[] text) throws EventGenerationException {
		stanfordPOS.setParameter("taggingModel", getParameter("taggingModel"));
		return transformToNgram(stanfordPOS.createEventSet(text));
	}

}
