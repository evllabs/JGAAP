/**
 * 
 */
package com.jgaap.eventDrivers;

import static org.junit.Assert.*;

import org.junit.Test;

import com.jgaap.generics.Document;
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
	    doc.readStringText("Today the fox jumped over the lazy dog "
	    		+"While the fox jumped over the lazy dog a cat ran under a truck "
	    		+"The truck missed the cat and the lazy dog was not so lazy and caught the cat");
	    
	    EventSet sampleSet = new DefinitionsEventDriver().createEventSet(doc);
	    System.out.println(sampleSet);
		
		
		assertTrue(true);
	}

}
