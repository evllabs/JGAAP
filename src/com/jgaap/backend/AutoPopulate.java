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
package com.jgaap.backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;

import com.jgaap.JGAAPConstants;
import com.jgaap.generics.*;

/**
 * This class dynamically locates subclasses of a given named superclass within
 * a specific directory. You can use it, for example, to find all (e.g.)
 * Preprocessors and populate the GUI with them automatically, eliminating the
 * need to recompile JGAAP every time you add a new canonicizer.
 * 
 * @author Juola/Noecker
 * @version 4.0
 */
public class AutoPopulate {

	static Logger logger = Logger.getLogger(AutoPopulate.class);

	private static final List<Canonicizer> CANONICIZERS = Collections.unmodifiableList(loadCanonicizers());
	private static final List<EventDriver> EVENT_DRIVERS = Collections.unmodifiableList(loadEventDrivers());
	private static final List<EventCuller> EVENT_CULLERS = Collections.unmodifiableList(loadEventCullers());
	private static final List<DistanceFunction> DISTANCE_FUNCTIONS = Collections.unmodifiableList(loadDistanceFunctions());
	private static final List<AnalysisDriver> ANALYSIS_DRIVERS = Collections.unmodifiableList(loadAnalysisDrivers());
	private static final List<Language> LANGUAGES = Collections.unmodifiableList(loadLanguages());

	/**
	 * Search named directory for all instantiations of the type.
	 * 
	 * @param directory
	 *            The directory to search for the implementing classes of the
	 *            super class
	 * @param superClass
	 *            The (super)class for finding all subclasses of
	 * @return A List containing instantiations of all classes that are
	 *         subclasses of the class.
	 */
	private static List<Object> findClasses(String directory,
			Class<?> superClass) {

		List<Object> classes = new ArrayList<Object>();
		List<String> list = new ArrayList<String>();

		CodeSource src = com.jgaap.JGAAP.class.getProtectionDomain()
				.getCodeSource();
		URL jar = src.getLocation();

		System.out.println(jar);

		if (jar.toString().endsWith(".jar")) {
			try {
				ZipInputStream zip = new ZipInputStream(jar.openStream());
				ZipEntry ze = null;
				while ((ze = zip.getNextEntry()) != null) {
					String entryName = ze.getName();
					if (entryName.startsWith(directory)
							&& entryName.endsWith(".class")) {
						list.add(entryName.substring(0, entryName.length() - 6)
								.replace("/", "."));
					}
				}
				zip.close();
			} catch (IOException e) {
				logger.error("Faild to open " + jar.toString(), e);
			}
		} else {
			InputStream is = com.jgaap.JGAAP.class.getResourceAsStream("/" + directory);
			String packageName = directory.replace("/", ".") + ".";
			if (is != null) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				String line;
				try {
					while ((line = reader.readLine()) != null) {
						if (line.endsWith(".class")) {
							list.add(packageName + line.substring(0, line.length() - 6));
						}
					}
					reader.close();
				} catch (IOException e) {
					logger.error("Failed to open " + directory, e);
				}
			} else {
				File file = new File(JGAAPConstants.JGAAP_BINDIR + directory);
				String[] files = file.list();
				for (String line : files) {
					if (line.endsWith(".class")) {
						list.add(packageName + line.substring(0, line.length() - 6));
					}
				}
			}

		}
		for (String current : list) {
			try {
				logger.debug(current);
				Object o = Class.forName(current).newInstance();
				if (superClass.isInstance(o))
					classes.add(o);
			} catch (InstantiationException e) {
				logger.warn("Problem instancing object", e);
			} catch (IllegalAccessException e) {
				logger.warn("Problem instancing object", e);
			} catch (ClassNotFoundException e) {
				logger.warn("Problem instancing object", e);
			}
		}
		return classes;
	}

	/**
	 * A read-only list of the Canonicizers
	 */
	public static List<Canonicizer> getCanonicizers() {
		return CANONICIZERS;
	}

	private static List<Canonicizer> loadCanonicizers() {
		List<Canonicizer> canonicizers = new ArrayList<Canonicizer>();
		for (Object tmpC : findClasses("com/jgaap/canonicizers",
				com.jgaap.generics.Canonicizer.class)) {
			Canonicizer canon = (Canonicizer) tmpC;
			canonicizers.add(canon);
		}
		Collections.sort(canonicizers);
		return canonicizers;
	}

	/**
	 * A read-only list of the EventDrivers
	 */
	public static List<EventDriver> getEventDrivers() {
		return EVENT_DRIVERS;
	}

	private static List<EventDriver> loadEventDrivers() {
		List<EventDriver> eventDrivers = new ArrayList<EventDriver>();
		for (Object tmpE : findClasses("com/jgaap/eventDrivers",
				com.jgaap.generics.EventDriver.class)) {
			EventDriver event = (EventDriver) tmpE;
			eventDrivers.add(event);
		}
		Collections.sort(eventDrivers);
		return eventDrivers;
	}

	/**
	 * A read-only list of the DistanceFunctions
	 */
	public static List<DistanceFunction> getDistanceFunctions() {
		return DISTANCE_FUNCTIONS;
	}

	private static List<DistanceFunction> loadDistanceFunctions() {
		List<DistanceFunction> distances = new ArrayList<DistanceFunction>();
		for (Object tmpD : findClasses("com/jgaap/distances",
				com.jgaap.generics.DistanceFunction.class)) {
			DistanceFunction method = (DistanceFunction) tmpD;
			distances.add(method);
		}
		Collections.sort(distances);

		return distances;
	}

	/**
	 * A read-only list of the AnalysisDrivers
	 */
	public static List<AnalysisDriver> getAnalysisDrivers() {
		return ANALYSIS_DRIVERS;
	}

	private static List<AnalysisDriver> loadAnalysisDrivers() {
		List<AnalysisDriver> analysisDrivers = new ArrayList<AnalysisDriver>();
		for (Object tmpA : findClasses("com/jgaap/classifiers",
				com.jgaap.generics.AnalysisDriver.class)) {
			AnalysisDriver method = (AnalysisDriver) tmpA;
			analysisDrivers.add(method);
		}
		Collections.sort(analysisDrivers);
		return analysisDrivers;
	}

	/**
	 * A read-only list of the Languages
	 */
	public static List<Language> getLanguages() {
		return LANGUAGES;
	}

	private static List<Language> loadLanguages() {
		List<Language> languages = new ArrayList<Language>();
		for (Object tmpA : findClasses("com/jgaap/languages",
				com.jgaap.generics.Language.class)) {
			Language lang = (Language) tmpA;
			languages.add(lang);
		}
		Collections.sort(languages);
		return languages;
	}

	/**
	 * A read-only list of the EventCullers
	 */
	public static List<EventCuller> getEventCullers() {
		return EVENT_CULLERS;
	}

	private static List<EventCuller> loadEventCullers() {
		List<EventCuller> cullers = new ArrayList<EventCuller>();
		for (Object tmpA : findClasses("com/jgaap/eventCullers",
				com.jgaap.generics.EventCuller.class)) {
			EventCuller lang = (EventCuller) tmpA;
			cullers.add(lang);
		}
		Collections.sort(cullers);
		return cullers;
	}
}
