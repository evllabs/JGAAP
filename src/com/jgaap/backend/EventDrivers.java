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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
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

	private static final ImmutableList<EventDriver> EVENT_DRIVERS = loadEventDrivers();
	private static final ImmutableMap<String, EventDriver> eventDrivers = loadEventDriversMap();
	
	/**
	 * A read-only list of the EventDrivers
	 */
	public static List<EventDriver> getEventDrivers() {
		return EVENT_DRIVERS;
	}

	private static ImmutableList<EventDriver> loadEventDrivers() {
		List<Object> objects = AutoPopulate.findObjects("com.jgaap.eventDrivers", EventDriver.class);
		for(Object tmp : AutoPopulate.findClasses("com.jgaap.generics", EventDriver.class)){
			objects.addAll(AutoPopulate.findObjects("com.jgaap.eventDrivers", (Class<?>) tmp));
		}
		List<EventDriver> eventDrivers = new ArrayList<EventDriver>(objects.size());
		for (Object tmp : objects) {
			eventDrivers.add((EventDriver) tmp);
		}
		Collections.sort(eventDrivers);
		return ImmutableList.copyOf(eventDrivers);
	}
	
	private static ImmutableMap<String, EventDriver> loadEventDriversMap() {
		// Load the event drivers dynamically
		ImmutableMap.Builder<String, EventDriver> builder = ImmutableMap.builder();
		for(EventDriver eventDriver : EVENT_DRIVERS){
			builder.put(eventDriver.displayName().toLowerCase().trim(), eventDriver);
		}
		return builder.build();
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
