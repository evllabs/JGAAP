package com.jgaap.languages;

import com.jgaap.generics.Language;

/**
 * 
 * Representation of english in jgaap using unicode UTC-8
 * 
 * @author Michael Ryan
 *
 */
public class English extends Language {

	public English() {
		super("English", "english", null);
	}

	public boolean showInGUI() {
		return true;
	}
}
