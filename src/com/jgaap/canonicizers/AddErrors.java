/**
 **/
package com.jgaap.canonicizers;

import java.awt.Color;

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

	private int percentErrors;

	@Override
	public String displayName() {
		return "Add Errors";
	}

	@Override
	public String tooltipText() {
		return "Add errors to the document.";
	}

	@Override
	public boolean showInGUI() {
		return false;
	}

	@Override
	public Color guiColor() {
		return null;
	}

	/**
	 * Prompt the user (via JOptionPane) for the percentage of characters to
	 * replace with errors.
	 */
	public AddErrors() {
		super();
		/*
		 * percentErrors = Integer.parseInt((String)
		 * JOptionPane.showInputDialog( null,
		 * "What percentage of characters should be modified?", "Percent Error",
		 * JOptionPane.PLAIN_MESSAGE, null, null, "5"));
		 */

	}

	/**
	 * Create a new canonicizer to add random transcription errors to a document
	 * with a specified error rate on a per-character basis.
	 * 
	 * @param perc
	 *            The percentage of characters which should be altered.
	 */
	public AddErrors(int perc) {
		super();
		percentErrors = perc;
	}

	/**
	 * Return a string representing the percentage of characters which we
	 * replaced with errors.
	 * 
	 * @return Formatted string representing percentage of errors added.
	 */
	public String getExtraInfo() {
		return ("(" + percentErrors + "%)");
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

		int numChanges = (int) ((percentErrors / 100.0) * procText.length);

		System.out.println("Introducing errors to " + percentErrors
				+ "% of document, or " + numChanges + " of " + procText.length
				+ " characters.");

		for (int i = 0; i < numChanges; i++) {
			int changePos = (int) (Math.random() * procText.length);
			if ((procText[changePos] == ' ') || (procText[changePos] == '\t')
					|| (procText[changePos] == '\n')) {
				int newChar = (int) (Math.random() * 3);
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
				int newChar = (int) (Math.random() * 26);
				char replaceWith = (char) (newChar + 'A');
				procText[changePos] = replaceWith;
			}
		}
		return procText;
	}
}
