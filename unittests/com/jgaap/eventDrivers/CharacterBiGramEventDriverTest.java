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
 * @author Chris
 *
 */
public class CharacterBiGramEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.CharacterBiGramEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {
		Document doc = new Document();
		doc.readStringText("abcdefghijklmnopqrstuvwxyz .");
		EventSet sampleEventSet = new CharacterBiGramEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("ab"));
		tmp.add(new Event("bc"));
		tmp.add(new Event("cd"));
		tmp.add(new Event("de"));
		tmp.add(new Event("ef"));
		tmp.add(new Event("fg"));
		tmp.add(new Event("gh"));
		tmp.add(new Event("hi"));
		tmp.add(new Event("ij"));
		tmp.add(new Event("jk"));
		tmp.add(new Event("kl"));
		tmp.add(new Event("lm"));
		tmp.add(new Event("mn"));
		tmp.add(new Event("no"));
		tmp.add(new Event("op"));
		tmp.add(new Event("pq"));
		tmp.add(new Event("qr"));
		tmp.add(new Event("rs"));
		tmp.add(new Event("st"));
		tmp.add(new Event("tu"));
		tmp.add(new Event("uv"));
		tmp.add(new Event("vw"));
		tmp.add(new Event("wx"));
		tmp.add(new Event("xy"));
		tmp.add(new Event("yz"));
		expectedEventSet.addEvents(tmp);
		expectedEventSet.addEvent(new Event("z "));
		//expectedEventSet.addEvents(tmp);
		expectedEventSet.addEvent(new Event(" ."));
// System.out.println(expectedEventSet.toString());
// System.out.println(sampleEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}

}
