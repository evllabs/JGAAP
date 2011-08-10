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
import java.util.List;
import java.util.Map;

import com.jgaap.generics.AnalysisDriver;
/**
 * @author Michael Ryan
 * @since 5.0.0
 */
public class AnalysisDriverFactory {

	private static final Map<String, AnalysisDriver> analysisDrivers = loadAnalysisDrivers();
	
	private static Map<String, AnalysisDriver> loadAnalysisDrivers() {
		// Load the classifiers dynamically
		Map<String, AnalysisDriver>analysisDrivers = new HashMap<String, AnalysisDriver>();
		for(AnalysisDriver analysisDriver: AutoPopulate.getAnalysisDrivers()){
			analysisDrivers.put(analysisDriver.displayName().toLowerCase(), analysisDriver);
		}
		return analysisDrivers;
	}
	
	public static AnalysisDriver getAnalysisDriver(String action) throws Exception{
		AnalysisDriver analysisDriver;
		List<String[]> parameters = Utils.getParameters(action);
		action = parameters.remove(0)[0].toLowerCase().trim();
		if(analysisDrivers.containsKey(action)){
			analysisDriver = analysisDrivers.get(action).getClass().newInstance();
		}else{
			throw new Exception("Analysis Driver "+action+" not found!");
		}
		for(String[] parameter : parameters){
			analysisDriver.setParameter(parameter[0].trim(), parameter[1].trim());
		}
		return analysisDriver;
	}
}
