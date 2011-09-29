package com.jgaap.classifiers;

import weka.classifiers.Classifier;

public class WEKASimpleLinearRegression extends WEKAAnalysis {

	@Override
	public String displayName() {
		return "WEKA Simple Linear Regression";
	}

	@Override
	public String tooltipText() {
		return "Simple Linear Regression Classifier, Courtesy of WEKA";
	}

	@Override
	public boolean showInGUI() {
		return false;
	}
	
	public Classifier getClassifier() {
		return (Classifier)(new weka.classifiers.functions.SimpleLinearRegression());
	}

}
