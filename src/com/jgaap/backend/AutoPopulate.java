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
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;

import com.jgaap.JGAAPConstants;

/**
 * This class dynamically locates subclasses of a given named superclass within
 * a specific directory. You can use it, for example, to find all (e.g.)
 * Preprocessors and populate the GUI with them automatically, eliminating the
 * need to recompile JGAAP every time you add a new canonicizer.
 * 
 * @author Michael Ryan
 * @author John Noecker
 * @author Patrick Juola
 * @version 4.0
 */
public class AutoPopulate {

	static Logger logger = Logger.getLogger(AutoPopulate.class);

	/**
	 * Search named directory for all instantiations of the type.
	 * 
	 * @param packageName
	 *            The package to search for the implementing classes of the
	 *            super class
	 * @param superClass
	 *            The (super)class for finding all subclasses of
	 * @return A List containing instantiations of all classes that are
	 *         subclasses of the class.
	 */
	public static List<Object> findClasses(String packageName, Class<?> superClass) {

		List<Object> classes = new ArrayList<Object>();
		List<String> list = new ArrayList<String>();

		String directory = packageName.replace(".", "/");
		
		CodeSource src = com.jgaap.JGAAP.class.getProtectionDomain()
				.getCodeSource();
		URL jar = src.getLocation();

		if (jar.toString().endsWith(".jar")) {
			try {
				ZipInputStream zip = new ZipInputStream(jar.openStream());
				ZipEntry ze = null;
				while ((ze = zip.getNextEntry()) != null) {
					String entryName = ze.getName();
					if (entryName.startsWith(directory)
							&& entryName.endsWith(".class") && !entryName.contains("$")) {
						list.add(entryName.substring(0, entryName.length() - 6)
								.replace("/", "."));
					}
				}
				zip.close();
			} catch (IOException e) {
				logger.error("Faild to open " + jar.toString(), e);
			}
		} else {  
			//This case was added to handle an error with ant 
			//where it cannot use InputStream or any of its impls
			InputStream is = com.jgaap.JGAAP.class.getResourceAsStream("/" + directory);
			//String packageName = directory.replace("/", ".") + ".";
			if (is != null) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				String line;
				try {
					while ((line = reader.readLine()) != null) {
						if (line.endsWith(".class") && !line.contains("$")) {
							list.add(packageName +"."+ line.substring(0, line.length() - 6));
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
					if (line.endsWith(".class") && !line.contains("$")) {
						list.add(packageName +"."+ line.substring(0, line.length() - 6));
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
				logger.warn("Problem instancing object "+current, e);
			} catch (IllegalAccessException e) {
				logger.warn("Problem instancing object "+current, e);
			} catch (ClassNotFoundException e) {
				logger.warn("Problem instancing object "+current, e);
			}
		}
		return classes;
	}
}
