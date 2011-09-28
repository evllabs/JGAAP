package com.jgaap.classifiers;

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

}
