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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.reflections.Reflections;

/**
 * This class dynamically locates subclasses of a given named superclass within
 * a specific package. You can use it, for example, to find all (e.g.)
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
	public static List<Object> findObjects(String packageName, Class<?> superClass) {
		List<Object> classes = new ArrayList<Object>();
		Reflections reflections = new Reflections(packageName);
		Set<?> allClasses = reflections.getSubTypesOf(superClass);
		for(Object obj : allClasses) {
			if(superClass.isAssignableFrom(((Class<?>)obj))){
				try {
					classes.add(((Class<?>)obj).newInstance());
				}  catch (Exception e) {
					logger.warn("Problem instancing object "+obj, e);
				}
			}
		}
		return classes;
	}
	
	/**
	 * Search named directory for all instantiations of the type.
	 * 
	 * @param packageName
	 *            The package to search for the implementing classes of the
	 *            super class
	 * @param superClass
	 *            The (super)class for finding all subclasses of
	 * @return A Set of all classes that are subclasses of the class.
	 */
	public static Set<?> findClasses(String packageName, Class<?> superClass) {
		Reflections reflections = new Reflections(packageName);
		Set<?> allClasses = reflections.getSubTypesOf(superClass);
		return allClasses;
	}
}
