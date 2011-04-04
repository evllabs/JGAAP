// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
package com.jgaap.backend;

import java.util.HashMap;
import java.util.Map;

import com.jgaap.generics.Language;

public class LanguageFactory {

	private Map<String, Language> languages;
	
	public LanguageFactory() {
		// Load the classifiers dynamically
		languages = new HashMap<String, Language>();
		for(Language language : AutoPopulate.getLanguages()){
			languages.put(language.getName().toLowerCase().trim(), language);
		}
	}
	
	public Language getLanguage(String action) throws Exception{
		Language language;
		action = action.toLowerCase().trim();
		if(languages.containsKey(action)){
			language = languages.get(action).getClass().newInstance();
		}else {
			throw new Exception("Language "+action+" was not found!");
		}
		return language;
	}
}
