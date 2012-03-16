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
package com.jgaap.generics;

import java.io.*;
import java.net.URL;

import javax.swing.text.BadLocationException;
import javax.swing.text.EditorKit;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;


/**
 * 
 * A helper class for Document that handles all the different ways documents can be loaded
 * 
 * @author Michael Ryan
 * @since 5.0.0
 */

class DocumentHelper {
	
	static Logger logger = Logger.getLogger(com.jgaap.generics.DocumentHelper.class);

	static char[] loadDocument(String filepath, String charset) throws IOException, BadLocationException {
		InputStream is;
		int fileSize = -1;
		if (filepath.startsWith("http://") || filepath.startsWith("https://")) {
			URL url = new URL(filepath);
			is = url.openStream();
		} else if (filepath.startsWith("/com/jgaap/resources")){
			is = com.jgaap.JGAAP.class.getResourceAsStream(filepath);
		} else {
			fileSize = (int) new File(filepath).length();
			is = new FileInputStream(filepath);
		} 
		if (filepath.endsWith(".pdf")) {
			return loadPDF(is);
		} else if (filepath.endsWith(".doc")) {
			return loadMSWord(is);
		} else if (filepath.endsWith(".docx")){
			return loadMSWordDocx(is);
		} else if (filepath.endsWith(".htm") || filepath.endsWith(".html")) {
			return loadHTML(is);
		} else {
			if(fileSize==-1)
				return readText(is, charset);
			else
				return readText(is, charset, fileSize);
		}
	}

	static DocType getDocType(String filepath) {
		if (filepath.endsWith(".pdf")) {
			return DocType.PDF;
		} else if (filepath.endsWith(".doc")||filepath.endsWith(".docx")) {
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
	 * Extracts text from an HTML document and stores it in the document.
	 * 
	 * @param filesInputStream
	 *            An input stream pointing to the HTML document to be read.
	 * @throws BadLocationException
	 * @throws IOException
	 */
	static private char[] loadHTML(InputStream filesInputStream)
			throws IOException, BadLocationException {
		EditorKit kit = new HTMLEditorKit();
		HTMLDocument doc = (HTMLDocument) kit.createDefaultDocument();
		doc.putProperty("IgnoreCharsetDirective", true);
		kit.read(filesInputStream, doc, 0);
		char[] origText = doc.getText(0, doc.getLength()).toCharArray();

		return origText;
	}

	/**
	 * Extracts text from a Word document and stores it in the document.
	 * 
	 * @param inputStream
	 *            An input stream pointing to the Word document to be read.
	 * @throws IOException
	 */
	static private char[] loadMSWord(InputStream inputStream)
			throws IOException {
		POIFSFileSystem fs = new POIFSFileSystem(inputStream);
		HWPFDocument doc = new HWPFDocument(fs);
		WordExtractor we = new WordExtractor(doc);
		char[] origText = we.getText().toCharArray();

		return origText;
	}
	
	/**
	 * Extracts text from a Word document and stores it in the document.
	 * 
	 * @param inputStream
	 *            An input stream pointing to the Word document to be read.
	 * @throws IOException
	 */
	static private char[] loadMSWordDocx(InputStream inputStream) throws IOException{
		XWPFDocument docx = new XWPFDocument(inputStream);
		XWPFWordExtractor extractor = new XWPFWordExtractor(docx);
		return extractor.getText().toCharArray();
	}
	
	/**
	 * Reads text from a local file. The raw text of
	 * the file is stored for quick access in an array.
	 * 
	 * @throws IOException
	 **/
	
	static private char[] readText(InputStream is, String charset, int length) throws IOException{
		Reader reader;
		if(charset==null || charset.isEmpty()){
			reader = new InputStreamReader(is);
		} else {
			reader = new InputStreamReader(is, charset);
		}
		char[] text = new char[length];
		int status = reader.read(text);
		if(status < length || reader.read() != -1)
			logger.warn("Possibility Document too large to stor into memory or using utf-8 with utf-16 chars in it."); 
		reader.close();
		return text;
	}
	
	/**
	 * Reads text from a local file. The raw text of
	 * the file is stored for quick access in an array.
	 * 
	 * @throws IOException
	 **/
	static private char[] readText(InputStream is, String charset) throws IOException {
		int c;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader reader;
		if (charset==null||charset.isEmpty()) {
			reader = new BufferedReader(new InputStreamReader(is));
		} else {
			reader = new BufferedReader(new InputStreamReader(is,charset));
		}
		while ((c = reader.read()) != -1) {
			stringBuilder.append((char)c);
		}
		reader.close();
		return stringBuilder.toString().toCharArray();
	}

}
