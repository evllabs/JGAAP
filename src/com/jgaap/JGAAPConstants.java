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
package com.jgaap;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Defines a whole slew of public static constants that can be used system-wide.
 */
public class JGAAPConstants {

	private static String path;
	
	public static String separator = System.getProperty("file.separator");

	static {
		try {
			path = processPath(new File(".").getCanonicalPath());
		} catch (IOException e) {
			path = ".."+separator;
		}
	}

	/**
	 * Location for binary (executable) objects. 
	 */
	public static final String JGAAP_BINDIR = path + "bin"+separator;

	/**
	 * Location for libraries such as word lists. 
	 */
	public static final String JGAAP_LIBDIR = path + "lib"+separator;

	/**
	 * Location for source code for JGAAP project. 
	 */
	public static final String JGAAP_SRCDIR = path + "src"+separator;

	/**
	 * Location for documentation objects.
	 */
	public static final String JGAAP_DOCSDIR = path + "docs"+separator;

	/**
	 * Location for utility programs. Not final but should not be changed.
	 */
	public static final String JGAAP_UTILDIR = path + "util"+separator;

	/**
	 * Location of the "tmp" directory - FIXME: I'm not sure that JGAAP should
	 * be producing temporary files, but SVM needs this.
	 */
	public static final String JGAAP_TMPDIR = path + "tmp"+separator;

	private static String processPath(String path) {
		path = path.replaceAll("bin$", "");
		path = path.replaceAll("src$", "");
		if(!path.endsWith(separator)){
			path+=separator;
		}
		return path;
	}

	public static final String JGAAP_RESOURCE_PACKAGE = "/com/jgaap/resources/";

	/**
	 * Java Prefix for different types of object collections
	 */
	public static final String JGAAP_CANONICIZERPREFIX = "com.jgaap.canonicizers.";
	public static final String JGAAP_EVENTDRIVERPREFIX = "com.jgaap.eventDrivers.";
	public static final String JGAAP_EVENTCULLERPREFIX = "com.jgaap.eventCullers.";
	public static final String JGAAP_DISTANCEPREFIX = "com.jgaap.distances.";
	public static final String JGAAP_CLASSIFIERPREFIX = "com.jgaap.classifiers.";
	public static final String JGAAP_GUIPREFIX = "com.jgaap.ui.";
	public static final String JGAAP_GENERICSPREFIX = "com.jgaap.generics.";
	public static final String JGAAP_BACKENDPREFIX = "com.jgaap.backend.";
	public static final String JGAAP_LANGUAGEPREFIX = "com.jgaap.languages.";
	/**
	 * Version Information
	 */
	public static final String VERSION = "8.0.3";
	public static final String YEAR = "2023";

	// search table
	public static HashMap<String, String[]> JGAAP_SEARCH_TABLE
		= new HashMap<String, String[]>();
	static {
		// if the search term contains the key, add its hash value to the search terms too.
		// a key's values are added if the key **contains** the text in the search box.
		// module is matched if its name contains any of the search terms.
		JGAAP_SEARCH_TABLE.put("neural networks", new String[] {"perceptron", "nn"});
		JGAAP_SEARCH_TABLE.put("neuro networks", new String[] {"perceptron", "nn"});
		JGAAP_SEARCH_TABLE.put("mlp", new String[] {"multilayer perceptron"});
		JGAAP_SEARCH_TABLE.put("ngrams", new String[] {"n gram", "n word gram", "n character gram", "ngram"});
		JGAAP_SEARCH_TABLE.put("n grams", new String[] {"n word gram", "n character gram", "ngram", "n gram"});
		JGAAP_SEARCH_TABLE.put("parts of speech", new String[] {"pos", "part of speech"});
		JGAAP_SEARCH_TABLE.put("part of speech", new String[] {"pos", "parts of speech"});
		JGAAP_SEARCH_TABLE.put("x", new String[] {"cross"});
		JGAAP_SEARCH_TABLE.put("xentropy", new String[] {"cross entropy"});
		JGAAP_SEARCH_TABLE.put("histogram distance", new String[] {"euclidean"});
		JGAAP_SEARCH_TABLE.put("euclidean", new String[] {"histogram distance"});
		JGAAP_SEARCH_TABLE.put("cosine similarity", new String[] {"cosine distance"});
		JGAAP_SEARCH_TABLE.put("cosine distance", new String[] {"cosine similarity"});
		JGAAP_SEARCH_TABLE.put("information radius", new String[] {"jensen shannon"});
		JGAAP_SEARCH_TABLE.put("linear discriminant analysis", new String[] {"lda"});
		JGAAP_SEARCH_TABLE.put("sequential minimal optimization", new String[] {"smo"});
		JGAAP_SEARCH_TABLE.put("juola wyner", new String[] {"jw", "thin cross entropy"});
		JGAAP_SEARCH_TABLE.put("iqr", new String[] {"interquartile range"});
		JGAAP_SEARCH_TABLE.put("burrow's delta", new String[] {"burrows delta"});
	}
}
