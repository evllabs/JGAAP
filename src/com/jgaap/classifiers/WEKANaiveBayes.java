package com.jgaap.classifiers;

import java.util.List;

import weka.classifiers.Classifier;

import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.WEKAAnalysisDriver;
import com.jgaap.util.Document;

public class WEKANaiveBayes extends WEKAAnalysisDriver {

	@Override
	public String displayName() {
		return "WEKA Naive Bayes";
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

	public void testRequirements(List<Document> knownList) throws AnalyzeException{
		//No requirements for Naive Bayes
		return;
	}

}
