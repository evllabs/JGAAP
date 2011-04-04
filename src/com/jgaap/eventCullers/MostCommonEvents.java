// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
package com.jgaap.eventCullers;

import com.jgaap.generics.EventCuller;
import com.jgaap.generics.EventSet;

import java.util.List;

/**
 * Sort out the N most common events (by average frequency) across all event sets
 */
public class MostCommonEvents extends EventCuller {

    @Override
    public List<EventSet> cull(List<EventSet> eventSets) {

        EventCuller underlyingCuller = new FrequencyRangeCuller();
        underlyingCuller.setParameter("minPos", 0);

        if(getParameter("numEvents").equals("")) {
            underlyingCuller.setParameter("numEvents", 50);
        }
        else {
            underlyingCuller.setParameter("numEvents", getParameter("numEvents"));
        }

        return underlyingCuller.cull(eventSets);  
    }

    @Override
    public String displayName() {
        return "Most Common Events";  
    }

    @Override
    public String tooltipText() {
        return "Analyze only the N most common events across all documents";
    }

    @Override
    public boolean showInGUI() {
        return true;
    }
}
