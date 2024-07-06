/*
 * JGAAP -- a graphical program for stylometric authorship attribution
 * Copyright (C) 2009,2011 by Patrick Juola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * 
 */
package com.jgaap.eventDrivers;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

/**
 * @author darren Vescovi
 *
 */
public class DefinitionsEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.DefinitionsEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 * @throws EventGenerationException 
	 */
	@Test
	public void testCreateEventSet() throws EventGenerationException {
		
		System.out.println("Test Started");
		String text = ("alumni Today the fox jumped over the lazy dog "
	    		+"While the fox jumped over the lazy dog a cat ran under a truck "
	    		+"The truck missed the cat and the lazy dog was not so lazy and caught the cat");
	    
		EventDriver eventDriver = new DefinitionsEventDriver();
	    EventSet sampleSet = eventDriver.createEventSet(text.toCharArray());
	    EventSet expectedSet = new EventSet();
	    Vector<Event> tmp = new Vector<Event>();
	    
	    tmp.add(new Event("person", eventDriver));
	    tmp.add(new Event("who", eventDriver));
	    tmp.add(new Event("has", eventDriver));
	    tmp.add(new Event("received", eventDriver));
	    tmp.add(new Event("degree", eventDriver));
	    tmp.add(new Event("from", eventDriver));
	    tmp.add(new Event("school", eventDriver));
	    tmp.add(new Event("high", eventDriver));
	    tmp.add(new Event("school", eventDriver));
	    tmp.add(new Event("college", eventDriver));
	    tmp.add(new Event("university", eventDriver));
	    tmp.add(new Event("present", eventDriver));
	    tmp.add(new Event("time", eventDriver));
	    tmp.add(new Event("age", eventDriver));
	    tmp.add(new Event("alert", eventDriver));
	    tmp.add(new Event("carnivorous", eventDriver));
	    tmp.add(new Event("mammal", eventDriver));
	    tmp.add(new Event("pointed", eventDriver));
	    tmp.add(new Event("muzzle", eventDriver));
	    tmp.add(new Event("ears", eventDriver));
	    tmp.add(new Event("bushy", eventDriver));
	    tmp.add(new Event("tail", eventDriver));
	    tmp.add(new Event("move", eventDriver));
	    tmp.add(new Event("forward", eventDriver));
	    tmp.add(new Event("leaps", eventDriver));
	    tmp.add(new Event("bounds", eventDriver));
	    tmp.add(new Event("moving", eventDriver));
	    tmp.add(new Event("slowly", eventDriver));
	    tmp.add(new Event("gently", eventDriver));
	    tmp.add(new Event("member", eventDriver));
	    tmp.add(new Event("genus", eventDriver));
	    tmp.add(new Event("Canis", eventDriver));
	    tmp.add(new Event("probably", eventDriver));
	    tmp.add(new Event("descended", eventDriver));
	    tmp.add(new Event("from", eventDriver));
	    tmp.add(new Event("common", eventDriver));
	    tmp.add(new Event("wolf", eventDriver));
	    tmp.add(new Event("has", eventDriver));
	    tmp.add(new Event("been", eventDriver));
	    tmp.add(new Event("domesticated", eventDriver));
	    tmp.add(new Event("man", eventDriver));
	    tmp.add(new Event("since", eventDriver));
	    tmp.add(new Event("prehistoric", eventDriver));
	    tmp.add(new Event("times", eventDriver));
	    tmp.add(new Event("alert", eventDriver));
	    tmp.add(new Event("carnivorous", eventDriver));
	    tmp.add(new Event("mammal", eventDriver));
	    tmp.add(new Event("pointed", eventDriver));
	    tmp.add(new Event("muzzle", eventDriver));
	    tmp.add(new Event("ears", eventDriver));
	    tmp.add(new Event("bushy", eventDriver));
	    tmp.add(new Event("tail", eventDriver));
	    tmp.add(new Event("move", eventDriver));
	    tmp.add(new Event("forward", eventDriver));
	    tmp.add(new Event("leaps", eventDriver));
	    tmp.add(new Event("bounds", eventDriver));
	    tmp.add(new Event("moving", eventDriver));
	    tmp.add(new Event("slowly", eventDriver));
	    tmp.add(new Event("gently", eventDriver));
	    tmp.add(new Event("member", eventDriver));
	    tmp.add(new Event("genus", eventDriver));
	    tmp.add(new Event("Canis", eventDriver));
	    tmp.add(new Event("probably", eventDriver));
	    tmp.add(new Event("descended", eventDriver));
	    tmp.add(new Event("from", eventDriver));
	    tmp.add(new Event("common", eventDriver));
	    tmp.add(new Event("wolf", eventDriver));
	    tmp.add(new Event("has", eventDriver));
	    tmp.add(new Event("been", eventDriver));
	    tmp.add(new Event("domesticated", eventDriver));
	    tmp.add(new Event("man", eventDriver));
	    tmp.add(new Event("since", eventDriver));
	    tmp.add(new Event("prehistoric", eventDriver));
	    tmp.add(new Event("times", eventDriver));
	    tmp.add(new Event("feline", eventDriver));
	    tmp.add(new Event("mammal", eventDriver));
	    tmp.add(new Event("usually", eventDriver));
	    tmp.add(new Event("having", eventDriver));
	    tmp.add(new Event("thick", eventDriver));
	    tmp.add(new Event("soft", eventDriver));
	    tmp.add(new Event("fur", eventDriver));
	    tmp.add(new Event("no", eventDriver));
	    tmp.add(new Event("ability", eventDriver));
	    tmp.add(new Event("roar", eventDriver));
	    tmp.add(new Event("domestic", eventDriver));
	    tmp.add(new Event("cats", eventDriver));
	    tmp.add(new Event("move", eventDriver));
	    tmp.add(new Event("fast", eventDriver));
	    tmp.add(new Event("using", eventDriver));
	    tmp.add(new Event("ones", eventDriver));
	    tmp.add(new Event("feet", eventDriver));
	    tmp.add(new Event("one", eventDriver));
	    tmp.add(new Event("foot", eventDriver));
	    tmp.add(new Event("off", eventDriver));
	    tmp.add(new Event("ground", eventDriver));
	    tmp.add(new Event("any", eventDriver));
	    tmp.add(new Event("given", eventDriver));
	    tmp.add(new Event("time", eventDriver));
	    tmp.add(new Event("automotive", eventDriver));
	    tmp.add(new Event("vehicle", eventDriver));
	    tmp.add(new Event("suitable", eventDriver));
	    tmp.add(new Event("hauling", eventDriver));
	    tmp.add(new Event("automotive", eventDriver));
	    tmp.add(new Event("vehicle", eventDriver));
	    tmp.add(new Event("suitable", eventDriver));
	    tmp.add(new Event("hauling", eventDriver));
	    tmp.add(new Event("fail", eventDriver));
	    tmp.add(new Event("perceive", eventDriver));
	    tmp.add(new Event("catch", eventDriver));
	    tmp.add(new Event("senses", eventDriver));
	    tmp.add(new Event("mind", eventDriver));
	    tmp.add(new Event("feline", eventDriver));
	    tmp.add(new Event("mammal", eventDriver));
	    tmp.add(new Event("usually", eventDriver));
	    tmp.add(new Event("having", eventDriver));
	    tmp.add(new Event("thick", eventDriver));
	    tmp.add(new Event("soft", eventDriver));
	    tmp.add(new Event("fur", eventDriver));
	    tmp.add(new Event("no", eventDriver));
	    tmp.add(new Event("ability", eventDriver));
	    tmp.add(new Event("roar", eventDriver));
	    tmp.add(new Event("domestic", eventDriver));
	    tmp.add(new Event("cats", eventDriver));
	    tmp.add(new Event("moving", eventDriver));
	    tmp.add(new Event("slowly", eventDriver));
	    tmp.add(new Event("gently", eventDriver));
	    tmp.add(new Event("member", eventDriver));
	    tmp.add(new Event("genus", eventDriver));
	    tmp.add(new Event("Canis", eventDriver));
	    tmp.add(new Event("probably", eventDriver));
	    tmp.add(new Event("descended", eventDriver));
	    tmp.add(new Event("from", eventDriver));
	    tmp.add(new Event("common", eventDriver));
	    tmp.add(new Event("wolf", eventDriver));
	    tmp.add(new Event("has", eventDriver));
	    tmp.add(new Event("been", eventDriver));
	    tmp.add(new Event("domesticated", eventDriver));
	    tmp.add(new Event("man", eventDriver));
	    tmp.add(new Event("since", eventDriver));
	    tmp.add(new Event("prehistoric", eventDriver));
	    tmp.add(new Event("times", eventDriver));
	    tmp.add(new Event("negation", eventDriver));
	    tmp.add(new Event("word", eventDriver));
	    tmp.add(new Event("group", eventDriver));
	    tmp.add(new Event("words", eventDriver));
	    tmp.add(new Event("very", eventDriver));
	    tmp.add(new Event("great", eventDriver));
	    tmp.add(new Event("extent", eventDriver));
	    tmp.add(new Event("degree", eventDriver));
	    tmp.add(new Event("moving", eventDriver));
	    tmp.add(new Event("slowly", eventDriver));
	    tmp.add(new Event("gently", eventDriver));
	    tmp.add(new Event("discover", eventDriver));
	    tmp.add(new Event("come", eventDriver));
	    tmp.add(new Event("upon", eventDriver));
	    tmp.add(new Event("accidentally", eventDriver));
	    tmp.add(new Event("suddenly", eventDriver));
	    tmp.add(new Event("unexpectedly", eventDriver));
	    tmp.add(new Event("feline", eventDriver));
	    tmp.add(new Event("mammal", eventDriver));
	    tmp.add(new Event("usually", eventDriver));
	    tmp.add(new Event("having", eventDriver));
	    tmp.add(new Event("thick", eventDriver));
	    tmp.add(new Event("soft", eventDriver));
	    tmp.add(new Event("fur", eventDriver));
	    tmp.add(new Event("no", eventDriver));
	    tmp.add(new Event("ability", eventDriver));
	    tmp.add(new Event("roar", eventDriver));
	    tmp.add(new Event("domestic", eventDriver));
	    tmp.add(new Event("cats", eventDriver));
	    
	    expectedSet.addEvents(tmp);
	    
	    
	    
		assertTrue(expectedSet.equals(sampleSet));
	}

}
