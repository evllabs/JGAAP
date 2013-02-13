package com.jgaap.eventDrivers;

import com.jgaap.generics.LeaveKOutNGramEventDriver;
import com.jgaap.util.EventSet;

public class LeaveKOutCharacterNGramEventDriver extends LeaveKOutNGramEventDriver {

	@Override
	public String displayName() {
		return "Leave K Out Character NGram";
	}

	@Override
	public String tooltipText() {
		return "Leave out all permutations k characters from a word gram of size n";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	private CharacterEventDriver characterDriver = new CharacterEventDriver();
	
	@Override
	public EventSet createEventSet(char[] text) {
		return transformEventSet(characterDriver.createEventSet(text));
	}

}
