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

import java.util.HashMap;
import java.util.Map;

import com.jgaap.generics.EventDriver;
/**
 * 
 * Instances new Event Drivers based on display name.
 * If parameters are also passed in the form DisplayName|name:value|name:value they are added to the Event Driver before it is returned
 *
 * @author Michael Ryan
 * @since 5.0.0
 */

public class EventDrivers {

	private static final Map<String, EventDriver> eventDrivers = loadEventDrivers();
	
	private static Map<String, EventDriver> loadEventDrivers() {
		// Load the event drivers dynamically
		Map<String, EventDriver> eventDrivers = new HashMap<String, EventDriver>();
		for(EventDriver eventDriver : EventDriver.getEventDrivers()){
			eventDrivers.put(eventDriver.displayName().toLowerCase().trim(), eventDriver);
		}
		return eventDrivers;
	}
	
	public static EventDriver getEventDriver(String action) throws Exception{
		EventDriver eventDriver;
		String[] tmp = action.split("\\|", 2);
		action = tmp[0].trim().toLowerCase();
		if(eventDrivers.containsKey(action)){
			eventDriver = eventDrivers.get(action).getClass().newInstance();
		}else{
			throw new Exception("Event Driver "+action+" not found!");
		}
		if(tmp.length > 1){
			eventDriver.setParameters(tmp[1]);
		}
		return eventDriver;
	}
}
