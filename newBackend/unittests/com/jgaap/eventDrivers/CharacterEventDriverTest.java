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
 * @author michael
 *
 */
public class CharacterEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.CharacterEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {
		Document doc = new Document();
		doc.readStringText("abcdefghijklmnopqrstuvwxyz abcdefghijklmnopqrstuvwxyz.");
		EventSet sampleEventSet = new CharacterEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("a"));
		tmp.add(new Event("b"));
		tmp.add(new Event("c"));
		tmp.add(new Event("d"));
		tmp.add(new Event("e"));
		tmp.add(new Event("f"));
		tmp.add(new Event("g"));
		tmp.add(new Event("h"));
		tmp.add(new Event("i"));
		tmp.add(new Event("j"));
		tmp.add(new Event("k"));
		tmp.add(new Event("l"));
		tmp.add(new Event("m"));
		tmp.add(new Event("n"));
		tmp.add(new Event("o"));
		tmp.add(new Event("p"));
		tmp.add(new Event("q"));
		tmp.add(new Event("r"));
		tmp.add(new Event("s"));
		tmp.add(new Event("t"));
		tmp.add(new Event("u"));
		tmp.add(new Event("v"));
		tmp.add(new Event("w"));
		tmp.add(new Event("x"));
		tmp.add(new Event("y"));
		tmp.add(new Event("z"));
		expectedEventSet.addEvents(tmp);
		expectedEventSet.addEvent(new Event(" "));
		expectedEventSet.addEvents(tmp);
		expectedEventSet.addEvent(new Event("."));
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}

}
