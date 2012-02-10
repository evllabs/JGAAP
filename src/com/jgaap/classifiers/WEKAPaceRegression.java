package com.jgaap.classifiers;

import java.util.List;

import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.EventSet;

import weka.classifiers.Classifier;
import weka.classifiers.meta.ClassificationViaRegression;

/**
 * Pace Regression requires many more training samples than usually available (thousands).
 * It is included here for completeness.
 * @author jnoecker
 *
 */
public class WEKAPaceRegression extends WEKAAnalysis {

	@Override
	public String displayName() {
		return "WEKA Pace Regression";
	}

	@Override
	public String tooltipText() {
		return "Pace Regression, Courtesy of WEKA";
	}

	@Override
	public boolean showInGUI() {
		return false;
	}
	
	public Classifier getClassifier() {
		ClassificationViaRegression c = new ClassificationViaRegression();
		c.setClassifier(new weka.classifiers.functions.PaceRegression());
		return (Classifier)c;
	}
	
	public void testRequirements(List<EventSet> knownList) throws AnalyzeException{
		//TODO: Need to figure out requirements using WEKAPaceRegressionTest
		return;
	}


}
