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

import com.jgaap.generics.KSkipNGramEventDriver;
import com.jgaap.util.EventSet;

/**
 * Skip Gram Event Driver for Characters
 * 
 * @author David Berdik
 */

public class KSkipNGramCharacterEventDriver extends KSkipNGramEventDriver {
	
	@Override
	public String displayName() {
		return "K Skip N Character Gram";
	}

	@Override
	public String tooltipText() {
		return "Generate character grams with N characters with K characters skipped between each character";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}
	
	@Override
	public EventSet createEventSet(char[] text) {
		return transformToKSkipNGram(new CharacterEventDriver().createEventSet(text));
	}

}
