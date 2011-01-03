package com.jgaap.backend;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jgaap.classifiers.NearestNeighborDriver;
import com.jgaap.generics.*;

/**
 * 
 * @author Michael Ryan
 * @since 5.0
 */
public class API {

	private List<Document> documents;
	private List<EventDriver> eventDrivers;
	private List<EventCuller> eventCullers;
	private List<AnalysisDriver> analysisDrivers;

	private CanonicizerFactory canonicizerFactory;
	private EventDriverFactory eventDriverFactory;
	private EventCullerFactory eventCullerFactory;
	private AnalysisDriverFactory analysisDriverFactory;
	private DistanceFunctionFactory distanceFunctionFactory;
	private LanguageFactory languageFactory;

	public API() {
		documents = new ArrayList<Document>();
		eventDrivers = new ArrayList<EventDriver>();
		eventCullers = new ArrayList<EventCuller>();
		analysisDrivers = new ArrayList<AnalysisDriver>();
		canonicizerFactory = new CanonicizerFactory();
		eventDriverFactory = new EventDriverFactory();
		eventCullerFactory = new EventCullerFactory();
		analysisDriverFactory = new AnalysisDriverFactory();
		distanceFunctionFactory = new DistanceFunctionFactory();
		languageFactory = new LanguageFactory();
	}

	public Document addDocument(String filepath, String author) throws Exception{
		System.out.println("Adding Document :"+filepath);
		Document document = new Document(filepath, author);
		documents.add(document);
		return document;
	}

	public Document addDocument(String filepath, String author, String title)
			throws Exception {
		System.out.println("Adding Document :" + filepath);
		Document document = new Document(filepath, author, title);
		documents.add(document);
		return document;
	}

	public Document addDocument(Document document) {
		documents.add(document);
		return document;
	}

	public Boolean removeDocument(Document document) {
		return documents.remove(document);
	}

	public void removeAllDocuments() {
		documents = new ArrayList<Document>();
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public List<Document> getUnknownDocuments() {
		List<Document> unknownDocuments = new ArrayList<Document>();
		for (Document document : documents) {
			if (!document.isAuthorKnown()) {
				unknownDocuments.add(document);
			}
		}
		return unknownDocuments;
	}

	public List<Document> getKnownDocuments() {
		List<Document> knownDocuments = new ArrayList<Document>();
		for (Document document : documents) {
			if (document.isAuthorKnown()) {
				knownDocuments.add(document);
			}
		}
		return knownDocuments;
	}

	public List<Document> getAuthorDocuments(String author) {
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

	public List<String> getAuthors() {
		Set<String> authors = new HashSet<String>();
		for (Document document : documents) {
			if (document.isAuthorKnown()) {
				authors.add(document.getAuthor());
			}
		}
		return new ArrayList<String>(authors);
	}

	public void addCanonicizer(String action) throws Exception {
		for (Document document : documents) {
			addCanonicizer(action, document);
		}
	}

	public void addCanonicizer(String action, DocType docType) throws Exception {
		for (Document document : documents) {
			if (document.getDocType().equals(docType)) {
				addCanonicizer(action, document);
			}
		}
	}

	public Canonicizer addCanonicizer(String action, Document document)
			throws Exception {
		Canonicizer canonicizer = canonicizerFactory.getCanonicizer(action);
		document.addCanonicizer(canonicizer);
		return canonicizer;
	}

	public void removeCanonicizer(String action, Document document) {
		document.removeCanonicizer(action);
	}

	public void removeCanonicizer(String action) {
		for (Document document : documents) {
			removeCanonicizer(action, document);
		}
	}

	public void removeCanonicizer(String action, DocType docType) {
		for (Document document : documents) {
			if (document.getDocType().equals(docType)) {
				removeCanonicizer(action, document);
			}
		}
	}

	public void removeAllCanonicizers(DocType docType) {
		for (Document document : documents) {
			document.clearCanonicizers();
		}
	}

	public void removeAllCanonicizers() {
		for (Document document : documents) {
			document.clearCanonicizers();
		}
	}

	public EventDriver addEventDriver(String action) throws Exception {
		EventDriver eventDriver = eventDriverFactory.getEventDriver(action);
		eventDrivers.add(eventDriver);
		return eventDriver;
	}

	public Boolean removeEventDriver(EventDriver eventDriver) {
		return eventDrivers.remove(eventDriver);
	}

	public void removeAllEventDrivers() {
		eventDrivers = new ArrayList<EventDriver>();
		for (Document document : documents) {
			document.clearEventSets();
		}
	}

	public List<EventDriver> getEventDrivers() {
		return eventDrivers;
	}

	public EventCuller addEventCuller(String action) throws Exception {
		EventCuller eventCuller = eventCullerFactory.getEventCuller(action);
		eventCullers.add(eventCuller);
		return eventCuller;
	}

	public Boolean removeEventCuller(EventCuller eventCuller) {
		return eventCullers.remove(eventCuller);
	}

	public void removeAllEventCullers() {
		eventCullers = new ArrayList<EventCuller>();
	}

	public List<EventCuller> getEventCullers() {
		return eventCullers;
	}

	public AnalysisDriver addAnalysisDriver(String action) throws Exception {
		AnalysisDriver analysisDriver;
		try {
			analysisDriver = analysisDriverFactory.getAnalysisDriver(action);
		} catch (Exception e) {
			analysisDriver = new NearestNeighborDriver();
			addDistanceFunction(action, analysisDriver);
		}
		analysisDrivers.add(analysisDriver);
		return analysisDriver;
	}

	public Boolean removeAnalysisDriver(AnalysisDriver analysisDriver) {
		return analysisDrivers.remove(analysisDriver);
	}

	public void removeAllAnalysisDrivers() {
		analysisDrivers = new ArrayList<AnalysisDriver>();
	}

	public DistanceFunction addDistanceFunction(String action,
			AnalysisDriver analysisDriver) throws Exception {
		DistanceFunction distanceFunction = distanceFunctionFactory
				.getDistanceFunction(action);
		((NeighborAnalysisDriver) analysisDriver).setDistance(distanceFunction);
		return distanceFunction;
	}

	public List<AnalysisDriver> getAnalysisDrivers() {
		return analysisDrivers;
	}

	public Language setLanguage(String action) throws Exception {
		Language language = languageFactory.getLanguage(action);
		language.apply();
		List<Document> tmpDocuments = new ArrayList<Document>(documents); 
		for(Document document : tmpDocuments){
			addDocument(document.getFilePath(), document.getAuthor(), document.getTitle());
			removeDocument(document);
		}
		return language;
	}

	private void canonicize() throws InterruptedException {
		List<Thread> threads = new ArrayList<Thread>();
		for (final Document document : documents) {
			Thread t = new Thread(new Runnable() {
				public void run() {
					document.processCanonicizers();
				}
			});
			threads.add(t);
			t.start();
		}
		for (Thread t : threads) {
			t.join();
		}
	}

	private void eventify() throws InterruptedException {
		List<Thread> threads = new ArrayList<Thread>();
		for (final Document document : documents) {
			Thread t = new Thread(new Runnable() {
				public void run() {
					for (EventDriver eventDriver : eventDrivers) {
						document.addEventSet(eventDriver,
								eventDriver.createEventSet(document));
					}
				}
			});
			threads.add(t);
			t.start();
		}
		for (Thread t : threads) {
			t.join();
		}
	}

	private void cull() throws InterruptedException {
		for (EventDriver eventDriver : eventDrivers) {
			List<EventSet> eventSets = new ArrayList<EventSet>();
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
		}
	}

	private void analyze() throws InterruptedException {
		List<Thread> threads = new ArrayList<Thread>();
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
				Thread t = new Thread(new Runnable() {
					public void run() {
						analysisDriver.analyze(unknownDocument, knownDocuments);
					}
				});
				threads.add(t);
				t.start();
			}
		for (Thread t : threads) {
			t.join();
		}
	}

	public void execute() throws InterruptedException {
		canonicize();
		eventify();
		cull();
		analyze();
	}

	public void clearResults() {
		List<Document> documents = getUnknownDocuments();
		for (Document document : documents) {
			document.clearResults();
		}
	}

	public List<Canonicizer> getAllCanonicizers() {
		return AutoPopulate.getCanonicizers();
	}

	public List<EventDriver> getAllEventDrivers() {
		return AutoPopulate.getEventDrivers();
	}

	public List<EventCuller> getAllEventCullers() {
		return AutoPopulate.getEventCullers();
	}

	public List<AnalysisDriver> getAllAnalysisDrivers() {
		return AutoPopulate.getAnalysisDrivers();
	}

	public List<DistanceFunction> getAllDistanceFunctions() {
		return AutoPopulate.getDistanceFunctions();
	}

	public List<Language> getAllLanguages() {
		return AutoPopulate.getLanguages();
	}

}
