package com.jgaap.classifiers;

import java.util.List;

import weka.classifiers.Classifier;

import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.WEKAAnalysisDriver;
import com.jgaap.util.Document;

public class WEKADecisionStump extends WEKAAnalysisDriver {

	@Override
	public String displayName() {
		return "WEKA Decision Stump";
	}

	@Override
	public String tooltipText() {
		return "Decision Stump, Courtesy of WEKA";
	}

	@Override
	public boolean showInGUI() {
		return false;
	}
	
	public Classifier getClassifier() {
		return (Classifier)(new weka.classifiers.trees.DecisionStump());
	}
	
	public void testRequirements(List<Document> knownList) throws AnalyzeException{
		//No requirements for Decision Stump
		return;
	}

}
