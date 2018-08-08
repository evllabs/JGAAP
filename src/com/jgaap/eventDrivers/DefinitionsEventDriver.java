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
package com.jgaap.eventDrivers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.jgaap.JGAAPConstants;
import com.jgaap.backend.API;
import com.jgaap.canonicizers.StripPunctuation;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

/**
 * @author Darren Vescovi
 * 
 *         replaces words with their definitions
 * 
 *         ENGLISH ONLY EVENT DRIVER
 * 
 *         NOTE:this is event driver uses packages that can only be used for
 *         non-commercial implementation specifically the
 *         edu.mit.jwi_2.1.5_jdk.jar
 */
public class DefinitionsEventDriver extends EventDriver {

	private static Logger logger = Logger.getLogger(DefinitionsEventDriver.class);

	private static ImmutableMap<String, Integer> table = ImmutableMap.<String, Integer> builder()
			.put("NN", Integer.valueOf(1)).put("NNS", Integer.valueOf(1)).put("NNP", Integer.valueOf(1))
			.put("NNPS", Integer.valueOf(1)).put("JJ", Integer.valueOf(3)).put("JJR", Integer.valueOf(3))
			.put("JJS", Integer.valueOf(3)).put("RB", Integer.valueOf(4)).put("RBS", Integer.valueOf(4))
			.put("RBR", Integer.valueOf(4)).put("VB", Integer.valueOf(2)).put("VBD", Integer.valueOf(2))
			.put("VBG", Integer.valueOf(2)).put("VBN", Integer.valueOf(2)).put("VBP", Integer.valueOf(2))
			.put("VBZ", Integer.valueOf(2)).build();

	private static ImmutableSet<String> stopWords = ImmutableSet.<String> builder().add("the").add("of").add("to")
			.add("and").add("a").add("in").add("is").add("it").add("you").add("that").add("he").add("was").add("for")
			.add("on").add("are").add("with").add("as").add("i").add("his").add("they").add("be").add("at").add("have")
			.add("this").add("or").add("had").add("by").add("but").add("some").add("what").add("there").add("we")
			.add("other").add("were").add("your").add("an").add("do").add("if").build();

	private static ImmutableMap<String, String> nouns = ImmutableMap.<String, String> builder()
			.put("alumni", "alumnus").put("analyses", "analysis").put("antennae", "antenna").put("antennas", "antenna")
			.put("appendices", "appendix").put("axes", "axis").put("bacteria", "bacterium").put("bases", "basis")
			.put("beaux", "beau").put("bureaux", "bureau").put("bureaus", "bureau").put("children", "child")
			.put("corpora", "corpus").put("corpuses", "corpus").put("crises", "crisis").put("criteria", "criterion")
			.put("curricula", "curriculum").put("data", "datum").put("deer", "deer").put("diagnoses", "diagnosis")
			.put("ellipses", "ellipsis").put("fish", "fish").put("foci", "focus").put("focuses", "focus")
			.put("feet", "foot").put("formulae", "formula").put("formulas", "formula").put("fungi", "fungus")
			.put("funguses", "fungus").put("genera", "genus").put("geese", "goose").put("hypotheses", "hypothesis")
			.put("indices", "index").put("indexes", "index").put("lice", "louse").put("men", "man")
			.put("matrices", "matrix").put("means", "means").put("media", "medium").put("mice", "mouse")
			.put("nebulae", "nebula").put("nuclei", "nucleus").put("oases", "oasis").put("oxen", "ox")
			.put("paralyses", "paralysis").put("parentheses", "parenthesis").put("phenomena", "phenomenon")
			.put("radii", "radius").put("series", "series").put("sheep", "sheep").put("species", "species")
			.put("stimuli", "stimulus").put("strata", "stratum").put("syntheses", "synthesis")
			.put("synopses", "synopsis").put("tableaux", "tableau").put("theses", "thesis").put("teeth", "tooth")
			.put("vertebrae", "vertebra").put("vitae", "vita").put("women", "woman").build();

	private static StripPunctuation stripPunctuation = new StripPunctuation();

	@Override
	public EventSet createEventSet(char[] text) throws EventGenerationException {

		EventSet eventSet = new EventSet();
		PorterStemmerWithIrregularEventDriver port = new PorterStemmerWithIrregularEventDriver();
		EventSet tmpevent;

		URL url = getClass().getResource(JGAAPConstants.JGAAP_RESOURCE_PACKAGE + "wordnet");
		IDictionary dict;
		if (url.getProtocol().equalsIgnoreCase("jar")) {
			throw new EventGenerationException(
					"DefinitionsEventDriver is current not able to run using the jar.  Please use ant with the source distrodution.");
		} else {
			dict = new Dictionary(url);
		}
		try {
			dict.open();
		} catch (Exception e) {
			logger.error("Could not open WordNet Dictionary " + url, e);
			throw new EventGenerationException("DefinitionsEventDriver failed to open WordNet");
		}

		String current = new String(text);

		MaxentTagger tagger = null;
		try {
			tagger = new MaxentTagger(getClass().getResource("/com/jgaap/resources/models/postagger/english-left3words-distsim.tagger").toString());
		} catch (Exception e) {}

		List<String> words = Lists.newArrayList(Splitter.on(CharMatcher.WHITESPACE).trimResults().omitEmptyStrings()
				.split(current));
		
		List<String> tagged = new ArrayList<String>(words);
		for(int x = 0; x < tagged.size(); x++) {
			tagged.set(x, tagger.tagString(tagged.get(x)));
			
			// The Stanford POS tagger's tagString() method returns the passed-in string with an underscore followed by the tag and a
			// space character appended to it. Since we only want the tag, we must strip off all extra data if the returned string
			// is not empty.
			if(!tagged.get(x).equals(""))
				tagged.set(x, tagged.get(x).substring(tagged.get(x).lastIndexOf('_') + 1, tagged.get(x).length() - 1));
		}
		
		IIndexWord idxWord;
		List<IWordID> wordID;
		IWord word;
		StringBuilder outDef = new StringBuilder();

		for (int i = 0; i < words.size(); i++) {
			// System.out.println(i);
			String definition = "";
			if (table.containsKey(tagged.get(i))) {
				if (nouns.containsKey(words.get(i))) {
					words.set(i, nouns.get(words.get(i)));
				}
				try {
					switch (table.get(tagged.get(i))) {
					case (1):
						idxWord = dict.getIndexWord(words.get(i), POS.NOUN);
						if (idxWord == null)
							break;
						wordID = idxWord.getWordIDs();
						word = dict.getWord(wordID.get(0));
						definition = word.getSynset().getGloss();
						break;
					case (2):
						tmpevent = port.createEventSet(words.get(i).toCharArray());
						idxWord = dict.getIndexWord(tmpevent.eventAt(0).toString(), POS.VERB);
						if (idxWord == null)
							break;
						wordID = idxWord.getWordIDs();
						word = dict.getWord(wordID.get(0));
						definition = word.getSynset().getGloss();
						break;
					case (3):
						idxWord = dict.getIndexWord(words.get(i), POS.ADJECTIVE);
						if (idxWord == null)
							break;
						wordID = idxWord.getWordIDs();
						word = dict.getWord(wordID.get(0));
						definition = word.getSynset().getGloss();
						break;
					case (4):
						idxWord = dict.getIndexWord(words.get(i), POS.ADVERB);
						if (idxWord == null)
							break;
						wordID = idxWord.getWordIDs();
						word = dict.getWord(wordID.get(0));
						definition = word.getSynset().getGloss();
						break;
					}

				} catch (IllegalArgumentException e) {
					logger.debug("Problem with possibly empty word: '" + words.get(i) + "'", e);
				}

				String[] tmpDef = definition.split(";");
				if (!tmpDef[0].equalsIgnoreCase(""))
					outDef.append(tmpDef[0]).append(" ");
			}

		}

		String[] eventArray = new String(stripPunctuation.process(outDef.toString().toCharArray())).split("\\s+");

		for (int i = 0; i < eventArray.length; i++) {
			if (!stopWords.contains(eventArray[i]))
				eventSet.addEvent(new Event(eventArray[i], this));
		}

		return eventSet;
	}

	@Override
	public String displayName() {
		return "Definition Events";
	}

	@Override
	public boolean showInGUI() {
		return API.getInstance().getLanguage().getLanguage().equalsIgnoreCase("English");
	}

	@Override
	public String tooltipText() {
		return "Replaces words with their definitions";
	}

	@Override
	public String longDescription() {
		return "Replaces words with words from their definitions as given in WordNet's dictionary";
	}
}
