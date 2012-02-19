package com.jgaap.eventDrivers;

import com.jgaap.canonicizers.StripNonPunc;
import com.jgaap.canonicizers.StripPunctuation;
import com.jgaap.canonicizers.UnifyCase;
import com.jgaap.generics.CanonicizationException;
import com.jgaap.generics.Canonicizer;
import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;

public class SecretSauce extends EventDriver {

	private Canonicizer one = new UnifyCase();
	private Canonicizer two = new StripPunctuation();
	private Canonicizer three = new StripNonPunc();

	private int n = 4;

	@Override
	public String displayName() {
		return "Secret Sauce";
	}

	@Override
	public String tooltipText() {
		return "Mike's Magical Mix";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public EventSet createEventSet(Document doc)
			throws EventGenerationException {

		String text = doc.stringify();
		String[] words = text.split("\\s+");
		EventSet eventSet = new EventSet();
		for (int length = 1; length <= n; length++) {
			for (int i = 0; i < words.length - length; i++) {
				StringBuilder wordGramBuilder = new StringBuilder();
				for (int j = 0; j < length; j++) {
					if (j > 0){
						wordGramBuilder.append(" ");
					}
					wordGramBuilder.append(words[i + j]);
				}
				String word = wordGramBuilder.toString();
				eventSet.addEvent(new Event(word));
				addAlts(word, eventSet);
			}
		}
		return eventSet;
	}

	private void addAlts(String word, EventSet eventSet) {
		char[] wordArray = word.toCharArray();
		try {
			char[] wordOne = one.process(wordArray);
			if (!wordArray.equals(wordOne)) {
				eventSet.addEvent(new Event(new String(wordOne)));
			}
			char[] wordTwo = two.process(wordArray);
			if (!wordArray.equals(wordTwo) && !wordOne.equals(wordTwo)) {
				eventSet.addEvent(new Event(new String(wordTwo)));
			}
			char[] wordOneTwo = two.process(one.process(wordArray));
			if (!wordArray.equals(wordOneTwo) && !wordOne.equals(wordOneTwo)
					&& !wordTwo.equals(wordOneTwo)) {
				eventSet.addEvent(new Event(new String(wordOneTwo)));
			}
			char[] wordThree = three.process(wordArray);
			if (!wordArray.equals(wordThree)) {
				eventSet.addEvent(new Event(new String(wordThree)));
			}
		} catch (CanonicizationException e) {
			e.printStackTrace();
		}
	}

}
