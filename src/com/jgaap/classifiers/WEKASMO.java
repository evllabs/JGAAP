package com.jgaap.classifiers;

import java.util.List;

import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.EventSet;

import weka.classifiers.Classifier;

public class WEKASMO extends WEKAAnalysis {

	@Override
	public String displayName() {
		return "WEKA SMO";
	}

	@Override
	public String tooltipText() {
		return "Sequential Minimal Optimization, Courtesy of WEKA";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	
	public Classifier getClassifier() {
		return (Classifier)(new weka.classifiers.functions.SMO());
	}
	
	public void testRequirements(List<EventSet> knownList) throws AnalyzeException{
		//TODO: WEKASMOTest not yet created to find requirements
		return;
	}

}
