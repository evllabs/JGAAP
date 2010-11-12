/**
 * 
 */
package com.jgaap.eventDrivers;

import static org.junit.Assert.*;

import java.util.Vector;

import org.junit.Test;

import com.jgaap.jgaapConstants;
import com.jgaap.generics.Document;
import com.jgaap.generics.DocumentSet;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;

/**
 * @author Peter Rutenbar
 *
 */

public class MostCommonEventDriverTest {


	/**
	 * Test method for {@link com.jgaap.eventDrivers.MostCommonEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
	 */
	@Test
	    public void testCreateEventSetDocumentSet(){
	    
		
		MostCommonEventDriver myDriver = new MostCommonEventDriver();
	    myDriver.setParameter("N", "50");
		
		// Generate sample input data
		
	    /* Note! This only works for N == 50 */
		String input1 = "";
		for (int i=1;i<=50;i++)
			for (int j=1;j<=i;j++)
				input1 = input1 + (" x"+i);

		String input2 = "";
		for (int i=1;i<=50;i++)
			for (int j=1;j<=i;j++)
				input2 = input2 + (" y"+i);

	    Document doc1 = new Document();
	    doc1.readStringText(input1);
	    Document doc2 = new Document();
	    doc2.readStringText(input2);

	    DocumentSet docSetjoint = new DocumentSet();
	    docSetjoint.register(doc1);
	    docSetjoint.register(doc2);

	    DocumentSet docSet1 = new DocumentSet(doc1);
	    DocumentSet docSet2 = new DocumentSet(doc2);
		
		
		// test createEventSet(DocumentSet ds) on a DocumentSet of the two generated documents
	    EventSet sampleEventSetjoint = myDriver.createEventSet(docSetjoint);


		// now test createEventSet(Vector<DocumentSet> dsv) on a vector of 2 DocumentSets, each of which contain 1 document
		Vector<DocumentSet> vds = new Vector<DocumentSet>();
		Vector<EventSet>	ves;
		vds.add(docSet1);
		vds.add(docSet2);
		ves = myDriver.createEventSet(vds);
		

		/* Now generate the expected results */

	    EventSet expectedEventSetjoint = new EventSet();
	    EventSet expectedEventSet1 = new EventSet();
	    EventSet expectedEventSet2 = new EventSet();

		
	    for (int i=26;i<=50;i++)
		for (int j=1;j<=i;j++) {
		    expectedEventSetjoint.events.add(new Event("x"+i));
		    expectedEventSet1.events.add(new Event("x"+i));
		}
	    for (int i=26;i<=50;i++)
		for (int j=1;j<=i;j++) {
		    expectedEventSetjoint.events.add(new Event("y"+i));
		    expectedEventSet2.events.add(new Event("y"+i));
		}

	    Vector<EventSet> ees = new Vector<EventSet>();
	    ees.add(expectedEventSet1);
	    ees.add(expectedEventSet2);

		/* Now TEST the results! */
	    assertTrue(sampleEventSetjoint.equals(expectedEventSetjoint));
	    assertTrue(ves.elementAt(0).equals(ees.elementAt(0)));
	    assertTrue(ves.elementAt(1).equals(ees.elementAt(1)));
	}

}	    
	    
	    
