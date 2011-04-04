/**
 **/
package com.jgaap.classifiers;

import libsvm.*;

import java.util.*;

import com.jgaap.backend.KernelMethodMatrix;
import com.jgaap.generics.AnalysisDriver;
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
        kernelType = t;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public List<Pair<String,Double>> analyze(EventSet unknown, List<EventSet> known) {
		
		int i;
		
		// Each known author is assigned a unique group ID, which is mapped in groupsMap
		HashMap groupsMap = new HashMap();
		
		// Iterate over all known authors, and build groupsMap
		for (i=0; i < known.size(); i++) {
			String author = known.get(i).getAuthor();
			if (!groupsMap.containsKey(author)) {
				Integer gid = new Integer(groupsMap.size()+1);
				groupsMap.put(author, gid);
				groupsMap.put(gid, author);
			}
		}
			
			
		// Build a set of all known documents' events
		TreeSet vocab = new TreeSet();
		for (i=0; i < known.size(); i++) {
            for (int j = 0; j < known.get(i).size(); j++) {
                vocab.add(known.get(i).eventAt(j));
            }
        }
		
		// Do whatever the original SVM implementation did... (?)
		// This a slightly more space-optimized implementation, merged with
		// the internals of svm_train, to generate a svm_problem object.
		
		KernelMethodMatrix matrixFactory = new KernelMethodMatrix();
		
		svm_problem prob = new svm_problem();
		prob.l = known.size(); // the number of known documents
		prob.x = new svm_node[prob.l][];
		prob.y = new double[prob.l];
		
        for (i = 0; i < known.size(); i++) {
            String author = known.get(i).getAuthor();
			int groupID = (Integer)groupsMap.get(author);
			
			double knownMatrix[] = matrixFactory.getRow(known.get(i), vocab, 1000);
			
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
		param.gamma = 0;
		param.coef0 = 0;
		param.nu = 0.5;
		param.cache_size = 100;
		param.C = 10;  //  C=10 was a parameter specificly set by the old SVM implementation
		param.eps = 1e-3;
		param.p = 0.1;
		param.shrinking = 1;
		param.probability = 0;
		param.nr_weight = 0;
		param.weight_label = new int[0];
		param.weight = new double[0];
		
		// Run svm_train (finally)
		svm_model model = svm.svm_train(prob, param);
		// We get back a svm_model which we can feed into svm_predict

		// Run the unknown eventset through matrixFactory, and build another svm_node[] 
		double[] unknownRow = matrixFactory.getRow(unknown, vocab, 1000);
		svm_node[] x = new svm_node[unknownRow.length];
		for (int j=0; j<unknownRow.length; j++) {
			x[j] = new svm_node();
			x[j].index = j+1;
			x[j].value = unknownRow[j]*100;
		}

		// svm_predict selects a groupID 
		int decision = (int)svm.svm_predict(model, x);

        double[] prob_estimates = new double[groupsMap.size()];

        double v = svm.svm_predict_probability(model, x, prob_estimates);

		// whose author we lookup in groupsMap and return.
		List<Pair<String, Double>> results = new ArrayList<Pair<String, Double>>();
		results.add(new Pair((String)groupsMap.get(new Integer(decision)),v));
        return results;
	}

}
