/**
 * 
 */
package com.jgaap.classifiers;

import com.jgaap.backend.KernelMethodMatrix;
import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

import java.util.*;

import static org.math.array.DoubleArray.identity;
import static org.math.array.DoubleArray.columnVector;
import static org.math.array.LinearAlgebra.inverse;
import static org.math.array.LinearAlgebra.minus;
import static org.math.array.LinearAlgebra.plus;
import static org.math.array.LinearAlgebra.times;
import static org.math.array.StatisticSample.covariance;

/**
 * MahalanobisDistance class does the generalized squared interpoint distance.
 * This is the dissimilarity measure between two random vectors from the same
 * distribution. The random vectors are event histograms or the relative
 * frequencey of events. We use the sample covariance matrix composed of the
 * sample mean for each element in the vectors. Here the sample is all the known
 * eventsets.
 * 
 * 
 * @author darrenvescovi
 * 
 */
public class MahalanobisDistance extends AnalysisDriver {

	public String displayName() {
		return "Mahalanobis Distance";
	}

	public String tooltipText() {
		return "Generalized Squared Interpoint Distance";
	}

	public boolean showInGUI() {
		return false; // doesnt pass test yet
	}

	@Override
	public List<Pair<String, Double>> analyze(EventSet unknown,
			List<EventSet> known) {

		/*
		 * start of spears code
		 */

		TreeSet<Event> vocab = new TreeSet<Event>(); // list of all events
		// (known & unknown)
		KernelMethodMatrix matrixFactory = new KernelMethodMatrix(); //
		double[][] eventMatrix;
		double totalSum = 0.0;
		double[] empiricalMean;
		double[][] covarianceMatrix;
		List<Pair<String, Double>> results = new ArrayList<Pair<String, Double>>();
		// *****************************************
		// create TreeSet of events
		// *****************************************
		for (int i = 0; i < known.size(); i++) {
			for (int j = 0; j < known.get(i).size(); j++) {
				vocab.add(known.get(i).eventAt(j));
			}
		}
		for (int j = 0; j < unknown.size(); j++) {
			vocab.add(unknown.eventAt(j));
		}

		// System.out.println("vocab size = " + vocab.size() +
		// "   known size = "+ known.size());
		// *****************************************
		// create relative freq matrix [document][event]
		// *****************************************
		eventMatrix = new double[known.size() + 1][vocab.size()];

		for (int i = 0; i < known.size(); i++) {
			eventMatrix[i] = matrixFactory.getRow(known.get(i), vocab);
		}
		eventMatrix[known.size()] = matrixFactory.getRow(unknown, vocab);
		/***************************************************************
		 * System.out.println("Event Matrix" ); for(int i=0;
		 * i<eventMatrix.length; i++){ for(int j=0; j<eventMatrix[0].length;
		 * j++){ System.out.print(eventMatrix[i][j] +" "); }
		 * System.out.println(); } System.out.println(); /
		 **************************************************************/
		// *****************************************
		// create mean vectors
		// *****************************************
		empiricalMean = new double[eventMatrix.length];

		for (int i = 0; i < eventMatrix.length; i++) {
			for (int j = 0; j < eventMatrix[i].length; j++) {
				totalSum += eventMatrix[i][j];
			}
			empiricalMean[i] = totalSum / eventMatrix[0].length;
			totalSum = 0.0;
		}

		/***************************************************************
		 * System.out.println("Mean Vector  -- orig data matrix" ); for(int i=0;
		 * i<empiricalMean.length; i++){ System.out.println(empiricalMean[i]
		 * +" "); } System.out.println(); /
		 **************************************************************/

		// *****************************************
		// create deviations from means matrix
		// *****************************************
		for (int i = 0; i < eventMatrix.length; i++) {
			for (int j = 0; j < eventMatrix[i].length; j++) {
				eventMatrix[i][j] = eventMatrix[i][j] - empiricalMean[i];
			}
		}
		/***************************************************************
		 * System.out.println("Event Matrix  -- MEAN ADJ matrix" ); for(int i=0;
		 * i<eventMatrix.length; i++){ for(int j=0; j<eventMatrix[0].length;
		 * j++){ System.out.print(eventMatrix[i][j] +" "); }
		 * System.out.println(); } System.out.println(); /
		 **************************************************************/

		// *****************************************
		// create covariance matrix
		// *****************************************

		covarianceMatrix = covariance(eventMatrix);

		// System.out.println(tostring(eventMatrix));
		// System.out.println("------------------------");
		// System.out.println(tostring(covarianceMatrix));

		/*
		 * end spears code
		 */

		// make sure the covariance matrix is nonsingular by adding the
		// appropriate identity matrix
		covarianceMatrix = plus(covarianceMatrix,
				identity(covarianceMatrix.length));

		double[][] covarMatrixInverse = inverse(covarianceMatrix);

		// do the analysis
		/*
		 * pre processing info
		 * 
		 * need covariance matrix vector for X unknown (histogram) vector for Y
		 * known.(i) (histogram)
		 */

		// create a vector for y
		double[] vectY;
		vectY = matrixFactory.getRow(unknown, vocab);
		for (int i = 0; i < known.size(); i++) {
			EventHistogram hist = new EventHistogram();

			for (int j = 0; j < known.get(i).size(); j++) {
				hist.add(known.get(i).eventAt(j));
			}

			double[] vectX = matrixFactory.getRow(known.get(i), vocab);

			vectX = minus(vectX, vectY);

			double[][] tmpMatrix = times(columnVector(vectX),
					covarMatrixInverse);

			double[] sumMatrix = times(tmpMatrix, vectX);

			results.add(new Pair<String, Double>(known.get(i).getAuthor(), Math.sqrt(sumMatrix[0]), 2));

		}
		
		Collections.sort(results);

		return results;

	}

}
