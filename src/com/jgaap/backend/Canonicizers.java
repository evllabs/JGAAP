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
import com.jgaap.generics.Canonicizer;
/**
 * 
 * Instances new Canonicizers based on display name.
 *
 * @author Michael Ryan
 * @since 5.0.0
 */

public class Canonicizers {
	
	private static final ImmutableList<Canonicizer> CANONICIZERS = loadCanonicizers();
	private static final ImmutableMap<String, Canonicizer> canonicizers = loadCanonicizersMap();
	
	/**
	 * A read-only list of the Canonicizers
	 */
	public static List<Canonicizer> getCanonicizers() {
		return CANONICIZERS;
	}

	private static ImmutableList<Canonicizer> loadCanonicizers() {
		List<Object> objects = AutoPopulate.findObjects("com.jgaap.canonicizers", Canonicizer.class);
		for(Object tmp : AutoPopulate.findClasses("com.jgaap.generics", Canonicizer.class)){
			objects.addAll(AutoPopulate.findObjects("com.jgaap.canonicizers", (Class<?>)tmp));
		}
		List<Canonicizer> canonicizers = new ArrayList<Canonicizer>(objects.size());
		for (Object tmp : objects) {
			canonicizers.add((Canonicizer)tmp);
		}
		Collections.sort(canonicizers);
		return ImmutableList.copyOf(canonicizers);
	}
	
	private static ImmutableMap<String, Canonicizer> loadCanonicizersMap() {
		// Load the canonicizers dynamically
		ImmutableMap.Builder<String, Canonicizer> builder = ImmutableMap.builder();
		for(Canonicizer canon : CANONICIZERS){
			builder.put(canon.displayName().toLowerCase().trim(), canon);
		}	
		return builder.build();
	}
	
	public static Canonicizer getCanonicizer(String action) throws Exception{
		Canonicizer canonicizer;
		String[] tmp = action.split("\\|", 2);
		action = tmp[0].trim().toLowerCase();
		if(canonicizers.containsKey(action)){
			canonicizer = canonicizers.get(action).getClass().newInstance();
		}else{
			throw new Exception("Canonicizer "+action+" not found!");
		}
		if(tmp.length > 1){
			canonicizer.setParameters(tmp[1]);
		}
		return canonicizer;
	}
	
}
