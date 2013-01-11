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

import com.jgaap.generics.Event;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;

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
	    
	    EventSet sampleSet = new DefinitionsEventDriver().createEventSet(text.toCharArray());
	    EventSet expectedSet = new EventSet();
	    Vector<Event> tmp = new Vector<Event>();
	    
	    tmp.add(new Event("person", null));
	    tmp.add(new Event("who", null));
	    tmp.add(new Event("has", null));
	    tmp.add(new Event("received", null));
	    tmp.add(new Event("degree", null));
	    tmp.add(new Event("from", null));
	    tmp.add(new Event("school", null));
	    tmp.add(new Event("high", null));
	    tmp.add(new Event("school", null));
	    tmp.add(new Event("college", null));
	    tmp.add(new Event("university", null));
	    tmp.add(new Event("present", null));
	    tmp.add(new Event("time", null));
	    tmp.add(new Event("age", null));
	    tmp.add(new Event("alert", null));
	    tmp.add(new Event("carnivorous", null));
	    tmp.add(new Event("mammal", null));
	    tmp.add(new Event("pointed", null));
	    tmp.add(new Event("muzzle", null));
	    tmp.add(new Event("ears", null));
	    tmp.add(new Event("bushy", null));
	    tmp.add(new Event("tail", null));
	    tmp.add(new Event("move", null));
	    tmp.add(new Event("forward", null));
	    tmp.add(new Event("leaps", null));
	    tmp.add(new Event("bounds", null));
	    tmp.add(new Event("moving", null));
	    tmp.add(new Event("slowly", null));
	    tmp.add(new Event("gently", null));
	    tmp.add(new Event("member", null));
	    tmp.add(new Event("genus", null));
	    tmp.add(new Event("Canis", null));
	    tmp.add(new Event("probably", null));
	    tmp.add(new Event("descended", null));
	    tmp.add(new Event("from", null));
	    tmp.add(new Event("common", null));
	    tmp.add(new Event("wolf", null));
	    tmp.add(new Event("has", null));
	    tmp.add(new Event("been", null));
	    tmp.add(new Event("domesticated", null));
	    tmp.add(new Event("man", null));
	    tmp.add(new Event("since", null));
	    tmp.add(new Event("prehistoric", null));
	    tmp.add(new Event("times", null));
	    tmp.add(new Event("alert", null));
	    tmp.add(new Event("carnivorous", null));
	    tmp.add(new Event("mammal", null));
	    tmp.add(new Event("pointed", null));
	    tmp.add(new Event("muzzle", null));
	    tmp.add(new Event("ears", null));
	    tmp.add(new Event("bushy", null));
	    tmp.add(new Event("tail", null));
	    tmp.add(new Event("move", null));
	    tmp.add(new Event("forward", null));
	    tmp.add(new Event("leaps", null));
	    tmp.add(new Event("bounds", null));
	    tmp.add(new Event("moving", null));
	    tmp.add(new Event("slowly", null));
	    tmp.add(new Event("gently", null));
	    tmp.add(new Event("member", null));
	    tmp.add(new Event("genus", null));
	    tmp.add(new Event("Canis", null));
	    tmp.add(new Event("probably", null));
	    tmp.add(new Event("descended", null));
	    tmp.add(new Event("from", null));
	    tmp.add(new Event("common", null));
	    tmp.add(new Event("wolf", null));
	    tmp.add(new Event("has", null));
	    tmp.add(new Event("been", null));
	    tmp.add(new Event("domesticated", null));
	    tmp.add(new Event("man", null));
	    tmp.add(new Event("since", null));
	    tmp.add(new Event("prehistoric", null));
	    tmp.add(new Event("times", null));
	    tmp.add(new Event("feline", null));
	    tmp.add(new Event("mammal", null));
	    tmp.add(new Event("usually", null));
	    tmp.add(new Event("having", null));
	    tmp.add(new Event("thick", null));
	    tmp.add(new Event("soft", null));
	    tmp.add(new Event("fur", null));
	    tmp.add(new Event("no", null));
	    tmp.add(new Event("ability", null));
	    tmp.add(new Event("roar", null));
	    tmp.add(new Event("domestic", null));
	    tmp.add(new Event("cats", null));
	    tmp.add(new Event("move", null));
	    tmp.add(new Event("fast", null));
	    tmp.add(new Event("using", null));
	    tmp.add(new Event("ones", null));
	    tmp.add(new Event("feet", null));
	    tmp.add(new Event("one", null));
	    tmp.add(new Event("foot", null));
	    tmp.add(new Event("off", null));
	    tmp.add(new Event("ground", null));
	    tmp.add(new Event("any", null));
	    tmp.add(new Event("given", null));
	    tmp.add(new Event("time", null));
	    tmp.add(new Event("automotive", null));
	    tmp.add(new Event("vehicle", null));
	    tmp.add(new Event("suitable", null));
	    tmp.add(new Event("hauling", null));
	    tmp.add(new Event("automotive", null));
	    tmp.add(new Event("vehicle", null));
	    tmp.add(new Event("suitable", null));
	    tmp.add(new Event("hauling", null));
	    tmp.add(new Event("fail", null));
	    tmp.add(new Event("perceive", null));
	    tmp.add(new Event("catch", null));
	    tmp.add(new Event("senses", null));
	    tmp.add(new Event("mind", null));
	    tmp.add(new Event("feline", null));
	    tmp.add(new Event("mammal", null));
	    tmp.add(new Event("usually", null));
	    tmp.add(new Event("having", null));
	    tmp.add(new Event("thick", null));
	    tmp.add(new Event("soft", null));
	    tmp.add(new Event("fur", null));
	    tmp.add(new Event("no", null));
	    tmp.add(new Event("ability", null));
	    tmp.add(new Event("roar", null));
	    tmp.add(new Event("domestic", null));
	    tmp.add(new Event("cats", null));
	    tmp.add(new Event("moving", null));
	    tmp.add(new Event("slowly", null));
	    tmp.add(new Event("gently", null));
	    tmp.add(new Event("member", null));
	    tmp.add(new Event("genus", null));
	    tmp.add(new Event("Canis", null));
	    tmp.add(new Event("probably", null));
	    tmp.add(new Event("descended", null));
	    tmp.add(new Event("from", null));
	    tmp.add(new Event("common", null));
	    tmp.add(new Event("wolf", null));
	    tmp.add(new Event("has", null));
	    tmp.add(new Event("been", null));
	    tmp.add(new Event("domesticated", null));
	    tmp.add(new Event("man", null));
	    tmp.add(new Event("since", null));
	    tmp.add(new Event("prehistoric", null));
	    tmp.add(new Event("times", null));
	    tmp.add(new Event("negation", null));
	    tmp.add(new Event("word", null));
	    tmp.add(new Event("group", null));
	    tmp.add(new Event("words", null));
	    tmp.add(new Event("very", null));
	    tmp.add(new Event("great", null));
	    tmp.add(new Event("extent", null));
	    tmp.add(new Event("degree", null));
	    tmp.add(new Event("moving", null));
	    tmp.add(new Event("slowly", null));
	    tmp.add(new Event("gently", null));
	    tmp.add(new Event("discover", null));
	    tmp.add(new Event("come", null));
	    tmp.add(new Event("upon", null));
	    tmp.add(new Event("accidentally", null));
	    tmp.add(new Event("suddenly", null));
	    tmp.add(new Event("unexpectedly", null));
	    tmp.add(new Event("feline", null));
	    tmp.add(new Event("mammal", null));
	    tmp.add(new Event("usually", null));
	    tmp.add(new Event("having", null));
	    tmp.add(new Event("thick", null));
	    tmp.add(new Event("soft", null));
	    tmp.add(new Event("fur", null));
	    tmp.add(new Event("no", null));
	    tmp.add(new Event("ability", null));
	    tmp.add(new Event("roar", null));
	    tmp.add(new Event("domestic", null));
	    tmp.add(new Event("cats", null));
	    
	    expectedSet.addEvents(tmp);
	    
	    
	    
		assertTrue(expectedSet.equals(sampleSet));
	}

}
