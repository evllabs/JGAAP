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
 * Replaces Unicode smart/curly quotes with plain ASCII equivalents so that
 * typographic and straight quotes are treated identically during feature
 * extraction.
 */
public class SmartQuotes extends Canonicizer {

	@Override
	public String displayName() {
		return "Smart Quotes";
	}

	@Override
	public String tooltipText() {
		return "Replace Unicode smart/curly quotes with plain ASCII quote characters.";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public char[] process(char[] procText) {
		StringBuilder sb = new StringBuilder(procText.length);
		for (char c : procText) {
			switch (c) {
				case '‘': // ' left single quotation mark
				case '’': // ' right single quotation mark
				case '‛': // ‛ single high-reversed-9 quotation mark
				case '′': // ′ prime
					sb.append('\'');
					break;
				case '“': // " left double quotation mark
				case '”': // " right double quotation mark
				case '„': // „ double low-9 quotation mark
				case '‟': // ‟ double high-reversed-9 quotation mark
				case '″': // ″ double prime
				case '«': // « left-pointing double angle quotation mark
				case '»': // » right-pointing double angle quotation mark
					sb.append('"');
					break;
				case '‹': // ‹ single left-pointing angle quotation mark
				case '›': // › single right-pointing angle quotation mark
					sb.append('\'');
					break;
				default:
					sb.append(c);
			}
		}
		return sb.toString().toCharArray();
	}
}
