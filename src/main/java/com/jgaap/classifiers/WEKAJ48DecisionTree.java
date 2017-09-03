package com.jgaap.classifiers;

import java.util.HashMap;
import java.util.List;

import weka.classifiers.Classifier;

import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.WEKAAnalysisDriver;
import com.jgaap.util.Document;

public class WEKAJ48DecisionTree extends WEKAAnalysisDriver {
	
	//TODO Decision Tree needs more than one document from
	//  each author for training, otherwise the results are
	//  funny.

	@Override
	public String displayName() {
		return "WEKA J48 Decision Tree";
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
	
	public void testRequirements(List<Document> knownList) throws AnalyzeException{
		//J48 Decision Tree requires at least two documents per author

		//Make a "list" of the number of documents per author
		HashMap<String, Integer> docsPerAuthor = new HashMap<String, Integer>();
		for(Document e : knownList){
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
