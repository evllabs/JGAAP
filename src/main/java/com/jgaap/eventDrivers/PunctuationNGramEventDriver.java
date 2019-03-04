package com.jgaap.eventDrivers;

import com.jgaap.generics.NGramEventDriver;
import com.jgaap.util.EventSet;

public class PunctuationNGramEventDriver extends NGramEventDriver {

	private PunctuationEventDriver punctuationEventDriver = new PunctuationEventDriver();
	
	@Override
	public String displayName() {
		return "Punctuation NGrams";
	}

	@Override
	public String tooltipText() {
		return "Sliding windows of punctuation.";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public EventSet createEventSet(char[] text) {
		return transformToNgram(punctuationEventDriver.createEventSet(text));
	}

}
