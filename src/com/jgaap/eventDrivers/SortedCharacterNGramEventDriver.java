package com.jgaap.eventDrivers;

import com.jgaap.generics.EventSet;
import com.jgaap.generics.SortedNGramEventDriver;

public class SortedCharacterNGramEventDriver extends SortedNGramEventDriver {

	@Override
	public String displayName() {
		return "Sorted Character NGram";
	}

	@Override
	public String tooltipText() {
		return "The characters in each ngram are alphabetically sorted";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	
	private CharacterEventDriver driver = new CharacterEventDriver();

	@Override
	public EventSet createEventSet(char[] text) {
		return sortEventSet(driver.createEventSet(text));
	}

}
