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
public class POSBiGramEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.POSBiGramEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {
			System.out.println("Test Started");
		 	Document doc = new Document();
			/* Note space separation of punctuation */
		    doc.readStringText("There once was a man from Nantucket , " +
		    		       "who kept all his cash in a bucket , " +
		    		       "but his daughter, named Nan , " +
		    		       "ran away with a man, " +
		    		       "and as for the bucket, Nantucket .");
				
		    
		    EventSet sampleSet = new POSBiGramEventDriver().createEventSet(doc);
		    //System.out.println(sampleSet.size());
		    //System.out.println(sampleSet.toString());
		    
		    
		    EventSet expectedSet = new EventSet();
		    Vector<Event> tmp = new Vector<Event>();

			tmp.add(new Event("(EX)-(RB)"));
 			tmp.add(new Event("(RB)-(VBD)"));
 			tmp.add(new Event("(VBD)-(DT)"));
 			tmp.add(new Event("(DT)-(NN)"));
 			tmp.add(new Event("(NN)-(IN)"));
 			tmp.add(new Event("(IN)-(NNP)"));
 			tmp.add(new Event("(NNP)-(,)"));
 			tmp.add(new Event("(,)-(WP)"));
 			tmp.add(new Event("(WP)-(VBD)"));
 			tmp.add(new Event("(VBD)-(DT)"));
 			tmp.add(new Event("(DT)-(PRP$)"));
 			tmp.add(new Event("(PRP$)-(NN)"));
 			tmp.add(new Event("(NN)-(IN)"));
 			tmp.add(new Event("(IN)-(DT)"));
 			tmp.add(new Event("(DT)-(NN)"));
 			tmp.add(new Event("(NN)-(,)"));
 			tmp.add(new Event("(,)-(CC)"));
 			tmp.add(new Event("(CC)-(PRP$)"));
 			tmp.add(new Event("(PRP$)-(NN)"));
 			tmp.add(new Event("(NN)-(VBN)"));
 			tmp.add(new Event("(VBN)-(NNP)"));
 			tmp.add(new Event("(NNP)-(,)"));
 			tmp.add(new Event("(,)-(VBD)"));
 			tmp.add(new Event("(VBD)-(RB)"));
 			tmp.add(new Event("(RB)-(IN)"));
 			tmp.add(new Event("(IN)-(DT)"));
 			tmp.add(new Event("(DT)-(NN)"));
 			tmp.add(new Event("(NN)-(CC)"));
 			tmp.add(new Event("(CC)-(IN)"));
 			tmp.add(new Event("(IN)-(IN)"));
 			tmp.add(new Event("(IN)-(DT)"));
 			tmp.add(new Event("(DT)-(NN)"));
 			tmp.add(new Event("(NN)-(NNP)"));
 			tmp.add(new Event("(NNP)-(.)"));


		     expectedSet.addEvents(tmp);
		     
/*
		     for(int i=0; i<sampleSet.size(); i++)
			    {
			    	System.out.println(sampleSet.eventAt(i).toString()+" "+expectedSet.eventAt(i).toString());
			    	System.out.println(sampleSet.eventAt(i).toString().equals(expectedSet.eventAt(i).toString()));
			    }
		     System.out.println(expectedSet.size());
*/
		     
		     
			    assertTrue(expectedSet.equals(sampleSet));
	}

}
