package com.jgaap.classifiers;

import java.util.HashMap;
import java.util.List;

import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.EventSet;

import weka.classifiers.Classifier;
import weka.classifiers.meta.ClassificationViaRegression;

public class WEKALinearRegression extends WEKAAnalysis {

	@Override
	public String displayName() {
		return "WEKA Linear Regression";
	}

	@Override
	public String tooltipText() {
		return "Linear Regression, Courtesy of WEKA";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	
	public Classifier getClassifier() {
		ClassificationViaRegression c = new ClassificationViaRegression();
		c.setClassifier(new weka.classifiers.functions.LinearRegression());
		return (Classifier)c;
	}
	
	public void testRequirements(List<EventSet> knownList) throws AnalyzeException{
		//Linear Regression requires at least two documents per author

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
				throw new AnalyzeException("Weka Linear Regression classifier requires at "
						+"least two documents per author.\nAuthor '"+s+"' has only "
						+docsPerAuthor.get(s)+" documents.");
			}
		}
	}

}
