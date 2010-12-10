package com.jgaap.canonicizers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

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
		return "Punctuation Seperater";
	}

	@Override
	public String tooltipText() {
		return "Whitespace pad all punctuation";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public Color guiColor() {
		return Color.DARK_GRAY;
	}

	@Override
	public List<Character> process(List<Character> procText) {
		List<Character> results = new ArrayList<Character>();
		int state = 0;
		for (Character character : procText) {
			if (state == 0) {
				state = getState(character);
			} else if (state == 1) {
				state = getState(character);
				if (state == 2) {
					results.add(' ');
				}
			} else {
				state = getState(character);
				if (state == 1 || state == 2) {
					results.add(' ');
				}
			}
			results.add(character);

		}
		return results;
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
