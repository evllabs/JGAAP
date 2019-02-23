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
package com.jgaap.canonicizers;

import com.jgaap.generics.CanonicizationException;
import com.jgaap.generics.Canonicizer;

/**
 * Canonicizer for smashing all instances in which "I" is used as a
 * word.
 * 
 * @author David
 * @since 8.0.0
 */
public class SmashI extends Canonicizer {

	@Override
	public String displayName() {
		return "Smash I";
	}

	@Override
	public String tooltipText() {
		return "Converts all uses of \"I\" to lowercase.";
	}
	
	@Override
	public String longDescription() {
		return "Converts all uses of \"I\" as a word to lowercase.";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public char[] process(char[] procText) throws CanonicizationException {
		for (int x = 0; x < procText.length; x++) {
			// Character is not even a potential candidate for smashing if it is
			// not a capital I.
			if (procText[x] == 'I' ) {
				// Left and right flag are for indicating if whitespace was found
				// on either side of "I."
				boolean leftFlag = false;
				boolean rightFlag = false;
				
				try {
					// Check for whitespace on left side.
					leftFlag = Character.isWhitespace(procText[x - 1]);
				}
				catch (ArrayIndexOutOfBoundsException e) {
					// If the I is at the beginning of the string, set left flag to true.
					leftFlag = true;
				}
				
				try {
					// Check for whitespace on the right side.
					rightFlag = Character.isWhitespace(procText[x + 1]);
				}
				catch (ArrayIndexOutOfBoundsException e) {
					// If the I is at the end of the string, set right flag to true.
					rightFlag = true;
				}
				
				// Smash character if both flags are set to true.
				if (leftFlag && rightFlag)
					procText[x] = Character.toLowerCase(procText[x]);
			}
		}
		return procText;
	}

}
