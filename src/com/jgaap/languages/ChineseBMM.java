package com.jgaap.languages;

import java.util.List;

import com.jgaap.jgaapConstants;
import com.jgaap.generics.Language;

import WordSegment.*;
/**
 * A Chinese representation that uses GB2123 and backwards word matching.
 * 
 * @author Michael Ryan
 */
public class ChineseBMM extends Language {

	public ChineseBMM() {
		super("Chinese BMM", "chinese", "GB2123");
		super.setParseable(true);
	}

	@Override
	public char[] parseLanguage(String document) {
		StringBuilder stringBuilder = new StringBuilder();
		WordSegment wordSegmenter = new WordSegment(jgaapConstants.libDir()
				+ "chinese_dictionary.dat", new BMM());
		List<String> segmented = wordSegmenter.Segment(document);
		for (String word : segmented) {
			stringBuilder.append(word);
		}
		return stringBuilder.toString().toCharArray();
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

}
