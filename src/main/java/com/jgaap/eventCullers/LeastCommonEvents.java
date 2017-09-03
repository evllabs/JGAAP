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

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.jgaap.generics.FilterEventCuller;
import com.jgaap.util.Event;
import com.jgaap.util.EventHistogram;
import com.jgaap.util.EventSet;
import com.jgaap.util.Pair;

/**
 * Sort out the N most common events (by average frequency) across all event
 * sets
 */
public class LeastCommonEvents extends FilterEventCuller {

	@Override
	public Set<Event> train(List<EventSet> eventSets) {
		int numEvents = getParameter("numEvents", 50);
		EventHistogram histogram = new EventHistogram();
		for(EventSet eventSet : eventSets) {
			for(Event event : eventSet) {
				histogram.add(event);
			}
		}
		List<Pair<Event, Integer>> eventFrequencies = histogram.getSortedHistogram();
		Collections.reverse(eventFrequencies);
		ImmutableSet.Builder<Event> builder = ImmutableSet.builder();
		int count = 0;
		for(Pair<Event, Integer> eventPair : eventFrequencies) {
			count++;
			builder.add(eventPair.getFirst());
			if(numEvents<=count) {
				break;
			}
		}
		return builder.build();
	}

	public LeastCommonEvents() {
		addParams("numEvents", "N", "50", new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "15", "20",
				"25", "30", "40", "45", "50", "75", "100", "150", "200" }, true);
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
		return "Analyze only the N rarest events across all documents; the value of N is passed as a parameter (numEvents). ";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

}
