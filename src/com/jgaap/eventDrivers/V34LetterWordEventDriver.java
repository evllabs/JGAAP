// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.eventDrivers;

import com.jgaap.generics.Document;
import com.jgaap.generics.EventSet;

/**
 * Extract vowel-initial words with 3 or 4 letters as features
 * @author Patrick Juola
 * @since 4.1
 *
 */
public class V34LetterWordEventDriver extends MNLetterWordEventDriver {

    @Override
    public String displayName(){
    	return "Vowel 3--4 letter Words";
    }
    
    @Override
    public String tooltipText(){
    	return "Vowel-initial Words with 3 or 4 letters";
    }
    
    @Override
    public boolean showInGUI(){
    	return false;
    }

    private MNLetterWordEventDriver theDriver;

    @Override
    public EventSet createEventSet(Document ds) {
        theDriver = new MNLetterWordEventDriver();
        theDriver.setParameter("underlyingevents", "VowelInitialWordEventDriver");
        theDriver.setParameter("M", "3");
        theDriver.setParameter("N", "4");
        return theDriver.createEventSet(ds);
    }
}
