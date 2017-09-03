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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.jgaap.generics.FilterEventCuller;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

/**
 * 
 * Analyzes only those Events appear in all samples [as suggested by (Jockers, 2008)].
 * 
 * @author Michael Ryan
 * @since 5.0.0
 */

public class ExtremeCuller extends FilterEventCuller {

	@Override
	public Set<Event> train(List<EventSet> eventSets) {
		List<Set<Event>> eventSetsUnique = new ArrayList<Set<Event>>();
		Set<Event> uniqueEvents = new HashSet<Event>();
		Set<Event> extremeEvents = new HashSet<Event>();
		for(EventSet eventSet : eventSets){
			Set<Event> events = new HashSet<Event>();
			for(Event event : eventSet){
				events.add(event);
				uniqueEvents.add(event);
			}
			eventSetsUnique.add(events);
		}
		for(Event event : uniqueEvents){
			boolean extreme = true;
			for(Set<Event> events : eventSetsUnique){
				if(!events.contains(event)){
					extreme = false;
					break;
				}
			}
			if(extreme){
				extremeEvents.add(event);
			}
		}
		return extremeEvents;
	}

	@Override
	public String displayName() {
		return "X-treme Culler";
	}

	@Override
	public String tooltipText() {
		return "All Events that appear in all samples.";
	}

	@Override
	public String longDescription() {
		return "Analyzes only those Events appear in all samples [as suggested by (Jockers, 2008)].";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

}
