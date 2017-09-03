package com.jgaap.classifiers;

import java.util.List;

import weka.classifiers.Classifier;
import weka.classifiers.meta.MultiClassClassifier;

import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.WEKAAnalysisDriver;
import com.jgaap.util.Document;

public class WEKAVotedPerceptron extends WEKAAnalysisDriver {

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

	public void testRequirements(List<Document> knownList) throws AnalyzeException{
		//TODO: WEKAVotedPerceptronTest not yet created to find requirements
		return;
	}

}
