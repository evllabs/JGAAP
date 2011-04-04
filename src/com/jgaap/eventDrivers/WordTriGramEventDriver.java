// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.eventDrivers;

import com.jgaap.generics.Document;
import com.jgaap.generics.EventSet;

/**
 * Extract tri-grams of words as features.
 * @author John Noecker Jr.
 *
 */
public class WordTriGramEventDriver extends NGramEventDriver {
    @Override
    public String displayName(){
    	return "Word TriGrams";
    }
    
    @Override
    public String tooltipText(){
    	return "Groups of Three Successive Words";
    }
    
    @Override
    public boolean showInGUI(){
    	return false;
    }

    private NGramEventDriver theDriver;

    @Override
    public EventSet createEventSet(Document ds) {
        theDriver = new NGramEventDriver();
        theDriver.setParameter("N", "3");
        return theDriver.createEventSet(ds);
    }
}
