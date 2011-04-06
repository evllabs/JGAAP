// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.canonicizers;

import java.awt.Color;

import com.jgaap.generics.Canonicizer;

/**
 * Strips all non-punctuation from the document.
 * 
 * @since 4.1
 */
public class StripNonPunc extends Canonicizer {

	private String punc = ",.?!\"\'`;:-()&$"; // Characters which will be

	// considered "punctuation"

	@Override
	public String displayName() {
		return "Strip AlphaNumeric";
	}

	@Override
	public String tooltipText() {
		return "Strip all non-punctuation/non-whitespace characters from the text.";
	}

	@Override
	public String longDescription() {
		return "Strip all non-whitespace characters that are not punctuation marks (one of \""+punc+"\" from the text.";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public Color guiColor() {
		return Color.YELLOW;
	}

	/**
	 * Strip non-punctuation from input characters
	 * 
	 * @param procText
	 *            Array of characters to be processed.
	 * @return Array of processed characters.
	 */
	@Override
	public char[] process(char[] procText) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < procText.length; i++) {
			if (punc.indexOf(procText[i]) == -1
					&& !Character.isWhitespace(procText[i])) {
				// If the character is not punctuation
				; // Do nothing
			} else {
				stringBuilder.append(procText[i]);
			}
		}
		return stringBuilder.toString().toCharArray();
	}
}
