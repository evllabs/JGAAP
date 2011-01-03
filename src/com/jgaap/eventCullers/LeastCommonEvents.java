package com.jgaap.eventCullers;

import com.jgaap.generics.EventCuller;
import com.jgaap.generics.EventSet;

import java.util.List;

/**
 * Sort out the N most common events (by average frequency) across all event sets
 */
public class LeastCommonEvents extends EventCuller {

    @Override
    public List<EventSet> cull(List<EventSet> eventSets) {

        EventCuller underlyingCuller = new FrequencyRangeCuller();
        underlyingCuller.setParameter("minPos", -1);

        if(getParameter("numEvents").equals("")) {
            underlyingCuller.setParameter("minPos", -50);
            underlyingCuller.setParameter("numEvents", 50);
        }
        else {
            underlyingCuller.setParameter("minPos", -Integer.parseInt(getParameter("numEvents")));
            underlyingCuller.setParameter("numEvents", getParameter("numEvents"));
        }

        return underlyingCuller.cull(eventSets);
    }

    @Override
    public String displayName() {
        return "Least Common Events";
    }

    @Override
    public String tooltipText() {
        return "Analyze only the N least common events across all documents";
    }

    @Override
    public boolean showInGUI() {
        return true;
    }
}
