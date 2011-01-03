package com.jgaap.languages;

import com.jgaap.generics.Language;


/**
 * 
 * A generic chinese representation using GB2123 that does not parse the documents.
 * 
 * @author Michael Ryan
 *
 */
public class Chinese extends Language {
	public Chinese() {
		super("Chinese", "chinese", "GB2123");
	}

	public boolean showInGUI() {
		return true;
	}
}
