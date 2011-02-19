package com.jgaap.eventDrivers;

import java.util.HashMap;
import java.util.Map;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;

public class CoarsePOSTagger extends EventDriver {

	static Map<String, String> translationTable;
	static {
		translationTable = new HashMap<String, String>();

		translationTable.put("CC", "C");
		translationTable.put("CD", "CD");
		translationTable.put("DT", "DT");
		translationTable.put("EX", "EX");
		translationTable.put("FW", "FW");
		translationTable.put("IN", "C");
		translationTable.put("JJ", "J");
		translationTable.put("JJR", "J");
		translationTable.put("JJS", "J");
		translationTable.put("LS", "LS");
		translationTable.put("MD", "MD");
		translationTable.put("NN", "N");
		translationTable.put("NNS", "N");
		translationTable.put("NNP", "N");
		translationTable.put("NNPS", "N");
		translationTable.put("PDT", "PDT");
		translationTable.put("POS", "PDT");
		translationTable.put("PRP", "P");
		translationTable.put("PRP$", "P");
		translationTable.put("RB", "R");
		translationTable.put("RBR", "R");
		translationTable.put("RBS", "R");
		translationTable.put("RP", "R");
		translationTable.put("SYM", "Punc");
		translationTable.put("TO", "TO");
		translationTable.put("UH", "UH");
		translationTable.put("VB", "V");
		translationTable.put("VBD", "V");
		translationTable.put("VBG", "V");
		translationTable.put("VBN", "V");
		translationTable.put("VBP", "V");
		translationTable.put("VBZ", "V");
		translationTable.put("WDT", "W");
		translationTable.put("WP", "W");
		translationTable.put("WP$", "W");
		translationTable.put("WRB", "W");
		translationTable.put("#", "Punc");
		translationTable.put("$", "Punc");
		translationTable.put(".", "Punc");
		translationTable.put(",", "Punc");
		translationTable.put(":", "Punc");
		translationTable.put("(", "Punc");
		translationTable.put(")", "Punc");
		translationTable.put("\"", "Punc");
		translationTable.put("`", "Punc");
		translationTable.put("\"", "Punc");
		translationTable.put("'", "Punc");
		translationTable.put("\"", "Punc");

	}

	@Override
	public String displayName() {
		return "Coarse POS Tagger";
	}

	@Override
	public String tooltipText() {
		return "A simplification of the normal part of speach tagger";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public EventSet createEventSet(Document doc) {
		EventSet preprocessEventSet = new PartOfSpeechEventDriver().createEventSet(doc);
		EventSet eventSet = new EventSet(doc.getAuthor());
		for (Event event : preprocessEventSet) {
			if(translationTable.containsKey(event.getEvent()))
				eventSet.addEvent(new Event(translationTable.get(event.getEvent())));
			else
				eventSet.addEvent(event);
		}
		return eventSet;
	}

}
