/**
 * 
 */
package com.jgaap.eventDrivers;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;

/**
 * @author Patrick Juola
 *
 */
public class WordSyllablesEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.WordSyllablesEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {
		/* test case 1 -- no punctuation */
		Document doc = new Document();
		doc.readStringText(
			"cat " +	// 1 syllable
			"at " +		// 1 syllable
			"a " + 		// 1 syllable
			"to " + 	// 1 syllable
			"oat " + 	// 1 syllable
			"oooo " + 	// 1 syllable
			"too " + 	// 1 syllable
			"dye " + 	// 1 syllable
			"eieio " + 	// 1 syllable
			"otto " + 	// 2 syllables
			"duty " + 	// 2 syllables
			"atom " + 	// 2 syllables
			"abloom " + 	// 2 syllables
			"banana " + 	// 3 syllables
			"dr " + 	// 0 syllables but becomes 1
			"!@#$%^&^ " + 	// 0 syllables but becomes 1
			"867-5309 " + 	// 0 syllables but becomes 1
			"CAT " +	// 1 syllable
			"AT " +		// 1 syllable
			"A " + 		// 1 syllable
			"TO " + 	// 1 syllable
			"OAT " + 	// 1 syllable
			"OOOO " + 	// 1 syllable
			"TOO " + 	// 1 syllable
			"DYE " + 	// 1 syllable
			"EIEIO " + 	// 1 syllable
			"EieIOo " + 	// 1 syllable
			"OTTO " 	// 2 syllables
		);

		EventSet sampleEventSet = new WordSyllablesEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("1"));
		tmp.add(new Event("1"));
		tmp.add(new Event("1"));
		tmp.add(new Event("1"));
		tmp.add(new Event("1"));
		tmp.add(new Event("1"));
		tmp.add(new Event("1"));
		tmp.add(new Event("1"));
		tmp.add(new Event("1"));
		tmp.add(new Event("2"));
		tmp.add(new Event("2"));
		tmp.add(new Event("2"));
		tmp.add(new Event("2"));
		tmp.add(new Event("3"));
		tmp.add(new Event("1"));
		tmp.add(new Event("1"));
		tmp.add(new Event("1"));
		tmp.add(new Event("1"));
		tmp.add(new Event("1"));
		tmp.add(new Event("1"));
		tmp.add(new Event("1"));
		tmp.add(new Event("1"));
		tmp.add(new Event("1"));
		tmp.add(new Event("1"));
		tmp.add(new Event("1"));
		tmp.add(new Event("1"));
		tmp.add(new Event("1"));
		tmp.add(new Event("2"));

		expectedEventSet.addEvents(tmp);
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}

}
