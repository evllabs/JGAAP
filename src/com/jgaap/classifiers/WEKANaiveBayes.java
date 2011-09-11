package com.jgaap.classifiers;

import weka.classifiers.Classifier;

public class WEKANaiveBayes extends WEKAAnalysis {

	@Override
	public String displayName() {
		return "WEKA Naive Bayes Classifier";
	}

	@Override
	public String tooltipText() {
		return "Naive Bayes Classifier, Courtesy of WEKA";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	
	public Classifier getClassifier() {
		return (Classifier)(new weka.classifiers.bayes.NaiveBayes());
	}

}
