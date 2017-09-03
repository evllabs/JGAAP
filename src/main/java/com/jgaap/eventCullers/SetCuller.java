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

import java.util.HashSet;
import java.util.List;

import com.jgaap.generics.EventCuller;
import com.jgaap.generics.EventCullingException;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

/**
 * Remove any duplicate events in the event sets.  This removes any
 *   frequency information for that event set.
 *   
 * Parameters: none
 * 
 * @author Amanda Kroft
 */
public class SetCuller extends EventCuller {

	@Override
	public void init(List<EventSet> eventSets) throws EventCullingException {
		//Empty
	}
	
	@Override
	public EventSet cull(EventSet eventSet) {
		HashSet<Event> currentSet = new HashSet<Event>();
		for (Event e : eventSet) {
			currentSet.add(e);
		}
		EventSet newSet = new EventSet();
		for (Event e : currentSet) {
			newSet.addEvent(e);
		}
		return newSet;
	}

	@Override
	public String displayName() {
		return "Set Culler";
	}

	@Override
	public String tooltipText() {
		return "Remove duplicate events from each event set.";
	}

	@Override
	public boolean showInGUI() {
		return false;
	}

}
