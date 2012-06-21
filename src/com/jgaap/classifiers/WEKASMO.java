package com.jgaap.classifiers;

import java.util.ArrayList;
import java.util.List;

import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.EventSet;

import weka.classifiers.Classifier;

public class WEKASMO extends WEKAAnalysis {

	public WEKASMO() {
		//complexity constant
		addParams("c", "Complexity constant C", "1", new String[]{"0","1","2","3","4","5","6","7","8","9","10"}, true);
		//exponent for the polynomial kernel
		addParams("e", "Exponent (polynomial kernel) E", "1", new String[]{"0","1","2","3","4","5","6","7","8","9","10"}, true);
		//Gamma for the RBF kernel
		addParams("g", "Gamma (RBF kernel) G", "0.01", new String[]{"0","0.01","0.02","0.03","0.04","0.05","0.06","0.07","0.08","0.09","0.001"}, true);
		//N
		addParams("n", "N", "normalize", new String[] {"normalize", "standardize", "neither"},false);
		//Feature space Normalization
		addParams("f", "Feature-space normalization (polynomial kernel)", "false", new String[]{"true","false"},false);
		//Use lower-order terms
		addParams("o", "Use lower-order terms (polynomial kernel)", "false", new String[]{"true", "false"},false);
		//kernel
		addParams("r", "Kernel", "Polynomial", new String[]{"Polynomial", "RBF"},false);
		//folds of cross-validation used
		addParams("v", "Cross-validation folds to generate models (-1 use training data)", "-1", new String[]{"-1","1","2","3","4","5","6","7","8","9","10"}, false);
		
	}
	
	@Override
	public String displayName() {
		return "WEKA SMO";
	}

	@Override
	public String tooltipText() {
		return "Sequential Minimal Optimization, Courtesy of WEKA";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	
	public Classifier getClassifier() {
		List<String> options = new ArrayList<String>();
		options.add("-C "+getParameter("c"));
		String n = getParameter("n");
		if(n.equalsIgnoreCase("neither")){
			options.add("-N 2");
		} else if (n.equalsIgnoreCase("standardize")){
			options.add("-N 1");
		} else {
			options.add("-N 0");
		}
		String kernal = getParameter("r");
		if(kernal.equalsIgnoreCase("Polynomial")){
			options.add("-E "+getParameter("e"));
			if(getParameter("f").equalsIgnoreCase("true")){
				options.add("-F");
			}
			if(getParameter("o").equalsIgnoreCase("true")){
				options.add("-O");
			}
		} else if(kernal.equalsIgnoreCase("RBF")){
			options.add("-R");
			options.add("-G "+getParameter("g"));
		}
		options.add("-V "+getParameter("v"));
		String[] optionArray = options.toArray(new String[0]);
		Classifier classifier = new weka.classifiers.functions.SMO();
		try {
			classifier.setOptions(optionArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classifier;
	}
	
	public void testRequirements(List<EventSet> knownList) throws AnalyzeException{
		//TODO: WEKASMOTest not yet created to find requirements
		return;
	}

}
