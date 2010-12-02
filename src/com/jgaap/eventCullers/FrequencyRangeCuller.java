package com.jgaap.eventCullers;

import com.jgaap.generics.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Analyze only the Xth through Yth most common events across the entire corpus.
 * N.B. This function currently uses the total occurrences across the corpus, which
 * may not be the best way to handle things.
 *
 * Parameters: minPos (the first event position to be included is minPos)
 *             numEvents (the number of events to include)
 *
 * If minPos is negative, the function returns numEvents events starting minPos positions
 * from the least common event (where minPos = -1 indicates the least common event).
 *
 * Default behavior:  50 most common words
 *
 * TODO: Verify the meaning of "most common"
 */
public class FrequencyRangeCuller extends EventCuller {
    @Override
    public List<EventSet> cull(List<EventSet> eventSets) {

        List<EventSet> results = new ArrayList<EventSet>();
        int minPos, numEvents;

        if(!getParameter("minPos").equals("")) {
            minPos = Integer.parseInt(getParameter("minPos"));
        }
        else {
            minPos = 0;
        }

        if(!getParameter("numEvents").equals("")) {
            numEvents = Integer.parseInt(getParameter("numEvents"));
        }
        else {
            numEvents = 50;
        }

        EventHistogram hist = new EventHistogram();

        for(EventSet oneSet : eventSets) {
            for(Event e : oneSet) {
                hist.add(e);
            }
        }

        List<Pair<Event, Integer>> eventFrequencies = hist.getSortedHistogram();

        if(minPos < 0) {
            minPos = eventFrequencies.size() + minPos;
        }

        // TODO: This is likely not the best way to do this, as it means for N most common events, we go through each event set N times.
        for(EventSet oneSet : eventSets) {
            EventSet newSet = new EventSet();
            for(Event e : oneSet) {
                for(int i = minPos; i < minPos + numEvents; i++) {
                    if(e.equals(eventFrequencies.get(i).getFirst())) {
                        newSet.addEvent(e);
                    }
                }
            }
            results.add(newSet);
        }
        
        return results;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String displayName() {
        return "Frequency Range Event Culler";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String tooltipText() {
        return "Analyze only the Xth through Yth most common events";  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean showInGUI() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
