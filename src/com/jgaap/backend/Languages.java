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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.jgaap.generics.Language;
/**
 * 
 * Instances new Language based on display name.
 *
 * @author Michael Ryan
 * @since 5.0.0
 */

public class Languages {

	private static ImmutableList<Language> LANGUAGES = loadLanguages();
	private static final Map<String, Language> languages = loadLanguagesMap();
	
	/**
	 * A read-only list of the Languages
	 */
	public static List<Language> getLanguages() {
		return LANGUAGES;
	}

	private static ImmutableList<Language> loadLanguages() {
		List<Object> objects = AutoPopulate.findObjects("com.jgaap.languages", Language.class);
		for(Object tmp : AutoPopulate.findClasses("com.jgaap.generics", Language.class)){
			objects.add(tmp);
		}
		List<Language> languages = new ArrayList<Language>(objects.size());
		for (Object tmp : objects) {
			languages.add((Language)tmp);
		}
		Collections.sort(languages);
		return ImmutableList.copyOf(languages);
	}
	
	private static Map<String, Language> loadLanguagesMap() {
		// Load the classifiers dynamically
		ImmutableMap.Builder<String, Language> builder = ImmutableMap.builder();
		for(Language language : LANGUAGES){
			builder.put(language.displayName().toLowerCase().trim(), language);
		}
		return builder.build();
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
