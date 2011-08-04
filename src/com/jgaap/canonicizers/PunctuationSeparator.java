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

import java.awt.Color;

import com.jgaap.generics.Canonicizer;

/**
 * If any punctuation (defined as a non-word non-whitespace character) if next to any non-whitespace character add a space between them.
 *  
 * @author Michael Ryan
 * @since 5.0
 */
public class PunctuationSeparator extends Canonicizer {

	@Override
	public String displayName() {
		return "Punctuation Separator";
	}

	@Override
	public String tooltipText() {
		return "Whitespace pad all punctuation";
	}

	@Override
	public String longDescription() {
		return "Put a single space before and after each punctuation mark, to keep them separate from adjacent words.";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public Color guiColor() {
		return Color.DARK_GRAY;
	}

    /**
     * Separate punctuation by inserting  whitespace into argument
     *
     * @param procText
     *            Array of Characters to be processed
     * @return Array of Characters after separating punctuation with 
     *         single space.
     */
	@Override
	public char[] process(char[] procText) {
		StringBuilder stringBuilder = new StringBuilder(procText.length);
		int state = 0;
		for (Character character : procText) {
			if (state == 0) {
				state = getState(character);
			} else if (state == 1) {
				state = getState(character);
				if (state == 2) {
					stringBuilder.append(' ');
				}
			} else {
				state = getState(character);
				if (state == 1 || state == 2) {
					stringBuilder.append(' ');
				}
			}
			stringBuilder.append(character);
		}
		return stringBuilder.toString().toCharArray();
	}

	private int getState(Character character) {
		if (character.toString().matches("\\s")) {
			return 0;
		} else if (character.toString().matches("\\w")) {
			return 1;
		} else {
			return 2;
		}
	}

}
