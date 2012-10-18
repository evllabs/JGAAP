package com.jgaap.classifiers;

import java.util.List;

import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.Document;
import com.jgaap.generics.WEKAAnalysisDriver;

import weka.classifiers.Classifier;

public class WEKANaiveBayes extends WEKAAnalysisDriver {

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

	public void testRequirements(List<Document> knownList) throws AnalyzeException{
		//No requirements for Naive Bayes
		return;
	}

}
