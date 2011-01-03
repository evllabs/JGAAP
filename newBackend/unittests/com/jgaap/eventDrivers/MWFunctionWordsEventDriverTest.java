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
public class MWFunctionWordsEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.MWFunctionWordsEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {
		Document doc = new Document();
		doc.readStringText(
// list of acceptable words
"a all also an and any are as at be been but by can do down\n" +
"even every for from had has have her his if in into is it its\n" +
"may more must my no not now of on one only or our shall should\n" +
"so some such than that the their then there things this to up\n" +
"upon was were what when which who will with would your\n" +
// line below should be eliminated 
"distractor fail eliminate megafail lose gark hoser shimatta"
		);
		EventSet sampleEventSet = new MWFunctionWordsEventDriver().createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();
		tmp.add(new Event("a"));
		tmp.add(new Event("all"));
		tmp.add(new Event("also"));
		tmp.add(new Event("an"));
		tmp.add(new Event("and"));
		tmp.add(new Event("any"));
		tmp.add(new Event("are"));
		tmp.add(new Event("as"));
		tmp.add(new Event("at"));
		tmp.add(new Event("be"));
		tmp.add(new Event("been"));
		tmp.add(new Event("but"));
		tmp.add(new Event("by"));
		tmp.add(new Event("can"));
		tmp.add(new Event("do"));
		tmp.add(new Event("down"));
		tmp.add(new Event("even"));
		tmp.add(new Event("every"));
		tmp.add(new Event("for"));
		tmp.add(new Event("from"));
		tmp.add(new Event("had"));
		tmp.add(new Event("has"));
		tmp.add(new Event("have"));
		tmp.add(new Event("her"));
		tmp.add(new Event("his"));
		tmp.add(new Event("if"));
		tmp.add(new Event("in"));
		tmp.add(new Event("into"));
		tmp.add(new Event("is"));
		tmp.add(new Event("it"));
		tmp.add(new Event("its"));
		tmp.add(new Event("may"));
		tmp.add(new Event("more"));
		tmp.add(new Event("must"));
		tmp.add(new Event("my"));
		tmp.add(new Event("no"));
		tmp.add(new Event("not"));
		tmp.add(new Event("now"));
		tmp.add(new Event("of"));
		tmp.add(new Event("on"));
		tmp.add(new Event("one"));
		tmp.add(new Event("only"));
		tmp.add(new Event("or"));
		tmp.add(new Event("our"));
		tmp.add(new Event("shall"));
		tmp.add(new Event("should"));
		tmp.add(new Event("so"));
		tmp.add(new Event("some"));
		tmp.add(new Event("such"));
		tmp.add(new Event("than"));
		tmp.add(new Event("that"));
		tmp.add(new Event("the"));
		tmp.add(new Event("their"));
		tmp.add(new Event("then"));
		tmp.add(new Event("there"));
		tmp.add(new Event("things"));
		tmp.add(new Event("this"));
		tmp.add(new Event("to"));
		tmp.add(new Event("up"));
		tmp.add(new Event("upon"));
		tmp.add(new Event("was"));
		tmp.add(new Event("were"));
		tmp.add(new Event("what"));
		tmp.add(new Event("when"));
		tmp.add(new Event("which"));
		tmp.add(new Event("who"));
		tmp.add(new Event("will"));
		tmp.add(new Event("with"));
		tmp.add(new Event("would"));
		tmp.add(new Event("your"));
		expectedEventSet.addEvents(tmp);
//		System.out.println(expectedEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}

}
