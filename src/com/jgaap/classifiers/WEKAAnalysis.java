package com.jgaap.classifiers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
 * Generic WEKA classifier.  In theory, WEKA classifiers can extend this
 * and set the underlying classifier, plus any special parameters, in their
 * constructor, and everything else will Just Work.
 * @author John Noecker Jr.
 *
 */
public abstract class WEKAAnalysis extends AnalysisDriver {

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
	 * Convert from the JGAAP event framework to the WEKA instance framework
	 * to perform analysis.
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
		for(EventSet eventSet : knownList) {
			allAuthorNames.add(eventSet.getAuthor());
			EventHistogram currentKnownHistogram = new EventHistogram();
			for(Event event : eventSet) {
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
		for(String currentAuthorName : allAuthorNames) {
			authorNames.addElement(currentAuthorName);
		}
		Attribute authorNameAttribute = new Attribute("authorName", authorNames);
		attributeList.addElement(authorNameAttribute);
		
		for(Event event : allEvents) {
			Attribute eventAttribute = new Attribute(event.getEvent());
			attributeList.addElement(eventAttribute); // Each unique event is an attribute in WEKA
		}
		
		/*
		 * Create the training "Instances" object, which is essentially the set
		 * of feature vectors for the training data.  
		 */
		Instances trainingSet = new Instances("JGAAP", attributeList, knownList.size());
		trainingSet.setClassIndex(0); // The label (author name) is in position 0.
		
		/*
		 * Put together the training set
		 */
		for(int i = 0; i < knownHistograms.size(); i++) {
			EventHistogram knownHistogram = knownHistograms.get(i);
			Instance currentTrainingDocument = new Instance(allEvents.size() + 1);
			currentTrainingDocument.setValue((Attribute)attributeList.elementAt(0), knownList.get(i).getAuthor());
		}
		
		
		return results;
	}

}
