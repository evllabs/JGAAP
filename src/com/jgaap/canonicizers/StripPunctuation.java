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

import java.awt.Color;

import com.jgaap.generics.Canonicizer;

/** 
 * Strips any punctuation from the document. 
 */
public class StripPunctuation extends Canonicizer {

    private String punc = ",.?!\"\'`;:-()&$"; // Characters which will be

    @Override
    public String displayName(){
    	return "Strip Punctuation";
    }
    
    @Override
    public String tooltipText(){
    	return "Strip all punctuation characters from the text.";
    }

    @Override
    public String longDescription(){
    	return "Strip all punctuation characters (\""+punc+"\") from the text.";
    }
    
    @Override
    public boolean showInGUI(){
    	return true;
    }
    
    @Override
    public Color guiColor(){
    	return Color.GREEN;
    }
    
    // considered "punctuation"

    /**
     * Strip punctuation from input characters
     * @param procText Array of characters to be processed.
     * @return Array of processed characters.
     */
    @Override
    public char[] process(char[] procText) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < procText.length; i++) {
            if (punc.indexOf(procText[i]) == -1) { // If the character
                // is not
                // punctuation
                stringBuilder.append(procText[i]);
            } else {
                ; // Do nothing
            }
        }
        return stringBuilder.toString().toCharArray();
    }
}
