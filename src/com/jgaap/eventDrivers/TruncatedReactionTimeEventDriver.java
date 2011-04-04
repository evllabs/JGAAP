// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.eventDrivers;

import com.jgaap.generics.Document;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.EventDriver;
import com.jgaap.jgaapConstants;
import javax.swing.*;


/**
 * Truncate lexical frequency for discrete binning 
 *
 */
public class TruncatedReactionTimeEventDriver extends EventDriver {

    @Override
    public String displayName(){
    	return "Binned Reaction Times";
    }
    
    @Override
    public String tooltipText(){
    	return "Discretized (by truncation) ELP lexical decision latencies";
    }
    
    @Override
    public boolean showInGUI(){
    	return jgaapConstants.globalParams.getParameter("language").equals("english");
    }

    @Override
    public GroupLayout getGUILayout(JPanel panel){
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
        return layout;
    }

    private EventDriver theDriver;

    @Override
    public EventSet createEventSet(Document ds) {
        theDriver = new TruncatedEventDriver();
        theDriver.setParameter("length", "2");
        theDriver.setParameter("underlyingEvents", "ReactionTimeEventDriver");
        return theDriver.createEventSet(ds);
    }
}
