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
import com.jgaap.generics.AnalysisDriver;
/**
 * 
 * Instances new Analysis Drivers based on display name.
 * If parameters are also passed in the form DisplayName|name:value|name:value they are added to the Analysis Driver before it is returned
 * 
 * @author Michael Ryan
 * @since 5.0.0
 */
public class AnalysisDrivers {

	private static final ImmutableList<AnalysisDriver> ANALYSIS_DRIVERS = loadAnalysisDrivers();
	private static final ImmutableMap<String, AnalysisDriver> analysisDrivers = loadAnalysisDriversMap();
	
	/**
	 * A read-only list of the AnalysisDrivers
	 */
	public static List<AnalysisDriver> getAnalysisDrivers() {
		return ANALYSIS_DRIVERS;
	}

	private static ImmutableList<AnalysisDriver> loadAnalysisDrivers() {
		List<Object> objects = AutoPopulate.findObjects("com.jgaap.classifiers", AnalysisDriver.class);
		for(Object tmp : AutoPopulate.findClasses("com.jgaap.generics", AnalysisDriver.class)){
			objects.addAll( AutoPopulate.findObjects("com.jgaap.classifiers", (Class<?>)tmp));
		}
		List<AnalysisDriver> analysisDrivers = new ArrayList<AnalysisDriver>(objects.size());
		for (Object tmp : objects) {
			analysisDrivers.add((AnalysisDriver) tmp);
		}
		Collections.sort(analysisDrivers);
		return ImmutableList.copyOf(analysisDrivers);
	}
	
	private static ImmutableMap<String, AnalysisDriver> loadAnalysisDriversMap() {
		// Load the classifiers dynamically
		ImmutableMap.Builder<String, AnalysisDriver> builder = ImmutableMap.builder();
		for(AnalysisDriver analysisDriver: ANALYSIS_DRIVERS){
			builder.put(analysisDriver.displayName().toLowerCase(), analysisDriver);
		}
		return builder.build();
	}
	
	public static AnalysisDriver getAnalysisDriver(String action) throws Exception{
		AnalysisDriver analysisDriver;
		String[] tmp = action.split("\\|", 2);
		action = tmp[0].trim().toLowerCase();
		if(analysisDrivers.containsKey(action)){
			analysisDriver = analysisDrivers.get(action).getClass().newInstance();
		}else{
			throw new Exception("Analysis Driver "+action+" not found!");
		}
		if(tmp.length > 1){
			analysisDriver.setParameters(tmp[1]);
		}
		return analysisDriver;
	}
}
