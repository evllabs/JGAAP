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
package com.jgaap.canonicizers;

import java.util.Random;

import org.apache.log4j.Logger;

import com.jgaap.generics.Canonicizer;

/**
 * Add random errors to a document by randomly changing a percentage of the
 * characters in that document. This is completely random, except whitespace is
 * always replaced by whitespace and non-whitespace characters will be replaced
 * with capital letters.
 * 
 * @author John Noecker Jr.
 * 
 */
public class AddErrors extends Canonicizer {

	private static Logger logger = Logger.getLogger(AddErrors.class);

	@Override
	public String displayName() {
		return "Add Errors";
	}

	@Override
	public String tooltipText() {
		return "Add errors to the document.";
	}

	@Override
	public String longDescription() {
		return "Add random character replacement errors to the document; useful for evaluating method effectiveness in the presence of OCR-type errors.";
	}

	@Override
	public boolean showInGUI() {
		return false;
	}

	public AddErrors() {
		addParams("percenterror", "Percent Error", "0", new String[] { "0", "1", "2", "3", "4", "5", "10", "15", "20",
				"50" }, false);
	}

	/**
	 * Process the actual texts, add errors and return a new set of modified
	 * texts.
	 * 
	 * @param procText
	 *            Array of characters to be processed.
	 * @return Array of characters after processing.
	 */
	public char[] process(char[] procText) {
		Random random = new Random();
		int percentErrors = getParameter("percenterror", 0);
		int numChanges = (int) ((percentErrors / 100.0) * procText.length);

		logger.debug("Introducing errors to " + percentErrors + "% of document, or " + numChanges + " of "
				+ procText.length + " characters.");

		for (int i = 0; i < numChanges; i++) {
			int changePos = random.nextInt(procText.length);
			if ((procText[changePos] == ' ') || (procText[changePos] == '\t') || (procText[changePos] == '\n')) {
				int newChar = random.nextInt(3);
				switch (newChar) {
				case 0:
					procText[changePos] = ' ';
					break;
				case 1:
					procText[changePos] = '\t';
					break;
				default:
					procText[changePos] = '\n';
				}
			} else {
				int newChar = random.nextInt(26);
				char replaceWith = (char) (newChar + 'A');
				procText[changePos] = replaceWith;
			}
		}
		return procText;
	}
}
