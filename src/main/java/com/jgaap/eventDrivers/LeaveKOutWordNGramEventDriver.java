package com.jgaap.eventDrivers;

import com.jgaap.generics.LeaveKOutNGramEventDriver;
import com.jgaap.util.EventSet;

public class LeaveKOutWordNGramEventDriver extends LeaveKOutNGramEventDriver {

	@Override
	public String displayName() {
		return "Leave K Out Word NGram";
	}

	@Override
	public String tooltipText() {
		return "Leave out all permutations k words from a word gram of size n";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	private NaiveWordEventDriver wordDriver = new NaiveWordEventDriver();
	
	@Override
	public EventSet createEventSet(char[] text) {
		return transformEventSet(wordDriver.createEventSet(text));
	}

}
