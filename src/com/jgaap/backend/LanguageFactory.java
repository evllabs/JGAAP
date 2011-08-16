/*
 * JGAAP -- a graphical program for stylometric authorship attribution
 * Copyright (C) 2009,2011 by Patrick Juola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jgaap.backend;

import java.util.HashMap;
import java.util.Map;

import com.jgaap.generics.Language;
/**
 * @author Michael Ryan
 * @since 5.0.0
 */

public class LanguageFactory {

	private static final Map<String, Language> languages = loadLanguages();
	
	private static Map<String, Language> loadLanguages() {
		// Load the classifiers dynamically
		Map<String, Language>languages = new HashMap<String, Language>();
		for(Language language : AutoPopulate.getLanguages()){
			languages.put(language.displayName().toLowerCase().trim(), language);
		}
		return languages;
	}
	
	public static Language getLanguage(String action) throws Exception{
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
