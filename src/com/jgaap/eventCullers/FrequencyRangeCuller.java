/*
 * JGAAP -- a graphical program for stylometric authorship attribution
 * Copyright (C) 2009,2011 by Patrick Juola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
    public List<EventSet> cull(List<EventSet> eventSets) throws EventCullingException {

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
        
        // If the user attempts to return too many events
        if(minPos + numEvents > eventFrequencies.size()) {
        	throw new EventCullingException("The requested frequency range is too broad.  This event set contains only " + eventFrequencies.size() + " elements\nYou requested elements " + (minPos + 1) + " through " + (minPos + numEvents + 1));
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
    public String longDescription() {
        return "Analyze only events in a frequency across all documents " +
          "(e.g., the 5th through 99th most common words in the corpus). " +
          "The parameter minPos is the first event position to be included e.g. 5th in the example above), " +
          "while numEvents is the number of events to include (e.g. 95). " +
          "If minPos is negative, the function returns numEvents events starting minPos positions from the least common event (where minPos = -1 indicates the least common event).";
    }


    @Override
    public boolean showInGUI() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
