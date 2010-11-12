package com.jgaap.eventDrivers;

import com.jgaap.generics.DocumentSet;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;
import com.knowledgebooks.nlp.fasttag.*;
import java.util.*;





/**
 *This changes words into their parts of speech in a document.
 *This does not handle punctuation well changes all words with 
 *punctuation at the end into the tag CD. 
 *
 */
 
 public class PartOfSpeechEventDriver extends EventDriver
 {
 	public static void main (String[] args)
 	{
 		PartOfSpeechEventDriver pos = new PartOfSpeechEventDriver();
 		
 	}
 
 
 	@Override
    public String displayName()
    {
    	return "POS";
    }
    
    @Override
    public String tooltipText()
    {
    	return "Parts of Speech";
    }
    
    @Override
    public boolean showInGUI()
    {
    	return true;
    }
    
    @Override
    public EventSet createEventSet(DocumentSet ds) 
    	{
    	
        EventSet es = new EventSet(ds.getDocument(0).getAuthor());
        for (int i = 0; i < ds.documentCount(); i++) 
        	{
            String current = ds.getDocument(i).stringify();
            
            FastTag tagger = new FastTag();
            
            List<String> tmp = new ArrayList<String>();
            
            String [] tmpArray = current.split("\\s");
            
            for(int j=0; j<tmpArray.length; j++)
            {
            	tmp.add(tmpArray[j]);
            }
            
            List<String> tagged = tagger.tag(tmp);
            
            Vector<Event> ev = new Vector<Event>();
            for(int j=0; j<tagged.size(); j++)
            {
            	ev.add(new Event(tagged.get(j)));
            }
            
            es.events.addAll(ev);
            
            
        }
        return es;
    	
    }
    
       	
 }
 
 
 