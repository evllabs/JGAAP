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
 * @author darrenvescovi
 *
 */
public class PartOfSpeechEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.PartOfSpeechEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {
			System.out.println("Test Started");
		 	Document doc = new Document();
		    doc.readStringText("Today the fox jumped over the lazy dog "
		    		+"While the fox jumped over the lazy dog a cat ran under a truck "
		    		+"The truck missed the cat and the lazy dog was not so lazy and caught the cat");
		    
		    EventSet sampleSet = new PartOfSpeechEventDriver().createEventSet(doc);
		    System.out.println(sampleSet.size());
		    
		    
		    EventSet expectedSet = new EventSet();
		    Vector<Event> tmp = new Vector<Event>();
		    tmp.add(new Event("NN"));
		    tmp.add(new Event("DT"));
		    tmp.add(new Event("NN"));
		    tmp.add(new Event("VBD"));
		    tmp.add(new Event("IN"));
		    tmp.add(new Event("DT"));
		    tmp.add(new Event("JJ"));
		    tmp.add(new Event("NN"));
		    
		    tmp.add(new Event("IN"));
		    tmp.add(new Event("DT"));
		    tmp.add(new Event("NN"));
		    tmp.add(new Event("VBD"));
		    tmp.add(new Event("IN"));
		    tmp.add(new Event("DT"));
		    tmp.add(new Event("JJ"));
		    tmp.add(new Event("NN"));
		    tmp.add(new Event("DT"));
		    tmp.add(new Event("NN"));
		    tmp.add(new Event("VBD"));//
		    tmp.add(new Event("IN"));
		    tmp.add(new Event("DT"));
		    tmp.add(new Event("NN"));
		   
		    tmp.add(new Event("DT"));
		    tmp.add(new Event("NN"));
		    tmp.add(new Event("VBD"));//
		    tmp.add(new Event("DT"));
		    tmp.add(new Event("NN"));
		    tmp.add(new Event("CC"));
		    tmp.add(new Event("DT"));
		    tmp.add(new Event("JJ"));
		    tmp.add(new Event("NN"));
		    tmp.add(new Event("VBD")); //33
		    tmp.add(new Event("RB"));
		    tmp.add(new Event("RB"));
		    tmp.add(new Event("JJ"));
		    tmp.add(new Event("CC"));
		    tmp.add(new Event("VBN"));
		    tmp.add(new Event("DT"));
		    tmp.add(new Event("NN"));
		    

		     expectedSet.addEvents(tmp);
		     
		     for(int i=0; i<sampleSet.size(); i++)
			    {
			    	System.out.println(sampleSet.eventAt(i).toString()+" "+expectedSet.eventAt(i).toString());
			    	System.out.println(sampleSet.eventAt(i).toString().equals(expectedSet.eventAt(i).toString()));
			    }
		     System.out.println(expectedSet.size());
		     
		     
			    assertTrue(expectedSet.equals(sampleSet));
	}

}
