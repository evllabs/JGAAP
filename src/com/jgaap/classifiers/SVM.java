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
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

import javax.swing.*;

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
        //this.addParams("N", "N", "50", new String[] {"50", "100", "200"}, true);
        kernelType = t;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
    public List<Pair<String,Double>> analyze(EventSet unknown, List<EventSet> known) {
		
		int i;
		
		// Each known author is assigned a unique group ID, which is mapped in groupsMap
		HashMap<String, Integer> authorMap = new HashMap<String, Integer>();
        HashMap<Integer, String> groupMap = new HashMap<Integer, String>();
		
		// Iterate over all known authors, and build groupsMap
		for (i=0; i < known.size(); i++) {
			String author = known.get(i).getAuthor();
			if (!authorMap.containsKey(author)) {
				Integer gid = new Integer(authorMap.size() + 1);
				authorMap.put(author, gid);
				groupMap.put(gid, author);
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
            int groupID = authorMap.get(author);

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

        double[] prob_estimates = new double[authorMap.size()];

        double v = svm.svm_predict_probability(model, x, prob_estimates);

		// whose author we lookup in groupsMap and return.
		List<Pair<String, Double>> results = new ArrayList<Pair<String, Double>>();
		results.add(new Pair((String)groupMap.get(new Integer(decision)),v));
        return results;
	}
/*
    JLabel NLabel = new JLabel();
    JComboBox NBox = new JComboBox();

    JLabel NLabel2 = new JLabel();
    JComboBox NBox2 = new JComboBox();

    @Override
    public GroupLayout getGUILayout(JPanel panel){

    	NLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    	NLabel.setText("N");

    	NBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "15", "20", "25", "30", "40", "45", "50", "75", "100", "150", "200" }));
        NBox.setEditable(true);
        String temp = this.getParameter("numEvents");
        if (temp.equals(""))
        {
            this.setParameter("numEvents", 50);
        }
        NBox.setSelectedItem(this.getParameter("numEvents"));
        NBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NBoxActionPerformed(evt);
            }
        });

        NLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    	NLabel2.setText("N");

    	NBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "15", "20", "25", "30", "40", "45", "50", "75", "100", "150", "200" }));
        NBox2.setEditable(true);
        if (temp.equals(""))
        {
            this.setParameter("numEvents", 50);
        }
        NBox2.setSelectedItem(this.getParameter("numEvents"));
        NBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup().addComponent(NLabel).addComponent(NLabel2));
        hGroup.addGroup(layout.createParallelGroup().addComponent(NBox).addComponent(NBox2));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup().addComponent(NLabel).addComponent(NBox));
        vGroup.addGroup(layout.createParallelGroup().addComponent(NLabel2).addComponent(NBox2));
        layout.setVerticalGroup(vGroup);

        return layout;
    }

    private void NBoxActionPerformed(java.awt.event.ActionEvent evt) {
        this.setParameter("numEvents", (String)NBox.getSelectedItem());
    }

*/
}
