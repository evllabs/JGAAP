package com.jgaap.languages;

import java.util.Vector;

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
	public Vector<Character> parseLanguage(String document) {
		Vector<Character> parsedDocument = new Vector<Character>();
		WordSegment wordSegmenter = new WordSegment(jgaapConstants.libDir()
				+ "chinese_dictionary.dat", new FMM());
		Vector<String> segmented = wordSegmenter.Segment(document);
		for (String word : segmented) {
			for (Character c : word.toCharArray()) {
				parsedDocument.add(c);
			}
		}
		return parsedDocument;
	}

}
