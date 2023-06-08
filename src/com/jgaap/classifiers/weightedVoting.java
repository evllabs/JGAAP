package com.jgaap.classifiers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import com.jgaap.backend.AnalysisDrivers;
import com.jgaap.backend.DistanceFunctions;
import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.NeighborAnalysisDriver;
import com.jgaap.generics.ValidationDriver;
import com.jgaap.util.Document;
import com.jgaap.util.Pair;
import com.jgaap.util.WeightingMethod;
/** @author Alejandro J Napolitano Jawerbaum
See tooltipText for a short description.
* weightedVoting weights algorithms' votes (prediction) according to a weighting algorithm. "None" is an option.
* Using sets instead of arraylists to user-proof it against having the same algorithm vote multiple times.
*/
public class weightedVoting extends AnalysisDriver {
	public Set<AnalysisDriver> classifiers = new HashSet<AnalysisDriver>();
	private static Set<Pair<AnalysisDriver, Double>> weightedClassifiers = new HashSet<Pair<AnalysisDriver, Double>>();
	private static Set<Pair<AnalysisDriver, Double>> weights = new HashSet<Pair<AnalysisDriver, Double>>();
	private static Set<String> authors = new HashSet<String>();
	private static Logger logger = Logger.getLogger(weightedVoting.class);

	
	public weightedVoting() {
		addParams("Classifiers", "Classifiers to be put to a vote.","Comma-separated list. Add | before parameters.", new String[] {""}, true); //TODO: Get all classifiers and add them to the array, then call each of them
		addParams("Distances", "Distance metrics for distance dependent Analysis Drivers","Comma-separated list", new String[] {""}, true);
		addParams("WeightingMethod", "Way to weight the classifiers.", "cross-validation", new String[]{"cross-validation", "accuracyOverSum", "none"}, false);
		addParams("Cutoff", "Minimum cross-validation score to consider an algorithm's vote.", "75", new String[]{"0", "10", "20","30","40","45","50","55","60","65","70","75","80","85","90","95", "100"}, true);
		addParams("VotingMethod", "Voting Method.", "sum", new String[] {"sum", "sum/count"}, false);
		addParams("AuthorsForCrossval", "Comma separated list of Authors to cross-validate. Empty = All.", "", new String[] {}, true);
	}

	@Override
	public String displayName() {
		return "Weighted Voting";
	}

	@Override
	public String tooltipText() {
		return "Takes in a list of analysis drivers, and put them to a vote on each unknown document. Warning: We recommend including independent classifiers only.";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	

	@Override
	public void train(List<Document> knownDocuments) throws AnalyzeException {
		for(Document doc : knownDocuments)
			authors.add(doc.getAuthor());
		Set<AnalysisDriver> clsfr = new HashSet<AnalysisDriver>(); 
		for(String s : getParameter("Classifiers").split(",")) {
			try {
				AnalysisDriver classifier = AnalysisDrivers.getAnalysisDriver(s.trim());
				if(classifier instanceof NeighborAnalysisDriver) {
					NeighborAnalysisDriver classif = (NeighborAnalysisDriver)AnalysisDrivers.getAnalysisDriver(s);;
					String[] distances = getParameter("Distances").split(",");
					for(String distance : distances) {
						DistanceFunction dist = DistanceFunctions.getDistanceFunction(distance);
						classif.setDistance(dist);
						clsfr.add(classif);
					}
				}
				else if(!(classifier instanceof LeaveOneOutNoDistanceDriver) && !(classifier instanceof ValidationDriver) && !(classifier instanceof weightedVoting)) 
					clsfr.add(classifier);
				else
					logger.info("Excluded cross-validation driver. Or worse, a weighted voting inception.");
				} catch (Exception e) {
				e.printStackTrace();
			}
		}
		classifiers = clsfr;
		 weights = WeightingMethod.weight(classifiers, knownDocuments, getParameter("WeightingMethod"), getParameter("AuthorsForCrossval"));
		 Set<Pair<AnalysisDriver, Double>> weighted = new HashSet<Pair<AnalysisDriver,Double>>();
		 for(Pair<AnalysisDriver, Double> weight : weights)
				if(weight.getSecond()>=(Double.parseDouble(getParameter("Cutoff"))/100))
					weighted.add(weight);
		 weightedClassifiers = weighted;
		 for(Pair<AnalysisDriver, Double> weightedClassifier : weightedClassifiers) {
				logger.info("Training " + weightedClassifier.getFirst().displayName() + " for analysis");
				weightedClassifier.getFirst().train(knownDocuments);
				logger.info("Finished training " + weightedClassifier.getFirst().displayName() + " for analysis");
	}
}
	/**
	 * Analyzes the unknown document and tallies the weighted votes.
	 * @param Document unknownDocument. Pass in the document to be analyzed. 
	 * */
	public Map<String, Double> vote(Document unknownDocument) throws AnalyzeException {
		List<Pair<String, Double>> authorVote = new ArrayList<Pair<String,Double>>();
		
		for(Pair<AnalysisDriver, Double> weightedClassifier : weightedClassifiers) {
			List<Pair<String, Double>> results = weightedClassifier.getFirst().analyze(unknownDocument);
			logger.info(weightedClassifier.getFirst().displayName()+ ". weight =  " + weightedClassifier.getSecond() + ". Voted for " + results.get(0).getFirst() + " for document " + unknownDocument.getTitle());
			authorVote.add(new Pair<String,Double>(results.get(0).getFirst(), weightedClassifier.getSecond()));	
		}
			//We should check the results for ties, and let the score be 0 for all authors if that is the case.
		Map<String, Double> authorVoteSumMap = new HashMap<String, Double>();
	    for (String author : authors) {
		        double totalVote = 0.0;
		        for (Pair<String, Double> vote : authorVote) {
		            if (vote.getFirst().contains(author)) {
		                totalVote += vote.getSecond();
		            }
		        }
		        if(!authorVoteSumMap.containsKey(author))
		        	authorVoteSumMap.put(author, totalVote);
		        
		    }
	    	logger.info(authorVoteSumMap);
	    	return authorVoteSumMap;
	}

	@Override
	public List<Pair<String, Double>> analyze(Document unknownDocument) throws AnalyzeException {
		Map<String, Double> authorVoteSumMap = vote(unknownDocument);
		Comparator<Pair<String, Double>> compareByScore = (Pair<String, Double> r1, Pair<String, Double> r2) -> r2.getSecond().compareTo(r1.getSecond());
		List<Pair<String,Double>> authorVoteSum = new ArrayList<Pair<String,Double>>();
		for(String author : authors)
			authorVoteSum.add(new Pair<String,Double>(author,authorVoteSumMap.get(author)));
			
		Collections.sort(authorVoteSum, compareByScore);
		//Collections.reverse(authorVoteSum);
		return authorVoteSum;
	}
}
