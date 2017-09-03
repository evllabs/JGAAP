package com.jgaap.generics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.FastVector;
//import weka.core.Instance;
import weka.core.Instances;
import weka.core.SparseInstance;

import com.jgaap.util.Document;
import com.jgaap.util.Event;
import com.jgaap.util.EventMap;
import com.jgaap.util.Instance;
import com.jgaap.util.Pair;


/**
 * Generic WEKA classifier. In theory, WEKA classifiers can extend this and set
 * the underlying classifier, plus any special parameters, in getClassifier(),
 * and everything else will Just Work.
 * 
 * @author John Noecker Jr.
 * 
 */
public abstract class WEKAAnalysisDriver extends AnalysisDriver {
	
	private Classifier classifier;

	private Set<String> allAuthorNames;
	private Set<Event> allEvents;
	private FastVector attributeList;
	private FastVector authorNames;
	private Instances trainingSet;

	@Override
	public abstract String displayName();

	@Override
	public abstract String tooltipText();

	@Override
	public abstract boolean showInGUI();

	public abstract Classifier getClassifier();

	public abstract void testRequirements(List<Document> knownDocuments) throws AnalyzeException;

	public void train(List<Document> knownDocuments) throws AnalyzeException {

		classifier = getClassifier();

		// Test requirements
		testRequirements(knownDocuments);

		/*
		 * Generate event histograms, unique event list, and unique author list.
		 */
		List<EventMap> knownEventMaps = new ArrayList<EventMap>();
		allAuthorNames = new HashSet<String>();
		allEvents = new LinkedHashSet<Event>();
		for (Document document : knownDocuments) {
			allAuthorNames.add(document.getAuthor());
			EventMap eventMap = new EventMap(document);
			
			allEvents.addAll(eventMap.uniqueEvents());
			knownEventMaps.add(eventMap);
		}

		/*
		 * Put together WEKA "Instances" object, which defines the attributes
		 * (aka "events" or "features").
		 */
		attributeList = new FastVector(allEvents.size() + 1);

		authorNames = new FastVector(allAuthorNames.size()+1);
		for (String currentAuthorName : allAuthorNames) {
			authorNames.addElement(currentAuthorName);
		}
		authorNames.addElement("Unknown");
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
		trainingSet = new Instances("JGAAP", attributeList, knownDocuments.size());
		trainingSet.setClassIndex(0); // The label (author name) is in position
										// 0.

		/*
		 * Put together the training set
		 */
		for (int i = 0; i < knownEventMaps.size(); i++) {
			EventMap knownEventMap = knownEventMaps.get(i);
			Instance currentTrainingDocument = new Instance(
					allEvents.size() + 1);
			currentTrainingDocument.setValue((Attribute) attributeList
					.elementAt(0), knownDocuments.get(i).getAuthor());
			int j = 1; // Start counting events (at 1, since 0 is the author
						// label)
			for (Event event : allEvents) {
				currentTrainingDocument.setValue(
						(Attribute) attributeList.elementAt(j),
						knownEventMap.normalizedFrequency(event));
				j++;
			}
			trainingSet.add(new SparseInstance(currentTrainingDocument));
		}		

		/*
		 * Train the classifier N.B. The classifier should be set in the
		 * constructor by all subclasses.
		 */
		try {
			classifier.buildClassifier(trainingSet);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AnalyzeException("WEKA classifier not trained: "+e.getMessage());
		}
	}

	/**
	 * Convert from the JGAAP event framework to the WEKA instance framework to
	 * perform analysis.
	 * 
	 * @throws AnalyzeException
	 */
	public List<Pair<String, Double>> analyze(Document unknownDocument)
			throws AnalyzeException {
		/*
		 * Generate the test sets, classifying each one as we go
		 */
		List<Pair<String, Double>> result = new ArrayList<Pair<String, Double>>();
		EventMap eventMap = new EventMap(unknownDocument);
		Instance currentTest = new Instance(allEvents.size() + 1);

		currentTest.setValue((Attribute) attributeList.elementAt(0), "Unknown");
		int i = 1; // Start at 1, again
		for (Event event : allEvents) {
			currentTest.setValue((Attribute) attributeList.elementAt(i),
					eventMap.normalizedFrequency(event));
			i++;
		}
		
		currentTest.setDataset(trainingSet);

		double[] probDistribution;
		try {
			probDistribution = classifier.distributionForInstance(currentTest);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AnalyzeException("Could not classify with WEKA: " + e.getMessage());
		}

		/*
		 * Loop through the probability distributions (by author), and add
		 * results as pairs (author, probability).
		 */
		int j = 0;
		for (String authorName : allAuthorNames) {
			result.add(new Pair<String, Double>(authorName,
					probDistribution[j], 2));
			j++;
		}
		Collections.sort(result);
		Collections.reverse(result); // Reverse since we want higher
										// probabilities first

		return result;
	}

	public String toString() {
		if (classifier != null) {
			return classifier.toString();
		} else {
			return "WEKAAnalysis. No classifier set.";
		}
	}

}
