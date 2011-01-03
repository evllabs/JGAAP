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
package com.jgaap.canonicizers;

import java.awt.Color;
import java.util.List;


import com.jgaap.generics.Canonicizer;

/**
 * Add random errors to a document by randomly changing a percentage of the
 * characters in that document.  This is completely random, except whitespace is
 * always replaced by whitespace and non-whitespace characters will be replaced with
 * capital letters.
 * @author John Noecker Jr.
 *
 */
public class AddErrors extends Canonicizer {

    private int percentErrors;
    
    @Override
    public String displayName(){
    	return "Add Errors";
    }
    
    @Override
    public String tooltipText(){
    	return "Add errors to the document.";
    }
    
    @Override
    public boolean showInGUI(){
    	return false;
    }
    
    @Override
    public Color guiColor(){
    	return null;
    }

    /**
     * Prompt the user (via JOptionPane) for the percentage of characters to replace
     * with errors.
     */
    public AddErrors() {
        super();
        /*percentErrors = Integer.parseInt((String) JOptionPane.showInputDialog(
                null, "What percentage of characters should be modified?",
                "Percent Error", JOptionPane.PLAIN_MESSAGE, null, null, "5"));*/

    }

    /**
     * Create a new canonicizer to add random transcription errors to a document
     * with a specified error rate on a per-character basis.
     * @param perc The percentage of characters which should be altered.
     */
    public AddErrors(int perc) {
        super();
        percentErrors = perc;
    }


    /**
     * Return a string representing the percentage of characters which we replaced with errors.
     * @return Formatted string representing percentage of errors added.
     */
    public String getExtraInfo() {
        return ("(" + percentErrors + "%)");
    }


    /**
     * Process the actual texts, add errors and return a new set of
     * modified texts.
     * @param procText Vector of characters to be processed.
     * @return Vector of characters after processing.
     */
    public List<Character> process(List<Character> procText) {

        int numChanges = (int) ((percentErrors / 100.0) * procText.size());

        System.out.println("Introducing errors to " + percentErrors
                + "% of document, or " + numChanges + " of " + procText.size()
                + " characters.");

        for (int i = 0; i < numChanges; i++) {
            int changePos = (int) (Math.random() * procText.size());
            if ((procText.get(changePos) == ' ')
                    || (procText.get(changePos) == '\t')
                    || (procText.get(changePos) == '\n')) {
                int newChar = (int) (Math.random() * 3);
                switch (newChar) {
                    case 0:
                        procText.set(changePos, ' ');
                        break;
                    case 1:
                        procText.set(changePos, '\t');
                        break;
                    default:
                        procText.set(changePos, '\n');
                }
            } else {
                int newChar = (int) (Math.random() * 26);
                char replaceWith = (char) (newChar + 'A');
                procText.set(changePos, replaceWith);
            }
        }
        return procText;
    }
}
