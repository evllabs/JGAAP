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
/**
 **/
package com.jgaap.generics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.google.common.collect.ImmutableMap;
import com.jgaap.JGAAPConstants;

/**
 * Transformation EventSet where transformed events are numeric, such as
 * frequency data or reaction times
 * 
 */


public abstract class NumericTransformationEventDriver extends NumericEventDriver {

	protected static ImmutableMap<String, String> getTransformationMap(String filename){
		ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
    	BufferedReader reader = new BufferedReader(new InputStreamReader(NumericTransformationEventDriver.class.getResourceAsStream(JGAAPConstants.JGAAP_RESOURCE_PACKAGE+filename)));
    	String current;
    	try {
			while((current = reader.readLine())!=null) {
				if(current.startsWith("/")){
					String[] pair = current.split("/");
					builder.put(pair[1], pair[2]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return builder.build();
	}

}
