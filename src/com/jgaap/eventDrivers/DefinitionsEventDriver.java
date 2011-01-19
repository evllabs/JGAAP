package com.jgaap.eventDrivers;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.jgaap.jgaapConstants;
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

/*
 * @author Darren Vescovi
 * 
 * replaces words with their definitions
 * 
 * NOTE:this is event driver uses packages that can only be used for non-commercial implementation
 * 		specifically the edu.mit.jwi_2.1.5_jdk.jar
 */
public class DefinitionsEventDriver extends EventDriver {
	
	
	
	

	@Override
	public EventSet createEventSet(Document doc) {
		Hashtable<String, Integer > table = new Hashtable<String , Integer>();
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
		
		
		EventSet eventSet = new EventSet(doc.getAuthor());
		
		
		// construct the URL to the Wordnet dictionary directory
		//String wnhome = System.getenv("WNHOME");
		//System.out.print(wnhome);
		//String path = wnhome + File.separator + "dict";
		jgaapConstants constants   = new jgaapConstants();
		
		URL url = null;
		try{ url = new URL("file", null, constants.utilDir()+"WordNet-3.0/dict"); } 
		catch(MalformedURLException e){ e.printStackTrace(); }
		if(url == null) return null;
		
		
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
		//System.out.println("entering loop");
		for(int i=0; i<tmpArray.length; i++){
			//System.out.println(i);
			if(table.containsKey(tagged.get(i))){
				
				switch(table.get(tagged.get(i))){
				case(1): 
					idxWord = dict.getIndexWord(tmpArray[i], POS.NOUN);
				   	wordID = idxWord.getWordIDs();
				   	word = dict.getWord(wordID.get(0));
				   	//TODO change things to do what juola wants them to do
				   	eventSet.addEvent(new Event(word.getSynset().getGloss()));
				   	break;
				case(2):
					idxWord = dict.getIndexWord(tmpArray[i], POS.VERB);
					if(idxWord==null)break;
			   		wordID = idxWord.getWordIDs();
			   		word = dict.getWord(wordID.get(0));
			   		eventSet.addEvent(new Event(word.getSynset().getGloss()));
			   		break;
				case(3):
					idxWord = dict.getIndexWord(tmpArray[i], POS.ADJECTIVE);
			   		wordID = idxWord.getWordIDs();
			   		word = dict.getWord(wordID.get(0));
			   		eventSet.addEvent(new Event(word.getSynset().getGloss()));
			   		break;
				case(4):
					idxWord = dict.getIndexWord(tmpArray[i], POS.ADVERB);
			   		wordID = idxWord.getWordIDs();
			   		word = dict.getWord(wordID.get(0));
			   		eventSet.addEvent(new Event(word.getSynset().getGloss()));
			   		break;
				}
				
				
				
				}
				
			}
		
		
		return eventSet;
	}

	@Override
	public String displayName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean showInGUI() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String tooltipText() {
		// TODO Auto-generated method stub
		return null;
	}

}

