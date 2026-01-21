package com.jgaap.classifiers;

import java.util.List;

import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.WEKAAnalysisDriver;
import com.jgaap.util.Document;

import weka.classifiers.Classifier;

public class WEKALogisticRegression extends WEKAAnalysisDriver {
	@Override
	public String displayName() {
		return "WEKA Logistic Regression";
	}

	@Override
	public String tooltipText() {
		return "Multinomial logistic regression, Courtesy of WEKA";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	
	public Classifier getClassifier() {
		return (Classifier)(new weka.classifiers.functions.Logistic());
	}
	public void testRequirements(List<Document> knownList) throws AnalyzeException{
		//No requirements
		return;
	}

}
