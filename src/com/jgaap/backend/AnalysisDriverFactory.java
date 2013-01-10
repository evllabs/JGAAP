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

import com.jgaap.generics.AnalysisDriver;
/**
 * 
 * Instances new Analysis Drivers based on display name.
 * If parameters are also passed in the form DisplayName|name:value|name:value they are added to the Analysis Driver before it is returned
 * 
 * @author Michael Ryan
 * @since 5.0.0
 */
public class AnalysisDriverFactory {

	private static final Map<String, AnalysisDriver> analysisDrivers = loadAnalysisDrivers();
	
	private static Map<String, AnalysisDriver> loadAnalysisDrivers() {
		// Load the classifiers dynamically
		Map<String, AnalysisDriver>analysisDrivers = new HashMap<String, AnalysisDriver>();
		for(AnalysisDriver analysisDriver: AnalysisDriver.getAnalysisDrivers()){
			analysisDrivers.put(analysisDriver.displayName().toLowerCase(), analysisDriver);
		}
		return analysisDrivers;
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
