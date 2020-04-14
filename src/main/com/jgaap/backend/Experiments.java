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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.jgaap.generics.Experiment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 
 * Instances new Event Drivers based on display name.
 * If parameters are also passed in the form DisplayName|name:value|name:value they are added to the Event Driver before it is returned
 *
 * @author Michael Ryan
 * @since 5.0.0
 */

public class Experiments {

	private static final ImmutableList<Experiment> EXPERIMENTS = loadExperiments();
	private static final ImmutableMap<String, Experiment> experiments = loadExperimentsMap();
	
	/**
	 * A read-only list of the Experiments
	 */
	public static List<Experiment> getExperiments() {
		return EXPERIMENTS;
	}

	private static ImmutableList<Experiment> loadExperiments() {
		List<Object> objects = AutoPopulate.findObjects("com.jgaap.experiments", Experiment.class);
		for(Object tmp : AutoPopulate.findClasses("com.jgaap.generics", Experiment.class)){
			objects.addAll(AutoPopulate.findObjects("com.jgaap.experiments", (Class<?>) tmp));
		}
		List<Experiment> experiments = new ArrayList<Experiment>(objects.size());
		for (Object tmp : objects) {
			experiments.add((Experiment) tmp);
		}
		Collections.sort(experiments);
		return ImmutableList.copyOf(experiments);
	}
	
	private static ImmutableMap<String, Experiment> loadExperimentsMap() {
		// Load the event drivers dynamically
		ImmutableMap.Builder<String, Experiment> builder = ImmutableMap.builder();
		for(Experiment experiment : EXPERIMENTS){
			builder.put(experiment.displayName().toLowerCase().trim(), experiment);
		}
		return builder.build();
	}
	
	public static Experiment getExperiment(String action) throws Exception{
		Experiment experiment;
		String[] tmp = action.split("\\|", 2);
		action = tmp[0].trim().toLowerCase();
		if(experiments.containsKey(action)){
			experiment = experiments.get(action).getClass().newInstance();
		}else{
			throw new Exception("Experiment "+action+" not found!");
		}
		if(tmp.length > 1){
			experiment.setParameters(tmp[1]);
		}
		return experiment;
	}
}
