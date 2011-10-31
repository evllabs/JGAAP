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

import com.jgaap.generics.*;


/**
 * Extract number of characters in each word as features.
 * 
 * @see com.jgaap.eventDrivers.NaiveWordEventDriver
 */
public class WordLengthEventDriver extends NumericEventDriver {

	@Override
	public String displayName() {
		return "Word Lengths";
	}

	@Override
	public String tooltipText() {
		return "Lengths of Word-Events";
	}

	@Override
	public String longDescription() {
		return "Lengths (in characters) of Word-Events";
	}


	@Override
	public boolean showInGUI() {
		return true;
	}

	private EventDriver wordTokenizer;

	@Override
	public NumericEventSet createEventSet(Document ds) throws EventGenerationException {

		wordTokenizer = new NaiveWordEventDriver();
		EventSet es = wordTokenizer.createEventSet(ds);

		NumericEventSet newEs = new NumericEventSet();
		newEs.setAuthor(es.getAuthor());
		newEs.setNewEventSetID(es.getAuthor());

		for (int i = 0; i < es.size(); i++) {
			String s = (es.eventAt(i)).toString();
			if (s.equals("JGAAP:DOCUMENTBOUNDARY") == false) {
				int l = s.length();
				newEs.addEvent(new Event(String.valueOf(l)));
			}
		}
		return newEs;
	}

}
