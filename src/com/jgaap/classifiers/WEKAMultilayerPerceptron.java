package com.jgaap.classifiers;

import weka.classifiers.Classifier;

public class WEKAMultilayerPerceptron extends WEKAAnalysis {

	@Override
	public String displayName() {
		return "WEKA Multilayer Perceptron";
	}

	@Override
	public String tooltipText() {
		return "Multilayer Perceptron, Courtesy of WEKA";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	
	public Classifier getClassifier() {
		return (Classifier)(new weka.classifiers.functions.MultilayerPerceptron());
	}

}
