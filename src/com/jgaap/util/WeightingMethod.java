package com.jgaap.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.AnalyzeException;
/**
 * @author Alejandro J Napolitano Jawerbaum
 * This class provides support for weightedVoting and other algorithms that put AnalysisDrivers to a vote by weighting said votes.
 */
public class WeightingMethod {
	private static Logger logger = Logger.getLogger(WeightingMethod.class);
	/**
	 * @param Set<AnalysisDriver> classifiers. A set of AnalysisDrivers.
	 * @param Set<Document> knownDocuments. These will be used for cross-validation
	 * @param String method. Name of the weighted algorithm.
	 * @param String authors. The authors to cross-validate for. An empty string means all authors will be cross-validated.
	 * @return Set<Pair<AnalysisDriver,Double>> So as to prevent having duplicate AnalysisDrivers.
	 * This algorithm simply takes in instructions and passes them onto the appropriate weighting method.
	 * Doing this because, in the case of suspected and distractor authors, a user may wish to only take into consideration how accurate an algorithm is at differentiating the suspected authors from each other and the distractor authors.
	 */
	public static Set<Pair<AnalysisDriver,Double>> weight(Set<AnalysisDriver> classifiers, List<Document> knownDocuments, String method, String authors) throws AnalyzeException{
		 if(method.equalsIgnoreCase("cross-validation"))
			 return weightByCrossVal(classifiers, knownDocuments, authors);
		 else if(method.equalsIgnoreCase("accuracyOverSum"))
			 return weightByAccuracyOverSum(classifiers, knownDocuments, authors);
		 else
		 {
			 Set<Pair<AnalysisDriver, Double>> unweightedClassifiers = new HashSet<Pair<AnalysisDriver,Double>>(); 
			 for(AnalysisDriver classifier : classifiers)
				 unweightedClassifiers.add(new Pair<AnalysisDriver,Double>(classifier, 1.0));
			 return unweightedClassifiers;
		 }
			
		}
	/**
	 * @param Set<AnalysisDriver> classifiers. A set of AnalysisDrivers.
	 * @param Set<Document> knownDocuments. These will be used for cross-validation
	 * @param String authors. The authors to cross-validate for. An empty string means all authors will be cross-validated.
	 * This algorithm weights by raw LOOCV score.
	 */
	public static Set<Pair<AnalysisDriver, Double>> weightByCrossVal(Set<AnalysisDriver> classifiers, List<Document> knownDocuments, String authors) throws AnalyzeException{//This will be expanded, but for now it weights by LOOCV score.
		Set<Pair<AnalysisDriver, Double>> weights = new HashSet<Pair<AnalysisDriver,Double>>();
		
		for(AnalysisDriver classifier : classifiers) {
			Double analysesCounter = 0.0;
			Double score = 0.0;
			for (Document knownDocument : knownDocuments) {
				if(authors.contains(knownDocument.getAuthor()) || authors.equals("")){
				List<Document> knownDocuments2 = new ArrayList<Document>();
					for(Document knownDocument2 : knownDocuments){
						if(!knownDocument2.equals(knownDocument))
							knownDocuments2.add(knownDocument2);
				}
				logger.info("Training " + classifier.displayName() +" for cross-validation");
				classifier.train(knownDocuments2);
				logger.info("Finished Training "+classifier.displayName() + " for cross-validation");
				logger.info("Begining Analyzing: " + knownDocument.toString() + " for cross-validation");
				List<Pair<String,Double>> results = classifier.analyze(knownDocument);
				logger.info("Finished Analyzing: "+ knownDocument.toString() + " for cross-validation");
				Pair<String,Double> result = results.get(0);
				analysesCounter++;
				if(result.getFirst().contains(knownDocument.getAuthor()))
					score++;
			}
		}
			weights.add(new Pair<AnalysisDriver,Double>(classifier, score/analysesCounter));
		}
		return weights;
	}
	/**
	 * @param Set<AnalysisDriver> classifiers. A set of AnalysisDrivers.
	 * @param Set<Document> knownDocuments. These will be used for cross-validation
	 * @param String authors. The authors to cross-validate for. An empty string means all authors will be cross-validated.
	 * This algorithm weights by raw LOOCV score divided by the total sum of weights.
	 */
	public static Set<Pair<AnalysisDriver, Double>> weightByAccuracyOverSum(Set<AnalysisDriver> classifiers, List<Document> knownDocuments, String authors) throws AnalyzeException{
		Set<Pair<AnalysisDriver, Double>> weights = weightByCrossVal(classifiers, knownDocuments, authors);
		Set<Pair<AnalysisDriver, Double>> weights2 = new HashSet<Pair<AnalysisDriver,Double>>();
		Double sum = 0.0;
		for(Pair<AnalysisDriver,Double> weight : weights)
			sum+=weight.getSecond();
		for(Pair<AnalysisDriver,Double> weight : weights)
			weights2.add(new Pair<AnalysisDriver, Double>(weight.getFirst(), weight.getSecond()/sum));
		return weights2;
		
		
	}
	
}
