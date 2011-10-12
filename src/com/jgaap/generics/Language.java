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
package com.jgaap.generics;


/**
 * This class was designed to make the implementation of languages in jgaap as
 * simple as possible. All that is needed to make a new language is to make a
 * new class in the com.jgaap.languages package that extends
 * com.jgaap.generics.Language all that you need is to send the constructor to
 * the super class specifying the name - how users will see the language
 * displayed, language - the way the internal system and other classes will view
 * the language (all lowercase), and the charset- the character set you will
 * read in when working with this language. If the language you are working with
 * requires pre-processing before it is handed off to jgaap that can be defined
 * in the parse method you can choose to override. If you are doing parsing you
 * MUST add super.setParseable(true) to your constructor.
 * 
 * @author Michael Ryan
 * @since 4.x
 */
public abstract class Language implements Comparable<Language>, Displayable {
	private String name = "Generic";
	private String language = "generic";
	private String charset = "";
	private Boolean parseable = false;
/**
 * 
 * Define the language to read documents in and which methods of jgaap's can be used on them.
 * 
 * @param name - display name of language (user visable)
 * @param language - internal name of language (checked against valid languages list in event set and analysis)
 * @param charset - the character set to read documents in with (set to null to use system default)
 */
	public Language(String name, String language, String charset) {
		if (name != null)
			this.name = name;
		if (language != null)
			this.language = language;
		if (charset != null)
			this.charset = charset;
	}

	public Language() {
	}
	/**
	 * 
	 * parses documents after they have been read in.
	 * 
	 * @param document - The unparsed document
	 * @return - the parsed document in the format required for storage and canonicization
	 */
	public char[] parseLanguage(String document) {
		return null;
	}

	public String getCharset(){
		return charset;
	}
	
	public String getLanguage(){
		return language;
	}

	public String displayName(){
		return name;
	}
	
	public String tooltipText(){
		return "";
	}
	
	public void setParseable(Boolean parseable) {
		this.parseable = parseable;
	}

	/**
	 * Checks if there has been a parse method defined for this language.
	 * @return
	 */
	public Boolean isParseable() {
		return this.parseable;
	}
	
	public int compareTo(Language o){
    	return name.compareTo(o.name);
    }

}
