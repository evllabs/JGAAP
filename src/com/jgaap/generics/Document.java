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
package com.jgaap.generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.jgaap.jgaapConstants;
import com.jgaap.gui.jgaapGUI;

/** 
 * Code for storing and processing individual documents of any type. 
 */
public class Document extends Parameterizable {

	@Override
	public String displayName(){
	    return "Document";
	}

	@Override
	public String tooltipText(){
	    return "For storing and processing individual documents of any type";
	}

	@Override
	public boolean showInGUI(){
	    return false;
	}

    private String           author;
    private String           filepath;
    private String           title;
    private List<Character>  rawText;
    private int              size;
    private DocType			 docType;
    private List<EventSet>   eventSets = new ArrayList<EventSet>();
    
    /**
     * List of possible document types.
     */
    public static enum DocType { PDF, URL, DOC, HTML, GENERIC };
    
    /**
     * This is a list of canonicizers that will be applied to this document.
     */
    private List<Canonicizer> canonicizers = new ArrayList<Canonicizer>();

    /** Contains current processed text **/
    private List<Character> procText = new ArrayList<Character>();

    /** Create document with no text, setting author later **/
    public Document() {
        author = null;
        filepath = null;
        docType = DocType.GENERIC;
        title = "null title";
        size = 0;
        rawText = new ArrayList<Character>();
        procText = new ArrayList<Character>();
        eventSets = new ArrayList<EventSet>();
        canonicizers.clear();
    }

    /** 
     * Create and read in document with only the filepath.
     * 
     * @param filepath
     * 				file path of the document to be read
     * @throws Exception 
     */
    public Document(String filepath) throws Exception {
        this( filepath, null );
    }

    /** 
     * Create and read in a document with a given filepath and author.
     * 
     * @param filepath
     * 			file path of the document to be read
     * @param author
     * 			author of the document; a null value indicates an unknown author
     * @throws Exception 
     */
    public Document(String filepath, String author) throws Exception {
    	this( filepath, author, getTitleFromPath(filepath) );
    }
    
    /**
     * Copy constructor. Can be used to break object references and protect a Document
     * instance from being modified by other classes.
     * 
     * @param document The document to be copied
     */
    public Document( Document document ){
    	this.author = new String(document.author);
    	this.canonicizers = new ArrayList<Canonicizer>(document.canonicizers);
    	this.docType = document.docType;
    	this.eventSets = new ArrayList<EventSet>(document.eventSets);
    	this.filepath = new String(document.filepath);
    	this.procText = new ArrayList<Character>(document.procText);
    	this.rawText = new ArrayList<Character>(document.rawText);
    	this.size = document.size;
    	this.title = new String(document.title);
    }
    
    /**
     * Constructor that takes three arguments: file path, file author, file title
     * 
     * @param filepath The path to the file
     * @param author The author of the document
     * @param title The title of the document
     * @throws Exception 
     */
    public Document( String filepath, String author, String title ) throws Exception
    {
    	this.author = author;
    	if( author!=null && author.equals("") ) // unknown authors are null
    		this.author = null;
        this.filepath = filepath;
        this.title = title;
        if( title.equals("") )
        	this.title = getTitleFromPath(filepath);
        this.rawText = DocumentHelper.loadDocument(filepath);
        this.docType = DocumentHelper.getDocType(filepath);
        this.size = this.rawText.size();
        if(this.size==0){
        	throw new Exception("Empty Document Error");
        }
    }
    
    /**
     * Takes a file path and returns only the file name.
     * 
     * @param filePath 
     * 				the full path to the file
     * @return A document title derived from the file path.
     */
    private static String getTitleFromPath( String filePath ){
    	String[] split = filePath.split( "[\\\\[\\/]]" );
    	return split[ split.length - 1 ];    	
    }
    
    public void readStringText(String text){
    	rawText = new ArrayList<Character>();
    	for(int i=0;i<text.length();i++){
    		rawText.add(text.charAt(i));
    	}
    }
    
    public void print() {
        for (Character c : rawText) {
            System.out.print(c);
        }
    }

   
    
    // --------------------------------------------------
    // ------------ Getters and Setters -----------------
    // --------------------------------------------------
    
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
     * Returns text with preprocessing done. Preprocessing can include stripping
     * whitespace or normalizing the case
     **/
    public List<Character> getProcessedText() {
        return procText;
    }
  
    public void setProcessedText(List<Character>procText){
    	this.procText = procText;
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
    public void setDocType(DocType t) {
        docType = t;
    }

    /** Sets the title of the current document **/
    public void setTitle(String t) {
        title = t;
    }
    
    /**
     * Clear the list of canonicizers associated with this Document.
     */
    public void clearCanonicizers(){
    	canonicizers.clear();
    }
    
    /**
     * Add a Canonicizer to the internal list maintained by this Document.
     * 
     * @param newCanonicizer A new canonicizer to add to the list
     */
    public void addCanonicizer( Canonicizer newCanonicizer ){
    	// Don't allow duplicate canonicizers in the list
    	//if( !canonicizers.contains( newCanonicizer ) )
    		canonicizers.add( newCanonicizer );
    }
    
    /**
     * Remove a Canonicizer from the internal list maintained by this Document.
     * 
     * @param canonicizer A canonicizer to remove from the list
     * @return Returns true if a matching Canonicizer was found and removed
     */
    public boolean removeCanonicizer( Canonicizer canonicizer ){
    	return canonicizers.remove( canonicizer );
    }
    
    /**
     * Set all the canonicizers associated with this Document at once.
     * 
     * @param newCanonicizers A vector of canonicizers to be applied to this document
     */
    public void setCanonicizers( Vector<Canonicizer> newCanonicizers ){
    	// defensively copy the new array, rather than just take a reference to it
    	canonicizers = new Vector<Canonicizer>( newCanonicizers );
    }
    
    /**
     * Get all the canonicizers associated with this Document.
     * 
     * return A vector of canonicizers associated with this document
     */
    public Vector<Canonicizer> getCanonicizers(){
    	// defensively copy the internal array, rather than give out a reference to it
    	return new Vector<Canonicizer>( canonicizers );
    }
    
    /**
     * Take the list of canonicizers associated with this document and apply them to 
     * the document one by one, in the same order they were added.
     */
    public void processCanonicizers(){
    	procText = new ArrayList<Character>();
		procText.addAll(rawText);
		if(jgaapConstants.globalObjects.containsKey("language")){
			Language language = (Language) jgaapConstants.globalObjects.get("language");
			if(language.isParseable())
				procText = language.parseLanguage(stringify());
		}
		for( Canonicizer canonicizer : canonicizers ) {
			procText = canonicizer.process(procText);
			jgaapGUI.incProgress();
		}
	}
    
    public void addEventSet(EventSet eventSet){
    	eventSet.setAuthor(author);
    	eventSet.setDocumentName(getFilePath());
    	eventSets.add(eventSet);
    }
	public List<EventSet> getEventSets(){
		return eventSets;
	}
    /**
     * Indicates whether this document has a known author or not.
     * 
     * @return boolean value indicating whether the author of this document is known
     */
    public boolean isAuthorKnown(){
    	return (author != null);
    }

    /**
     * Convert processed document into one really long string.
     **/
    public String stringify() {
        /*
         * JN 3/11/08 - Changed the String to a StringBuffer. This has reduced
         * the running time from several minutes to instantaneous.
         */
        StringBuffer t = new StringBuffer(procText.size());
        for (int i = 0; i < procText.size(); i++) {
            t.append((char) procText.get(i));
        }
        return t.toString();
    }

    @Override
    public String toString() {
        String t = new String();
        t =  "Title:  " + title + "\n";
        t += "Path:   " + filepath + "\n";
        t += "Author: " + author + "\n";
        t += "Canons: " + getCanonicizers() + "\n";
        return t;
    }
}
