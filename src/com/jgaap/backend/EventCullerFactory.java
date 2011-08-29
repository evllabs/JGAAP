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
import java.util.List;
import java.util.Map;

/**
 * Instances new Event Cullers based on display name.
 * If parameters are also passed in the form DisplayName|name:value|name:value they are added to the Event Culler before it is returned
 *
 * @author John Noecker
 * @since 5.0.0
 */

public class EventCullerFactory {

	private static final Map<String, EventCuller> eventCullers = loadEventCullers();

	private static Map<String, EventCuller> loadEventCullers() {
		// Load the classifiers dynamically
		Map<String, EventCuller> eventCullers = new HashMap<String, EventCuller>();
		for(EventCuller eventCuller: AutoPopulate.getEventCullers()){
			eventCullers.put(eventCuller.displayName().toLowerCase().trim(), eventCuller);
		}
		return eventCullers;
	}

	public static EventCuller getEventCuller(String action) throws Exception{
		EventCuller eventCuller;
		List<String[]> parameters = Utils.getParameters(action);
		action = parameters.remove(0)[0].toLowerCase().trim();
		if(eventCullers.containsKey(action)){
			eventCuller = eventCullers.get(action).getClass().newInstance();
		}else{
			throw new Exception("Event culler "+action+" not found!");
		}
		for(String[] parameter : parameters){
			eventCuller.setParameter(parameter[0].trim(), parameter[1].trim());
		}
		return eventCuller;
	}
}
