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
import com.jgaap.DivergenceType;
import com.jgaap.jgaapConstants;
import com.jgaap.distances.HistogramDistance;
import com.jgaap.eventDrivers.MostCommonEventDriver;
import com.jgaap.eventDrivers.NaiveWordEventDriver;
import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.Document;
import com.jgaap.generics.DocumentSet;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Language;
import com.jgaap.generics.NearestNeighborDriver;
import com.jgaap.generics.Canonicizer;
import com.jgaap.gui.jgaapGUI;
import com.jgaap.languages.*;

public class guiDriver {

	Vector<Document> k_docs;
	Vector<DocumentSet> k_docsets;
	Vector<EventSet> k_es;

	Vector<Document> u_docs;
	Vector<DocumentSet> u_docsets;
	Vector<EventSet> u_es;

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
		k_docs = new Vector<Document>();
		k_docsets = new Vector<DocumentSet>();
		k_es = new Vector<EventSet>();
		u_docs = new Vector<Document>();
		u_docsets = new Vector<DocumentSet>();
		u_es = new Vector<EventSet>();
		// Load the canonicizers dynamically
		canonicizers = AutoPopulate.getCanonicizers();
		// Load the event drivers dynamically
		events = AutoPopulate.getEventDrivers();
		// Load the distance functions dynamically
		distances = AutoPopulate.getDistanceFunctions();
		// Load the classifiers dynamically
		analysises = AutoPopulate.getAnalysisDrivers();
		// Load the classifiers dynamically
		languages = AutoPopulate.getLanguages();

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
	public void addDocument(Document newDocument) {
		if (newDocument.isAuthorKnown())
			k_docs.add(newDocument);
		else
			u_docs.add(newDocument);
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
	 */
	// CKL 1/24/2009 re-designed and re-coded
	public void addDocument(String path, String author) {
		this.addDocument(new Document(path, author));
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
	 */
	// CKL 1/24/2009 re-designed and re-coded
	public void addDocument(String path, String author, String title) {
		this.addDocument(new Document(path, author, title));
	}

	public Collection<Document> getAllDocuments() {
		ArrayList<Document> documents = new ArrayList<Document>();
		documents.addAll(k_docs);
		documents.addAll(u_docs);
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
	public Canonicizer lookupCanonicizerName(String name)
	{
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
		for (Document doc : k_docs)
			doc.addCanonicizer(canonicizer);
		for (Document doc : u_docs)
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
		for (Document doc : k_docs)
			if (doc.getDocType() == docType)
				doc.addCanonicizer(canonicizer);
		for (Document doc : u_docs)
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
		for (Document doc : k_docs)
			doc.removeCanonicizer(canonicizer);
		for (Document doc : u_docs)
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
		for (Document doc : k_docs)
			if (doc.getDocType() == docType)
				doc.removeCanonicizer(canonicizer);
		for (Document doc : u_docs)
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
		for (Document doc : k_docs)
			doc.clearCanonicizers();
		for (Document doc : u_docs)
			doc.clearCanonicizers();
	}

	/**
	 * Take the Document instances that are stored internally and call their
	 * 'processCanonicizers()' methods.
	 */
	// CKL 1/24/2009 re-designed and re-coded
	public void canonicize() {
		u_docsets.clear();
		k_docsets.clear();
		
		for (Document doc : k_docs) {
			doc.processCanonicizers();
			k_docsets.add(new DocumentSet(doc));
		}
		for (Document doc : u_docs) {
			doc.processCanonicizers();
			u_docsets.add(new DocumentSet(doc));
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
		/*
		 * StringBuffer usedCanon = new StringBuffer(); for (Canonicizer tmp :
		 * processEngine) { usedCanon.append(tmp.getClass().getName() + " " +
		 * tmp.getExtraInfo()); } return usedCanon.toString();
		 */
		StringBuffer usedCanon = new StringBuffer();
		for (Canonicizer c : ((Document) (jgaapConstants.globalObjects
				.get("hDoc"))).getCanonicizers()) {
			usedCanon.append(c);
		}
		return usedCanon.toString();
	}

	public void clearAll() {
		k_docs = new Vector<Document>();
		k_docsets = new Vector<DocumentSet>();
		k_es = new Vector<EventSet>();
		u_docs = new Vector<Document>();
		u_docsets = new Vector<DocumentSet>();
		u_es = new Vector<EventSet>();
	}

	/*	CreateEventSet() and runStatisticalAnalysis() set processError and processErrorMessage,
		which report whether an error occured internally. These members are private, and they're
		never printed, so nobody sees them.
		For those two methods, getProcessError()/getProcessErrorMessage return meaningful error information.

		FIXME: implement that standardized error-handling system already!
	*/
	public boolean getProcessError() {
		return processError;
	}
	public String getProcessErrorMessage() {
		return processErrorMessage;
	}

	// getAllEventSets() returns the set of all valid event sets
	public Set getAllEventSets() {
		return new HashSet(events.keySet()); // return a copy of the set
	}

	public void createEventSet(String action, boolean onlyCommon) {
		u_es.clear();
		k_es.clear();
		EventDriver te;
		/* Create the appropriate event set type */
		if (events.containsKey(action.toLowerCase()))
			try {
				te = events.get(action.toLowerCase()).getClass().newInstance();
			} catch (Exception e) {
				te = new NaiveWordEventDriver();
				processError = true; // Flag that an error has resulted in a
				// default
				// action
				processErrorMessage += "Error with event type: \"" + action
						+ "\" .  Using default event type \"Words\"\n";
			}
		else {
			/*
			 * JIN - 7/30/08 Default to words. This is done under the assumption
			 * that we would rather produce incorrect/unexpected results rather
			 * than crash. I'm not confident this is a good assumption.
			 */
			for (String s : events.keySet())
				System.out.println(s);
			te = new NaiveWordEventDriver();
			processError = true; // Flag that an error has resulted in a default
			// action
			processErrorMessage += "Error with event type: \"" + action
					+ "\" .  Using default event type \"Words\"\n";
		}
		// Check if we only want the most common events
		if (onlyCommon) {
			EventDriver tmpEventDriver = te;
			te = new MostCommonEventDriver();
			((MostCommonEventDriver) te).setEvents(tmpEventDriver);
			((MostCommonEventDriver) te).setN(50);
		}

		// Add all of the documents to one big Vector of Document Sets
		Vector<DocumentSet> tmpDocs = new Vector<DocumentSet>();
		for (int i = 0; i < k_docsets.size(); i++) {
			tmpDocs.add(k_docsets.elementAt(i));
		}
		for (int i = 0; i < u_docsets.size(); i++) {
			tmpDocs.add(u_docsets.elementAt(i));
		}

		// Now, eventify all of the documents together
		Vector<EventSet> tmpEventSets = te.createEventSet(tmpDocs);

		/*
		 * This might be slightly confusing. tmpEventSets.size() =
		 * k_docsets.size() + u_docsets.size(). So, the first k_docsets.size()
		 * elements of tmpEventSets go into the known event sets, while the next
		 * u_docsets.size() go into the unknown event sets. This results in some
		 * strange bounds for the loops, but it works as long as the order of
		 * the event sets is preserved (which it is).
		 */

		// Then, separate the event sets into known and unknown classes again
		for (int i = 0; i < k_docsets.size(); i++) {
			EventSet es = tmpEventSets.elementAt(i);
			es.setAuthor(k_docsets.elementAt(i).getDocument(0).getAuthor());
			es.setDocumentName(k_docsets.elementAt(i).getDocument(0)
					.getFilePath());
			k_es.add(es);
		}
		System.out.println("debug: k_docsets.size() = " + k_docsets.size() + " total = " + (k_docsets.size() + u_docsets.size()));
		for (int i = k_docsets.size(); i < (k_docsets.size() + u_docsets.size()); i++) {
			EventSet es = tmpEventSets.elementAt(i);
			es.setAuthor(u_docsets.elementAt(i - k_docsets.size()).getDocument(
					0).getAuthor());
			es.setDocumentName(u_docsets.elementAt(i - k_docsets.size())
					.getDocument(0).getFilePath());
			u_es.add(es);
		}
		
		System.out.println("debug: createEventString: u_es.size() = " + u_es.size());
	}

	public void loadDocuments(Vector<Vector<String>> authorFileTable) {
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

	public String[] runStatisticalAnalysisRaw(String action) {
		AnalysisDriver ad;
		String[] results;

		if (distances.containsKey(action.toLowerCase())) {
			ad = new NearestNeighborDriver();
			((NearestNeighborDriver) ad).setDist(distances.get(action
					.toLowerCase()));
		} else if (analysises.containsKey(action.toLowerCase())) {
			ad = analysises.get(action.toLowerCase());
		}
		/*
		 * JIN - 7/31/08 Unknown event sets will default to Histogram Distance,
		 * as this is pretty fast. This works on the assumption that it's better
		 * to produce unexpected/incorrect results than it is for the program to
		 * crash.
		 */
		else {
			processError = true; // Flag that an error has resulted in a default
			// action
			processErrorMessage += "Error processing statistical analysis method \""
					+ action
					+ "\".  Using default method \"Histogram Distance\"\n";
			ad = new NearestNeighborDriver();
			((NearestNeighborDriver) ad).setDist(new HistogramDistance());
		}

		for (EventSet estmp : k_es) {
			jgaapGUI.incProgress();
		}
		
		results = new String[u_es.size()*2];
		
		if (action.equalsIgnoreCase("Linear SVM")
				|| action.equalsIgnoreCase("SVM")
				|| action.equalsIgnoreCase("Gaussian SVM")) {
		
			for (int i = 0; i < u_es.size(); i++) {
				results[i*2] = u_docsets.elementAt(i).getDocument(0).getFilePath();
				results[i*2+1] = ad.analyze(u_es.elementAt(i), k_es);
			}
		}
		else {
			for (int i=0; i<u_es.size(); i++) {
				results[i*2] = u_docsets.elementAt(i).getDocument(0).getFilePath();
				results[i*2+1] = ad.analyze(u_es.elementAt(i), k_es);
				jgaapGUI.incProgress();
			}
		}
		
		return results;
	}
	
	// This has been written from scratch to emulate the old runStatisticalAnalysis() method.
	// The old one returned a friendly, String-ified set of results.
	// The new one (runStatisticalAnalysisRaw), returns a String array of {document, author, document, author, ...} pairs.
	// This method should behave identically to the original method. 
	public String runStatisticalAnalysis(String action) 
	{
		String[] results;
		String output = "";
		int i;
		
		results = runStatisticalAnalysisRaw(action);
		
		if (processError) {
			output += "*** Warning ***  An error in processing the EventSet and/or " +
					"Statistical Analysis Method choices has resulted in one or more " +
					"default actions being used, which may result in unexpected or incorrect results\n\n";
			output += processErrorMessage + "\n\n";
		}
		
		for (i=0; i < results.length; i+=2) {
			output += results[i] + " - ";
			output += results[i+1] + "\n";
		}
		
		return output;
	}
		
		

	public void saveDocuments(String which, File file) {
		Vector<Vector<String>> authorFileTable = new Vector<Vector<String>>();
		if (which.equals("Save Corpus")) {
			for (int i = 0; i < k_docs.size(); i++) {
				authorFileTable.add(new Vector<String>());
				authorFileTable.elementAt(i).add(
						k_docs.elementAt(i).getAuthor());
				authorFileTable.elementAt(i).add(
						k_docs.elementAt(i).getFilePath());
				authorFileTable.elementAt(i)
						.add(k_docs.elementAt(i).getTitle());
			}
		} else if (which.equals("Save Unknown")) {
			for (int i = 0; i < u_docs.size(); i++) {
				authorFileTable.add(new Vector<String>());
				authorFileTable.elementAt(i).add("");
				authorFileTable.elementAt(i).add(
						u_docs.elementAt(i).getFilePath());
				authorFileTable.elementAt(i)
						.add(u_docs.elementAt(i).getTitle());
			}
		} else if (which.equals("Save All")) {
			int i;
			for (i = 0; i < k_docs.size(); i++) {
				authorFileTable.add(new Vector<String>());
				authorFileTable.elementAt(i).add(
						k_docs.elementAt(i).getAuthor());
				authorFileTable.elementAt(i).add(
						k_docs.elementAt(i).getFilePath());
				authorFileTable.elementAt(i)
						.add(k_docs.elementAt(i).getTitle());
			}
			for (int j = i; j < u_docs.size() + i; j++) {
				authorFileTable.add(new Vector<String>());
				authorFileTable.elementAt(j).add("");
				authorFileTable.elementAt(j).add(
						u_docs.elementAt(j - i).getFilePath());
				authorFileTable.elementAt(j).add(
						u_docs.elementAt(j - i).getTitle());
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
	 *             string representing a language class
	 * @return  an instance of the specified language class
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
	 * Parses all documents using the language defined parseLanguage method.
	 * 
	 * @param lang
	 *             the language the documents are in / the parse method to use.
	 */
	public void evaluateLanguage(Language lang) {
		if (lang.isParseable()) {
			for (Document doc : this.k_docs) {
				doc.setProcessedText(lang.parseLanguage(doc.stringify()));
			}
			for (Document doc : this.u_docs) {
				doc.setProcessedText(lang.parseLanguage(doc.stringify()));
			}
		}
	}

	/**
	 * Modifies the nearest neighbor methods so that the neighbors are authors
	 * not documents
	 * 
	 * @param value
	 *             "true" or "false"
	 */
	public void setAuthorDistance(boolean value) {
		jgaapConstants.globalParams.setParameter("authorDistance",
				(value ? "true" : "false"));
	}

	/**
	 * Lists the currently available arguments for the specified part of the
	 * jgaap pipeline
	 * 
	 * @param action
	 *             a part of the pipeline
	 * @return  list of all arguments from the dynamically loaded classes for
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
