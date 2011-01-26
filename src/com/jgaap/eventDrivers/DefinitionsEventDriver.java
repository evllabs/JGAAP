package com.jgaap.eventDrivers;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
		
		
		//Set of stop words.
		HashSet<String> stopWords = new HashSet<String>();
		
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
		
		
		
		
		
		EventSet eventSet = new EventSet(doc.getAuthor());
		PorterStemmerEventDriver port = new PorterStemmerEventDriver();
		EventSet tmpevent;
		//System.out.println(tmpevent+"\n\n\n");
		
		
		
		URL url = null;
		try{ url = new URL("file", null, jgaapConstants.utilDir()+"WordNet-3.0/dict"); } 
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
		String outDef="";
		//System.out.println("entering loop");
		
		/*idxWord = dict.getIndexWord("todai", POS.NOUN);
	   	wordID = idxWord.getWordIDs();
	   	word = dict.getWord(wordID.get(0));*/
	   	//TODO change things to do what juola wants them to do
	   	//System.out.println(word.getSynset().getGloss()+"\nprinting");
		
		for(int i=0; i<tmpArray.length; i++){
			//System.out.println(i);
			String definition = "";
			if(table.containsKey(tagged.get(i))){
				
				switch(table.get(tagged.get(i))){
				case(1): 
					idxWord = dict.getIndexWord(tmpArray[i], POS.NOUN);
					if(idxWord == null)break;
					wordID = idxWord.getWordIDs();
					word = dict.getWord(wordID.get(0));
					definition = word.getSynset().getGloss();
					break;
				case(2):
					Document tmpDoc = new Document();
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
					outDef = outDef+tmpDef[0]+" ";
				
												
				}
				
			}
		Document outDoc = new Document();
		outDoc.readStringText(outDef);
		StripPunctuation strip = new StripPunctuation();
		
		List<Character> charDef = strip.process(outDoc.getProcessedText());
		
		outDoc.setProcessedText(charDef);
		
		
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
		return false;
	}

	@Override
	public String tooltipText() {
		// TODO Auto-generated method stub
		return null;
	}

}

