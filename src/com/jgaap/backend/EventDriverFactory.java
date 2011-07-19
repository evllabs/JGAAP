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
 * @author Michael Ryan
 * @since 5.0.0
 */

public class EventDriverFactory {

	private Map<String, EventDriver> eventDrivers;
	
	public EventDriverFactory() {
		// Load the event drivers dynamically
		eventDrivers = new HashMap<String, EventDriver>();
		for(EventDriver eventDriver : AutoPopulate.getEventDrivers()){
			eventDrivers.put(eventDriver.displayName().toLowerCase().trim(), eventDriver);
		}
	}
	
	public EventDriver getEventDriver(String action) throws Exception{
		EventDriver eventDriver;
		action = action.toLowerCase().trim();
		if(eventDrivers.containsKey(action)){
			eventDriver = eventDrivers.get(action).getClass().newInstance();
		}else{
			throw new Exception("Event Driver "+action+" not found!");
		}
		return eventDriver;
	}
}
