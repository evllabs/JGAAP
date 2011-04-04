// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
package com.jgaap.languages;

import com.jgaap.generics.Language;
/**
 * 
 * Representation of arabic in jgaap using ISO-8859-6
 * 
 * @author Michael Ryan
 *
 */
public class Arabic extends Language {
	public Arabic() {
		super("Arabic", "arabic", "ISO-8859-6");
	}

	public boolean showInGUI() {
		return true;
	}
}
