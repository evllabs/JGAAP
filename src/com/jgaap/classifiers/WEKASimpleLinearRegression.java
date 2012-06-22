package com.jgaap.classifiers;

import java.util.List;

import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.WEKAAnalysis;

import weka.classifiers.Classifier;

public class WEKASimpleLinearRegression extends WEKAAnalysis {

	@Override
	public String displayName() {
		return "WEKA Simple Linear Regression";
	}

	@Override
	public String tooltipText() {
		return "Simple Linear Regression Classifier, Courtesy of WEKA";
	}

	@Override
	public boolean showInGUI() {
		return false;
	}
	
	public Classifier getClassifier() {
		return (Classifier)(new weka.classifiers.functions.SimpleLinearRegression());
	}
	
	public void testRequirements(List<EventSet> knownList) throws AnalyzeException{
		//TODO: Need to figure out requirements using WEKASimpleLinearRegressionTest
		return;
	}

}
