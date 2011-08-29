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

	/**
	 * This flag sets if you want the jgaap jar to be independent (true) or if
	 * you want it to require the directory structure (false)
	 */
	public static final boolean JGAAP_PACKAGE_JAR = false;

	private static String path;

	static {
		try {
			path = processPath(new File(".").getCanonicalPath());
		} catch (IOException e) {
			path = "..";
		}
	}

	/**
	 * Location for binary (executable) objects. 
	 */
	public static final String JGAAP_BINDIR = path + "bin/";

	public static String binDir() {
		return path + "/bin/";
	}

	/**
	 * Location for libraries such as word lists. 
	 */
	public static final String JGAAP_LIBDIR = path + "lib/";

	public static String libDir() {
		return path + "/lib/";
	}

	/**
	 * Location for source code for JGAAP project. 
	 */
	public static final String JGAAP_SRCDIR = path + "src/";

	public static String srcDir() {
		return path + "/src/";
	}

	/**
	 * Location for documentation objects.
	 */
	public static final String JGAAP_DOCSDIR = path + "docs/";

	public static String docsDir() {
		return path + "/docs/";
	}

	/**
	 * Location for utility programs. Not final but should not be changed.
	 */
	public static final String JGAAP_UTILDIR = path + "util/";

	public static String utilDir() {
		return path + "/util/";
	}

	/**
	 * Location of the "tmp" directory - FIXME: I'm not sure that JGAAP should
	 * be producing temporary files, but SVM needs this.
	 */
	public static final String JGAAP_TMPDIR = path + "tmp/";

	public static String tmpDir() {
		return path + "/tmp/";
	}

	private static String processPath(String path) {
		path = path.replaceAll("bin$", "");
		path = path.replaceAll("src$", "");
		return path;
	}

	public static URL getLocation() {
		return jgaap.class.getResource("");
	}

	public static final String JGAAP_RESOURCE_PACKAGE = "/com/jgaap/resources/";

	/**
	 * Java Prefix for different types of object collections
	 */
	public static final String JGAAP_CANONICIZERPREFIX = "com.jgaap.canonicizers.";
	public static final String JGAAP_EVENTDRIVERPREFIX = "com.jgaap.eventDrivers.";
	public static final String JGAAP_DISTANCEPREFIX = "com.jgaap.distances.";
	public static final String JGAAP_CLASSIFIERPREFIX = "com.jgaap.classifiers.";
	public static final String JGAAP_GUIPREFIX = "com.jgaap.ui.";
	public static final String JGAAP_GENERICSPREFIX = "com.jgaap.generics.";
	public static final String JGAAP_BACKENDPREFIX = "com.jgaap.backend.";
	public static final String JGAAP_LANGUAGEPREFIX = "com.jgaap.languages.";

	/*
	 * JGAAP prints out a lot of warnings/messages that ordinary users needn't
	 * see. Set this to 'false' for releases.
	 */
	public static final boolean JGAAP_DEBUG_VERBOSITY = false;

	// // MVR This can and should be changed.
	// /** Set of global parameters, to change via usual schemes. */
	// public static Parameterizable globalParams = new Parameterizable();
	//
	// /**
	// * Set of global objects, accessed via a HashMap (when Parameterizable is
	// * not sufficient because we need to store generic objects)
	// */
	// public static HashMap<String, Object> globalObjects = new HashMap<String,
	// Object>();

}
