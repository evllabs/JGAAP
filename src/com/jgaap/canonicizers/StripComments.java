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

import com.jgaap.backend.API;
import com.jgaap.generics.Canonicizer;

/**
 * Strips comments from source file.
 * Eliminates comments of the following format:
 * * C-style comments        / * like this * /
 * * C++/Java-style comments // like this 
 * * Perl-style comments     # like this
 * 
 * @since 5.0
 * @author Patrick Juola
 **/
public class StripComments extends Canonicizer {

	private enum LanguageEnum {
		UNDEF,
		C,
		JAVA,
		PERL
	};

	@Override
	public String displayName() {
		return "Strip Comments";
	}

	@Override
	public String tooltipText() {
		return "Strips comments from source code";
	}

	@Override
	public String longDescription() {
		return "Strips C, C++, Java, and Perl-style comments from source code";
	}

	@Override
	public boolean showInGUI(){
        	return API.getInstance().getLanguage().displayName().equalsIgnoreCase("computer");
	}

	/**
	 * strip comments from source code given in argument
	 * 
	 * @param procText
	 *            Array of Characters to be processed
	 * @return modified Array of Characters
	 * 
	 */
	@Override
	public char[] process(char[] procText) {
		StringBuilder stringBuilder = new StringBuilder(procText.length);
		boolean isPrinting = true;
		LanguageEnum theLang = LanguageEnum.UNDEF;

		
		for (int i = 0; i < procText.length; i++) {

		// case 1 : C-style comments */
			if (theLang == LanguageEnum.UNDEF &&
			    procText[i] == '/' && procText[i+1] == '*') {
				i = i+1;
				theLang = LanguageEnum.C;
				isPrinting = false;
				continue;

			}
			
			if (theLang == LanguageEnum.C &&
			    procText[i] == '*' && procText[i+1] == '/') {
				i = i+1;
				theLang = LanguageEnum.UNDEF;
				isPrinting = true;
				continue;
			}

		// case 2 : Java-style comments */
			if (theLang == LanguageEnum.UNDEF &&
			    procText[i] == '/' && procText[i+1] == '/') {
				i = i+1;
				theLang = LanguageEnum.JAVA;
				isPrinting = false;
				continue;

			}
			
			if (theLang == LanguageEnum.JAVA &&
			    procText[i] == '\n') {
				isPrinting = true;
				theLang = LanguageEnum.UNDEF;
				continue;
			}

		// case 3 : Perl-style comments
			if (theLang == LanguageEnum.UNDEF &&
				procText[i] == '#') {
				theLang = LanguageEnum.PERL;
				isPrinting = false;
				continue;

			}
			
			if (theLang == LanguageEnum.PERL &&
			    procText[i] == '\n') {
				theLang = LanguageEnum.UNDEF;
				isPrinting = true;
				continue;
			}



			if (isPrinting) {
				//System.out.println("Appending "+procText[i]);
				stringBuilder.append(procText[i]);
			}
		}

		return stringBuilder.toString().toCharArray();
	}
}
