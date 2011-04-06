// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
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
		StringBuilder stringBuilder = new StringBuilder();
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
