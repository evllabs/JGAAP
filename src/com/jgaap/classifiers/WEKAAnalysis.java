package com.jgaap.classifiers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventHistogram;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

/**
 * Generic WEKA classifier. In theory, WEKA classifiers can extend this and set
 * the underlying classifier, plus any special parameters, in their constructor,
 * and everything else will Just Work.
 * 
 * @author John Noecker Jr.
 * 
 */
public abstract class WEKAAnalysis extends AnalysisDriver {

	public Classifier classifier;

	@Override
	public String displayName() {
		return "Generic WEKA Classifier";
	}

	@Override
	public String tooltipText() {
		return "Generic WEKA Classifier; should not appear in GUI";
	}

	@Override
	public boolean showInGUI() {
		return false;
	}

	/**
	 * Convert from the JGAAP event framework to the WEKA instance framework to
	 * perform analysis.
	 */
	public List<List<Pair<String, Double>>> analyze(List<EventSet> unknownList,
			List<EventSet> knownList) {
		List<List<Pair<String, Double>>> results = new ArrayList<List<Pair<String, Double>>>();

		/*
		 * Generate event histograms, unique event list, and unique author list.
		 */
		List<EventHistogram> knownHistograms = new ArrayList<EventHistogram>();
		Set<String> allAuthorNames = new HashSet<String>();
		Set<Event> allEvents = new HashSet<Event>();
		for (EventSet eventSet : knownList) {
			allAuthorNames.add(eventSet.getAuthor());
			EventHistogram currentKnownHistogram = new EventHistogram();
			for (Event event : eventSet) {
				allEvents.add(event);
				currentKnownHistogram.add(event);
			}
			knownHistograms.add(currentKnownHistogram);
		}

		/*
		 * Put together WEKA "Instances" object, which defines the attributes
		 * (aka "events" or "features").
		 */
		FastVector attributeList = new FastVector(allEvents.size() + 1);

		FastVector authorNames = new FastVector(allAuthorNames.size());
		for (String currentAuthorName : allAuthorNames) {
			authorNames.addElement(currentAuthorName);
		}
		Attribute authorNameAttribute = new Attribute("authorName", authorNames);
		attributeList.addElement(authorNameAttribute);

		for (Event event : allEvents) {
			Attribute eventAttribute = new Attribute(event.getEvent());
			attributeList.addElement(eventAttribute); // Each unique event is an
														// attribute in WEKA
		}

		/*
		 * Create the training "Instances" object, which is essentially the set
		 * of feature vectors for the training data.
		 */
		Instances trainingSet = new Instances("JGAAP", attributeList,
				knownList.size());
		trainingSet.setClassIndex(0); // The label (author name) is in position
										// 0.

		/*
		 * Put together the training set
		 */
		for (int i = 0; i < knownHistograms.size(); i++) {
			EventHistogram knownHistogram = knownHistograms.get(i);
			Instance currentTrainingDocument = new Instance(
					allEvents.size() + 1);
			currentTrainingDocument.setValue((Attribute) attributeList
					.elementAt(0), knownList.get(i).getAuthor());
			int j = 1; // Start counting events (at 1, since 0 is the author
						// label)
			for (Event event : allEvents) {
				currentTrainingDocument.setValue(
						(Attribute) attributeList.elementAt(j),
						knownHistogram.getNormalizedFrequency(event));
				j++;
			}
			trainingSet.add(currentTrainingDocument);
		}

		/*
		 * Train the classifier N.B. The classifier should be set in the
		 * constructor by all subclasses.
		 */
		try {
			classifier.buildClassifier(trainingSet);
		} catch (Exception e) {
			e.printStackTrace();
			List<Pair<String, Double>> errorResults = new ArrayList<Pair<String, Double>>();
			Pair<String, Double> errorResult = new Pair<String, Double>(
					"Error training classifier", 0.0);
			errorResults.add(errorResult);
			results.add(errorResults);
			return results;
		}

		/*
		 * Generate the test sets, classifying each one as we go
		 */
		for (EventSet unknownEventSet : unknownList) {
			List<Pair<String, Double>> oneResult = new ArrayList<Pair<String, Double>>();
			EventHistogram currentUnknownHistogram = new EventHistogram();
			for (Event event : currentUnknownHistogram) {
				currentUnknownHistogram.add(event);
			}

			Instance currentTest = new Instance(allEvents.size() + 1);

			currentTest.setValue((Attribute) attributeList.elementAt(0),
					"Unknown");
			int i = 1; // Start at 1, again
			for (Event event : allEvents) {
				currentTest.setValue((Attribute) attributeList.elementAt(i),
						currentUnknownHistogram.getNormalizedFrequency(event));
			}
			currentTest.setDataset(trainingSet);

			double[] probDistribution;
			try {
				probDistribution = classifier
						.distributionForInstance(currentTest);
			} catch (Exception e) {
				e.printStackTrace();
				List<Pair<String, Double>> errorResults = new ArrayList<Pair<String, Double>>();
				Pair<String, Double> errorResult = new Pair<String, Double>(
						"Error training classifier", 0.0);
				errorResults.add(errorResult);
				results.add(errorResults);
				return results;
			}
			
			/*
			 * Loop through the probability distributions (by author), and add results
			 * as pairs (author, probability).
			 */
			int j = 0;
			for(String authorName : allAuthorNames) {
				oneResult.add(new Pair<String, Double>(authorName, probDistribution[j], 2));
			}
			Collections.sort(oneResult);
			Collections.reverse(oneResult); // Reverse since we want higher probabilities first
			results.add(oneResult);
		}

		return results;
	}

}
