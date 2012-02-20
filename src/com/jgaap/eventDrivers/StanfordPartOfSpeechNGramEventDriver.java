package com.jgaap.eventDrivers;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;

public class StanfordPartOfSpeechNGramEventDriver extends EventDriver {

	private EventDriver stanfordPOS = new StanfordPartOfSpeechEventDriver();
	
	public StanfordPartOfSpeechNGramEventDriver(){
		addParams("tagginModel", "Model", "english-bidirectional-distsim", 
				new String[] { "arabic-accurate","arabic-fast.tagger","chinese",
				"english-bidirectional-distsim","english-left3words-distsim",
				"french",
				//"german-dewac",
				"german-fast"
				//,"german-hgc"
				}, false);
		addParams("N", "N", "2", 
				new String[] { "1","2","3","4","5","6","7",
				"8","9","10","11","12","13","14","15"}, false);
	}
	
	@Override
	public String displayName() {
		return "Stanford Part of Speech "+getParameter("N")+"Grams";
	}

	@Override
	public String tooltipText() {
		return "A sliding window of N parts of speech as tagged by the Stanford PoS Tagger.";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public EventSet createEventSet(Document doc)
			throws EventGenerationException {
		EventSet eventSet = new EventSet();
		stanfordPOS.setParameter("taggingModel", getParameter("taggingModel"));
		EventSet posEventSet = stanfordPOS.createEventSet(doc);
		String gramSize = getParameter("N");
		int n;
		if("".equals(gramSize)){
			n = 2;
		}else{ 
			n = Integer.parseInt(getParameter("N"));
		}
		String s;
		for (int i = n; i <= posEventSet.size(); i++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = i - n; j < i; j++) {
                s = posEventSet.eventAt(j).getEvent();
                stringBuilder.append("(").append(s).append(")");
                if (j != i - 1) {
                    stringBuilder.append("-");
                }
            }
            eventSet.addEvent(new Event(stringBuilder.toString()));
        }
		return eventSet;
	}

}
