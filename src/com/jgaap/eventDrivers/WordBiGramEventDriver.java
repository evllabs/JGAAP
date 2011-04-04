// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.eventDrivers;

import com.jgaap.generics.Document;
import com.jgaap.generics.EventSet;

/**
 * Extract word bigrams as features.
 * @author John Noecker Jr.
 *
 */
public class WordBiGramEventDriver extends NGramEventDriver {

    @Override
    public String displayName(){
        return "Word BiGrams";
    }

    @Override
    public String tooltipText(){
        return "Word Pairs in Sequence";
    }

    @Override
    public boolean showInGUI(){
        return false;
    }


    private NGramEventDriver theDriver;

    @Override
    public EventSet createEventSet(Document ds) {
        theDriver = new NGramEventDriver();
        // no changes needed because it uses the default parameters
        return theDriver.createEventSet(ds);
    }
}
