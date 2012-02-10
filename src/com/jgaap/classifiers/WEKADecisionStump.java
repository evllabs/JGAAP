package com.jgaap.classifiers;

import java.util.List;

import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.EventSet;

import weka.classifiers.Classifier;

public class WEKADecisionStump extends WEKAAnalysis {

	@Override
	public String displayName() {
		return "WEKA Decision Stump Classifier";
	}

	@Override
	public String tooltipText() {
		return "Decision Stump, Courtesy of WEKA";
	}

	@Override
	public boolean showInGUI() {
		return false;
	}
	
	public Classifier getClassifier() {
		return (Classifier)(new weka.classifiers.trees.DecisionStump());
	}
	
	public void testRequirements(List<EventSet> knownList) throws AnalyzeException{
		//No requirements for Decision Stump
		return;
	}

}
