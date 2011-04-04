/**
 **/
package com.jgaap.eventDrivers;

import com.jgaap.generics.Document;
import com.jgaap.generics.EventSet;

/**
 * Extract tetragrams of characters as features.
 *
 */
public class POSTetraGramEventDriver extends NGramEventDriver {

    @Override
    public String displayName(){
    	return "POS TetraGrams";
    }
    
    @Override
    public String tooltipText(){
    	return "Part-of- Speech Tetrairs in Sequence";
    }
    
    @Override
    public boolean showInGUI(){
    	return false;
    }

  private NGramEventDriver theDriver;

    @Override
    public EventSet createEventSet(Document ds) {
        theDriver = new NGramEventDriver();
        // default value of N is 2 already
        theDriver.setParameter("N", "4");
        theDriver.setParameter("underlyingEvents", "PartOfSpeechEventDriver");
        return theDriver.createEventSet(ds);
    }
}
