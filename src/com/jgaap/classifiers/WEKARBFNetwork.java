package com.jgaap.classifiers;

import weka.classifiers.Classifier;

public class WEKARBFNetwork extends WEKAAnalysis {

	@Override
	public String displayName() {
		return "WEKA RBF Network";
	}

	@Override
	public String tooltipText() {
		return "Radial Basis Function Network, Courtesy of WEKA";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	
	public Classifier getClassifier() {
		return (Classifier)(new weka.classifiers.functions.RBFNetwork());
	}

}
