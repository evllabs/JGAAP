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

import static org.math.array.DoubleArray.identity;
import static org.math.array.DoubleArray.transpose;
import static org.math.array.LinearAlgebra.inverse;
import static org.math.array.LinearAlgebra.minus;
import static org.math.array.LinearAlgebra.plus;
import static org.math.array.LinearAlgebra.times;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

import com.jgaap.backend.KernelMethodMatrix;
import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

/*
 * LDA class performs linear discriminant analysis on a set of data. NOTE: We
 * are using regularized LDA. To do this, we are adding the appropriately sized
 * identity matrix to the covariant matrix in order to guarantee that it is
 * invertible (nonsingular). Please see
 * "Linear Discriminant Analysis Numerical Example" by Dr. Kardi Teknomo for the
 * algorithm used within. At the time of writing, this article was available at
 * http://people.revoledu.com/kardi/tutorial/LDA/Numerical%20Example.html
 *
 * @author John Noecker Jr.
 */
public class LDA extends AnalysisDriver {
	public String displayName(){
		return "LDA";
	}

	public String tooltipText(){
		return "Fisher Linear Discriminant Analysis";
	}

	public boolean showInGUI(){
		return true;
	}

	double[][]         features;
	int[]              classifications;
	int                numGroups;
	Vector<double[]>[] groupFeatures;
	double[][]         groupMean;
	double[]           globalMean;
	Vector<double[][]> correctedFeatures;
	double[][][]       groupCovarianceMatrix;
	double[][]         covarianceMatrix;
	double[][]         covarianceInverse;
	double[]           priorProbability;
	String[]           authors;
	TreeSet<Event>     vocab;

	public LDA() {
		super();
	}

	@Override
	/*
	 * Method converted by AMK from previous method which took parameter of single
	 * EventSet.
	 */
	public synchronized List<List<Pair<String, Double>>> analyze(List<EventSet> unknownSet, List<EventSet> known) {

		KernelMethodMatrix matrixFactory = new KernelMethodMatrix();

		//Training
		int numGroups = 0;
		authors = new String[50]; // This can be increased if we need
		// more than 50 authors.
		// Why not make this the actual count of known authors?
		// Or make this a List instead?
		
		/* These classifications are simply integers such that classifications[i] denotes
		 * the class to which the ith row of the features array belongs.*/
		int[] tmpClassifications = new int[known.size()];
		
		//Collect all unique Events into vocab
		vocab = new TreeSet<Event>();
		for (int i = 0; i < known.size(); i++) {
			for (int j = 0; j < known.get(i).size(); j++) {
				vocab.add(known.get(i).eventAt(j));
			}
		}
		
		//Collect all unique known authors
		//Also construct the array of classifications of each row
		//  of the feature matrix.
		EventSet theEvent;
		boolean found;
		for (int i = 0; i < known.size(); i++) {
			found = false;
			theEvent = known.get(i);
			for (int j = 0; j < authors.length; j++) { //Maybe only go to numGroups here?
				if (theEvent.getAuthor().equals(authors[j])) {
					found = true;
					tmpClassifications[i] = j;
				}
			}
			if (!found) {
				authors[numGroups] = theEvent.getAuthor();
				tmpClassifications[i] = numGroups;
				numGroups++;
			}
		}
		
		double[][] knownMatrix = new double[known.size()][];
		for (int i = 0; i < known.size(); i++) {
			knownMatrix[i] = matrixFactory.getRow(known.get(i), vocab,1000);
		}

		generateTransformation(knownMatrix, tmpClassifications, numGroups);

		//Classification
		List<List<Pair<String,Double>>> results = new ArrayList<List<Pair<String,Double>>>();
		List<Pair<String,Double>> holder;
		for(EventSet unknown : unknownSet){
			holder = new ArrayList<Pair<String,Double>>();
			double[] unknownRow = matrixFactory.getRow(unknown, vocab,1000);
			double [] distArray = getClassification(unknownRow);
			String [] authorArray = authors;
			for(int i=0; i<distArray.length; i++){
				holder.add(new Pair<String, Double>(authorArray[i], distArray[i], 2));
			}
			Collections.sort(holder);
			Collections.reverse(holder);
			results.add(holder);
		}

		return results;
	}

	/** 
	 * Perform the discriminant function given the group mean, the data on which
	 * the function should be performed, and the prior probability for the current
	 * group.
	 */
	double discriminantFunction(double[][] newGroupMean, double[][] newData,
			double newPriorProbability) {
		return (minus(times(times(newGroupMean, covarianceInverse),
				transpose(newData)), times(times(times(newGroupMean,
						covarianceInverse), transpose(newGroupMean)), (0.5))))[0][0]
						                                                          + Math.log(newPriorProbability);
	}

	/**
	 * The LDA class should be fed an array of features, where each row
	 * represents one object from the training data, and each column is a single
	 * feature. An array of classifications must also be passed. These
	 * classifications are simply integers such that classifications[i] denotes
	 * the class to which the ith row of the features array belongs. Finally,
	 * the number of groups to be analyzed must be provided.
	 */
	void generateTransformation(double[][] f, int[] c, int ng) {
		features = f;
		classifications = c;
		numGroups = ng;
		globalMean = new double[features[0].length];
		priorProbability = new double[features.length];
		groupMean = new double[ng][features[0].length];
		correctedFeatures = new Vector<double[][]>();
		prelim();
		return;
	}

	/**
	 * Get the classification assigned by LDA to a given set of features (a
	 * datapoint). Notice that, because the JMathTools package only works with
	 * double[][] matrices, we need to do some funky things (have double[1][x]
	 * matrices) to convert our data into the appropriate form needed to process
	 * it. We assign classification based on the maximum discriminant function
	 * (see previously referenced source in header). To plot the new data, we
	 * would plot the vector created from the values of the discriminant
	 * functions.
	 */
	public double [] getClassification(double[] data) {
		//int max = 0;
		double endResults[] = new double[numGroups];
		double[][] newData = new double[1][data.length];
		for (int i = 0; i < data.length; i++) {
			newData[0][i] = data[i];
		}
		for (int i = 0; i < numGroups; i++) {
			double[][] newGroupMean = new double[1][groupMean[i].length];
			for (int j = 0; j < groupMean[i].length; j++) {
				newGroupMean[0][j] = groupMean[i][j];
			}
			endResults[i] = discriminantFunction(newGroupMean, newData,
					priorProbability[i]);
			//System.out.println("Class " + i	+ " has discriminant function value: " + endResults[i]);
			/*  if (endResults[i] > endResults[max]) {
                max = i;
            }*/
		}
		//System.out.println("");
		return endResults;
	}

	/**
	 * Provides the preliminary setup for the LDA by setting up the
	 * various variables which will be used in LDA.
	 */
	@SuppressWarnings("unchecked")
	void prelim() {
		groupFeatures = new Vector[numGroups];
		int numEach[] = new int[numGroups];
		double totalSum[] = new double[features[0].length];
		double sumEach[][] = new double[numGroups][features[0].length];
		// Initialize groupFeatures (because of generic array creation issues)
		for (int i = 0; i < numGroups; i++) {
			groupFeatures[i] = new Vector<double[]>();
			numEach[i] = 0;
		}
		// Divide the featureset into group features, calculate the number of
		// datapoints in each group, and find the vector sums for calculating
		// both the local and global means.
		for (int i = 0; i < features.length; i++) {
			groupFeatures[classifications[i]].add(features[i]);
			numEach[classifications[i]]++;
			for (int j = 0; j < features[i].length; j++) {
				totalSum[j] += features[i][j];
				sumEach[classifications[i]][j] += features[i][j];
			}
		}

		// Calculate the global mean, which is still a vector.
		for (int i = 0; i < totalSum.length; i++) {
			globalMean[i] = totalSum[i] / features.length;
		}

		// Calculate the group means and prior probabilities.
		// Note here that if there are 'a' objects of classification
		// X and b objects total, the prior probability of class
		// X is a/b.
		for (int i = 0; i < sumEach.length; i++) {
			for (int j = 0; j < sumEach[i].length; j++) {
				groupMean[i][j] = sumEach[i][j] / numEach[i];
			}
			priorProbability[i] = (double) numEach[i] / features.length;
		}

		// Get the corrected group features, which is a vector of
		// double[][] calculated by subtracting the global mean
		// from each row of the group features.
		for (int i = 0; i < numGroups; i++) {
			double[][] tmp = new double[numEach[i]][features[0].length];
			for (int j = 0; j < groupFeatures[i].size(); j++) {
				for (int k = 0; k < groupFeatures[i].get(j).length; k++) {
					tmp[j][k] = groupFeatures[i].get(j)[k] - globalMean[k];
				}
			}
			correctedFeatures.add(tmp);
		}

		groupCovarianceMatrix = new double[numGroups][features[0].length][features[0].length];
		for (int i = 0; i < numGroups; i++) {
			groupCovarianceMatrix[i] = times(times(transpose(correctedFeatures
					.get(i)), correctedFeatures.get(i)), (1.0 / numEach[i]));
		}

		covarianceMatrix = new double[features[0].length][features[0].length];
		for (int i = 0; i < features[0].length; i++) {
			for (int j = 0; j < features[0].length; j++) {
				double tmpSum = 0;
				for (int k = 0; k < numGroups; k++) {
					tmpSum += priorProbability[k]
					                           * groupCovarianceMatrix[k][i][j];
				}
				covarianceMatrix[i][j] = tmpSum;
			}
		}

		// Regularize the covariance matrix to assure that it is nonsingular
		// by adding the appropriately sized identity matrix to it.
		covarianceMatrix = plus(covarianceMatrix,
				identity(covarianceMatrix.length));

		covarianceInverse = inverse(covarianceMatrix);

		return;
	}

	/**
	 *  Print out various variables used internally in the LDA
	 *  class for testing purposes.
	 */
	void printInfo() {
		System.out.print("[");
		for (int i = 0; i < globalMean.length; i++) {
			System.out.print(globalMean[i] + " ");
		}
		System.out.println("]");

		for (int i = 0; i < groupMean.length; i++) {
			System.out.print("[");
			for (int j = 0; j < groupMean[i].length; j++) {
				System.out.print(groupMean[i][j] + " ");
			}
			System.out.println("]\n\n\n");
		}

		for (int i = 0; i < numGroups; i++) {
			System.out.println(correctedFeatures.get(i) + "\n\n");
		}

		System.out.println("\n\n\n");
		for (int i = 0; i < numGroups; i++) {
			System.out.println(groupCovarianceMatrix[i] + "\n");
		}

		System.out.println("\n\n\n\n");
		System.out.println(covarianceMatrix);

		System.out.println("\n\n");
		for (int i = 0; i < numGroups; i++) {
			System.out.println(priorProbability[i]);
		}

		System.out.println("\n\n" + covarianceInverse + "\n\n");
	}

}
