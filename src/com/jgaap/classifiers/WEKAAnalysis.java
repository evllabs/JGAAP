package com.jgaap.classifiers;

import java.util.ArrayList;
import java.util.List;

import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

public abstract class WEKAAnalysis extends AnalysisDriver {

	@Override
	public String displayName() {
		return "Generic WEKA Classifier";
	}

	@Override
	public String tooltipText() {
		return "Generic WEKA Classifier; should not appear in GUI";
	}

	@Override
	public boolean showInGUI() {
		return false;
	}

	/**
	 * 
	 */
	public List<List<Pair<String, Double>>> analyze(List<EventSet> unknownList,
			List<EventSet> known) {
		List<List<Pair<String, Double>>> results = new ArrayList<List<Pair<String, Double>>>();

		
		
		return results;
	}

}
