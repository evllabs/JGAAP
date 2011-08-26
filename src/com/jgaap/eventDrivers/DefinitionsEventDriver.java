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
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import com.jgaap.jgaapConstants;
import com.jgaap.canonicizers.StripPunctuation;
import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;
import edu.mit.jwi.*;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

import com.knowledgebooks.nlp.fasttag.*;

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

	private static Hashtable<String, Integer> table;
	private static Set<String> stopWords;
	private static Hashtable<String, String> nouns;

	static {
		table = new Hashtable<String, Integer>(17);
		table.put("NN", new Integer(1));
		table.put("NNS", new Integer(1));
		table.put("NNP", new Integer(1));
		table.put("NNPS", new Integer(1));
		table.put("JJ", new Integer(3));
		table.put("JJR", new Integer(3));
		table.put("JJS", new Integer(3));
		table.put("RB", new Integer(4));
		table.put("RBR", new Integer(4));
		table.put("RBS", new Integer(4));
		table.put("RBR", new Integer(4));
		table.put("VB", new Integer(2));
		table.put("VBD", new Integer(2));
		table.put("VBG", new Integer(2));
		table.put("VBN", new Integer(2));
		table.put("VBP", new Integer(2));
		table.put("VBZ", new Integer(2));

		stopWords = Collections.synchronizedSet(new HashSet<String>(37));
		stopWords.add("the");
		stopWords.add("of");
		stopWords.add("to");
		stopWords.add("and");
		stopWords.add("a");
		stopWords.add("in");
		stopWords.add("is");
		stopWords.add("it");
		stopWords.add("you");
		stopWords.add("that");
		stopWords.add("he");
		stopWords.add("was");
		stopWords.add("for");
		stopWords.add("on");
		stopWords.add("are");
		stopWords.add("with");
		stopWords.add("as");
		stopWords.add("i");
		stopWords.add("his");
		stopWords.add("they");
		stopWords.add("be");
		stopWords.add("at");
		stopWords.add("have");
		stopWords.add("this");
		stopWords.add("or");
		stopWords.add("had");
		stopWords.add("by");
		stopWords.add("but");
		stopWords.add("some");
		stopWords.add("what");
		stopWords.add("there");
		stopWords.add("we");
		stopWords.add("other");
		stopWords.add("were");
		stopWords.add("your");
		stopWords.add("an");
		stopWords.add("do");
		stopWords.add("if");

		nouns = new Hashtable<String, String>(60);
		nouns.put("alumni", "alumnus");
		nouns.put("analyses", "analysis");
		nouns.put("antennae", "antenna");
		nouns.put("antennas", "antenna");
		nouns.put("appendices", "appendix");
		nouns.put("axes", "axis");
		nouns.put("bacteria", "bacterium");
		nouns.put("bases", "basis");
		nouns.put("beaux", "beau");
		nouns.put("bureaux", "bureau");
		nouns.put("bureaus", "bureau");
		nouns.put("children", "child");
		nouns.put("corpora", "corpus");
		nouns.put("corpuses", "corpus");
		nouns.put("crises", "crisis");
		nouns.put("criteria", "criterion");
		nouns.put("curricula", "curriculum");
		nouns.put("data", "datum");
		nouns.put("deer", "deer");
		nouns.put("diagnoses", "diagnosis");
		nouns.put("ellipses", "ellipsis");
		nouns.put("fish", "fish");
		nouns.put("foci", "focus");
		nouns.put("focuses", "focus");
		nouns.put("feet", "foot");
		nouns.put("formulae", "formula");
		nouns.put("formulas", "formula");
		nouns.put("fungi", "fungus");
		nouns.put("funguses", "fungus");
		nouns.put("genera", "genus");
		nouns.put("geese", "goose");
		nouns.put("hypotheses", "hypothesis");
		nouns.put("indices", "index");
		nouns.put("indexes", "index");
		nouns.put("lice", "louse");
		nouns.put("men", "man");
		nouns.put("matrices", "matrix");
		nouns.put("means", "means");
		nouns.put("media", "medium");
		nouns.put("mice", "mouse");
		nouns.put("nebulae", "nebula");
		nouns.put("nuclei", "nucleus");
		nouns.put("oases", "oasis");
		nouns.put("oxen", "ox");
		nouns.put("paralyses", "paralysis");
		nouns.put("parentheses", "parenthesis");
		nouns.put("phenomena", "phenomenon");
		nouns.put("radii", "radius");
		nouns.put("series", "series");
		nouns.put("sheep", "sheep");
		nouns.put("species", "species");
		nouns.put("stimuli", "stimulus");
		nouns.put("strata", "stratum");
		nouns.put("syntheses", "synthesis");
		nouns.put("synopses", "synopsis");
		nouns.put("tableaux", "tableau");
		nouns.put("theses", "thesis");
		nouns.put("teeth", "tooth");
		nouns.put("vertebrae", "vertebra");
		nouns.put("vitae", "vita");
		nouns.put("women", "woman");
	}

	@Override
	public EventSet createEventSet(Document doc) {

		EventSet eventSet = new EventSet(doc.getAuthor());
		PorterStemmerWithIrregularEventDriver port = new PorterStemmerWithIrregularEventDriver();
		//PorterStemmerEventDriver port = new PorterStemmerEventDriver();
		EventSet tmpevent;
		//System.out.println(tmpevent+"\n\n\n");
		
		URL url = getClass().getResource(jgaapConstants.JGAAP_RESOURCE_PACKAGE+"wordnet");
		
		// construct the dictionary object and open it
		IDictionary dict = new Dictionary(url);
		dict.open();
		
		String current = doc.stringify();

		FastTag tagger = new FastTag();

		List<String> tmp = new ArrayList<String>();

		String[] tmpArray = current.split("\\s");

		for (int j = 0; j < tmpArray.length; j++) {
			tmp.add(tmpArray[j]);
		}

		List<String> tagged = tagger.tag(tmp);
		IIndexWord idxWord;
		List<IWordID> wordID;
		IWord word;
		StringBuilder outDef= new StringBuilder();
		Document tmpDoc = new Document();
		
		
		
		
		for(int i=0; i<tmpArray.length; i++){
			//System.out.println(i);
			String definition = "";
			if(table.containsKey(tagged.get(i))){
				if(nouns.containsKey(tmpArray[i])){
					tmpArray[i]=nouns.get(tmpArray[i]);
				}
				
				switch(table.get(tagged.get(i))){
				case(1):
					
					idxWord = dict.getIndexWord(tmpArray[i], POS.NOUN);
					if(idxWord == null)break;
					wordID = idxWord.getWordIDs();
					word = dict.getWord(wordID.get(0));
					definition = word.getSynset().getGloss();
					break;
				case(2):
					tmpDoc = new Document();
					tmpDoc.readStringText(tmpArray[i]);
					tmpevent = port.createEventSet(tmpDoc);
					idxWord = dict.getIndexWord(tmpevent.eventAt(0).getEvent(), POS.VERB);
					if(idxWord==null)break;
			   		wordID = idxWord.getWordIDs();
			   		word = dict.getWord(wordID.get(0));
			   		definition = word.getSynset().getGloss();
			   		break;
				case(3):
					idxWord = dict.getIndexWord(tmpArray[i], POS.ADJECTIVE);
					if(idxWord == null)break;
			   		wordID = idxWord.getWordIDs();
			   		word = dict.getWord(wordID.get(0));
			   		definition = word.getSynset().getGloss();
			   		break;
				case(4):
					idxWord = dict.getIndexWord(tmpArray[i], POS.ADVERB);
					if(idxWord == null)break;
			   		wordID = idxWord.getWordIDs();
			   		word = dict.getWord(wordID.get(0));
			   		definition = word.getSynset().getGloss();
			   		break;
				}
				
				String [] tmpDef = definition.split(";");
				if(tmpDef[0]!="")
					outDef.append(tmpDef[0]).append(" ");
				}
				
			}
		Document outDoc = new Document();
		outDoc.readStringText(outDef.toString());
		StripPunctuation strip = new StripPunctuation();
		
		outDoc.addCanonicizer(strip);
		outDoc.processCanonicizers();
		
		String [] eventArray = outDoc.stringify().split("\\s");
		for(int i=0; i<eventArray.length; i++){
			if(!stopWords.contains(eventArray[i]))
				eventSet.addEvent(new Event(eventArray[i]));
		}
		
		
		return eventSet;
	}

	@Override
	public String displayName() {
		// TODO Auto-generated method stub
		return "Definition Events";
	}

	@Override
	public boolean showInGUI() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String tooltipText() {
		// TODO Auto-generated method stub
		return "Replaces words with their definitions";
	}

	@Override
	public String longDescription() {
		// TODO Auto-generated method stub
		return "Replaces words with words from their definitions as given in WordNet's dictionary";
	}
}
