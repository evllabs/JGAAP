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
package com.jgaap.backend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jgaap.classifiers.NearestNeighborDriver;
import com.jgaap.generics.*;
import com.jgaap.languages.English;

/**
 * 
 * This class provides a simple interface into jgaap for use in 
 * other software packages and for development of any human interfaces.
 * 
 * For examples of how to use the API class see the com.jgaap.ui package for a GUI example
 * or the com.jgaap.backend.CLI class for a command line example
 * 
 * @author Michael Ryan
 * @since 5.0.0
 */
public class API {

	private List<Document> documents;
	private Language language;
	private List<EventDriver> eventDrivers;
	private List<EventCuller> eventCullers;
	private List<AnalysisDriver> analysisDrivers;
	private WorkQueue loadCanonicizeEventifyWorkQueue;
	
	private final int loadCanonicizeEventifyWorkers = 50;

	private static final API INSTANCE = new API();
	
	private API() {
		documents = new ArrayList<Document>();
		language = new English();
		eventDrivers = new ArrayList<EventDriver>();
		eventCullers = new ArrayList<EventCuller>();
		analysisDrivers = new ArrayList<AnalysisDriver>();
	}
	
	public static API getInstance(){
		return INSTANCE;
	}
	
	public static API getPrivateInstance(){
		return new API();
	}

	/**
	 * 
	 * This allows for the addition of documents to the system.
	 * Both Training (known) and Sample (unknown) documents must be provided before runing an experiment.
	 * Training Documents are added by providing an author(tag) for them.
	 * Sample documents are added when no auythor(tag) is given.
	 * 
	 * @param filepath - the system file path or URL to a document 
	 * @param author - the author of this document or the tag being applied to this document, if null or the empty string this document is considered unknown and is one of those classified
	 * @param title - Some means of identifying the document, if null or the empty string are provided a title will be generated from the file name
	 * @return - a reference to the document generated
	 * @throws Exception - if there is a problem loading the document from file web or parsing file format
	 */
	public Document addDocument(String filepath, String author, String title)
			throws Exception {
		System.out.println("Adding Document :" + filepath);
		Document document = new Document(filepath, author, title);
		documents.add(document);
		return document;
	}

	/**
	 * Adds a previously generated document to the jgaap system.
	 * 
	 * @param document - a file that has already been loaded as a Document
	 * @return - a refence to the document generated
	 */
	public Document addDocument(Document document) {
		documents.add(document);
		return document;
	}

	/**
	 * Removes a document from the system.
	 * 
	 * @param document - a reference to the document that is to be removed
	 * @return - true on success false on failure
	 */
	public Boolean removeDocument(Document document) {
		return documents.remove(document);
	}

	/**
	 * Removes all documents loaded into the system.
	 */
	public void removeAllDocuments() {
		documents.clear();
	}

	/**
	 * Get a List of all Documents currently loaded into jgaap
	 * 
	 * @return - a List of Documents loaded into the system
	 */
	public List<Document> getDocuments() {
		return documents;
	}

	/**
	 * Get a List of all currently loaded Documents that do not have an author(tag)
	 * 
	 * @return List of Documents without authors
	 */
	public List<Document> getUnknownDocuments() {
		List<Document> unknownDocuments = new ArrayList<Document>();
		for (Document document : documents) {
			if (!document.isAuthorKnown()) {
				unknownDocuments.add(document);
			}
		}
		return unknownDocuments;
	}

	/**
	 * Get a List of Documents currently loaded into the system that have a author(tag)
	 * 
	 * @return List of Documents with authors
	 */
	public List<Document> getKnownDocuments() {
		List<Document> knownDocuments = new ArrayList<Document>();
		for (Document document : documents) {
			if (document.isAuthorKnown()) {
				knownDocuments.add(document);
			}
		}
		return knownDocuments;
	}

	/**
	 * Get a List of Documents that all have the same author(tag)
	 * 
	 * @param author - the author(tag) to select documents on 
	 * @return - List of Documents limited by the author provided
	 */
	public List<Document> getDocumentsByAuthor(String author) {
		List<Document> authorDocuments = new ArrayList<Document>();
		for (Document document : documents) {
			if (document.isAuthorKnown()) {
				if (author.equalsIgnoreCase(document.getAuthor())) {
					authorDocuments.add(document);
				}
			}
		}
		return authorDocuments;
	}

	/**
	 * Get a List of all unique authors(tags) applied to Known(Training) Documents
	 *  
	 * @return List of authors
	 */
	public List<String> getAuthors() {
		Set<String> authors = new HashSet<String>();
		for (Document document : documents) {
			if (document.isAuthorKnown()) {
				authors.add(document.getAuthor());
			}
		}
		return new ArrayList<String>(authors);
	}
	
	/**
	 * Loads the documents from the file system
	 * @throws Exception 
	 */
	public void loadDocuments() throws Exception{
		for(Document document : documents){
			document.load();
		}
	}

	/**
	 * Adds the specified canonicizer to all documents currently loaded in the system.
	 * 
	 * @param action - the unique string name representing a canonicizer (displayName())
	 * @throws Exception - if the canonicizer specified cannot be found or instanced
	 */
	public void addCanonicizer(String action) throws Exception {
		for (Document document : documents) {
			addCanonicizer(action, document);
		}
	}

	/**
	 * Adds the specified canonicizer to all Documents that have the DocType docType.
	 * 
	 * @param action - the unique string name representing a canonicizer (displayName())
	 * @param docType - The DocType this canonicizer is restricted to 
	 * @throws Exception - if the canonicizer specified cannot be found or instanced
	 */
	public void addCanonicizer(String action, DocType docType) throws Exception {
		for (Document document : documents) {
			if (document.getDocType().equals(docType)) {
				addCanonicizer(action, document);
			}
		}
	}

	/**
	 * Add the Canonicizer specified to the document referenced.
	 * 
	 * @param action - the unique string name representing a canonicizer (displayName())
	 * @param document - the Document to add the canonicizer to
	 * @return - a reference to the canonicizer added
	 * @throws Exception - if the canonicizer specified cannot be found or instanced
	 */
	public Canonicizer addCanonicizer(String action, Document document)
			throws Exception {
		Canonicizer canonicizer = CanonicizerFactory.getCanonicizer(action);
		document.addCanonicizer(canonicizer);
		return canonicizer;
	}

	/**
	 * Removes the first instance of the canoniciser corresponding to the action(displayName()) 
	 * from the Document referenced.
	 * 
	 * @param action - the unique string name representing a canonicizer (displayName())
	 * @param document - a reference to the Document to remove the canonicizer from
	 */
	public void removeCanonicizer(String action, Document document) {
		document.removeCanonicizer(action);
	}

	/**
	 * Removes the first occurrence of the canonicizer corresponding to the action(displayName())
	 * from every document
	 * 
	 * @param action - the unique string name representing a canonicizer (displayName())
	 */
	public void removeCanonicizer(String action) {
		for (Document document : documents) {
			removeCanonicizer(action, document);
		}
	}

	/**
	 * Removes the first occurrence of the canonicizer from every Document of the DocType docType
	 * 
	 * @param action - the unique string name representing a canonicizer (displayName())
	 * @param docType - the DocType to remove the canonicizer from
	 */
	public void removeCanonicizer(String action, DocType docType) {
		for (Document document : documents) {
			if (document.getDocType().equals(docType)) {
				removeCanonicizer(action, document);
			}
		}
	}

	/**
	 * Removes all canonicizers from Documents with the DocType docType
	 * 
	 * @param docType - the DocType to remove canonicizers from
	 */
	public void removeAllCanonicizers(DocType docType) {
		for (Document document : documents) {
			document.clearCanonicizers();
		}
	}

	/**
	 * Removes all canonicizers from All Documents loaded in the system
	 */
	public void removeAllCanonicizers() {
		for (Document document : documents) {
			document.clearCanonicizers();
		}
	}

	/**
	 * Add an Event Driver which will be used to 
	 * eventify(Generate a List of Events order in the sequence they are found in the document) 
	 * all of the documents
	 * @param action - the identifier for the EventDriver to add (displayName())
	 * @return - a reference to the added EventDriver
	 * @throws Exception - If the action is not found or the EventDriver cannot be instanced
	 */
	public EventDriver addEventDriver(String action) throws Exception {
		EventDriver eventDriver = EventDriverFactory.getEventDriver(action);
		eventDrivers.add(eventDriver);
		return eventDriver;
	}

	/**
	 * Removes the Event Driver reference from the system
	 * @param eventDriver - the EventDriver to be removed
	 * @return - true if successful false if failure 
	 */
	public Boolean removeEventDriver(EventDriver eventDriver) {
		return eventDrivers.remove(eventDriver);
	}

	/**
	 * Removes all EventDrivers from the system
	 */
	public void removeAllEventDrivers() {
		eventDrivers.clear();
		for (Document document : documents) {
			document.clearEventSets();
		}
	}

	/**
	 * Gets a List of all EventDrivers currently loaded in the system
	 * @return List of All loaded EventDrivers
	 */
	public List<EventDriver> getEventDrivers() {
		return eventDrivers;
	}

	/**
	 * Add an Event Culler to the system
	 * 
	 * @param action - unique identifier for the event culler to add (displayName())
	 * @return - a reference to the added event culler
	 * @throws Exception - if the EventCuller cannot be found or cannor be instanced 
	 */
	public EventCuller addEventCuller(String action) throws Exception {
		EventCuller eventCuller = EventCullerFactory.getEventCuller(action);
		eventCullers.add(eventCuller);
		return eventCuller;
	}

	/**
	 * Remove the supplied EventCuller from the system
	 * 
	 * @param eventCuller - EventCuller to be removed
	 * @return - true if success false if failure
	 */
	public Boolean removeEventCuller(EventCuller eventCuller) {
		return eventCullers.remove(eventCuller);
	}

	/**
	 * Removes all loaded EventCullers from the system
	 */
	public void removeAllEventCullers() {
		eventCullers.clear();
	}

	/**
	 * Get a List of all EventCullers currently loaded in the system
	 * @return List of EventCullers loaded
	 */
	public List<EventCuller> getEventCullers() {
		return eventCullers;
	}

	/**
	 * Add an AnalysisDriver to the system as referenced by the action.
	 * 
	 * NOTE! for legacy purposes this methods also accepts actions that reference DistanceFunctions
	 *  it will use the supplied distance function along with the NeighborAnalysisDriver NearestNeighborDriver 
	 *  which had been the only NeighbotAnalysisDriver prior to version 5.0 
	 *  !!!WARNING!!! There are no guarantees that this functionality will remain in future releases please use addDistanceFunction
	 * 
	 * @param action - the unique identifier for a AnalysisDriver (alternately a DistanceFunction)
	 * @return - a reference to the generated Analysis Driver
	 * @throws Exception - If the AnalysisDriver cannot be found or if it cannot be instanced 
	 */
	public AnalysisDriver addAnalysisDriver(String action) throws Exception {
		AnalysisDriver analysisDriver;
		try {
			analysisDriver = AnalysisDriverFactory.getAnalysisDriver(action);
		} catch (Exception e) {
			analysisDriver = new NearestNeighborDriver();
			addDistanceFunction(action, analysisDriver);
		}
		analysisDrivers.add(analysisDriver);
		return analysisDriver;
	}

	/**
	 * Removed the passed AnalysisDriver from the system
	 * @param analysisDriver - reference to the AnalysisDriver to be removed
	 * @return True if success false if failure
	 */
	public Boolean removeAnalysisDriver(AnalysisDriver analysisDriver) {
		return analysisDrivers.remove(analysisDriver);
	}

	/**
	 * Removes all AnalysisDrivers from the system
	 */
	public void removeAllAnalysisDrivers() {
		analysisDrivers.clear();
	}

	/**
	 * Adds a DistanceFunction to the AnalysisDriver supplied.
	 * Only AnalysisDrivers that extend the NeighborAnalysisDriver can be used
	 * 
	 * @param action - unique identifier for the DistanceFunction you want to add
	 * @param analysisDriver - a reference to the AnalysisDriver you want the distance added to
	 * @return - a reference to the generated DistanceFunction
	 * @throws Exception - if the AnalysisDriver does not extend NeighborAnalysisDriver if the DistanceFunction cannot be found if the DistanceFunction cannot be instanced
	 */
	public DistanceFunction addDistanceFunction(String action,
			AnalysisDriver analysisDriver) throws Exception {
		DistanceFunction distanceFunction = DistanceFunctionFactory
				.getDistanceFunction(action);
		((NeighborAnalysisDriver) analysisDriver).setDistance(distanceFunction);
		return distanceFunction;
	}

	/**
	 * Get a List of All AnalysisDrivers currently loaded on the system
	 * @return List of All AnalysisDrivers
	 */
	public List<AnalysisDriver> getAnalysisDrivers() {
		return analysisDrivers;
	}

	public Language getLanguage(){
		return language;
	}
	
	/**
	 * Set the Language that JGAAP will operate in.
	 * This restricts what methods are available, changes the charset that is expected when reading files, and will add any pre-processing that is needed
	 * @param action - the Language to operate under
	 * @return - a Reference to the language object selected
	 * @throws Exception - if the language cannot be found or cannot be instanced 
	 */
	public Language setLanguage(String action) throws Exception {
		language = LanguageFactory.getLanguage(action);
		return language;
	}

	private void loadCanonicizeEventify() throws Exception{
		loadCanonicizeEventifyWorkQueue= new WorkQueue(loadCanonicizeEventifyWorkers);
		for(final Document document : documents){
			Runnable work = new Runnable() {
				@Override
				public void run() {
					try {
						document.setLanguage(language);
						document.load();
						document.processCanonicizers();
						for (EventDriver eventDriver : eventDrivers) {
							document.addEventSet(eventDriver,eventDriver.createEventSet(document));
						}
						document.readStringText("");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			};
			loadCanonicizeEventifyWorkQueue.execute(work);
		}
		for(int i =0; i<loadCanonicizeEventifyWorkers;i++){
			loadCanonicizeEventifyWorkQueue.execute(-1);
		}
		while(loadCanonicizeEventifyWorkQueue.isRunning()){
			Thread.sleep(500);
		}
	}
	
	/**
	 * Events are culled from EventSets across all Documents on a per EventDriver basis
	 * @throws InterruptedException
	 */
	private void cull() {
		List<EventSet> eventSets = new ArrayList<EventSet>();
		for (EventDriver eventDriver : eventDrivers) {
			for (final Document document : documents) {
				if (document.getEventSets().containsKey(eventDriver)) {
					eventSets.add(document.getEventSet(eventDriver));
				}
			}
			for (EventCuller culler : eventCullers) {
				eventSets = culler.cull(eventSets);
			}
			for (Document document : documents) {
				if (document.getEventSets().containsKey(eventDriver)) {
					document.addEventSet(eventDriver, eventSets.remove(0));
				}
			}
			eventSets.clear();
		}
	}

	/**
	 * Threads are generated for every Unknown(sample) Document.
	 * In each Thread all loaded AnalysisDrivers are run over All EventSets compairing the Unknown(sample) to the Known(training) Documents.
	 * @throws InterruptedException
	 */
	private void analyze() throws InterruptedException {
//		List<Thread> threads = new ArrayList<Thread>();
		final List<Document> knownDocuments = new ArrayList<Document>();
		List<Document> unknownDocuments = new ArrayList<Document>();
		for (Document document : documents) {
			if (document.isAuthorKnown()) {
				knownDocuments.add(document);
			} else {
				unknownDocuments.add(document);
			}
		}
		for (final AnalysisDriver analysisDriver : analysisDrivers)
			for (final Document unknownDocument : unknownDocuments) {
//				Thread t = new Thread(new Runnable() {
//					public void run() {
						analysisDriver.analyze(unknownDocument, knownDocuments);
//					}
//				});
//				threads.add(t);
//				t.start();
			}
//		for (Thread t : threads) {
//			t.join();
//		}
	}

	/**
	 * Preforms the canonicize eventify cull and analyze methods since a strict order has to be enforced when using them 
	 * @throws Exception 
	 */
	public void execute() throws Exception {
		loadCanonicizeEventify();
		cull();
		analyze();
		clearEventSets();
	}

	/**
	 * Removes all results generated by the analysis methods 
	 */
	public void clearResults() {
		List<Document> documents = getUnknownDocuments();
		for (Document document : documents) {
			document.clearResults();
		}
	}
	
	/**
	 * Removes all EventSets generated by EventDriver
	 */
	public void clearEventSets() {
		for(Document document : documents){
			document.clearEventSets();
		}
	}

	/**
	 * Get a List of All Canonicizers that are available to be used
	 * @return List of All Canonicizers
	 */
	public List<Canonicizer> getAllCanonicizers() {
		return AutoPopulate.getCanonicizers();
	}

	/**
	 * Get a List of All EventDrivers that are available to be used
	 * @return List of All EventDrivers
	 */
	public List<EventDriver> getAllEventDrivers() {
		return AutoPopulate.getEventDrivers();
	}

	/**
	 * Get a List of All EventCuller that are available to be used
	 * @return List of All EventCullers
	 */
	public List<EventCuller> getAllEventCullers() {
		return AutoPopulate.getEventCullers();
	}

	/**
	 * Get a List of All AnalysisDriver that are available to be used
	 * @return List of All AnalysisDrivers
	 */
	public List<AnalysisDriver> getAllAnalysisDrivers() {
		return AutoPopulate.getAnalysisDrivers();
	}

	/**
	 * Get a List of All DistanceFunctions that are available to be used
	 * @return List of All DistanceFunctions
	 */
	public List<DistanceFunction> getAllDistanceFunctions() {
		return AutoPopulate.getDistanceFunctions();
	}

	/**
	 * Get a List of All Languages that are available to be used
	 * @return List of All Languages
	 */
	public List<Language> getAllLanguages() {
		return AutoPopulate.getLanguages();
	}

}
