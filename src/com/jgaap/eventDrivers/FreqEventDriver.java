/**
 **/
package com.jgaap.eventDrivers;

import com.jgaap.jgaapConstants;
import com.jgaap.generics.Document;
import com.jgaap.generics.NumericEventSet;

/**
 * Corpus frequencies taken from English Lexicon Project. Converts each word
 * (using table) to the the (log-scaled) frequency in which that word appears in
 * the general purpose HAL corpus as recorded in the ELP database. Obviously
 * English-only, and obviously incomplete; words that are not in the database
 * are silently removed.
 */

public class FreqEventDriver extends NumericTransformationEventDriver {

    @Override
    public String displayName(){
    	return "Lexical Frequencies";
    }
    
    @Override
    public String tooltipText(){
    	return "(Log Scaled) HAL frequencies from English Lexicon Project";
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
                + "/ELPfreq.dat");

        return theDriver.createEventSet(ds);
    }

}
