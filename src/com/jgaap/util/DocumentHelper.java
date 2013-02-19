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

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.tika.Tika;


/**
 * 
 * A helper class for Document that handles all the different ways documents can be loaded
 * 
 * @author Michael Ryan
 * @since 5.0.0
 */

class DocumentHelper {
	
	static Logger logger = Logger.getLogger(com.jgaap.util.DocumentHelper.class);
	static private Tika tika = new Tika();
	
	static {
		tika.setMaxStringLength(-1);
	}
	
	static String loadDocument(String filepath, String charset) throws Exception {
		InputStream is;
		if (filepath.startsWith("http://") || filepath.startsWith("https://")) {
			URL url = new URL(filepath);
			is = url.openStream();
		} else if (filepath.startsWith("/com/jgaap/resources")){
			is = com.jgaap.JGAAP.class.getResourceAsStream(filepath);
		} else {
			is = new FileInputStream(filepath);
		} 
		String text = tika.parseToString(is);
		is.close();
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

}
