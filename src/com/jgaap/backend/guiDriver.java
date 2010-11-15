/**
 *   JGAAP -- Java Graphical Authorship Attribution Program
 *   Copyright (C) 2009 Patrick Juola
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation under version 3 of the License.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/
package com.jgaap.backend;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import com.jgaap.jgaapConstants;
import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Document;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Language;
import com.jgaap.generics.NearestNeighborDriver;
import com.jgaap.generics.Canonicizer;
import com.jgaap.gui.jgaapGUI;
import com.jgaap.languages.*;

public class guiDriver {

	List<Document> documents;
	List<EventDriver> eventDrivers;
	List<AnalysisDriver> analysisDrivers;

	Map<String, Canonicizer> canonicizers;
	Map<String, EventDriver> events;
	Map<String, DistanceFunction> distances;
	Map<String, AnalysisDriver> analysises;
	Map<String, Language> languages;
	// Vector<Canonicizer> processEngine; CKL 1/24/2009 Removed, no longer
	// needed

	private boolean processError = false; // Flag to indicate
	// whether some error has
	// resulted in a default
	// action being taken
	// (e.g. falling back to
	// default events "word")
	private String processErrorMessage = ""; // Error message detailing

	// exact error encountered

	public guiDriver() {
		// Create a hidden document for tracking canonicizers
		jgaapConstants.globalObjects.put("hDoc", (new Document()));
		documents = new ArrayList<Document>();
		eventDrivers = new ArrayList<EventDriver>();
		analysisDrivers = new ArrayList<AnalysisDriver>();
		// Load the canonicizers dynamically
		canonicizers = new HashMap<String, Canonicizer>();
		for(Canonicizer canon : AutoPopulate.getCanonicizers()){
			canonicizers.put(canon.displayName().toLowerCase(), canon);
		}
		// Load the event drivers dynamically
		events = new HashMap<String, EventDriver>();
		for(EventDriver eventDriver : AutoPopulate.getEventDrivers()){
			events.put(eventDriver.displayName().toLowerCase(), eventDriver);
		}
		// Load the distance functions dynamically
		distances = new HashMap<String, DistanceFunction>();
		for(DistanceFunction distanceFunction: AutoPopulate.getDistanceFunctions()){
			distances.put(distanceFunction.displayName().toLowerCase(), distanceFunction);
		}
		// Load the classifiers dynamically
		analysises = new HashMap<String, AnalysisDriver>();
		for(AnalysisDriver analysisDriver: AutoPopulate.getAnalysisDrivers()){
			analysises.put(analysisDriver.displayName().toLowerCase(), analysisDriver);
		}
		// Load the classifiers dynamically
		languages = new HashMap<String, Language>();
		for(Language language : AutoPopulate.getLanguages()){
			languages.put(language.getName().toLowerCase(), language);
		}

	}

	// ---------------------------------------------------
	// ------------- Document Manipulation ---------------
	// ---------------------------------------------------

	/**
	 * Add a document to the GUI backend.
	 * 
	 * @param newDocument
	 *            An instance of the Document class being added to the backend
	 */
	public Document addDocument(Document newDocument) {
		documents.add(newDocument);
		return newDocument;
	}

	/**
	 * Method for adding more than one document simultaneously.
	 * 
	 * @param newDocuments
	 *            Vector of documents to be added to the backend
	 */
	public void addDocuments(Collection<Document> newDocuments) {
		for (Document doc : newDocuments) {
			addDocument(doc);
			jgaapGUI.incProgress();
		}
	}

	/**
	 * Method for adding documents using only a few of the document attributes.
	 * 
	 * @param path
	 *            the path to the document
	 * @param author
	 *            the author of the document
	 * @throws Exception 
	 */
	// CKL 1/24/2009 re-designed and re-coded
	public Document addDocument(String path, String author) throws Exception {
		return this.addDocument(new Document(path, author));
	}

	/**
	 * Method for adding documents using only a few of the document attributes.
	 * 
	 * @param path
	 *            The file path to the document
	 * @param author
	 *            The document's author
	 * @param title
	 *            The document's title
	 * @throws Exception 
	 */
	// CKL 1/24/2009 re-designed and re-coded
	public Document addDocument(String path, String author, String title) throws Exception {
		return this.addDocument(new Document(path, author, title));
	}

	public Collection<Document> getAllDocuments() {
		ArrayList<Document> documents = new ArrayList<Document>();
		documents.addAll(this.documents);
		return documents;
	}

	// ---------------------------------------------------
	// ----------- Canonicizer Manipulation --------------
	// ---------------------------------------------------

	/**
	 * Method to find a Canonicizer object given its name.
	 * 
	 * @param name
	 *            The name of the canonicizer
	 * @return
	 */
	// Rutenbar 16/2/2010 - This is for JgaapAPI
	public Canonicizer lookupCanonicizerName(String name) {
		String formatedName = name.toLowerCase();
		if (canonicizers.containsKey(formatedName)) {
			return canonicizers.get(formatedName);
		}

		return null;
	}

	/**
	 * Method to add a Canonicizer to a single Document
	 * 
	 * @param canonicizer
	 *            the Canonicizer to add
	 * @param document
	 *            the Document to add the Canonicizer to
	 */
	public void addCanonicizerToDocument(Canonicizer canonicizer,
			Document document) {
		document.addCanonicizer(canonicizer);
		System.out.println("Document now has: " + document.getCanonicizers());
	}

	/**
	 * Method to add a Canonicizer to every Document
	 * 
	 * @param canonicizer
	 *            the canonicizer to add to each document
	 */
	public void addCanonicizerToAllDocuments(Canonicizer canonicizer) {
		for (Document doc : documents)
			doc.addCanonicizer(canonicizer);
		((Document) (jgaapConstants.globalObjects.get("hDoc")))
				.addCanonicizer(canonicizer);
	}

	/**
	 * Method to add a Canonicizer to Documents with a certain docType
	 * 
	 * @param canonicizer
	 *            the canonicizer to add to each document
	 * @param docType
	 *            the docType that a Document must be to add this canonicizer
	 */
	public void addCanonicizerToDocType(Canonicizer canonicizer,
			Document.DocType docType) {
		for (Document doc : documents)
			if (doc.getDocType() == docType)
				doc.addCanonicizer(canonicizer);
	}

	/**
	 * Method to remove a Canonicizer from a single Document
	 * 
	 * @param canonicizer
	 *            the Canonicizer to remove
	 * @param document
	 *            the Document to remove the Canonicizer from
	 */
	public void removeCanonicizerFromDocument(Canonicizer canonicizer,
			Document document) {
		document.removeCanonicizer(canonicizer);
	}

	/**
	 * Method to remove a Canonicizer from every Document
	 * 
	 * @param canonicizer
	 *            the canonicizer to remove from each document
	 */
	public void removeCanonicizerFromAllDocuments(Canonicizer canonicizer) {
		for (Document doc : documents)
			doc.removeCanonicizer(canonicizer);
		((Document) (jgaapConstants.globalObjects.get("hDoc")))
				.removeCanonicizer(canonicizer);
	}

	/**
	 * Method to remove a Canonicizer from Documents with a certain docType
	 * 
	 * @param canonicizer
	 *            the canonicizer to remove from each document
	 * @param docType
	 *            the docType that a Document must be to remove this canonicizer
	 */
	public void removeCanonicizerFromDocType(Canonicizer canonicizer,
			Document.DocType docType) {
		for (Document doc : documents)
			if (doc.getDocType() == docType)
				doc.removeCanonicizer(canonicizer);
	}

	/**
	 * Method for adding multiple Canonicizers to every Document
	 * 
	 * @param canonicizers
	 *            the canonicizers to add to each document
	 */
	public void addCanonicizersToAllDocuments(
			Collection<Canonicizer> canonicizers) {
		for (Canonicizer canonicizer : canonicizers)
			addCanonicizerToAllDocuments(canonicizer);
	}

	/**
	 * Method for adding multiple Canonicizers to Documents with a certain
	 * docType
	 * 
	 * @param canonicizers
	 *            the canonicizers to add to each document
	 * @param docType
	 *            the docType that a Document must be to add this canonicizer
	 */
	public void addCanonicizersToDocType(Collection<Canonicizer> canonicizers,
			Document.DocType docType) {
		for (Canonicizer canonicizer : canonicizers)
			addCanonicizerToDocType(canonicizer, docType);
	}

	/**
	 * Method for removing multiple Canonicizers from every Document
	 * 
	 * @param canonicizers
	 *            the canonicizers to remove from each document
	 */
	public void removeCanonicizersFromAllDocuments(
			Collection<Canonicizer> canonicizers) {
		for (Canonicizer canonicizer : canonicizers)
			removeCanonicizerFromAllDocuments(canonicizer);
	}

	/**
	 * Method for removing multiple Canonicizers from Documents with a certain
	 * docType
	 * 
	 * @param canonicizers
	 *            the canonicizers to remove from each document
	 * @param docType
	 *            the docType that a Document must be to remove this canonicizer
	 */
	public void removeCanonicizersFromDocType(
			Collection<Canonicizer> canonicizers, Document.DocType docType) {
		for (Canonicizer canonicizer : canonicizers)
			removeCanonicizerFromDocType(canonicizer, docType);
	}

	/**
	 * Remove all canonicizers from all documents.
	 */
	public void clearAllCanonicizers() {
		for (Document doc : documents)
			doc.clearCanonicizers();
	}

	/**
	 * Take the Document instances that are stored internally and call their
	 * 'processCanonicizers()' methods.
	 */
	private void canonicize() {
		for (Document doc : documents) {
			doc.processCanonicizers();
		}
	}

	// MVR 4/23/2009 fixed to work with the new canon system
	// left the method deprecated so this get a
	// CKL 1/24/2009 This will probably need to be re-designed and re-coded due
	// to the new canonicizer system
	// MVR 10/6/2008 a redesign of the canonicizer
	public void canonicizerProcessEngine(String canon) {
		if (canonicizers.containsKey(canon.toLowerCase())) {
			addCanonicizerToAllDocuments(canonicizers.get(canon.toLowerCase()));
		} else {
			System.out
					.println("Canonicizer: " + canon + " was not recognized.");
		}
	}

	// MVR 4/23/2009 Added useful output
	// CKL 1/24/2009 This will probably need to be re-designed and re-coded due
	// to the new canonicizer system
	// MVR 10/6/2008 This will be the new method to find out which canonicizers
	// were used after he array method is abandon
	// JN 4/28/09 Redesigned to use a "hidden document to keep track of
	// canonicizers
	// that were applied to ALL documents (since per-document canonicization
	// makes this
	// harder to keep track of).
	public String canonicizersUsed() {
		StringBuffer usedCanon = new StringBuffer();
		for (Canonicizer c : ((Document) (jgaapConstants.globalObjects
				.get("hDoc"))).getCanonicizers()) {
			usedCanon.append(c);
		}
		return usedCanon.toString();
	}

	public void clearAll() {
		documents = new ArrayList<Document>();
	}

	/*
	 * CreateEventSet() and runStatisticalAnalysis() set processError and
	 * processErrorMessage, which report whether an error occured internally.
	 * These members are private, and they're never printed, so nobody sees
	 * them. For those two methods, getProcessError()/getProcessErrorMessage
	 * return meaningful error information.
	 * 
	 * FIXME: implement that standardized error-handling system already!
	 */
	public boolean getProcessError() {
		return processError;
	}

	public String getProcessErrorMessage() {
		return processErrorMessage;
	}

	// getAllEventSets() returns the set of all valid event sets
	public Set<String> getAllEventSets() {
		return new HashSet<String>(events.keySet()); // return a copy of the set
	}

	public EventDriver addEventDriver(String action) throws Exception {
		if (events.containsKey(action.toLowerCase())){
			EventDriver eventDriver =events.get(action.toLowerCase()).getClass().newInstance(); 
			eventDrivers.add(eventDriver);
			return eventDriver;
		}
		else {
			throw new Exception("Event Driver "+action+" not found!");
		}
	}
	
	public Boolean removeEventDriver(EventDriver eventDriver){
		return eventDrivers.remove(eventDriver);
	}

	private void createEventSet() {
		for (EventDriver eventDriver : eventDrivers) {
			for (Document document : documents) {
				document.addEventSet(eventDriver.createEventSet(document));
			}
		}
	}

	public void loadDocuments(Vector<Vector<String>> authorFileTable) throws Exception {
		for (int i = 0; i < authorFileTable.size(); i++) {
			addDocument(authorFileTable.elementAt(i).elementAt(1),
					authorFileTable.elementAt(i).elementAt(0));
		}
	}

	/**
	 * 
	 * Performs the selected analysis on the corpus, return results in a HashMap
	 * 
	 * @param action
	 *            the analysis or distance that is going to be used
	 * @param currentDivergenceMethod
	 *            the version of the divergence method that is going to be used
	 * @return
	 */

	private List<String> runStatisticalAnalysisRaw() {
		List<String> results = new ArrayList<String>();
		List<List<EventSet>> knownEventSetsList = new ArrayList<List<EventSet>>();
		List<List<EventSet>> unknownEventSetsList = new ArrayList<List<EventSet>>();
		for (Document document : documents) {
			if (document.isAuthorKnown()) {
				knownEventSetsList.add(document.getEventSets());
			} else {
				unknownEventSetsList.add(document.getEventSets());
			}
		}
		for (AnalysisDriver analysisDriver : analysisDrivers)
			for (List<EventSet> unknownEventSets : unknownEventSetsList) {
				for(int i=0; i<unknownEventSets.size();i++){
					EventSet unknownEventSet = unknownEventSets.get(i);
					List<EventSet> knownEventSets = new ArrayList<EventSet>();
					for(List<EventSet> eventSets: knownEventSetsList){
						knownEventSets.add(eventSets.get(i));
					}
					results.add(unknownEventSet.getDocumentName());
					results.add(analysisDriver.analyze(unknownEventSet, knownEventSets));
					
				}
			}
		return results;
	}

	// This has been written from scratch to emulate the old
	// runStatisticalAnalysis() method.
	// The old one returned a friendly, String-ified set of results.
	// The new one (runStatisticalAnalysisRaw), returns a String array of
	// {document, author, document, author, ...} pairs.
	// This method should behave identically to the original method.
	private String runStatisticalAnalysis() {
		List<String> results;
		String output = "";
		results = runStatisticalAnalysisRaw();
		if (processError) {
			output += "*** Warning ***  An error in processing the EventSet and/or "
					+ "Statistical Analysis Method choices has resulted in one or more "
					+ "default actions being used, which may result in unexpected or incorrect results\n\n";
			output += processErrorMessage + "\n\n";
		}
		Iterator<String> resultsIterator = results.iterator();
		while (resultsIterator.hasNext()) {
			output += resultsIterator.next() + " - ";
			output += resultsIterator.next() + "\n";
		}
		return output;
	}
	
	public AnalysisDriver addAnalysis(String action) throws Exception{
		AnalysisDriver analysisDriver;
		if (distances.containsKey(action.toLowerCase())) {
			analysisDriver = new NearestNeighborDriver();
			((NearestNeighborDriver) analysisDriver).setDist(distances.get(action
					.toLowerCase()).getClass().newInstance());
		} else if (analysises.containsKey(action.toLowerCase())) {
				analysisDriver = analysises.get(action.toLowerCase()).getClass().newInstance();
		}
		else {
			throw new Exception();
		}
		analysisDrivers.add(analysisDriver);
		return analysisDriver;
	}
	
	public Boolean removeAnalysisDriver(AnalysisDriver analysisDriver){
		return analysisDrivers.remove(analysisDriver);
	}
	
	public String execute(){
		canonicize();
		createEventSet();
		return runStatisticalAnalysis();
	}
	
	public List<String> executeRaw(){
		canonicize();
		createEventSet();
		return runStatisticalAnalysisRaw();
	}
	
	public List<Canonicizer> getCanonicizers(){
		return AutoPopulate.getCanonicizers();
	}
	
	public List<EventDriver> getEventDrivers(){
		return AutoPopulate.getEventDrivers();
	}
	
	public List<AnalysisDriver> getAnalysisDrivers(){
		return AutoPopulate.getAnalysisDrivers();
	}
	
	public List<DistanceFunction> getDistanceFunctions(){
		return AutoPopulate.getDistanceFunctions();
	}

	public void saveDocuments(String which, File file) {
		Vector<Vector<String>> authorFileTable = new Vector<Vector<String>>();
		if (which.equals("Save Corpus")) {
			for (Document document : documents) {
				Vector<String> current = new Vector<String>();
				if (document.isAuthorKnown()) {
					current.add(document.getAuthor());
					current.add(document.getFilePath());
					current.add(document.getTitle());
				}
			}
		} else if (which.equals("Save Unknown")) {
			for (Document document : documents) {
				Vector<String> current = new Vector<String>();
				if (!document.isAuthorKnown()) {
					current.add(document.getAuthor());
					current.add(document.getFilePath());
					current.add(document.getTitle());
				}
			}
		} else if (which.equals("Save All")) {
			for (Document document : documents) {
				Vector<String> current = new Vector<String>();
				current.add(document.getAuthor());
				current.add(document.getFilePath());
				current.add(document.getTitle());
			}
		}
		System.out.println(authorFileTable.toString());
		CSVIO.writeCSV(authorFileTable, file);
	}

	public void saveResults(String fileName, String results) {
		try {
			Writer output = new BufferedWriter(new FileWriter(fileName));
			output.write(results);
			output.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * Checks the selected language against those loaded from com.jgaap.language
	 * 
	 * @param e
	 *            string representing a language class
	 * @return an instance of the specified language class
	 */
	public Language selectedLanguage(String e) {
		Language selected;
		if (languages.containsKey(e.toLowerCase())) {
			try {
				selected = languages.get(e.toLowerCase()).getClass()
						.newInstance();
			} catch (Exception l) {
				System.err.println("Language selected " + e.toString()
						+ " not recognized using default English.");
				selected = new English();
			}
		} else {
			System.err.println("Language selected " + e.toString()
					+ " not recognized using default English.");
			selected = new English();
		}
		return selected;
	}

	/**
	 * Modifies the nearest neighbor methods so that the neighbors are authors
	 * not documents
	 * 
	 * @param value
	 *            "true" or "false"
	 */
	public void setAuthorDistance(boolean value) {
		jgaapConstants.globalParams.setParameter("authorDistance",
				(value ? "true" : "false"));
	}

	// TODO: move this into the cli
	/**
	 * Lists the currently available arguments for the specified part of the
	 * jgaap pipeline
	 * 
	 * @param action
	 *            a part of the pipeline
	 * @return list of all arguments from the dynamically loaded classes for
	 *         action
	 */
	public Vector<String> argumentList(String action) {
		Vector<String> result = new Vector<String>();
		if (action.equalsIgnoreCase("c")
				|| action.equalsIgnoreCase("canonicizer")) {
			result.addAll(this.canonicizers.keySet());
		} else if (action.equalsIgnoreCase("es")
				|| action.equalsIgnoreCase("event set")) {
			result.addAll(this.events.keySet());
		} else if (action.equalsIgnoreCase("a")
				|| action.equalsIgnoreCase("analysis")) {
			result.addAll(this.analysises.keySet());
			result.addAll(this.distances.keySet());
		} else if (action.equalsIgnoreCase("lang")
				|| action.equalsIgnoreCase("language")) {
			result.addAll(this.languages.keySet());
		} else {
			result.add("Argument type: " + action + " was not recognized.");
			result.add("Please choose a valid argument:");
			result.add("c, canonicizer");
			result.add("es, event set");
			result.add("a, analysis");
			result.add("lang, language");
		}
		return result;
	}
}
