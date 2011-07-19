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
package com.jgaap.backend;

import com.jgaap.generics.EventCuller;

import java.util.HashMap;
import java.util.Map;

public class EventCullerFactory {

	private Map<String, EventCuller> eventCullers;

	public EventCullerFactory() {
		// Load the classifiers dynamically
		eventCullers = new HashMap<String, EventCuller>();
		for(EventCuller eventCuller: AutoPopulate.getEventCullers()){
			eventCullers.put(eventCuller.displayName().toLowerCase().trim(), eventCuller);
		}
	}

	public EventCuller getEventCuller(String action) throws Exception{
		EventCuller eventCuller;
		action = action.toLowerCase().trim();
		if(eventCullers.containsKey(action)){
			eventCuller = eventCullers.get(action).getClass().newInstance();
		}else{
			throw new Exception("Event culler "+action+" not found!");
		}
		return eventCuller;
	}
}
