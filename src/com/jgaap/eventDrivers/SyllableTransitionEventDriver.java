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

import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.NGramEventDriver;
import com.jgaap.util.EventSet;

/**
 * Extract syllable bigrams as features. (Suggested by Richard Forsyth, David I
 * Holmes, and Emily K Tse, in 1998 tech report "Cicero, Sigonio and Burrows:
 * Investigating the Authenticity of the 'Consolatio'"
 * 
 * @author Patrick Juola
 * 
 */
public class SyllableTransitionEventDriver extends NGramEventDriver {

	@Override
	public String displayName() {
		return "Syllable Transitions";
	}

	@Override
	public String tooltipText() {
		return "NGrams of Syllable Numbers";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	private EventDriver theDriver = new WordSyllablesEventDriver();

	@Override
	public EventSet createEventSet(char[] text) throws EventGenerationException {
		return transformToNgram(theDriver.createEventSet(text));
	}
}
