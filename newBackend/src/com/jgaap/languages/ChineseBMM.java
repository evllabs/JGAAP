package com.jgaap.languages;

import java.util.ArrayList;
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
	public List<Character> parseLanguage(String document) {
		List<Character> parsedDocument = new ArrayList<Character>();
		WordSegment wordSegmenter = new WordSegment(jgaapConstants.libDir()
				+ "chinese_dictionary.dat", new BMM());
		List<String> segmented = wordSegmenter.Segment(document);
		for (String word : segmented) {
			for (Character c : word.toCharArray()) {
				parsedDocument.add(c);
			}
		}
		return parsedDocument;
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

}
