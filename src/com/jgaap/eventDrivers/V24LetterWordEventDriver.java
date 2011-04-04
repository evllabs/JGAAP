/**
 **/
package com.jgaap.eventDrivers;

import com.jgaap.generics.Document;
import com.jgaap.generics.EventSet;

/**
 * Extract vowel-initial words with 2 or 3 letters as features
 * @author Patrick Juola
 * @since 4.1
 *
 */
public class V24LetterWordEventDriver extends MNLetterWordEventDriver {


    @Override
    public String displayName(){
    	return "Vowel 2--4 letter Words";
    }
    
    @Override
    public String tooltipText(){
    	return "Vowel-initial Words with 2, 3 or 4 letters";
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
        theDriver.setParameter("M", "2");
        theDriver.setParameter("N", "4");
        return theDriver.createEventSet(ds);
    }
}
