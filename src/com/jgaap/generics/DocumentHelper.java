// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
package com.jgaap.generics;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

import com.jgaap.jgaapConstants;

class DocumentHelper {

	static char[] loadDocument(String filepath) throws IOException,
			BadLocationException {
		if (filepath.startsWith("http://") || filepath.startsWith("https://")) {
			return readURLText(filepath);
		} else if (filepath.endsWith(".pdf")) {
			return loadPDF(filepath);
		} else if (filepath.endsWith(".doc")) {
			return loadMSWord(filepath);
		} else if (filepath.endsWith(".htm") || filepath.endsWith(".html")) {
			return loadHTML(filepath);
		} else {
			return readLocalText(filepath);
		}
	}

	static DocType getDocType(String filepath) {
		if (filepath.startsWith("http://") || filepath.startsWith("https://")) {
			return DocType.URL;
		} else if (filepath.endsWith(".pdf")) {
			return DocType.PDF;
		} else if (filepath.endsWith(".doc")) {
			return DocType.DOC;
		} else if (filepath.endsWith(".htm") || filepath.endsWith(".html")) {
			return DocType.HTML;
		} else {
			return DocType.GENERIC;
		}
	}

	/**
	 * Extracts text from a PDF and stores it in the document. Takes an input
	 * stream rather than a file name.
	 * 
	 * @param filesInputStream
	 *            An input stream pointing to a PDF file.
	 * @throws IOException
	 */
	static private char[] loadPDF(InputStream filesInputStream)
			throws IOException {
		PDDocument doc;
		doc = PDDocument.load(filesInputStream);
		PDFTextStripper pdfStripper = new PDFTextStripper();
		pdfStripper.setSortByPosition(false);
		char[] origText = pdfStripper.getText(doc).toCharArray();
		doc.close();

		return origText;
	}

	/**
	 * Extracts text from a PDF and stores it in the document.
	 * 
	 * @param filepath
	 *            The filepath of the PDF to be read.
	 * @throws IOException
	 */
	static private char[] loadPDF(String filepath) throws IOException {
		return loadPDF(new FileInputStream(filepath));
	}

	/**
	 * Extracts text from an HTML document and stores it in the document.
	 * 
	 * @param filepath
	 *            The filepath of the HTML document to be read.
	 * @throws BadLocationException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	static private char[] loadHTML(String filepath)
			throws FileNotFoundException, IOException, BadLocationException {
		return loadHTML(new FileInputStream(filepath));
	}

	/**
	 * Extracts text from an HTML document and stores it in the document.
	 * 
	 * @param filesInputStream
	 *            An input stream pointing to the HTML document to be read.
	 * @throws BadLocationException
	 * @throws IOException
	 */
	static private char[] loadHTML(InputStream filesInputStream)
			throws IOException, BadLocationException {
		System.out.println("HTML Document");
		EditorKit kit = new HTMLEditorKit();
		HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
		doc.putProperty("IgnoreCharsetDirective", new Boolean(true));
		kit.read(filesInputStream, doc, 0);
		char[] origText = doc.getText(0, doc.getLength()).toCharArray();

		return origText;
	}

	/**
	 * Extracts text from a Word document and stores it in the document.
	 * 
	 * @param filepath
	 *            The filepath of the Word document to be read.
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	static private char[] loadMSWord(String filepath)
			throws FileNotFoundException, IOException {
		return loadMSWord(new FileInputStream(filepath));
	}

	/**
	 * Extracts text from a Word document and stores it in the document.
	 * 
	 * @param filesInputStream
	 *            An input stream pointing to the Word document to be read.
	 * @throws IOException
	 */
	static private char[] loadMSWord(InputStream filesInputStream)
			throws IOException {
		System.out.println("Word Document");
		POIFSFileSystem fs = new POIFSFileSystem(filesInputStream);
		HWPFDocument doc = new HWPFDocument(fs);
		WordExtractor we = new WordExtractor(doc);
		char[] origText = we.getText().toCharArray();

		return origText;
	}

	/**
	 * Reads text from a local file. Exceptions are not caught by name. Rather,
	 * all exceptions are handled through just printing the error message to
	 * stdout. This should probably be changed for robustness. The raw text of
	 * the file is stored for quick access in an array.
	 * 
	 * @throws IOException
	 **/
	static public char[] readLocalText(String filepath) throws IOException {
		return readText(new FileInputStream(filepath));
	}

	static public char[] readText(InputStream is) throws IOException {
		int c;
		List<Character> rawText = new ArrayList<Character>();
		BufferedReader br;
		if (jgaapConstants.globalParams.getParameter("charset").equals("")) {
			br = new BufferedReader(new InputStreamReader(is));
		} else {
			br = new BufferedReader(new InputStreamReader(is,
					jgaapConstants.globalParams.getParameter("charset")));
		}

		while ((c = br.read()) != -1) {
			rawText.add(new Character((char) c));
		}
		char[] raw = new char[rawText.size()];
		for (int i = 0; i < rawText.size(); i++) {
			raw[i] = rawText.get(i);
		}
		return raw;
	}

	/**
	 * Reads text from a local file. Exceptions are not caught by name. Rather,
	 * all exceptions are handled through just printing the error message to
	 * stdout. This should probably be changed for robustness. The raw text of
	 * the file is stored for quick access in an array. Modeled from
	 * readLocalText PMJ 10/25/08
	 * 
	 * @throws IOException
	 * @throws BadLocationException
	 **/

	static public char[] readURLText(String filepath) throws IOException,
			BadLocationException {
		char[] rawText;
		URL input = new URL(filepath);
		InputStream is = input.openStream();
		if (filepath.endsWith(".pdf")) {
			rawText = loadPDF(is);
		} else if (filepath.endsWith(".htm") || filepath.endsWith(".html")) {
			rawText = loadHTML(is);
		} else if (filepath.endsWith(".doc")) {
			rawText = loadMSWord(is);
		} else {
			rawText = readText(is);
		}
		return rawText;
	}

}
