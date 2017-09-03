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
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.CanonicizationException;
import com.jgaap.generics.Canonicizer;
import com.jgaap.generics.DistanceFunction;
import com.jgaap.generics.EventCuller;
import com.jgaap.generics.EventCullingException;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.Language;
import com.jgaap.generics.LanguageParsingException;
import com.jgaap.generics.NeighborAnalysisDriver;
import com.jgaap.generics.ValidationDriver;
import com.jgaap.generics.WEKAAnalysisDriver;
import com.jgaap.languages.English;
import com.jgaap.util.Document;
import com.jgaap.util.EventSet;

/**
 * 
 * This class provides a simple interface into jgaap for use in 
 * other software packages and for development of any human interfaces.
 * 
 * Instructions for using the JGAAP API:
 * 
 * First add documents both known and unknown
 * 
 * All other settings can be performed in any order which are setLanguage, addCanonicizer,
 * addEventDriver, addEventCuller, addAnalysisDriver, addDistanceFunction
 * Note: of the settings only one EventDriver and one AnalysisDriver are required to run an experiment
 * 
 * The execute method is then used to start the experiment running
 * 
 * Results are placed in unknown documents to access them simple use the getUnknownDocuments method in the API
 * The results can be retrieved as a List<Pair<String, Double>> this is a sorted list 
 * from most likely to least likely author followed by a score generated based on your settings using the getRawResult method
 * You can also get a Map of Maps of the raw results (Map<EventDriver, Map<AnalysisDriver, List<String, Double>>>) with the getRawResults method
 * They can also be retrieved as a string using either the getFormattedResult or getResult methods.
 * 
 * For examples of how to use the API class see the com.jgaap.ui package for a GUI example
 * or the com.jgaap.backend.CLI class for a command line example
 * 
 * @author Michael Ryan
 * @since 5.0.0
 */
public class API {

	static Logger logger = Logger.getLogger(API.class);
	
	private List<Document> documents;
	private Language language;
	private List<EventDriver> eventDrivers;
	private List<EventCuller> eventCullers;
	private List<AnalysisDriver> analysisDrivers;
	
	private ExecutorService executor;

	private static final API INSTANCE = new API();
	
	private API() {
		documents = new ArrayList<Document>();
		language = new English();
		eventDrivers = new ArrayList<EventDriver>();
		eventCullers = new ArrayList<EventCuller>();
		analysisDrivers = new ArrayList<AnalysisDriver>();
	}
	
	/**
	 * This allows a singleton of the api to be used in the gui 
	 * or any program that needs to access a single copy of JGAAP 
	 * from multiple classes
	 * 
	 * @return a reference to the singleton API
	 */
	public static API getInstance(){
		return INSTANCE;
	}
	
	/**
	 * This is a unique instance of the api to be used when running
	 * bulk experiments and you want to reset everything or if you 
	 * want to thread running more than one experiment at a time 
	 * as in the class com.jgaap.backend.ExperimentEngine
	 * 
	 * @return a unique API instance
	 */
	public static API getPrivateInstance(){
		return new API();
	}

	/**
	 * 
	 * This allows for the addition of documents to the system.
	 * Both Training (known) and Sample (unknown) documents must be provided before running an experiment.
	 * Training Documents are added by providing an author(tag) for them.
	 * Sample documents are added when no author(tag) is given.
	 * 
	 * @param filepath - the system file path or URL to a document 
	 * @param author - the author of this document or the tag being applied to this document, if null or the empty string this document is considered unknown and is one of those classified
	 * @param title - Some means of identifying the document, if null or the empty string are provided a title will be generated from the file name
	 * @return - a reference to the document generated
	 * @throws Exception - if there is a problem loading the document from file web or parsing file format
	 */
	public Document addDocument(String filepath, String author, String title)
			throws Exception {
		Document document = new Document(filepath, author, title);
		return addDocument(document);
	}

	/**
	 * Adds a previously generated document to the jgaap system.
	 * 
	 * @param document - a file that has already been loaded as a Document
	 * @return - a reference to the document generated
	 */
	public Document addDocument(Document document) {
		documents.add(document);
		logger.info("Adding Document "+document.toString());
		return document;
	}

	/**
	 * Removes a document from the system.
	 * 
	 * @param document - a reference to the document that is to be removed
	 * @return - true on success false on failure
	 */
	public Boolean removeDocument(Document document) {
		logger.info("Removing Document "+document.toString());
		return documents.remove(document);
	}

	/**
	 * Removes all documents loaded into the system.
	 */
	public void removeAllDocuments() {
		logger.info("Removing all Documents");
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
		List<String> authorsList = new ArrayList<String>(authors);
		Collections.sort(authorsList);
		return authorsList;
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
	 * @return - a reference to the canonicizer added
	 * @throws Exception - if the canonicizer specified cannot be found or instanced
	 */
	public Canonicizer addCanonicizer(String action) throws Exception {
		Canonicizer canonicizer = Canonicizers.getCanonicizer(action);
		for (Document document : documents) {
			addCanonicizer(canonicizer, document);
		}
		return canonicizer;
	}

	/**
	 * Adds the specified canonicizer to all Documents that have the DocType docType.
	 * 
	 * @param action - the unique string name representing a canonicizer (displayName())
	 * @param docType - The DocType this canonicizer is restricted to 
	 * @return - a reference to the canonicizer added
	 * @throws Exception - if the canonicizer specified cannot be found or instanced
	 */
	public Canonicizer addCanonicizer(String action, Document.Type docType) throws Exception {
		Canonicizer canonicizer = Canonicizers.getCanonicizer(action);
		for (Document document : documents) {
			if (document.getDocType().equals(docType)) {
				addCanonicizer(canonicizer, document);
			}
		}
		return canonicizer;
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
		Canonicizer canonicizer = Canonicizers.getCanonicizer(action);
		return addCanonicizer(canonicizer, document);
	}
	
	/**
	 * Add the Canonicizer specified to the document referenced.
	 * 
	 * @param canonicizer - the canonicizer to add
	 * @param document - the Document to add the canonicizer to
	 * @return - a reference to the canonicizer added
	 */
	public Canonicizer addCanonicizer(Canonicizer canonicizer, Document document) {
		document.addCanonicizer(canonicizer);
		logger.info("Adding Canonicizer "+canonicizer.displayName()+" to Document "+document.toString());
		return canonicizer;
	}
	
	public Canonicizer addCanonicizer(String action, EventDriver eventDriver) throws Exception {
		Canonicizer canonicizer = Canonicizers.getCanonicizer(action);
		return addCanonicizer(canonicizer, eventDriver);
	}
	
	public Canonicizer addCanonicizer(Canonicizer canonicizer, EventDriver eventDriver) {
		eventDriver.addCanonicizer(canonicizer);
		logger.info("Adding Canonicizer "+canonicizer.displayName()+" to EventDriver "+eventDriver.displayName());
		return canonicizer;
	}

	/**
	 * Removes the first instance of the canoniciser corresponding to the action(displayName()) 
	 * from the Document referenced.
	 * 
	 * @param canonicizer - the canonicizer to be removed
	 * @param document - a reference to the Document to remove the canonicizer from
	 */
	public void removeCanonicizer(Canonicizer canonicizer, Document document) {
		document.removeCanonicizer(canonicizer);
	}
	
	public void removeCanonicizer(Canonicizer canonicizer, EventDriver eventDriver) {
		eventDriver.removeCanonicizer(canonicizer);
	}

	/**
	 * Removes the first occurrence of the canonicizer corresponding to the action(displayName())
	 * from every document
	 * 
	 * @param canonicizer - the canonicizer to be removed
	 */
	public void removeCanonicizer(Canonicizer canonicizer) {
		for (Document document : documents) {
			removeCanonicizer(canonicizer, document);
		}
	}

	/**
	 * Removes the first occurrence of the canonicizer from every Document of the DocType docType
	 * 
	 * @param canonicizer - the canonicizer to be removed
	 * @param docType - the DocType to remove the canonicizer from
	 */
	public void removeCanonicizer(Canonicizer canonicizer, Document.Type docType) {
		for (Document document : documents) {
			if (document.getDocType().equals(docType)) {
				removeCanonicizer(canonicizer, document);
			}
		}
	}

	/**
	 * Removes all canonicizers from Documents with the DocType docType
	 * 
	 * @param docType - the DocType to remove canonicizers from
	 */
	public void removeAllCanonicizers(Document.Type docType) {
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
		EventDriver eventDriver = EventDrivers.getEventDriver(action);
		return addEventDriver(eventDriver);
	}
	
	/**
	 * Add an Event Driver which will be used to 
	 * eventify(Generate a List of Events order in the sequence they are found in the document) 
	 * all of the documents
	 * @param eventDriver - the EventDriver to add 
	 * @return - a reference to the added EventDriver
	 */
	public EventDriver addEventDriver(EventDriver eventDriver) {
		eventDrivers.add(eventDriver);
		logger.info("Adding EventDriver "+eventDriver.displayName());
		return eventDriver;
	}

	/**
	 * Removes the Event Driver reference from the system
	 * @param eventDriver - the EventDriver to be removed
	 * @return - true if successful false if failure 
	 */
	public Boolean removeEventDriver(EventDriver eventDriver) {
		logger.info("Removing EventDriver "+eventDriver.displayName());
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
		EventCuller eventCuller = EventCullers.getEventCuller(action);
		eventCullers.add(eventCuller);
		for(EventDriver eventDriver : eventDrivers) {
			addEventCuller(eventCuller, eventDriver);
		}
		return eventCuller;
	}
	
	public EventCuller addEventCuller(String action, EventDriver eventDriver) throws Exception {
		EventCuller eventCuller = EventCullers.getEventCuller(action);
		return addEventCuller(eventCuller, eventDriver);
	}
	
	public EventCuller addEventCuller(EventCuller eventCuller, EventDriver eventDriver) {
		eventDriver.addCuller(eventCuller);
		logger.info("Adding EventCuller "+eventCuller.displayName()+" to "+eventDriver.displayName());
		return eventCuller;
	}

	/**
	 * Remove the supplied EventCuller from the system
	 * 
	 * @param eventCuller - EventCuller to be removed
	 * @return - true if success false if failure
	 */
	public Boolean removeEventCuller(EventCuller eventCuller) {
		logger.info("Removing EventCuller "+eventCuller.displayName());
		eventCullers.remove(eventCuller);
		for(EventDriver eventDriver : eventDrivers){
			eventDriver.removeCuller(eventCuller);
		}
		return true;
	}

	/**
	 * Removes all loaded EventCullers from the system
	 */
	public void removeAllEventCullers() {
		eventCullers.clear();
		for(EventDriver eventDriver : eventDrivers){
			eventDriver.clearCullers();
		}
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
	 * @param action - the unique identifier for a AnalysisDriver (alternately a DistanceFunction)
	 * @return - a reference to the generated Analysis Driver
	 * @throws Exception - If the AnalysisDriver cannot be found or if it cannot be instanced 
	 */
	public AnalysisDriver addAnalysisDriver(String action) throws Exception {
		AnalysisDriver analysisDriver = AnalysisDrivers.getAnalysisDriver(action);
		return addAnalysisDriver(analysisDriver);
	}
	
	public AnalysisDriver addAnalysisDriver(AnalysisDriver analysisDriver) {
		logger.info("Adding AnalysisDriver "+analysisDriver.displayName());
		analysisDrivers.add(analysisDriver);
		return analysisDriver;
	}

	/**
	 * Removed the passed AnalysisDriver from the system
	 * @param analysisDriver - reference to the AnalysisDriver to be removed
	 * @return True if success false if failure
	 */
	public Boolean removeAnalysisDriver(AnalysisDriver analysisDriver) {
		logger.info("Removing AnalysisDriver "+analysisDriver.displayName());
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
	 * @throws Exception - if the AnalysisDriver does not extend NeighborAnalysisDriver or if the DistanceFunction cannot be found the DistanceFunction cannot be instanced
	 */
	public DistanceFunction addDistanceFunction(String action,
			AnalysisDriver analysisDriver) throws Exception {
		DistanceFunction distanceFunction = DistanceFunctions
				.getDistanceFunction(action);
		return addDistanceFunction(distanceFunction, analysisDriver);
	}

	/**
	 * Adds a DistanceFunction to the AnalysisDriver supplied.
	 * Only AnalysisDrivers that extend the NeighborAnalysisDriver can be used
	 * 
	 * @param distanceFunction - the DistanceFunction you want to add
	 * @param analysisDriver - a reference to the AnalysisDriver you want the distance added to
	 * @return - a reference to the generated DistanceFunction
	 */
	public DistanceFunction addDistanceFunction(DistanceFunction distanceFunction, AnalysisDriver analysisDriver) {
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

	/**
	 * Get the current Language JGAAP is set to be working on
	 * @return
	 */
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
		language = Languages.getLanguage(action);
		return language;
	}

	/**
	 * Pipelines the independent aspects of loading and processing a document into separate threads
	 *  
	 * Load the text from disk or the web
	 * Take into account any special treatment based on the language currently selected
	 * Place the text into canonical form using the Canonicizers 
	 * Use the EventDrivers to transform the text into EventSets 
	 * 
	 * @throws Exception
	 */
	private void loadCanonicizeEventify() throws Exception{
		List<Future<Document>> documentsProcessing = new ArrayList<Future<Document>>(documents.size());
		for(final Document document : documents){
			Callable<Document> work = new Callable<Document>() {
				@Override
				public Document call() throws Exception {
					try {
						document.setLanguage(language);
						document.load();
						document.processCanonicizers();
						for (EventDriver eventDriver : eventDrivers) {
							char[] text = document.getText();
							for(Canonicizer canonicizer : eventDriver.getCanonicizers()){
								text = canonicizer.process(text);
							}
							try{
								document.addEventSet(eventDriver,eventDriver.createEventSet(text));
							} catch (EventGenerationException e) {
								logger.error("Could not Eventify with "+eventDriver.displayName()+" on File:"+document.getFilePath()+" Title:"+document.getTitle(),e);
								throw new Exception("Could not Eventify with "+eventDriver.displayName()+" on File:"+document.getFilePath()+" Title:"+document.getTitle(),e);
							}
						}
						document.setText("");
					} catch (LanguageParsingException e) {
						logger.fatal("Could not Parse Language: "+language.displayName()+" on File:"+document.getFilePath()+" Title:"+document.getTitle(),e);
						document.failed();
					} catch (CanonicizationException e) {
						logger.fatal("Could not Canonicize File: "+document.getFilePath()+" Title:"+document.getTitle(),e);
						document.failed();
					} catch (Exception e) {
						logger.fatal("Could not load File: "+document.getFilePath()+" Title:"+document.getTitle(),e);
						document.failed();
					}
					return document;
				}
			};
			documentsProcessing.add(executor.submit(work));
		}

		while(true){
			if(documentsProcessing.size()==0){
				break;
			}else {
				Iterator<Future<Document>> documentIterator = documentsProcessing.iterator();
				while(documentIterator.hasNext()){
					Future<Document> futureDocument = documentIterator.next();
					if(futureDocument.isDone()){
						Document document = futureDocument.get();
						if(document.hasFailed()){
							throw new Exception("One or more documents could not be read / parsed / canonicized Experiment Failed");
						}
						logger.info("Document: "+document.getTitle()+" has finished processing.");
						documentIterator.remove();
					}
				}
			}
		}
	}
	
	/**
	 * Events are culled from EventSets across all Documents on a per EventDriver basis
	 * @throws EventCullingException 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	private void cull() throws EventCullingException, InterruptedException, ExecutionException {
		List<Future<EventDriver>> futureEventDrivers = new ArrayList<Future<EventDriver>>();
		for (EventDriver eventDriver : eventDrivers) {
			if (!eventDriver.getEventCullers().isEmpty()) {
				futureEventDrivers.add(executor.submit(new Culling(eventDriver)));
			}
		}
		while(futureEventDrivers.size() != 0) {
			Iterator<Future<EventDriver>> iterator = futureEventDrivers.iterator();
			while(iterator.hasNext()) {
				Future<EventDriver> futureEventDriver = iterator.next();
				if(futureEventDriver.isDone()){
					EventDriver eventDriver = futureEventDriver.get();
					logger.info("Finished Culling "+eventDriver.displayName());
					iterator.remove();
				}
			}
		}
	}

	/**
	 * All loaded AnalysisDrivers are run over All EventSets comparing the Unknown(sample) to the Known(training) Documents.
	 */
	private void analyze() throws AnalyzeException {
		List<Document> knownDocuments = new ArrayList<Document>();
		List<Document> unknownDocuments = new ArrayList<Document>();
		for (Document document : documents) {
			if (document.isAuthorKnown()) {
				knownDocuments.add(document);
			} else {
				unknownDocuments.add(document);
			}
		}
		for (AnalysisDriver analysisDriver : analysisDrivers) {
			logger.info("Training " + analysisDriver.displayName());
			analysisDriver.train(knownDocuments);
			logger.info("Finished Training "+analysisDriver.displayName());
			List<Future<Document>> futureDocuments = new ArrayList<Future<Document>>();
			if (analysisDriver instanceof ValidationDriver) {
				for (Document knownDocument : knownDocuments) {
					futureDocuments.add(executor.submit(new AnalysisWorker(knownDocument, analysisDriver)));
				}
			} else if (analysisDriver instanceof WEKAAnalysisDriver){
				for (Document unknownDocument : unknownDocuments){
					logger.info("Begining Analyzing: " + unknownDocument.toString());
					unknownDocument.addResult(analysisDriver, analysisDriver.analyze(unknownDocument));
					logger.info("Finished Analyzing: "+unknownDocument.toString());
				}
			} else {
				for (Document unknownDocument : unknownDocuments) {
					futureDocuments.add(executor.submit(new AnalysisWorker(unknownDocument, analysisDriver)));
				}
			}
			//await analysis to finish
			while(futureDocuments.size() != 0){
				Iterator<Future<Document>> iterator = futureDocuments.iterator();
				while(iterator.hasNext()) {
					Future<Document> futureDocument = iterator.next();
					if(futureDocument.isDone()) {
						iterator.remove();
					}
				}
			}
			logger.info("Finished Analysis with "+analysisDriver.displayName());
		}
	}

	/**
	 * Performs the canonicize eventify cull and analyze methods since a strict order has to be enforced when using them 
	 * @throws Exception 
	 */
	public void execute() throws Exception {
		clearData();
		executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		loadCanonicizeEventify();
		cull();
		analyze();
		executor.shutdown();
		executor.awaitTermination(5, TimeUnit.SECONDS);
	}
	
	/**
	 * Removes canonicizors from all documents
	 */
	public void clearCanonicizers() {
		for(Document document : documents){
			document.clearCanonicizers();
		}
	}
	
	/**
	 * Removes all Generated data from a run but leaves all settings untouched
	 */
	public void clearData() {
		for(Document document : documents){
			document.clearEventSets();
			document.clearResults();
		}
	}
	
	private class Culling implements Callable<EventDriver> {
		private EventDriver eventDriver;
		private ExecutorService cullingExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		
		Culling(EventDriver eventDriver) {
			this.eventDriver = eventDriver;
		}
		
		@Override
		public EventDriver call() throws Exception {
			List<EventSet> eventSets = new ArrayList<EventSet>();
			for(Document document : documents){
				eventSets.add(document.getEventSet(eventDriver));
			}
			for(EventCuller culler : eventDriver.getEventCullers()) {
				culler.init(eventSets);
				List<Future<EventSet>> futureEventSets = new ArrayList<Future<EventSet>>(eventSets.size());
				for(EventSet eventSet : eventSets) {
					futureEventSets.add(cullingExecutor.submit(new CullerWorker(eventSet, culler)));
				}
				eventSets.clear();
				for(Future<EventSet> futureEventSet : futureEventSets) {
					eventSets.add(futureEventSet.get());
				}
			}
			cullingExecutor.shutdown();
			for(int i = 0; i < documents.size(); i++) {
				documents.get(i).addEventSet(eventDriver, eventSets.get(i));
			}
			return eventDriver;
		}
	}
	
	private class CullerWorker implements Callable<EventSet> {
		private EventSet eventSet;
		private EventCuller culler;
		
		CullerWorker(EventSet eventSet, EventCuller culler) {
			this.eventSet = eventSet;
			this.culler = culler;
		}
		
		public EventSet call() {
			return culler.cull(eventSet);
		}
	}
	
	private class AnalysisWorker implements Callable<Document> {
		private Document document;
		private AnalysisDriver analysisDriver;
		
		AnalysisWorker(Document document, AnalysisDriver analysisDriver){
			this.document = document;
			this.analysisDriver = analysisDriver;
		}
		
		@Override
		public Document call() throws Exception {
			logger.info("Begining Analyzing: " + document.toString());
			document.addResult(analysisDriver, analysisDriver.analyze(document));
			logger.info("Finished Analyzing: "+document.toString());
			return document;
		}
	}
}
