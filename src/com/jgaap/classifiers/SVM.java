/*
 * JGAAP -- a graphical program for stylometric authorship attribution
 * Copyright (C) 2009,2011 by Patrick Juola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 **/
package com.jgaap.classifiers;

import libsvm.*;

import java.util.*;

import com.jgaap.backend.KernelMethodMatrix;
import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

/**
 * SVM classifier rewritten to talk directly to libsvm. 
 * The previous incarnation invoked a system call and launched another instance of Java
 * to call libsvm - which was slow.
 * This version is functionally identical (hopefully), and doesn't use temporary files.
 *
 * Warning: This code was originally written by cyborgs from the future. 
 *          Its inner workings are slightly mysterious.
 */
 
public class SVM extends AnalysisDriver {

	private svm_model model;
	
	private Set<Event> vocab;
	
	private Map<String, Integer> authorMap;
	
	private Map<Integer, String> groupMap;
	
	public String displayName(){
	    return "Linear SVM";
	}

	public String tooltipText(){
	    return "Linear Kernel Support Vector Machine Classification";
	}

	public boolean showInGUI(){
	    return true;
	}

    int                kernelType;

    public SVM() {
        this(0); // Linear kernel type
    }

    public SVM(int t) {
        super();
        //this.addParams("N", "N", "50", new String[] {"50", "100", "200"}, true);
        kernelType = t;
    }

    
    public void train(List<EventSet> knowns){
    	//int i;
		
		// Each known author is assigned a unique group ID, which is mapped in groupsMap
		authorMap = new HashMap<String, Integer>();
        groupMap = new HashMap<Integer, String>();
		
		// Iterate over all known authors, and build groupsMap
		for (int i=0; i < knowns.size(); i++) {
			String author = knowns.get(i).getAuthor();
			if (!authorMap.containsKey(author)) {
				Integer gid = (authorMap.size() + 1);
				authorMap.put(author, gid);
				groupMap.put(gid, author);
			}
		}
			
			
		// Build a set of all known documents' events
		vocab = new HashSet<Event>();
		for (int i=0; i < knowns.size(); i++) {
            for (int j = 0; j < knowns.get(i).size(); j++) {
                vocab.add(knowns.get(i).eventAt(j));
            }
        }
		
		// Do whatever the original SVM implementation did... (?)
		// This a slightly more space-optimized implementation, merged with
		// the internals of svm_train, to generate a svm_problem object.
				
		svm_problem prob = new svm_problem();
		prob.l = knowns.size(); // the number of known documents
		prob.x = new svm_node[prob.l][];
		prob.y = new double[prob.l];
		
        for (int i = 0; i < knowns.size(); i++) {
            String author = knowns.get(i).getAuthor();
            int groupID = authorMap.get(author);

			double knownMatrix[] = KernelMethodMatrix.getRow(knowns.get(i), vocab, 1000);
			
			prob.y[i] = (double)groupID;
			prob.x[i] = new svm_node[knownMatrix.length];
			for (int j=0; j < knownMatrix.length; j++) {
				prob.x[i][j] = new svm_node();
				prob.x[i][j].index = j+1;
				prob.x[i][j].value = knownMatrix[j]*100;
			}
        }
		
		// Now the svm_problem is built. Build a svm_parameters with default values 
		// (This is lifted directly from svm_train)
		
		svm_parameter param = new svm_parameter();
		param.svm_type = svm_parameter.C_SVC;
		param.kernel_type = kernelType;
		param.degree = 3;
		param.gamma = 0.0001220703125;
		param.coef0 = 0;
		param.nu = 0.5;
		param.cache_size = 100;
		param.C = 512;
		param.eps = 1e-3;
		param.p = 0.1;
		param.shrinking = 1;
		param.probability = 0;
		param.nr_weight = 0;
		param.weight_label = new int[0];
		param.weight = new double[0];
		
		// Run svm_train (finally)
		model = svm.svm_train(prob, param);
		// We get back a svm_model which we can feed into svm_predict
    }
    
	@Override
    public List<Pair<String,Double>> analyze(EventSet unknown) {
		// Run the unknown eventset through matrixFactory, and build another svm_node[] 
		double[] unknownRow = KernelMethodMatrix.getRow(unknown, vocab, 1000);
		svm_node[] x = new svm_node[unknownRow.length];
		for (int j=0; j<unknownRow.length; j++) {
			x[j] = new svm_node();
			x[j].index = j+1;
			x[j].value = unknownRow[j]*100;
		}

		// svm_predict selects a groupID 
		int decision = (int)svm.svm_predict(model, x);

        double[] prob_estimates = new double[authorMap.size()];

        double v = svm.svm_predict_probability(model, x, prob_estimates);

		// whose author we lookup in groupsMap and return.
		List<Pair<String, Double>> results = new ArrayList<Pair<String, Double>>();
		results.add(new Pair<String, Double>(groupMap.get(decision),v));
        return results;
	}
}
