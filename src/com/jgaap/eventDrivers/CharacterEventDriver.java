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

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;


/**
 * This event set is all individual characters, as determined by the
 * preprocessing applied in the previous stage.
 **/
public class CharacterEventDriver extends EventDriver {
	/**
	 * test using files named on command line
	 * 
	 * @param args
	 *            files to process
	 */

	@Override
	public String displayName() {
		return "Characters";
	}

	@Override
	public String tooltipText() {
		return "UNICODE Characters";
	}

	@Override
	public String longDescription() {
		return "UNICODE Characters";
	}


	@Override
	public boolean showInGUI() {
		return true;
	}

	/**
	 * Create sequence of characters from document set.
	 * 
	 * @param document
	 *            document of interest
	 */
	@Override
	public EventSet createEventSet(Document document) {
		EventSet es = new EventSet(document.getAuthor());
		char[] cd = document.getProcessedText();
		for (int j = 0; j < cd.length; j++) {
			es.addEvent(new Event(cd[j]));
		}
		return es;
	}

}
