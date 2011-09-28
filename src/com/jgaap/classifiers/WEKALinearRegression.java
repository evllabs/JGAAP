package com.jgaap.classifiers;

import weka.classifiers.Classifier;

public class WEKALinearRegression extends WEKAAnalysis {

	@Override
	public String displayName() {
		return "WEKA Linear Regression";
	}

	@Override
	public String tooltipText() {
		return "Linear Regression, Courtesy of WEKA";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	
	public Classifier getClassifier() {
		return (Classifier)(new weka.classifiers.functions.LinearRegression());
	}

}
