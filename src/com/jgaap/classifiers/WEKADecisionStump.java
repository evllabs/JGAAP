package com.jgaap.classifiers;

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

}
