/**
 *   JGAAP -- Java Graphical Authorship Attribution Program
 *   Copyright (C) 2009 Patrick Juola
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation under version 3 of the License.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/
package com.jgaap.backend;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import com.jgaap.generics.*;
import com.jgaap.jgaapConstants;

/**
 * This class dynamically locates subclasses of a given named superclass within
 * a specific directory. You can use it, for example, to find all (e.g.)
 * Preprocessors and populate the GUI with them automatically, eliminating the
 * need to recompile JGAAP every time you add a new canonicizer.
 * 
 * NOTE: This is a rewritten version of findClasses, since findClasses has been
 * modified to work only on canonicizers.  The original findClasses class has
 * not been removed for legacy reasons.
 * 
 * @author Juola/Noecker
 * @version 4.0
 */
public class AutoPopulate {
	
    /**
     * Search named directory for all instantiations of the type named.
     * 
     * NOTE: This only works if the classes are part of a package beginning
     * with com.jgaap
     * 
     * @param directory
     *            The directory to search for a given (super)class
     * @param theclass
     *            The (super)class for finding all subclasses of
     * @return A Vector containing the names of all classes that are
     *         subclasses of 'theclass'.
     */
    public static Vector<String> findAll(String directory, String theclass) {
        Vector<String> list = new Vector<String>();
        Class<?> thingy = null;
        try {
            thingy = Class.forName(theclass);
        } catch (Exception e) {
            if (jgaapConstants.JGAAP_DEBUG_VERBOSITY)
                System.out.println("Error: problem instantiating " + theclass + " (" + e.getClass().getName() + ")");
        }

        File dir = new File(directory);
        //for( String str : dir.list() )
        	//System.out.println( str );
        String[] children = dir.list();
        if (children == null) {
            System.err.println("Cannot open " + dir.getAbsolutePath() + " for reading");
            return list;
        } else {
            for (int i = 0; i < children.length; i++) {
                if (children[i].endsWith(".class")) {
                    String s = children[i].substring(0,
                            children[i].length() - 6);

                    // System.out.println(s);
                    // list.add(s);
                    String fulQualName = (directory.split("com/jgaap")[(directory.split("com/jgaap")).length - 1]);
                    fulQualName = "com.jgaap" + fulQualName.replace("/", ".");
                    
                    try {
                        Object o = Class.forName(fulQualName + "." + s).newInstance();
                        if (thingy != null && thingy.isInstance(o)) {
                            list.add(s);
                        }

                    } catch (Exception ex) {
                        if (jgaapConstants.JGAAP_DEBUG_VERBOSITY)
                            System.out.println(
                                "Error: problem instantiating " + s + " (" + ex.getClass().getName() + ")");
                    }
                }
            }
        }
        return list;
    }
    
    /**
     * Search named directory for all instantiations of the type named.
     * 
     * NOTE: This only works if the classes are part of a package beginning
     * with com.jgaap
     * 
     * @param directory
     *            The directory to search for a given (super)class
     * @param theclass
     *            The (super)class for finding all subclasses of
     * @param getObjects
     * 			  This field is ignored, but if it is present the vector will contain the actual instantiated objects          
     * @return A Vector containing instantiations of all classes that are
     *         subclasses of 'theclass'.
     */
    public static Vector<Object> findAll(String directory, String theclass, boolean getObjects) {
        Vector<Object> list = new Vector<Object>();
        Class<?> thingy = null;
        try {
            thingy = Class.forName(theclass);
        } catch (Exception e) {
            if (jgaapConstants.JGAAP_DEBUG_VERBOSITY)
                System.out.println("Error: problem instantiating " + theclass + " (" + e.getClass().getName() + ")");
        }

        File dir = new File(directory);
        //for( String str : dir.list() )
        	//System.out.println( str );
        String[] children = dir.list();
        if (children == null) {
            System.err.println("Cannot open " + dir.getAbsolutePath() + " for reading");
            return list;
        } else {
            for (int i = 0; i < children.length; i++) {
                if (children[i].endsWith(".class")) {
                    String s = children[i].substring(0,
                            children[i].length() - 6);

                    // System.out.println(s);
                    // list.add(s);
                    String fulQualName = (directory.split("com/jgaap")[(directory.split("com/jgaap")).length - 1]);
                    fulQualName = "com.jgaap" + fulQualName.replace("/", ".");
                    
                    try {
                        Object o = Class.forName(fulQualName + "." + s).newInstance();
                        if (thingy != null && thingy.isInstance(o)) {
                            list.add(o);
                        }

                    } catch (Exception ex) {
                        if (jgaapConstants.JGAAP_DEBUG_VERBOSITY) 
                            System.out.println(
                                "Error: problem instantiating " + s + " (" + ex.getClass().getName() + ")");
                    }
                }
            }
        }
        return list;
    }
    
    
    @SuppressWarnings("unchecked")
	public static List<Canonicizer> getCanonicizers(){
    	List<Canonicizer> canonicizers = new ArrayList<Canonicizer>();
    	if(jgaapConstants.globalObjects.containsKey("canonicizers")){
    		canonicizers.addAll( (List<Canonicizer>) jgaapConstants.globalObjects.get("canonicizers"));
    	}else{
			for (Object tmpC : AutoPopulate.findAll(
					jgaapConstants.binDir()+"/com/jgaap/canonicizers",
					"com.jgaap.generics.Canonicizer", true)) {
				Canonicizer canon = (Canonicizer) tmpC;
				canonicizers.add(canon);
			}
			Collections.sort(canonicizers);
			jgaapConstants.globalObjects.put("canonicizers", canonicizers);
    	}
		return canonicizers;
    }
    
    
 // Load the event drivers dynamically
    @SuppressWarnings("unchecked")
	public static List<EventDriver> getEventDrivers(){
		List<EventDriver> eventDrivers;
    	if(jgaapConstants.globalObjects.containsKey("eventDrivers")){
    		eventDrivers = (List<EventDriver>) jgaapConstants.globalObjects.get("eventDrivers");
    	}else{
    	eventDrivers = new ArrayList<EventDriver>();
			for (Object tmpE : AutoPopulate.findAll(jgaapConstants.binDir()+"/com/jgaap/eventDrivers",
					"com.jgaap.generics.EventDriver", true)) {
				EventDriver event = (EventDriver) tmpE;
				eventDrivers.add(event);
			}
			Collections.sort(eventDrivers);
			jgaapConstants.globalObjects.put("eventDrivers", eventDrivers);
    	}
		return eventDrivers;
    }
    @SuppressWarnings("unchecked")
	public static List<DistanceFunction> getDistanceFunctions(){
		// Load the distance functions dynamically
    	List<DistanceFunction> distances;
    	if(jgaapConstants.globalObjects.containsKey("distances")){
    		distances = (List<DistanceFunction>) jgaapConstants.globalObjects.get("distances");
    	}else{
			distances = new ArrayList<DistanceFunction>();
			for (Object tmpD : AutoPopulate.findAll(jgaapConstants.binDir()+"/com/jgaap/distances",
					"com.jgaap.generics.DistanceFunction", true)) {
				DistanceFunction method = (DistanceFunction) tmpD;
				distances.add(method);
			}
			Collections.sort(distances);
			jgaapConstants.globalObjects.put("distances", distances);
    	}
    	return distances;
    }
    @SuppressWarnings("unchecked")
	public static List<AnalysisDriver> getAnalysisDrivers(){
	// Load the classifiers dynamically
    	List<AnalysisDriver> analysisDrivers;
    	if(jgaapConstants.globalObjects.containsKey("classifiers")){
    		analysisDrivers=(List<AnalysisDriver>) jgaapConstants.globalObjects.get("classifiers");
    	}else{
	    	analysisDrivers = new ArrayList<AnalysisDriver>();
			for (Object tmpA : AutoPopulate.findAll(jgaapConstants.binDir()+"/com/jgaap/classifiers",
					"com.jgaap.generics.AnalysisDriver", true)) {
				AnalysisDriver method = (AnalysisDriver) tmpA;
				analysisDrivers.add(method);
			}
			Collections.sort(analysisDrivers);
			jgaapConstants.globalObjects.put("classifiers", analysisDrivers);
    	}
	return analysisDrivers;
    }
    @SuppressWarnings("unchecked")
	public static List<Language> getLanguages(){
	// Load the classifiers dynamically
    	List<Language> languages;
    	if(jgaapConstants.globalObjects.containsKey("languages")){
    		languages = (List<Language>) jgaapConstants.globalObjects.get("languages");
    	}else{
			languages = new ArrayList<Language>();
			for (Object tmpA : AutoPopulate.findAll(jgaapConstants.binDir()+"/com/jgaap/languages",
					"com.jgaap.generics.Language", true)) {
				Language lang = (Language) tmpA;
				languages.add(lang);
			}
			Collections.sort(languages);
			jgaapConstants.globalObjects.put("languages", languages);
    	}
	return languages;
    }
    @SuppressWarnings("unchecked")
	public static List<EventCuller> getEventCullers(){
	// Load the event cullers dynamically
    	List<EventCuller> cullers;
    	if(jgaapConstants.globalObjects.containsKey("eventCullers")){
    		cullers = (List<EventCuller>) jgaapConstants.globalObjects.get("eventCullers");
    	}else{
			cullers = new ArrayList<EventCuller>();
			for (Object tmpA : AutoPopulate.findAll(jgaapConstants.binDir()+"/com/jgaap/eventCullers",
					"com.jgaap.generics.EventCuller", true)) {
				EventCuller lang = (EventCuller) tmpA;
				cullers.add(lang);
			}
			Collections.sort(cullers);
			jgaapConstants.globalObjects.put("eventCullers", cullers);
    	}
	return cullers;
    }
    
    /**
     * Main routine for testing. Lists (on the screen) all subclasses of the
     * type(s) named as argument(s).
     * 
     * @param args
     *            The second EventSet
     * @return nothing
     */
    public static void main(String args[]) {
        Vector<String> v;

        for (int i = 0; i < args.length; i++) {
            v = findAll("src/com/jgaap/canonicizers", args[i]);
            for (int j = 0; j < v.size(); j++) {
                System.out.print(v.elementAt(j));
                if (j == v.size() - 1) {
                    System.out.println("");
                } else {
                    System.out.print(", ");
                }
            }
        }
    }

}
