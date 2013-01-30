package com.jgaap.eventDrivers;

import com.jgaap.generics.SortedNGramEventDriver;
import com.jgaap.util.EventSet;

public class SortedWordNGramEventDriver extends SortedNGramEventDriver {

	@Override
	public String displayName() {
		return "Sorted Word NGram";
	}

	@Override
	public String tooltipText() {
		return "The words in each ngram are alphabetically sorted";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	private NaiveWordEventDriver wordEventDriver = new NaiveWordEventDriver();
	
	@Override
	public EventSet createEventSet(char[] text) {
		return sortEventSet(wordEventDriver.createEventSet(text));
	}

}
