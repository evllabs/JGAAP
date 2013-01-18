package com.jgaap.classifiers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.Document;
import com.jgaap.generics.WEKAAnalysisDriver;

import weka.classifiers.Classifier;
import weka.classifiers.functions.RBFNetwork;

public class WEKARBFNetwork extends WEKAAnalysisDriver {

	@Override
	public String displayName() {
		return "WEKA RBF Network";
	}

	@Override
	public String tooltipText() {
		return "Radial Basis Function Network, Courtesy of WEKA";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	
	private int numClusters = 1;
	
	public Classifier getClassifier() {
		RBFNetwork classifier = new RBFNetwork();
		classifier.setNumClusters(numClusters);
		return (Classifier)(classifier);
	}

	public void testRequirements(List<Document> knownList) throws AnalyzeException{
		Set<String> authors = new HashSet<String>();
		for(Document document : knownList) {
			authors.add(document.getAuthor());
		}
		numClusters = authors.size();
		//TODO: Need to figure out requirements using WEKARBFNetworkTest
		return;
	}

}
