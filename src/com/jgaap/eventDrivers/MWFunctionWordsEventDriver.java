// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.eventDrivers;

import com.jgaap.jgaapConstants;
import com.jgaap.generics.Document;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;
import javax.swing.*;


/**
 * Uses function words as defined by Mosteller-Wallace in their Federalist
 * papers study.
 */
public class MWFunctionWordsEventDriver extends EventDriver {


    @Override
    public String displayName(){
    	return "MW Function Words";
    }
    
    @Override
    public String tooltipText(){
    	return "Function Words from Mosteller-Wallace";
    }

    @Override
    public String longDescription(){
    	return "Function Words from Mosteller-Wallace's study of the Federalist Papers";
    }
    
    @Override
    public boolean showInGUI(){
    	return true;
    }

        @Override
        public GroupLayout getGUILayout(JPanel panel){
            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
            return layout;
        }

    /** Static field for efficiency */
    // private static EventDriver e;
    // Peter Jan 21 2010 ^ if this is static, then why are we setting it in the constructor?
    private EventDriver e;

    /** Default constructor. Sets parameters for WhiteList */
    // Peter Jan 21 2010 - this needs to be public for autopopulator
    public MWFunctionWordsEventDriver() {
        e = new WhiteListEventDriver();
        e.setParameter("underlyingEvents", "NaiveWordEventDriver");
        e.setParameter("filename", jgaapConstants.libDir()
                + "/MWfunctionwords.dat");
    }

    /** Creates EventSet using M-W function word list */
    @Override
    public EventSet createEventSet(Document ds) {
        return e.createEventSet(ds);
    }

}
