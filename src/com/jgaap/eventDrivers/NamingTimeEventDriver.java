// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.eventDrivers;

import com.jgaap.jgaapConstants;
import com.jgaap.generics.Document;
import com.jgaap.generics.NumericEventSet;

/**
 * Naming times taken from English Lexicon Project. Converts each word (using
 * table) to the time it takes to name that word in the ELP database. Obviously
 * English-only, and obviously incomplete; words that are not in the database
 * are silently removed.
 */
public class NamingTimeEventDriver extends NumericTransformationEventDriver {

    @Override
    public String displayName(){
    	return "Naming Reaction Times";
    }
    
    @Override
    public String tooltipText(){
    	return "Naming Times from English Lexicon Project";
    }
    
    @Override
    public boolean showInGUI(){
    	return jgaapConstants.globalParams.getParameter("language").equals("english");
    }

    @Override
    public NumericEventSet createEventSet(Document ds) {
        NumericTransformationEventDriver theDriver = new NumericTransformationEventDriver();
        // uses NaiveWordEventSet for now
        theDriver.setParameter("implicitWhitelist", "true");
        theDriver.setParameter("filename", jgaapConstants.libDir()
                + "/ELPnaming.dat");

        return theDriver.createEventSet(ds);
    }

}
