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
import java.net.URL;

/**
 * Defines a whole slew of public static constants that can be used system-wide.
 */
public class jgaapConstants {

	/*
	 * n.b. these are NOT final but should nevertheless not be changed.
	 */
	
	/**
	 * This flag sets if you want the jgaap jar to be independent (true) 
	 * or if you want it to require the directory structure (false)  
	 */
	public static final boolean JGAAP_PACKAGE_JAR = false;

	/**
	 * Location for binary (executable) objects. Not final but should not be
	 * changed.
	 */
	//public static String JGAAP_BINDIR = "../bin/";

	public static String binDir() {
		try {
			File folder = new File(".");
			String path = folder.getCanonicalPath();
			path=processPath(path);
			return path +"/bin/";

		} catch (IOException e) {
			return "../bin/";
		}
	}

	/**
	 * Location for libraries such as word lists. Not final but should not be
	 * changed.
	 */
	//public static String JGAAP_LIBDIR = "../lib/";

	public static String libDir() {
		try {
			File folder = new File(".");
			String path = folder.getCanonicalPath();
			path=processPath(path);
			return path + "/lib/";

		} catch (IOException e) {
			return "../lib/";
		}
	}

	/**
	 * Location for source code for JGAAP project. Not final but should not be
	 * changed.
	 */
//	public static String JGAAP_SRCDIR = "../src/";

	public static String srcDir() {
		try {
			File folder = new File(".");
			String path = folder.getCanonicalPath();
			path=processPath(path);
			return path + "/src/";

		} catch (IOException e) {
			return "../src/";
		}
	}

	/**
	 * Location for documentation objects. Not final but should not be changed.
	 */
//	public static String JGAAP_DOCSDIR = "../docs/";

	public static String docsDir() {
		try {
			File folder = new File(".");
			String path = folder.getCanonicalPath();
			path=processPath(path);
			return path + "/docs/";
		} catch (IOException e) {
			return "../docs/";
		}
	}

	/**
	 * Location for utility programs. Not final but should not be changed.
	 */
//	public static String JGAAP_UTILDIR = "../util/";

	public static String utilDir() {
		try {
			File folder = new File(".");
			String path = folder.getCanonicalPath();
			path=processPath(path);
			return path + "/util/";

		} catch (IOException e) {
			return "../util/";
		}
	}

	/**
	 * Location of the "tmp" directory - FIXME: I'm not sure that JGAAP should
	 * be producing temporary files, but SVM needs this.
	 */
//	public static String JGAAP_TMPDIR = "../tmp/";

	public static String tmpDir() {
		try {
			File folder = new File(".");
			String path = folder.getCanonicalPath();
			path=processPath(path);
			return path + "/tmp/";

		} catch (IOException e) {
			return "../tmp/";
		}
	}
	
	
	private static String processPath(String path){
		path = path.replaceAll("bin$", "");
		path = path.replaceAll("src$", "");
		return path;
	}
	
	public static URL getLocation(){
		return jgaap.class.getResource("");
	}
	
	/**
	 * Java Prefix for different types of object collections
	 */
	public static final String JGAAP_CANONICIZERPREFIX = "com.jgaap.canonicizers.";
	public static final String JGAAP_EVENTDRIVERPREFIX = "com.jgaap.eventDrivers.";
	public static final String JGAAP_DISTANCEPREFIX = "com.jgaap.distances.";
	public static final String JGAAP_CLASSIFIERPREFIX = "com.jgaap.classifiers.";
	public static final String JGAAP_GUIPREFIX = "com.jgaap.gui.";
	public static final String JGAAP_GENERICSPREFIX = "com.jgaap.generics.";
	public static final String JGAAP_BACKENDPREFIX = "com.jgaap.backend.";
	public static final String JGAAP_LANGUAGEPREFIX = "com.jgaap.languages.";

	/*
	 * JGAAP prints out a lot of warnings/messages that ordinary users needn't
	 * see. Set this to 'false' for major releases.
	 */
	public static final boolean JGAAP_DEBUG_VERBOSITY = false;

//	// MVR This can and should be changed.
//	/** Set of global parameters, to change via usual schemes. */
//	public static Parameterizable globalParams = new Parameterizable();
//
//	/**
//	 * Set of global objects, accessed via a HashMap (when Parameterizable is
//	 * not sufficient because we need to store generic objects)
//	 */
//	public static HashMap<String, Object> globalObjects = new HashMap<String, Object>();

}
