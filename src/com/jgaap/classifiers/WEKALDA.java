package com.jgaap.classifiers;

import java.util.List;

import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

public class WEKALDA extends AnalysisDriver {

	@Override
	public String displayName() {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public String tooltipText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean showInGUI() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void train(List<EventSet> knownEventSets) throws AnalyzeException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Pair<String, Double>> analyze(EventSet unknownEventSet)
			throws AnalyzeException {
		// TODO Auto-generated method stub
		return null;
	}


}
