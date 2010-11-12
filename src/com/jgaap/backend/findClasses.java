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
import java.net.URI;
import java.util.Collection;
import java.util.Vector;

/**
 * This class dynamically locates subclasses of a given named superclass within
 * a specific directory. You can use it, for example, to find all (e.g.)
 * Preprocessors and populate the GUI with them automatically, eliminating the
 * need to recompile JGAAP every time you add a new canonicizer.
 * 
 * @author Juola
 * @version 1.0
 */
@Deprecated
public class findClasses {
	
	public final static boolean DEBUG = false;

    /**
     * Search named directory for all instantiations of the type named.
     * 
     * @param directory
     *            The directory to search for a given (super)class
     * @param theclass
     *            The (super)class for finding all subclasses of
     * @return A Vector containing the names of all .class files representing
     *         subclasses of 'theclass'.
     */
    public static Vector<String> findAll(String directory, String theclass) {
        Vector<String> list = new Vector<String>();
        Class<?> thingy = null;
        try {
            thingy = Class.forName(theclass);
        } catch (Exception e) {
            System.out.println("Error: problem instantiating " + theclass);
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

                    try {
                        Object o = Class.forName("com.jgaap.canonicizers." + s).newInstance();
                        if (thingy != null && thingy.isInstance(o)) {
                            list.add(s);
                        }

                    } catch (Exception ex) {
                         System.out.println(
                         "Error: problem instantiating " + s);
                    }
                }
            }
        }
        return list;
    }
    
    /**
     * This method is used to return a Collection of Class objects without actually
     * instantiating any instances of the classes they are linked to. This method
     * is a convenience method for calling the other version of getSubClasses without
     * needing to worry about having a Class object.
     * 
     * @param directory
     *   The directory to search. 
     * @param parentClassName
     *   The fully qualified name of the parent class. For example, Canonicizer's fully
     *   qualified name is "com.jgaap.generics.Canonicizer".
     * @param subClassesPrefix
     *   This is a String that contains the first part of a fully qualified name. To check the class files
     *   in com/jgaap/canonicizers, this argument would be "com.jgaap.canonicizers.". Note the final dot.
     * @return A Collection of Class objects, each representing a valid
     *  subclass of the parent class passed as an argument
     */
    public static Collection< Class<?> > getSubClasses( URI directory, 
    												 String parentClassName,
    												 String subClassesPrefix )
    {
    	try{
    		return getSubClasses( directory, Class.forName(parentClassName), subClassesPrefix);
    	}catch(ClassNotFoundException e)
    	{
    		System.err.println("The class " + parentClassName + " could not be found." );
    		e.printStackTrace();
    	}catch( Exception e )
    	{
    		e.printStackTrace();
    	}
    	return null;
    }
    
    /**
     * This method is used to return a Collection of Class objects without actually
     * instantiating any instances of the classes they are linked to.
     * 
     * @param directory
     *   The directory to search. 
     * @param parentClassName
     *   Actual Class instance linking to the class that will be treated as the parent class.
     * @param subClassesPrefix
     *   This is a String that contains the first part of a fully qualified name. To check the class files
     *   in com/jgaap/canonicizers, this argument would be "com.jgaap.canonicizers.". Note the final dot.
     * @return
     */
    public static Collection< Class<?> > getSubClasses( URI directory, 
			 											Class<?> parentClassName,
			 											String subClassesPrefix )
	{
    	Vector< Class<?> > subClassList = new Vector< Class<?> >();
    	try {
    	for( String file : new File( directory ).list() ) {
			if( file.endsWith( ".class" ) ) {
				String className = file.substring(0, file.length() - 6); // chop off '.class'
				try {
					if(DEBUG)System.out.print("Possible class: " + className );
					final Class<?> subClass = Class.forName( subClassesPrefix + className ).asSubclass( parentClassName );					
					if( subClass != null ) {
						if(DEBUG)System.out.println(" Result: Subclass " );
						subClassList.add( subClass );
					}
					else
						if(DEBUG)System.out.println(" Result: Not A Subclass" );
				}catch( ClassCastException e ) {
					// If we end up here, the class file is NOT a subclass of AssignmentPanel
					if(DEBUG)System.out.println(" Result: ClassCastException " );
				}catch( NoClassDefFoundError e){
					// If we end up here, the class file tested didn't work with the subClassesPrefix
					if(DEBUG)System.out.println(" Result: NoClassDefFoundError " );
				}catch(Exception e)  {
					// If we end up here, we've encountered a real error
					e.printStackTrace();
				}				
			}
		}
    	}catch(NullPointerException e)
    	{
    		System.err.println("Could not open canonicizers directory. Looked in :" + directory );
    		System.err.println("If this directory is not correct, change the constant 'canonSearchDirectory' " +
    				"in com.jgaap.gui.stepPanels.CanonicizeStepPanel");
    	}
    	return subClassList;
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
            v = findAll(".", args[i]);
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
