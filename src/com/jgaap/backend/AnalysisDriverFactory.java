// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
package com.jgaap.backend;

import java.util.HashMap;
import java.util.Map;

import com.jgaap.generics.AnalysisDriver;

public class AnalysisDriverFactory {

	private Map<String, AnalysisDriver> analysisDrivers;
	
	public AnalysisDriverFactory() {
		// Load the classifiers dynamically
		analysisDrivers = new HashMap<String, AnalysisDriver>();
		for(AnalysisDriver analysisDriver: AutoPopulate.getAnalysisDrivers()){
			analysisDrivers.put(analysisDriver.displayName().toLowerCase(), analysisDriver);
		}
	}
	
	public AnalysisDriver getAnalysisDriver(String action) throws Exception{
		AnalysisDriver analysisDriver;
		action = action.toLowerCase();
		if(analysisDrivers.containsKey(action)){
			analysisDriver = analysisDrivers.get(action).getClass().newInstance();
		}else{
			throw new Exception("Analysis Driver "+action+" not found!");
		}
		return analysisDriver;
	}
}
