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
    public String longDescription() {
        return "Analyze only the N rarest events across all documents; " +
               "the value of N is passed as a parameter (numEvents). ";
    }

    @Override
    public boolean showInGUI() {
        return true;
    }
}
