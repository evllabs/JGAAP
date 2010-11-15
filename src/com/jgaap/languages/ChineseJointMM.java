package com.jgaap.languages;

import java.util.Vector;

import com.jgaap.jgaapConstants;
import com.jgaap.generics.Language;

import WordSegment.*;

/**
 * 
 * Representation of Chinese in jgaap using GB2123 and a joint matching technique for parsing it into words.
 * Joint matching compares the results of forward matching and reverse matching and takes the more likely result.
 * 
 * @author Michael Ryan
 *
 */
public class ChineseJointMM extends Language {

	public ChineseJointMM() {
		super("Chinese JointMM", "chinese", "GB2123");
		super.setParseable(true);
	}

	@Override
	public Vector<Character> parseLanguage(String document) {
		Vector<Character> parsedDocument = new Vector<Character>();
		WordSegment wordSegmenter = new WordSegment(jgaapConstants.libDir()
				+ "chinese_dictionary.dat", new JointMM());
		Vector<String> segmented = wordSegmenter.Segment(document);
		for (String word : segmented) {
			for (Character c : word.toCharArray()) {
				parsedDocument.add(c);
			}
		}
		return parsedDocument;
	}

}
