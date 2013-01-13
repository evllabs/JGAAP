package com.jgaap.eventDrivers;

import com.jgaap.backend.Utils;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;

/**
 * 
 * @author Michael Ryan
 *
 */
public class StanfordPartOfSpeechNGramEventDriver extends EventDriver {

	private EventDriver stanfordPOS = new StanfordPartOfSpeechEventDriver();
	
	public StanfordPartOfSpeechNGramEventDriver(){
		addParams("tagginModel", "Model", "english-bidirectional-distsim", 
				new String[] { "arabic-accurate","arabic-fast.tagger","chinese",
				"english-bidirectional-distsim","english-left3words-distsim",
				"french",
				//"german-dewac",
				"german-fast"
				//,"german-hgc"
				}, false);
		addParams("N", "N", "2", 
				new String[] { "1","2","3","4","5","6","7",
				"8","9","10","11","12","13","14","15"}, false);
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
	public EventSet createEventSet(char[] text)
			throws EventGenerationException {
		stanfordPOS.setParameter("taggingModel", getParameter("taggingModel"));
		EventSet posEventSet = stanfordPOS.createEventSet(text);
		int n = getParameter("N", 2);
		return Utils.convertNGrams(posEventSet, n, this);
	}

}
