package com.jgaap.eventDrivers;

import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;

public class LeaveKOutWordNGramEventDriver extends EventDriver {

	public LeaveKOutWordNGramEventDriver() {
		addParams("K", "K", "1", new String[] { "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
				"18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
				"28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
				"38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
				"48", "49", "50" }, false);
		addParams("N", "N", "3", new String[] { "1", "2", "3", "4", "5", "6",
				"7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
				"18", "19", "20", "21", "22", "23", "24", "25", "26", "27",
				"28", "29", "30", "31", "32", "33", "34", "35", "36", "37",
				"38", "39", "40", "41", "42", "43", "44", "45", "46", "47",
				"48", "49", "50" }, false);
	}

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

	@Override
	public EventSet createEventSet(char[] text)
			throws EventGenerationException {
		EventDriver driver = new LeaveKOutNGramEventDriver();
		driver.setParameter("K", getParameter("K"));
		driver.setParameter("N", getParameter("N"));
		return driver.createEventSet(text);
	}

}
