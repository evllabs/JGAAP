// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
package com.jgaap.languages;

import com.jgaap.generics.Language;

/**
 * 
 * Representation of computer languages (e.g. C++) in jgaap using unicode UTC-8
 * 
 * @author Patrick Juola
 *
 */
public class ComputerLanguage extends Language {

	public ComputerLanguage() {
		super("Computer Language", "computer", null);
	}

	public boolean showInGUI() {
		return false;
	}
}
