package com.jgaap.classifiers;

import java.util.List;

import weka.classifiers.Classifier;

import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.WEKAAnalysisDriver;
import com.jgaap.util.Document;

public class WEKAMultilayerPerceptron extends WEKAAnalysisDriver {

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
	
	public void testRequirements(List<Document> knownList) throws AnalyzeException{
		//No requirements for Multilayer Perceptron
		return;
	}


}
