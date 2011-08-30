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
package com.jgaap.eventDrivers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.jgaap.backend.EventDriverFactory;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.NumericEventSet;



/**
 * TransformationEventSet where transformed events are numeric, such as
 * frequency data or reaction times
 * 
 * @see TransformationEventSet
 */

import com.jgaap.generics.*;


public class NumericTransformationEventDriver extends NumericEventDriver {

	@Override
	public String displayName() {
		return "Numeric Transformation Events";
	}

	@Override
	public String tooltipText() {
		return "Filtered Numeric Transformation Events";
	}

	@Override
	public String longDescription() {
		return "Modified TransformationEventDriver where the results of the transformation are numbers, such as frequencies or reaction times"; 
	}


	@Override
	public boolean showInGUI() {
		return false;
	}

	private EventDriver underlyingEvents;

	private String filename;

	@Override
	public NumericEventSet createEventSet(Document ds) {
		String param;
		HashMap<String, String> transform = new HashMap<String, String>();
		boolean whitelist = true;

		String line;
		String[] words;

		if (!(param = (getParameter("underlyingEvents"))).equals("")) {
			try {
				underlyingEvents = EventDriverFactory.getEventDriver(param);
			} catch (Exception e) {
				System.out.println("Error: cannot create EventDriver " + param);
				System.out.println(" -- Using NaiveWordEventSet");
				underlyingEvents = new NaiveWordEventDriver();
			}
		} else { // no underlyingEventsParameter, use NaiveWordEventSet
			underlyingEvents = new NaiveWordEventDriver();
		}

		if (!(param = (getParameter("filename"))).equals("")) {
			filename = param;
		} else { // no underlyingfilename,
			filename = null;
		}

		if (!(param = (getParameter("implicitWhiteList"))).equals("")) {
			if (param.equalsIgnoreCase("false")) {
				whitelist = false;
			}
		} else { // default is implicit whitelist to eliminate invalid events
			whitelist = true;
		}

		EventSet es = underlyingEvents.createEventSet(ds);

		NumericEventSet newEs = new NumericEventSet();
		newEs.setAuthor(es.getAuthor());
		newEs.setNewEventSetID(es.getAuthor());

		BufferedReader br = null;

		if (filename != null) {
			try {
				InputStream is = getClass().getResourceAsStream(filename);
				br = new BufferedReader(new InputStreamReader(is));

				while ((line = br.readLine()) != null) {
					if (line.length() > 0) {
						String sep = line.substring(0, 1);

						words = line.substring(1).split(sep);

						if (words.length > 1) {
							if (!isNumber(words[1])) {
								System.err.println("Warning : " + words[0]
										+ "->" + words[1]
										+ " is not a number, omitted.");
							} else {
								transform.put(words[0], words[1]);
							}
						}
					}
				}

			} catch (IOException e) {
				// catch io errors from FileInputStream or readLine()
				System.out.println("Cannot open/read " + filename);
				System.out.println("IOException error! " + e.getMessage());
				transform = null;
			} finally {
				// if the file opened okay, make sure we close it
				if (br != null) {
					try {
						br.close();
					} catch (IOException ioe) {
					}
				}
			}
		} else {
			transform = null;
		}

		for (Event e : es) {
			String s = e.toString();
			if (transform == null) {
				newEs.addEvent(e);
			} else if (transform.containsKey(s)) {
				String newS = transform.get(s);
				if (newS.length() > 0) {
					newEs.addEvent(new Event(newS));
				}
			} else // s is not in transformation list
			if (whitelist == false) {
				// add only if no implicit whitelisting
				newEs.addEvent(e);
			} // otherwise add nothing
		}
		return newEs;
	}

	private boolean isNumber(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (Exception e) {
			/* any exception means it's not a number */
			return false;
		}

	}

}
