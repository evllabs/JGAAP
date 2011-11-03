package com.jgaap.classifiers;

import weka.classifiers.Classifier;

public class WEKAJ48DecisionTree extends WEKAAnalysis {
	
	//TODO Decision Tree needs more than one document from
	//  each author for training, otherwise the results are
	//  funny.

	@Override
	public String displayName() {
		return "WEKA J48 Decision Tree Classifier";
	}

	@Override
	public String tooltipText() {
		return "J48 Decision Tree Classifier, Courtesy of WEKA";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	
	public Classifier getClassifier() {
		return (Classifier)(new weka.classifiers.trees.J48());
	}

}
