package com.jgaap.eventDrivers;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

public class RyhmeEventDriverTest {
    /**
	 * Test method for {@link com.jgaap.eventDrivers.RhymeEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 * @throws EventGenerationException 
	 */
	@Test
	public void testCreateEventSetDocumentSet() throws EventGenerationException {
		String text = "As I walked past the chalked sky, I noticed the last talk on the walk I had was a bad Goodbye. " + 
                    " I drew a line, I drew a line for you, and everything you do"+ 
                    " I'm sure we're taller another dimension, you say we're small and not worth the mention. " +
                    "You're tired of moving, your body's aching, we could vacation there's places to go. "+
                    "Falling, dreaming, talking, in your sleep I know you want to fight all night; all night " +
                    "Plotting, scheming, finding reason to defend all your violent nights";
		EventDriver eventDriver = new RhymeEventDriver();
		EventSet sampleEventSet = eventDriver.createEventSet(text.toCharArray());
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("As", eventDriver));
		tmp.add(new Event("I I I I I I", eventDriver));
		tmp.add(new Event("walked chalked", eventDriver));
		tmp.add(new Event("past last", eventDriver));
		tmp.add(new Event("the the the a a a the", eventDriver));
		tmp.add(new Event("sky Goodbye", eventDriver));
		tmp.add(new Event("noticed", eventDriver));
		tmp.add(new Event("talk walk", eventDriver));
		tmp.add(new Event("on", eventDriver));
		tmp.add(new Event("had bad", eventDriver));
		tmp.add(new Event("drew drew you you do you to you to to", eventDriver));
		tmp.add(new Event("line line", eventDriver));
		tmp.add(new Event("for your your your", eventDriver));
		tmp.add(new Event("and and", eventDriver));
		tmp.add(new Event("everything", eventDriver));
		tmp.add(new Event("sure You're", eventDriver));
		tmp.add(new Event("we're we're", eventDriver));
		tmp.add(new Event("taller another", eventDriver));
		tmp.add(new Event("dimention mention vacation reason", eventDriver));
		tmp.add(new Event("say", eventDriver));
		tmp.add(new Event("small all all all", eventDriver));
		tmp.add(new Event("not", eventDriver));
		tmp.add(new Event("worth", eventDriver));
		tmp.add(new Event("tired", eventDriver));
		tmp.add(new Event("of", eventDriver));
		tmp.add(new Event("moving aching Falling dreaming talking Plotting scheming finding", eventDriver));
        tmp.add(new Event("body's", eventDriver));
        tmp.add(new Event("we", eventDriver));
        tmp.add(new Event("could", eventDriver));
        tmp.add(new Event("there's", eventDriver));
        tmp.add(new Event("places", eventDriver));
        tmp.add(new Event("go know", eventDriver));
        tmp.add(new Event("in", eventDriver));
        tmp.add(new Event("sleep", eventDriver));
        tmp.add(new Event("want", eventDriver));
        tmp.add(new Event("fight night night", eventDriver));
        tmp.add(new Event("defend", eventDriver));
        tmp.add(new Event("violent", eventDriver));
        tmp.add(new Event("nights", eventDriver));


		expectedEventSet.addEvents(tmp);
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}
}
