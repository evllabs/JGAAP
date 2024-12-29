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

import com.jgaap.generics.Canonicizer;

/**
 * Converts Typographical quotes to the standard straight quotes in an attempt to standardize all
 * quotation marks.
 */
public class ConvertCurlyQuotes extends Canonicizer{
    @Override
	public String displayName() {
		return "Convert Curly Quotes";
	}

    @Override
	public String tooltipText() {
		return "Converts Curly Quotes (Smart Quotes) into nonslanted marks to standardize them.";
	}

	@Override
	public String longDescription() {
		return "Converts Curly Quotes (Smart Quotes) into nonslanted marks to standardize them. Curly Quotes typically in Word and PDF Documents.";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

    /**
	 * Strip punctuation from input characters
	 * 
	 * @param procText
	 *            array of characters to be processed.
	 * @return array of processed characters.
	 */
	@Override
	public char[] process(char[] procText) {
		try{
        	char curly_double1 = '\u201d';
			char curly_double2 = '\u201c';
			char curly_single1 = '\u2018';
			char curly_single2 = '\u2019';
			char target_single = '\'';
			for(int i = 0; i < procText.length; i++){
				//check for "Curly" quotes
				if(procText[i] == curly_double1 || procText[i] == curly_double2){
					procText[i] = '\u0022';
				}
				else if(procText[i] == curly_single1 || procText[i] == curly_single2){
					procText[i] = target_single;
				}

			}
			return procText;
		}catch (Exception e){
			return procText;
		}

		
	}
}
