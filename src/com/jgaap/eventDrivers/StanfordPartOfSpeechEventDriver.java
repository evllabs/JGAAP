package com.jgaap.eventDrivers;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;

import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * 
 * @author Michael Ryan
 *
 */
public class StanfordPartOfSpeechEventDriver extends EventDriver {
	
	static private Logger logger = Logger.getLogger(
			com.jgaap.eventDrivers.StanfordPartOfSpeechEventDriver.class);
	
	private MaxentTagger tagger = null;
	
	private Boolean semaphore = true;
	
	public StanfordPartOfSpeechEventDriver() {
		addParams("tagginModel", "Model", "english-bidirectional-distsim", 
				new String[] { "arabic-accurate","arabic-fast.tagger","chinese",
				"english-bidirectional-distsim","english-left3words-distsim",
				"french",
				//"german-dewac",
				"german-fast"
				//,"german-hgc"
				}, false);
	}
	
	@Override
	public String displayName() {
		return "Stanford Part of Speech";
	}

	@Override
	public String tooltipText() {
		return "A Part of Speech Tagger using the MaxentTagger developed by the Stanford NLP Group http://nlp.stanford.edu";
	}

	@Override
	public boolean showInGUI() {
		return true;
	}

	@Override
	public EventSet createEventSet(Document doc)
			throws EventGenerationException {
		if (tagger == null)
			synchronized (semaphore) {
				if (tagger == null) {
					String taggingModel = getParameter("taggingModel");
					if ("".equals(taggingModel)){
						 taggingModel = "english-bidirectional-distsim";
					}
					taggingModel = "com/jgaap/resources/models/postagger/"+taggingModel+".tagger";
					try {
						tagger = new MaxentTagger(taggingModel);
					} catch (Exception e) {
						logger.error("Could Not instance Maxent Tagger "+taggingModel,e);
						throw new EventGenerationException(
								"Could not instance Maxent Tagger with model located at "+taggingModel);
					}
				}
			}
		List<ArrayList<TaggedWord>> taggedSentences = tagger
				.process(MaxentTagger.tokenizeText(new StringReader(doc.stringify())));
		EventSet eventSet = new EventSet(taggedSentences.size());
		for (ArrayList<TaggedWord> sentence : taggedSentences) {
			for (TaggedWord taggedWord : sentence) {
				eventSet.addEvent(new Event(taggedWord.tag()));
			}
		}
		return eventSet;
	}

}
