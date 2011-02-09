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
 * @author darren Vescovi
 *
 */
public class DefinitionsEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.DefinitionsEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 */
	@Test
	public void testCreateEventSet() {
		
		System.out.println("Test Started");
	 	Document doc = new Document();
	    doc.readStringText("alumni Today the fox jumped over the lazy dog "
	    		+"While the fox jumped over the lazy dog a cat ran under a truck "
	    		+"The truck missed the cat and the lazy dog was not so lazy and caught the cat");
	    
	    EventSet sampleSet = new DefinitionsEventDriver().createEventSet(doc);
	    EventSet expectedSet = new EventSet();
	    Vector<Event> tmp = new Vector<Event>();
	    
	    tmp.add(new Event("person"));
	    tmp.add(new Event("who"));
	    tmp.add(new Event("has"));
	    tmp.add(new Event("received"));
	    tmp.add(new Event("degree"));
	    tmp.add(new Event("from"));
	    tmp.add(new Event("school"));
	    tmp.add(new Event("high"));
	    tmp.add(new Event("school"));
	    tmp.add(new Event("college"));
	    tmp.add(new Event("university"));
	    tmp.add(new Event("present"));
	    tmp.add(new Event("time"));
	    tmp.add(new Event("age"));
	    tmp.add(new Event("alert"));
	    tmp.add(new Event("carnivorous"));
	    tmp.add(new Event("mammal"));
	    tmp.add(new Event("pointed"));
	    tmp.add(new Event("muzzle"));
	    tmp.add(new Event("ears"));
	    tmp.add(new Event("bushy"));
	    tmp.add(new Event("tail"));
	    tmp.add(new Event("move"));
	    tmp.add(new Event("forward"));
	    tmp.add(new Event("leaps"));
	    tmp.add(new Event("bounds"));
	    tmp.add(new Event("moving"));
	    tmp.add(new Event("slowly"));
	    tmp.add(new Event("gently"));
	    tmp.add(new Event("member"));
	    tmp.add(new Event("genus"));
	    tmp.add(new Event("Canis"));
	    tmp.add(new Event("probably"));
	    tmp.add(new Event("descended"));
	    tmp.add(new Event("from"));
	    tmp.add(new Event("common"));
	    tmp.add(new Event("wolf"));
	    tmp.add(new Event("has"));
	    tmp.add(new Event("been"));
	    tmp.add(new Event("domesticated"));
	    tmp.add(new Event("man"));
	    tmp.add(new Event("since"));
	    tmp.add(new Event("prehistoric"));
	    tmp.add(new Event("times"));
	    tmp.add(new Event("alert"));
	    tmp.add(new Event("carnivorous"));
	    tmp.add(new Event("mammal"));
	    tmp.add(new Event("pointed"));
	    tmp.add(new Event("muzzle"));
	    tmp.add(new Event("ears"));
	    tmp.add(new Event("bushy"));
	    tmp.add(new Event("tail"));
	    tmp.add(new Event("move"));
	    tmp.add(new Event("forward"));
	    tmp.add(new Event("leaps"));
	    tmp.add(new Event("bounds"));
	    tmp.add(new Event("moving"));
	    tmp.add(new Event("slowly"));
	    tmp.add(new Event("gently"));
	    tmp.add(new Event("member"));
	    tmp.add(new Event("genus"));
	    tmp.add(new Event("Canis"));
	    tmp.add(new Event("probably"));
	    tmp.add(new Event("descended"));
	    tmp.add(new Event("from"));
	    tmp.add(new Event("common"));
	    tmp.add(new Event("wolf"));
	    tmp.add(new Event("has"));
	    tmp.add(new Event("been"));
	    tmp.add(new Event("domesticated"));
	    tmp.add(new Event("man"));
	    tmp.add(new Event("since"));
	    tmp.add(new Event("prehistoric"));
	    tmp.add(new Event("times"));
	    tmp.add(new Event("feline"));
	    tmp.add(new Event("mammal"));
	    tmp.add(new Event("usually"));
	    tmp.add(new Event("having"));
	    tmp.add(new Event("thick"));
	    tmp.add(new Event("soft"));
	    tmp.add(new Event("fur"));
	    tmp.add(new Event("no"));
	    tmp.add(new Event("ability"));
	    tmp.add(new Event("roar"));
	    tmp.add(new Event("domestic"));
	    tmp.add(new Event("cats"));
	    tmp.add(new Event("move"));
	    tmp.add(new Event("fast"));
	    tmp.add(new Event("using"));
	    tmp.add(new Event("ones"));
	    tmp.add(new Event("feet"));
	    tmp.add(new Event("one"));
	    tmp.add(new Event("foot"));
	    tmp.add(new Event("off"));
	    tmp.add(new Event("ground"));
	    tmp.add(new Event("any"));
	    tmp.add(new Event("given"));
	    tmp.add(new Event("time"));
	    tmp.add(new Event("automotive"));
	    tmp.add(new Event("vehicle"));
	    tmp.add(new Event("suitable"));
	    tmp.add(new Event("hauling"));
	    tmp.add(new Event("automotive"));
	    tmp.add(new Event("vehicle"));
	    tmp.add(new Event("suitable"));
	    tmp.add(new Event("hauling"));
	    tmp.add(new Event("fail"));
	    tmp.add(new Event("perceive"));
	    tmp.add(new Event("catch"));
	    tmp.add(new Event("senses"));
	    tmp.add(new Event("mind"));
	    tmp.add(new Event("feline"));
	    tmp.add(new Event("mammal"));
	    tmp.add(new Event("usually"));
	    tmp.add(new Event("having"));
	    tmp.add(new Event("thick"));
	    tmp.add(new Event("soft"));
	    tmp.add(new Event("fur"));
	    tmp.add(new Event("no"));
	    tmp.add(new Event("ability"));
	    tmp.add(new Event("roar"));
	    tmp.add(new Event("domestic"));
	    tmp.add(new Event("cats"));
	    tmp.add(new Event("moving"));
	    tmp.add(new Event("slowly"));
	    tmp.add(new Event("gently"));
	    tmp.add(new Event("member"));
	    tmp.add(new Event("genus"));
	    tmp.add(new Event("Canis"));
	    tmp.add(new Event("probably"));
	    tmp.add(new Event("descended"));
	    tmp.add(new Event("from"));
	    tmp.add(new Event("common"));
	    tmp.add(new Event("wolf"));
	    tmp.add(new Event("has"));
	    tmp.add(new Event("been"));
	    tmp.add(new Event("domesticated"));
	    tmp.add(new Event("man"));
	    tmp.add(new Event("since"));
	    tmp.add(new Event("prehistoric"));
	    tmp.add(new Event("times"));
	    tmp.add(new Event("negation"));
	    tmp.add(new Event("word"));
	    tmp.add(new Event("group"));
	    tmp.add(new Event("words"));
	    tmp.add(new Event("very"));
	    tmp.add(new Event("great"));
	    tmp.add(new Event("extent"));
	    tmp.add(new Event("degree"));
	    tmp.add(new Event("moving"));
	    tmp.add(new Event("slowly"));
	    tmp.add(new Event("gently"));
	    tmp.add(new Event("discover"));
	    tmp.add(new Event("come"));
	    tmp.add(new Event("upon"));
	    tmp.add(new Event("accidentally"));
	    tmp.add(new Event("suddenly"));
	    tmp.add(new Event("unexpectedly"));
	    tmp.add(new Event("feline"));
	    tmp.add(new Event("mammal"));
	    tmp.add(new Event("usually"));
	    tmp.add(new Event("having"));
	    tmp.add(new Event("thick"));
	    tmp.add(new Event("soft"));
	    tmp.add(new Event("fur"));
	    tmp.add(new Event("no"));
	    tmp.add(new Event("ability"));
	    tmp.add(new Event("roar"));
	    tmp.add(new Event("domestic"));
	    tmp.add(new Event("cats"));
	    
	    expectedSet.addEvents(tmp);
	    
	    
	    
		assertTrue(expectedSet.equals(sampleSet));
	}

}
