package com.jgaap.languages;

import java.util.List;

import com.jgaap.jgaapConstants;
import com.jgaap.generics.Language;

import WordSegment.*;

/**
 * 
 * A Chinese representation that uses GB2123 and forwards word matching.
 * 
 * @author Michael Ryan
 *
 */
public class ChineseFMM extends Language {

	public ChineseFMM() {
		super("Chinese FMM", "chinese", "GB2123");
		super.setParseable(true);
	}

	@Override
	public char[] parseLanguage(String document) {
		StringBuilder stringBuilder = new StringBuilder();
		WordSegment wordSegmenter = new WordSegment(jgaapConstants.libDir()
				+ "chinese_dictionary.dat", new FMM());
		List<String> segmented = wordSegmenter.Segment(document);
		for (String word : segmented) {
			stringBuilder.append(word);
		}
		return stringBuilder.toString().toCharArray();
	}

	public boolean showInGUI() {
		return true;
	}
}
