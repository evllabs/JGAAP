/**
 *   JGAAP -- Java Graphical Authorship Attribution Program
 *   Copyright (C) 2009 Patrick Juola
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation under version 3 of the License.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
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
	public boolean showInGUI() {
		return true;
	}

	private EventDriver wordTokenizer;

	@Override
	public NumericEventSet createEventSet(Document ds) {

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
