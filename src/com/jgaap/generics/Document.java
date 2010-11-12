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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Vector;

import javax.swing.text.*;
import javax.swing.text.html.*;

import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.hwpf.*;
import org.apache.poi.hwpf.extractor.*;

import com.jgaap.jgaapConstants;
import com.jgaap.gui.jgaapGUI;

/** 
 * Code for storing and processing individual documents of any type. 
 */
public class Document extends Parameterizable {

	public String displayName(){
	    return "Document";
	}

	public String tooltipText(){
	    return "For storing and processing individual documents of any type";
	}

	public boolean showInGUI(){
	    return false;
	}

    private String           author;
    private String           filepath;
    private String           title;
    private char[]           rawText;
    private int              size;
    private DocType			 docType;
    
    /**
     * List of possible document types.
     */
    public static enum DocType { PDF, URL, DOC, HTML, GENERIC };
    
    /**
     * This is a list of canonicizers that will be applied to this document.
     */
    private Vector<Canonicizer> canonicizers = new Vector<Canonicizer>();

    /** Contains current processed text **/
    public Vector<Character> procText;

    /** Create document with no text, setting author later **/
    public Document() {
        author = null;
        filepath = null;
        docType = DocType.GENERIC;
        title = "null title";
        size = 0;
        rawText = null;
        procText = null;
        canonicizers.clear();
    }

    /** 
     * Create and read in document with only the filepath.
     * 
     * @param filepath
     * 				file path of the document to be read
     */
    public Document(String filepath) {
        this( filepath, null );
    }

    /** 
     * Create and read in a document with a given filepath and author.
     * 
     * @param filepath
     * 			file path of the document to be read
     * @param author
     * 			author of the document; a null value indicates an unknown author
     */
    public Document(String filepath, String author) {
    	this( filepath, author, getTitleFromPath(filepath) );
    }
    
    /**
     * Copy constructor. Can be used to break object references and protect a Document
     * instance from being modified by other classes.
     * 
     * @param document The document to be copied
     */
    public Document( Document document ){
    	this( document.getFilePath(), document.getAuthor(), document.getTitle() );
    }
    
    /**
     * Constructor that takes three arguments: file path, file author, file title
     * 
     * @param filepath The path to the file
     * @param author The author of the document
     * @param title The title of the document
     */
    public Document( String filepath, String author, String title )
    {
    	this.author = author;
    	if( author.equals("") ) // unknown authors are null
    		this.author = null;
        this.filepath = filepath;
        this.title = title;
        if( title.equals("") )
        	this.title = getTitleFromPath(filepath);
        if (filepath.startsWith("http://") || filepath.startsWith("https://")) {
            docType = DocType.URL;
            readURLText(filepath);
        } else if (filepath.endsWith(".pdf")) {
        	docType = DocType.PDF;
            loadPDF(filepath);
        } else if (filepath.endsWith(".doc")) {
        	docType = DocType.DOC;
            loadMSWord(filepath);
        } else if (filepath.endsWith(".htm") || filepath.endsWith(".html")) {
        	docType = DocType.HTML;
            loadHTML(filepath);
        }        
        /*
         * The POI MSWord reader seems to be broken. Code left here for
         * reference only. else if(filepath.endsWith(".doc")) {
         * System.out.println("Doc file"); try { POIFSFileSystem doc = new
         * POIFSFileSystem(new FileInputStream(filepath)); WordExtractor
         * extractor = new WordExtractor(doc);
         * System.out.println(extractor.getText()); procText = new
         * Vector<Character>(); char[] origText =
         * extractor.getText().toCharArray(); for(Character c : origText) {
         * procText.add(c); } } catch(Exception e) { e.printStackTrace(); } }
         */
        else {
            docType = DocType.GENERIC;
            readLocalText(filepath);
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
    
    /**
     * Extracts text from a PDF and stores it in the document. Takes an input
     * stream rather than a file name.
     * 
     * @param filesInputStream
     *            An input stream pointing to a PDF file.
     */
    private void loadPDF(InputStream filesInputStream) {
        PDDocument doc;
        try {
            doc = PDDocument.load(filesInputStream);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setSortByPosition(false);
            procText = new Vector<Character>();
            char[] origText = pdfStripper.getText(doc).toCharArray();
            for (Character c : origText) {
                procText.add(c);
            }

            doc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Extracts text from a PDF and stores it in the document.
     * 
     * @param filepath
     *            The filepath of the PDF to be read.
     */
    private void loadPDF(String filepath) {
        PDDocument doc;
        try {
            doc = PDDocument.load(new FileInputStream(filepath));
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setSortByPosition(false);
            procText = new Vector<Character>();
            char[] origText = pdfStripper.getText(doc).toCharArray();
            for (Character c : origText) {
                procText.add(c);
            }
            doc.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Extracts text from an HTML document and stores it in the document.
     * 
     * @param filepath The filepath of the HTML document to be read.
     */
    private void loadHTML(String filepath) {
        System.out.println("HTML Document");
        try {
            EditorKit kit = new HTMLEditorKit();
            javax.swing.text.Document doc = kit.createDefaultDocument();
            kit.read((InputStream)(new FileInputStream(filepath)), doc, 0);
            procText = new Vector<Character>();
            System.out.println(doc.getText(0, doc.getLength()));
            char[] origText = doc.getText(0, doc.getLength()).toCharArray();
            for (Character c : origText) {
                procText.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Extracts text from an HTML document and stores it in the document.
     * 
     * @param filesInputStream An input stream pointing to the HTML document to be read.
     */
    private void loadHTML(InputStream filesInputStream) {
        System.out.println("HTML Document");
        try {
            EditorKit kit = new HTMLEditorKit();
            javax.swing.text.Document doc = kit.createDefaultDocument();
            kit.read(filesInputStream, doc, 0);
            procText = new Vector<Character>();
            System.out.println(doc.getText(0, doc.getLength()));
            char[] origText = doc.getText(0, doc.getLength()).toCharArray();
            for (Character c : origText) {
                procText.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Extracts text from a Word document and stores it in the document.
     * 
     * @param filepath The filepath of the Word document to be read.
     */
    private void loadMSWord(String filepath) {
        System.out.println("Word Document");
        procText = new Vector<Character>();
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filepath));
            HWPFDocument doc = new HWPFDocument(fs);
            WordExtractor we = new WordExtractor(doc);
            System.out.println(we.getText());
            char[] origText = we.getText().toCharArray();
            for (Character c : origText) {
                procText.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Extracts text from a Word document and stores it in the document.
     * 
     * @param filesInputStream An input stream pointing to the Word document to be read.
     */
    private void loadMSWord(InputStream filesInputStream) {
        System.out.println("Word Document");
        try {
            POIFSFileSystem fs = new POIFSFileSystem(filesInputStream);
            HWPFDocument doc = new HWPFDocument(fs);
            WordExtractor we = new WordExtractor(doc);
            System.out.println(we.getText());
            char[] origText = we.getText().toCharArray();
            for (Character c : origText) {
                procText.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void print() {
        for (Character c : procText) {
            System.out.print(c);
        }
    }

    /**
     * Reads text from a local file. Exceptions are not caught by name. Rather,
     * all exceptions are handled through just printing the error message to
     * stdout. This should probably be changed for robustness. The raw text of
     * the file is stored for quick access in an array.
     **/
    /*
     * MVR - 4/7/08 readLocalText has been changed so that it now uses a
     * InputStreamReader wrapped in a BufferedReader to handle the
     * FileInputStream. The updated readLocalText also uses the parameterizable
     * class to check what charset is being used and then read the files in the
     * chosen charset.
     */
    public void readLocalText(String filepath) {
        int c, ctr = 0;
        try {
            File input = new File(filepath);
            size = (int) input.length();
            rawText = new char[size];
            procText = new Vector<Character>();
            FileInputStream fis = new FileInputStream(input);
            BufferedReader br;
            if (jgaapConstants.globalParams.getParameter("charset").equals("")) {
                br = new BufferedReader(new InputStreamReader(fis));
            } else {
                br = new BufferedReader(new InputStreamReader(fis,
                        jgaapConstants.globalParams.getParameter("charset")));
            }
            while ((c = br.read()) != -1) {
                rawText[ctr++] = (char) c;
                procText.add(new Character((char) c));
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void readStringText(String text){
    	procText = new Vector<Character>();
    	for(int i=0;i<text.length();i++){
    		procText.add(text.charAt(i));
    	}
    }
    
    /**
     * Reads text from a local file. Exceptions are not caught by name. Rather,
     * all exceptions are handled through just printing the error message to
     * stdout. This should probably be changed for robustness. The raw text of
     * the file is stored for quick access in an array. Modeled from
     * readLocalText PMJ 10/25/08
     **/

    public void readURLText(String url) {
        int c, ctr = 0;
        try {
            URL input = new URL(url);
            InputStream ir = input.openStream();
            BufferedReader br;

            procText = new Vector<Character>();

            if (url.endsWith(".pdf")) {
                loadPDF(input.openStream());
                return;
            } else if (filepath.endsWith(".htm") || filepath.endsWith(".html")) {
                loadHTML(input.openStream());
                return;
            } else if (filepath.endsWith(".doc")) {
                loadMSWord(input.openStream());
                return;
            }

            if (jgaapConstants.globalParams.getParameter("charset").equals("")) {
                br = new BufferedReader(new InputStreamReader(ir));
            } else {
                br = new BufferedReader(new InputStreamReader(ir,
                        jgaapConstants.globalParams.getParameter("charset")));
            }
            while ((c = br.read()) != -1) {
                procText.add(new Character((char) c));
            }

            size = procText.size();
            rawText = new char[size];
            for (int i = 0; i < size; i++) {
                rawText[ctr++] = procText.elementAt(i);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
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
    public Vector<Character> getProcessedText() {
        return procText;
    }
  
    public void setProcessedText(Vector<Character>procText){
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
		
		for( Canonicizer canonicizer : canonicizers ) {
			procText = canonicizer.process(procText);
		}
		jgaapGUI.incProgress();
	
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
            t.append((char) procText.elementAt(i));
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
