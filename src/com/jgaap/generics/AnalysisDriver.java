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
package com.jgaap.generics;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for statistical analysis methods. As an abstract class, can only be
 * instantiated through subclasses. Legacy code inherited from WAY back.
 * 
 * @author unknown
 * @since 1.0
 */
public abstract class AnalysisDriver extends Parameterizable implements
		Comparable<AnalysisDriver>, Displayable {

	public String longDescription() {
		return tooltipText();
	}

	public abstract boolean showInGUI();

	/**
	 * Generic statistical analysis method. Analyze a given unknown EventSet in
	 * terms of its similarity (broadly defined) to elements of a List of
	 * EventSets of known authorship. Legacy code from WAY back. We should
	 * probably add a verify() method as well once the technology improves.
	 * 
	 * @param unknown
	 *            the EventSet to be analyzed
	 * @param known
	 *            a vector of EventSets of known authorship
	 * @return a list of (name,numeric-result) pairs
	 */
	/*
	 * Modified 18 Aug 2011 by PMJ to allow for multiple unknowns at once.
	 * Instead of passing a single unknown Event Set, you pass a List, which
	 * allows methods like LDA to create the (time-consuming and memory-
	 * intensive) matrix only once and re-use it. There are therefore two entry
	 * points (for backwards compatibility); AnalysisDrivers are only required
	 * to provide one. The default behavior is to call the other one. I.e. if
	 * MyEventDriver provides only the first (singleton) entry point, it will
	 * also inherit the second one from AnalysisDriver. (This little bit of
	 * design work is courtesy of Mike Ryan, and a brilliant idea it is.)
	 */

	/*
	 * First entry point for singleton EventSet; makes 1-place list and calls
	 * second entry point.
	 */
	public List<Pair<String, Double>> analyze(EventSet unknown, List<EventSet> known) throws AnalyzeException {
		List<EventSet> ukl = new ArrayList<EventSet>();
		ukl.add(unknown);
		return analyze(ukl, known).get(0);
	}

	/**
	 * Generic statistical analysis method. Analyze a group of unknown EventSet
	 * in terms of their similarity (broadly defined) to elements of a Vector of
	 * EventSets of known authorship. Legacy code from WAY back. We should
	 * probably add a verify() method as well once the technology improves.
	 * 
	 * @param unknown
	 *            the list of EventSets to be analyzed
	 * @param known
	 *            a list of EventSets of known authorship
	 * @return a list of lists of (name,numeric-result) pairs
	 */
	/*
	 * Second entry point for List<EventSet>; calls on individual list elements
	 * using first entry point and staples results together
	 */
	public List<List<Pair<String, Double>>> analyze(List<EventSet> unknownList, List<EventSet> known) throws AnalyzeException {
		List<List<Pair<String, Double>>> retVal = new ArrayList<List<Pair<String, Double>>>();
		for (EventSet unknown : unknownList) {
			retVal.add(analyze(unknown, known));
		}
		return retVal;
	}

	public void analyzeDocuments(List<Document> unknownDocuments, List<Document> knownDocuments, EventDriver eventDriver) throws AnalyzeException {
		List<EventSet> unknownEventSets = new ArrayList<EventSet>(unknownDocuments.size());
		List<EventSet> knownEventSets = new ArrayList<EventSet>(knownDocuments.size());
		for (Document document : unknownDocuments) {
			unknownEventSets.add(document.getEventSet(eventDriver));
		}
		for (Document document : knownDocuments) {
			knownEventSets.add(document.getEventSet(eventDriver));
		}
		List<List<Pair<String, Double>>> results = analyze(unknownEventSets, knownEventSets);
		for (int i = 0; i < results.size(); i++) {
			unknownDocuments.get(i).addResult(this, eventDriver, results.get(i));
		}
	}

	public int compareTo(AnalysisDriver o) {
		return displayName().compareTo(o.displayName());
	}
}
