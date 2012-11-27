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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jgaap.languages.English;

/**
 * Code for storing and processing individual documents of any type.
 */
public class Document extends Parameterizable {

	private String author;
	private String filepath;
	private String title;
	private char[] text;
	private int size;
	private DocType docType;
	private Language language;
	private List<EventCuller> eventCullers;
	private List<Canonicizer> canonicizers;
	private Map<EventDriver, EventSet> eventSets;
	private Map<AnalysisDriver, List<Pair<String, Double>>> results;
	private boolean failed = false;
	
	public Document() {
		filepath = "";
		title = "";
		size = 0;
		canonicizers = new ArrayList<Canonicizer>();
		eventSets = new HashMap<EventDriver, EventSet>();
		results = new HashMap<AnalysisDriver, List<Pair<String,Double>>>();
		eventCullers = new ArrayList<EventCuller>();
		docType = DocType.GENERIC;
		this.language = new English();
	}

	public Document(String filepath, String author) throws Exception {
		this(filepath, author, getTitleFromPath(filepath));
	}

	/**
	 * Copy constructor. Can be used to break object references and protect a
	 * Document instance from being modified by other classes.
	 * 
	 * @param document
	 *            The document to be copied
	 */
	public Document(Document document) {
		this.author = document.author;
		this.canonicizers = new ArrayList<Canonicizer>(document.canonicizers);
		this.eventCullers = new ArrayList<EventCuller>(document.eventCullers);
		this.docType = document.docType;
		this.eventSets = new HashMap<EventDriver, EventSet>(document.eventSets);
		this.results = new HashMap<AnalysisDriver, List<Pair<String,Double>>>(document.results);
		this.filepath = document.filepath;
		this.text = Arrays.copyOf(document.text, document.text.length);
		this.size = document.size;
		this.title = document.title;
		this.language = document.getLanguage();
	}

	/**
	 * Constructor that takes three arguments: file path, file author, file
	 * title
	 * 
	 * @param filepath
	 *            The path to the file
	 * @param author
	 *            The author of the document
	 * @param title
	 *            The title of the document
	 * @throws Exception
	 */
	public Document(String filepath, String author, String title){
		this.author = author;
		if (author != null && author.equals("")) // unknown authors are null
			this.author = null;
		this.filepath = filepath;
		this.title = title;
		if (title == null || title.equals(""))
			this.title = getTitleFromPath(filepath);
		this.docType = DocumentHelper.getDocType(filepath);
		this.language = new English();
		this.eventSets = new HashMap<EventDriver, EventSet>();
		this.canonicizers = new ArrayList<Canonicizer>();
		this.eventCullers = new ArrayList<EventCuller>();
		results = new HashMap<AnalysisDriver, List<Pair<String,Double>>>();
	}
	
	public void load() throws Exception {
		if (this.docType != DocType.DATABASE) {
			this.text = DocumentHelper.loadDocument(filepath, language.getCharset());
			this.size = this.text.length;
			if (this.size == 0) {
				throw new Exception("Document: "+this.filepath+" was empty.");
			}
		}
	}

	/**
	 * Takes a file path and returns only the file name.
	 * 
	 * @param filePath
	 *            the full path to the file
	 * @return A document title derived from the file path.
	 */
	private static String getTitleFromPath(String filePath) {
		String[] split = filePath.split("[\\\\[\\/]]");
		return split[split.length - 1];
	}

	public void readStringText(String text) {
		this.text = text.toCharArray();
		size = this.text.length;
	}
	
	public void setText(char[] text){
		this.text = text;
		size = text.length;
	}

	/**
	 * Prints the text of the document to std out
	 * 
	 */
	public void print() {
		System.out.println(stringify());
	}

	/** Retrieves the author of the current document **/
	public String getAuthor() {
		return author;
	}

	/** Returns the docType of the current document **/
	public DocType getDocType() {
		return docType;
	}

	/** Returns the full filepath of the current document **/
	public String getFilePath() {
		return filepath;
	}
	
	/**
	 * Sets the filepath of the current document
	 */
	public void setFilePath(String filePath) {
		this.filepath = filePath;
	}
	
	/**
	 * @deprecated use getText()
	 * Returns text with preprocessing done. Preprocessing can include stripping
	 * whitespace or normalizing the case
	 **/
	@Deprecated
	public char[] getProcessedText() {
			return Arrays.copyOf(text, text.length);
	}
	
	/**
	 * The text of the document as a character array
	 * 
	 * This is only preprocessed if processCanonicizers() has been run
	 * 
	 * @return 
	 */
	public char[] getText() {
		return text;
	}

	/**
	 * Returns the size of the document. Size is determined by the number of
	 * characters plus whitespace
	 **/
	public int getSize() {
		return size;
	}

	/** Returns the title of the current document **/
	public String getTitle() {
		return title;
	}

	/** Sets the author of the current document **/
	public void setAuthor(String author) {
		this.author = author;
	}

	/** Sets the docType of the current document **/
	public void setDocType(DocType docType) {
		this.docType = docType;
	}

	/** Sets the title of the current document **/
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Clear the list of canonicizers associated with this Document.
	 */
	public void clearCanonicizers() {
		canonicizers.clear();
	}

	/**
	 * Add a Canonicizer to the internal list maintained by this Document.
	 * 
	 * @param canonicizer
	 *            A new canonicizer to add to the list
	 */
	public void addCanonicizer(Canonicizer canonicizer) {
		canonicizers.add(canonicizer);
	}

	/**
	 * Remove a Canonicizer from the internal list maintained by this Document.
	 * 
	 * @param canonicizer
	 *            A canonicizer to remove from the list
	 * @return Returns true if a matching Canonicizer was found and removed
	 */
	public boolean removeCanonicizer(Canonicizer canonicizer) {
		return canonicizers.remove(canonicizer);
	}

	/**
	 * Get all the canonicizers associated with this Document.
	 * 
	 * return A vector of canonicizers associated with this document
	 */
	public List<Canonicizer> getCanonicizers() {
		return new ArrayList<Canonicizer>(canonicizers);
	}

	/**
	 * Take the list of canonicizers associated with this document and apply
	 * them to the document one by one, in the same order they were added.
	 */
	public void processCanonicizers() throws LanguageParsingException, CanonicizationException {
		if (language.isParseable()){
			text = language.parseLanguage(stringify());
		}
		for (Canonicizer canonicizer : canonicizers) {
			text = canonicizer.process(text);
		}
	}

	/**
	 * Adds a mapping of an EventDriver used on this Document to the EventSet generated by using the EventDriver on it
	 * @param eventDriver
	 * @param eventSet
	 */
	public void addEventSet(EventDriver eventDriver, EventSet eventSet) {
		eventSet.setAuthor(author);
		eventSet.setDocumentName(getFilePath());
		eventSets.put(eventDriver, eventSet);
	}

	/**
	 * Returns a map of all EventDrivers used on this Document to the EventSets generated by them
	 * @return EventDrivers to EventSets
	 */
	public Map<EventDriver, EventSet> getEventSets() {
		return eventSets;
	}

	/**
	 * Get the EventSet generated by the passed eventDriver
	 * 
	 * @param eventDriver 
	 * @return the eventset generated by running the passed eventDriver on this document
	 */
	public EventSet getEventSet(EventDriver eventDriver) {
		return eventSets.get(eventDriver);
	}

	/**
	 * Removes all EventSets generated from this document
	 */
	public void clearEventSets() {
		eventSets.clear();
	}

	/** 
	 * add the result of an analysis to the document
	 * 
	 * @param analysisDriver
	 * @param eventDriver
	 * @param list
	 */
	public void addResult(AnalysisDriver analysisDriver, List<Pair<String, Double>> list) {
		results.put(analysisDriver, list);
	}
	
	/**
	 * 
	 * @param analysisDriver
	 * @param eventDriver
	 * @return
	 */
	public List<Pair<String, Double>> getRawResult(AnalysisDriver analysisDriver){
		return results.get(analysisDriver);
	}
	
	/**
	 * Generates a formatted report for the analysisDriver and eventDriver specified 
	 * @param analysisDriver
	 * @param eventDriver
	 * @return Report of analysis run on this document
	 */
	public String getFormattedResult(AnalysisDriver analysisDriver) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(getTitle() + " ");
		buffer.append(getFilePath() + "\n");
		buffer.append("Canonicizers: \n");
		if (canonicizers.isEmpty()) {
			buffer.append("none");
		} else {
			for (Canonicizer canonicizer : canonicizers) {
				buffer.append(canonicizer.displayName()).append(", ");
			}
			buffer.delete(buffer.length()-2, buffer.length()-1);
		}
		buffer.append("\n");
		buffer.append("EventDrivers: \n");
		for(EventDriver eventDriver : eventSets.keySet()){
			buffer.append(eventDriver.displayName()).append(" ").append(eventDriver.getParameters());
			for(EventCuller eventCuller : eventDriver.getEventCullers()){
				buffer.append("\n\t").append(eventCuller.displayName()).append(" ").append(eventDriver.getParameters());
			}
			buffer.append("\n");
		}
		buffer.append("Analysis: \n").append(analysisDriver.displayName()).append(" ").append(analysisDriver.getParameters());
		buffer.append("\n");
		int count = 0; // Keeps a relative count (adjusted for ties)
		int fullCount = 0; // Keeps the absolute count (does not count ties)
		Double lastResult = Double.NaN;
		List<Pair<String, Double>> results = getRawResult(analysisDriver);
		if(results == null){
			return null;
		}
		for (Pair<String, Double> result : results) {
			fullCount++;
			// Account for ties
			if (!result.getSecond().equals(lastResult)) {
				count = fullCount;
			}
			lastResult = result.getSecond();
			buffer.append(count + ". " + result.getFirst() + " " + result.getSecond() + "\n");
		}
		buffer.append("\n\n");
		return buffer.toString();
	}

	/**
	 * Generates and returns a formatted report of all results 
	 * @return
	 */
	public String getResult() {
		StringBuilder buffer = new StringBuilder();
		Set<AnalysisDriver> analysisDrivers = results.keySet();
		for (AnalysisDriver analysisDriver : analysisDrivers) {
			buffer.append(getFormattedResult(analysisDriver));
		}
		return buffer.toString();
	}
	
	public  Map<AnalysisDriver, List<Pair<String, Double>>> getRawResults() { 
		return results;
	}

	public void clearResults() {
		results.clear();
	}

	/**
	 * Indicates whether this document has a known author or not.
	 * 
	 * @return boolean value indicating whether the author of this document is
	 *         known
	 */
	public boolean isAuthorKnown() {
		return (author != null);
	}

	/**
	 * Convert processed document into one really long string.
	 **/
	public String stringify() {
		return new String(text);
	}

	@Override
	public String toString() {
		String string = this.getTitle()+" (";
		if(isAuthorKnown()){
			string += this.getAuthor()+")";
		} else {
			string += "unknown)";
		}
		return string;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
	
	public void failed(){
		failed = true;
	}
	
	public boolean hasFailed(){
		return failed;
	}
}
