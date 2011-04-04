/**
 **/
package com.jgaap.eventDrivers;

import com.jgaap.generics.Document;
import com.jgaap.generics.EventSet;

/**
 * Extract words with 2 or 3 letters as features
 * @author Patrick Juola
 * @since 4.1
 *
 */
/**
 * N.b use of _ to mark class name beginning with digit.
 */
public class _23LetterWordEventDriver extends MNLetterWordEventDriver {
  
    @Override
    public String displayName(){
    	return "2--3 letter Words";
    }
    
    @Override
    public String tooltipText(){
    	return "Words with 2 or 3 letters";
    }
    
    @Override
    public boolean showInGUI(){
    	return false;
    }

    private MNLetterWordEventDriver theDriver;

    @Override
    public EventSet createEventSet(Document ds) {
        theDriver = new MNLetterWordEventDriver();
        theDriver.setParameter("M", "2");
        theDriver.setParameter("N", "3");
        return theDriver.createEventSet(ds);
    }
}
