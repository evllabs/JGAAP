package com.jgaap.classifiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.FeatureVectorFactory;
import com.jgaap.generics.Pair;

import org.jscience.mathematics.number.Float64;
import org.jscience.mathematics.vector.*;

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
	
	@Override
	public synchronized List<List<Pair<String, Double>>> analyze(List<EventSet> unknownSet, List<EventSet> knownSet) {
		
		// Generate the feature vectors
		Pair<double[][], double[][]> vectors = FeatureVectorFactory.getNormalizedFeatures(knownSet, unknownSet);
		double[][] knownFeatures = vectors.getFirst();
		double[][] unknownFeatures = vectors.getSecond();
		
		// Some information for later
		double numFeatures = knownFeatures[0].length;
		double numTrainingPoints = knownFeatures.length;
		
		// Generate author list
		Pair<Integer, int[]> authorListPair = getAuthorList(knownSet);
		int numAuthors = authorListPair.getFirst();
		int[] authorList = authorListPair.getSecond();
		
		// Generate the individual training author matrices
		List<Matrix<Float64>> authorMatrices = new ArrayList<Matrix<Float64>>();
		for(int i = 0; i < numAuthors; i++) {
			authorMatrices.add(Float64Matrix.valueOf(getAuthorMatrix(knownFeatures, authorList, i)));
		}
		
		// Find within-class averages
		List<double[]> averages = getAverages(numAuthors, authorMatrices);
		
		// Find overall average
		Matrix<Float64> allKnownMatrix = Float64Matrix.valueOf(knownFeatures);
		Float64Vector mu = Float64Vector.valueOf(getAverage(allKnownMatrix));
		
		// Generate mean corrected training data
		List<Matrix<Float64>> correctedAuthorMatrices = new ArrayList<Matrix<Float64>>();
		for(Matrix<Float64> matrix : authorMatrices) {
			List<Float64Vector> rows = new ArrayList<Float64Vector>();
			for(int i = 0; i < matrix.getNumberOfRows(); i++) {
				rows.add(Float64Vector.valueOf(matrix.getRow(i).minus(mu)));
			}
			correctedAuthorMatrices.add(Float64Matrix.valueOf(rows));
		}
		
		// Generate within-class covariance matrices
		List<Matrix<Float64>> covarianceMatrices = new ArrayList<Matrix<Float64>>();
		for(Matrix<Float64> matrix : correctedAuthorMatrices) {
			Matrix<Float64> covariance = ((matrix.transpose()).times(matrix)).times(Float64.valueOf(1.0 / matrix.getNumberOfRows()));
			covarianceMatrices.add(covariance);
		}
		
		// Generate pooled within-class covariance matrix
		double[][] pooledCovarianceArray = new double[(int)numFeatures][(int)numFeatures];
		for(int i = 0; i < numFeatures; i++) {
			for(int j = 0; j < numFeatures; j++) {
				double sum = 0.0;
				for(int k = 0; k < covarianceMatrices.size(); k++) {
					int numTrainingPointsThisAuthor = correctedAuthorMatrices.get(k).getNumberOfRows();
					sum = sum + (numTrainingPointsThisAuthor / numTrainingPoints) * covarianceMatrices.get(k).get(i, j).doubleValue();
				}
				pooledCovarianceArray[i][j] = sum;
			}
		}
		Matrix<Float64> pooledCovarianceMatrix = Float64Matrix.valueOf(pooledCovarianceArray);
		
		// Generate inverse pooled covariance matrix
		Matrix<Float64> inversePooledCovarianceMatrix = pooledCovarianceMatrix.pseudoInverse();
		
		// Calculate prior probabilities
		List<Double> priorProbabilities = new ArrayList<Double>();
		for(int i = 0; i < correctedAuthorMatrices.size(); i++) {
			double priorProbability = correctedAuthorMatrices.get(i).getNumberOfRows() / numTrainingPoints;
			priorProbabilities.add(priorProbability);
		}
		
		// Calculate discriminant functions
		List<Double> discriminantValues = new ArrayList<Double>();
		for(int j = 0; j < unknownFeatures.length; j++) {
			Float64Vector observation = Float64Vector.valueOf(unknownFeatures[j]);
			observation = observation.minus(mu); // Mean correct test vector
			Float64Matrix trainingMatrix = Float64Matrix.valueOf(observation);
			Float64Matrix trainingMatrixTranspose = trainingMatrix.transpose();
			
			for(int i = 0; i < numAuthors; i++) {
				Matrix<Float64> mean = Float64Matrix.valueOf(Float64Vector.valueOf(averages.get(i)));
				Matrix<Float64> meanTranspose = mean.transpose();
			
				Matrix<Float64> f = mean.times(inversePooledCovarianceMatrix).times(trainingMatrixTranspose);
				f = f.minus(mean.times(Float64.valueOf(0.5)).times(inversePooledCovarianceMatrix).times(meanTranspose));
				
				double fValue = f.get(0, 0).doubleValue();
				fValue = fValue + Math.log(priorProbabilities.get(i));
				discriminantValues.add(fValue);
			}
		}

		

		return null;
	}
	
	// Return a vector of the author numbers for each known author.
	private Pair<Integer, int[]> getAuthorList(List<EventSet> eventSets) {
		HashMap<String, Integer> authors = new HashMap<String, Integer>();
		int authorNumber = 0;
		int[] authorVector = new int[eventSets.size()];
		int i = 0;
		for(EventSet es : eventSets) {
			if(authors.get(es.getAuthor()) == null) {
				authors.put(es.getAuthor(), authorNumber++);
			}
			authorVector[i++] = authors.get(es.getAuthor());
		}
		
		return new Pair<Integer, int[]>(authorNumber, authorVector);
	}

	private double[][] getAuthorMatrix(double[][] allFeatures, int[] authorList, int authorNum) {

		// Count the number of training examples for this author.
		int count = 0;
		for(int i = 0; i < authorList.length; i++) {
			if(authorList[i] == authorNum) {
				count++;
			}
		}
		
		double[][] authorMatrix = new double[count][];
		int j = 0;
		for(int i = 0; i < authorList.length; i++) {
			if(authorList[i] == authorNum) {
				authorMatrix[j++] = allFeatures[i];
			}
		}
		
		return authorMatrix;
	}
	
	private List<double[]> getAverages(int numAuthors, List<Matrix<Float64>> authorMatrices) {
		List<double[]> averages = new ArrayList<double[]>();
		for(int i = 0; i < numAuthors; i++) {
			Matrix<Float64> matrix = authorMatrices.get(i);
			double[] average = new double[matrix.getNumberOfColumns()];
			for(int j = 0; j < average.length; j++) {
				Vector<Float64> column = matrix.getColumn(j);
				double sum = 0.0;
				double dimension = column.getDimension();
				for(int k = 0; k < dimension; k++) {
					sum += column.get(k).doubleValue();
				}
				average[j] = sum / dimension;
			}
			averages.add(average);
		}
		return averages;
	}
	
	private double[] getAverage(Matrix<Float64> matrix) {
		List<Matrix<Float64>> matrixList = new ArrayList<Matrix<Float64>>();
		matrixList.add(matrix);
		return getAverages(1, matrixList).get(0);
	}
}
