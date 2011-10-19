package com.jgaap.classifiers;

import weka.classifiers.Classifier;
import weka.classifiers.meta.MultiClassClassifier;

public class WEKAVotedPerceptron extends WEKAAnalysis {

	@Override
	public String displayName() {
		return "WEKA Voted Perceptron";
	}

	@Override
	public String tooltipText() {
		return "Voted Perceptron, Courtesy of WEKA";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	
	public Classifier getClassifier() {
		MultiClassClassifier c = new MultiClassClassifier();
		c.setClassifier(new weka.classifiers.functions.VotedPerceptron());
		return (Classifier)c;
	}

}