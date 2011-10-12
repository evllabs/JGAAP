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
package com.jgaap.languages;

import java.util.List;

import com.jgaap.generics.Language;

import WordSegment.*;

/**
 * 
 * A Chinese representation that uses GB2312 and forwards word matching.
 * 
 * @author Michael Ryan
 *
 */
public class ChineseFMM extends Language {

	public ChineseFMM() {
		super("Chinese FMM (GB2312)", "chinese", "GB2312");
		super.setParseable(true);
	}

	@Override
	public char[] parseLanguage(String document) {
		StringBuilder stringBuilder = new StringBuilder(document.length());
		WordSegment wordSegmenter = new WordSegment(new FMM());
		List<String> segmented = wordSegmenter.segment(document);
		for (String word : segmented) {
			stringBuilder.append(word).append(" ");
		}
		return stringBuilder.toString().toCharArray();
	}

	public boolean showInGUI() {
		return true;
	}
}
