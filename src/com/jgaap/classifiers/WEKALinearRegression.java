package com.jgaap.classifiers;

import weka.classifiers.Classifier;
import weka.classifiers.meta.ClassificationViaRegression;

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
		ClassificationViaRegression c = new ClassificationViaRegression();
		c.setClassifier(new weka.classifiers.functions.LinearRegression());
		return (Classifier)c;
	}

}
