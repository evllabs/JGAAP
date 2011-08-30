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
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.jgaap.backend.EventDriverFactory;
import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;


/**
 * Transforms Event strings for other Event Strings in generation. Creates an
 * EventSet using an underlying EventDriver, then reads in a file containing
 * /from/to/ substitutions pairs. Can be used, for example, for normalization,
 * stemming, and so forth
 */
public class TransformationEventDriver extends EventDriver {

	@Override
	public String displayName() {
		return "Transformation Events";
	}

	@Override
	public String tooltipText() {
		return "Generic file-driven substitution events";
	}

	@Override
	public boolean showInGUI() {
		return false;
	}

	private EventDriver underlyingEvents;

	private String filename;

	@Override
	public EventSet createEventSet(Document ds) {
		String param;
		HashMap<String, String> transform = new HashMap<String, String>();
		boolean whitelist = false;

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
			if (param.equalsIgnoreCase("true")) {
				whitelist = true;
			}
		} else { // no underlyingfilename,
			whitelist = false;
		}

		EventSet es = underlyingEvents.createEventSet(ds);

		EventSet newEs = new EventSet();
		newEs.setAuthor(es.getAuthor());
		newEs.setNewEventSetID(es.getAuthor());

		BufferedReader br = null;

		if (filename != null) {
			try {
				FileInputStream fis = new FileInputStream(filename);
				br = new BufferedReader(new InputStreamReader(fis));

				while ((line = br.readLine()) != null) {
					if (line.length() > 0) {
						String sep = line.substring(0, 1);
						words = line.substring(1).split(sep, -1);
						if (words.length > 1) {
							transform.put(words[0], words[1]);
							System.out.println("Adding \"" + words[0]
									+ "\" : \"" + words[1] + "\"");
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

}
