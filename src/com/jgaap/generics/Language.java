// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
package com.jgaap.generics;

import com.jgaap.jgaapConstants;

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
 * 
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
 * @param charset - the character set to read documents in with (set to null for unicode UTC-8)
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
	 * sets the global parameters language and charset to the values defined by the language
	 */
	public void apply() {
		jgaapConstants.globalObjects.put("language", this);
		jgaapConstants.globalParams.setParameter("language", this.language);
		jgaapConstants.globalParams.setParameter("charset", this.charset);
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

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String displayName(){
		return getName();
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
    	return getName().compareTo(o.getName());
    }

}
