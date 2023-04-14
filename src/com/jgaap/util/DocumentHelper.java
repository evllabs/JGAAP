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
package com.jgaap.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.tika.Tika;


/**
 * 
 * A helper class for Document that handles all the different ways documents can be loaded
 * 
 * @author Michael Ryan
 * @contributor Michael Fang
 * @since 5.0.0
 */

class DocumentHelper {
	
	static Logger logger = Logger.getLogger(com.jgaap.util.DocumentHelper.class);
	static private Tika tika = new Tika();
	
	static {
		tika.setMaxStringLength(-1);
	}
	
	static String loadDocument(String filepath, String charset) throws Exception {
		String text = "";
		if("tika".equalsIgnoreCase(charset)){
			InputStream is = getInputStream(filepath);
			text= tika.parseToString(is);
			is.close();
		}
		if(text.isEmpty()){
			InputStream is = getInputStream(filepath);
			text = readText(is, charset);
			is.close();
		}
		text = replaceCRLF(text);
		return text;
	}

	static Document.Type getDocType(String filepath) {
		if (filepath.endsWith(".pdf")) {
			return Document.Type.PDF;
		} else if (filepath.endsWith(".doc")||filepath.endsWith(".docx")) {
			return Document.Type.DOC;
		} else if (filepath.endsWith(".htm") || filepath.endsWith(".html")) {
			return Document.Type.HTML;
		} else {
			return Document.Type.GENERIC;
		}
	}

	static private String replaceCRLF(String text) {
		// change CRLF sequences (\r, \n, and \r\n) to LF (\n)
		text = text.replaceAll("(\r\n)|\r|\n", "\n");
		return text;
	}
	
	static private InputStream getInputStream(String filepath) throws Exception{
		InputStream is;
		if (filepath.startsWith("http://") || filepath.startsWith("https://")) {
			URL url = new URL(filepath);
			is = url.openStream();
		} else if (filepath.startsWith("/com/jgaap/resources")){
			is = com.jgaap.JGAAP.class.getResourceAsStream(filepath);
		} else {
			is = new FileInputStream(filepath);
		} 
		return is;
	}
	
	/**
	 * Reads text from a local file. The raw text of
	 * the file is stored for quick access in an array.
	 * 
	 * @throws IOException
	 **/
	static private String readText(InputStream is, String charset) throws IOException {
		int c;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader reader;
		if (charset==null||charset.isEmpty()||charset.equalsIgnoreCase("tika")) {
			reader = new BufferedReader(new InputStreamReader(is));
		} else {
			reader = new BufferedReader(new InputStreamReader(is,charset));
		}
		while ((c = reader.read()) != -1) {
			stringBuilder.append((char)c);
		}
		reader.close();
		return stringBuilder.toString();
	}

}
