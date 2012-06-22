package com.jgaap.classifiers;

import java.util.HashMap;
import java.util.List;

import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.WEKAAnalysis;

import weka.classifiers.Classifier;

public class WEKAJ48DecisionTree extends WEKAAnalysis {
	
	//TODO Decision Tree needs more than one document from
	//  each author for training, otherwise the results are
	//  funny.

	@Override
	public String displayName() {
		return "WEKA J48 Decision Tree Classifier";
	}

	@Override
	public String tooltipText() {
		return "J48 Decision Tree Classifier, Courtesy of WEKA";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	
	public Classifier getClassifier() {
		return (Classifier)(new weka.classifiers.trees.J48());
	}
	
	public void testRequirements(List<EventSet> knownList) throws AnalyzeException{
		//J48 Decision Tree requires at least two documents per author

		//Make a "list" of the number of documents per author
		HashMap<String, Integer> docsPerAuthor = new HashMap<String, Integer>();
		for(EventSet e : knownList){
			if(docsPerAuthor.containsKey(e.getAuthor())){
				docsPerAuthor.put(e.getAuthor(), docsPerAuthor.get(e.getAuthor())+1);
			}else{
				docsPerAuthor.put(e.getAuthor(),1);
			}
		}
		
		//Test that each author has at least two documents.  If not, throw a
		//  new exception.
		for(String s : docsPerAuthor.keySet()){
			if(docsPerAuthor.get(s) < 2){
				throw new AnalyzeException("Weka J48 Decision Tree classifier requires at "
						+"least two documents per author.\nAuthor '"+s+"' has only "
						+docsPerAuthor.get(s)+" documents.");
			}
		}
	}

}
