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
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.generics.EventSet;

/**
 * @author darrenvescovi
 *
 */
public class POSBiGramEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.POSNGramEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 * @throws EventGenerationException 
	 */
	@Test
	public void testCreateEventSetDocumentSet() throws EventGenerationException {
			System.out.println("Test Started");
			String text = ("There once was a man from Nantucket , " +
		    		       "who kept all his cash in a bucket , " +
		    		       "but his daughter, named Nan , " +
		    		       "ran away with a man, " +
		    		       "and as for the bucket, Nantucket .");
			
		    EventDriver eventDriver = new POSNGramEventDriver();
		    eventDriver.setParameter("N", 2);
		    
		    EventSet sampleSet = eventDriver.createEventSet(text.toCharArray());
		    //System.out.println(sampleSet.size());
		    //System.out.println(sampleSet.toString());
		    
		    
		    EventSet expectedSet = new EventSet();
		    Vector<Event> tmp = new Vector<Event>();

			tmp.add(new Event("(EX)-(RB)", null));
 			tmp.add(new Event("(RB)-(VBD)", null));
 			tmp.add(new Event("(VBD)-(DT)", null));
 			tmp.add(new Event("(DT)-(NN)", null));
 			tmp.add(new Event("(NN)-(IN)", null));
 			tmp.add(new Event("(IN)-(NNP)", null));
 			tmp.add(new Event("(NNP)-(,)", null));
 			tmp.add(new Event("(,)-(WP)", null));
 			tmp.add(new Event("(WP)-(VBD)", null));
 			tmp.add(new Event("(VBD)-(DT)", null));
 			tmp.add(new Event("(DT)-(PRP$)", null));
 			tmp.add(new Event("(PRP$)-(NN)", null));
 			tmp.add(new Event("(NN)-(IN)", null));
 			tmp.add(new Event("(IN)-(DT)", null));
 			tmp.add(new Event("(DT)-(NN)", null));
 			tmp.add(new Event("(NN)-(,)", null));
 			tmp.add(new Event("(,)-(CC)", null));
 			tmp.add(new Event("(CC)-(PRP$)", null));
 			tmp.add(new Event("(PRP$)-(NN)", null));
 			tmp.add(new Event("(NN)-(,)", null));
 			tmp.add(new Event("(,)-(VBN)", null));
 			tmp.add(new Event("(VBN)-(NNP)", null));
 			tmp.add(new Event("(NNP)-(,)", null));
 			tmp.add(new Event("(,)-(VBD)", null));
 			tmp.add(new Event("(VBD)-(RB)", null));
 			tmp.add(new Event("(RB)-(IN)", null));
 			tmp.add(new Event("(IN)-(DT)", null));
 			tmp.add(new Event("(DT)-(NN)", null));
 			tmp.add(new Event("(NN)-(,)", null));
 			tmp.add(new Event("(,)-(CC)", null));
 			tmp.add(new Event("(CC)-(IN)", null));
 			tmp.add(new Event("(IN)-(IN)", null));
 			tmp.add(new Event("(IN)-(DT)", null));
 			tmp.add(new Event("(DT)-(NN)", null));
 			tmp.add(new Event("(NN)-(,)", null));
 			tmp.add(new Event("(,)-(NNP)", null));
 			tmp.add(new Event("(NNP)-(.)", null));


		     expectedSet.addEvents(tmp);
		     
//		     for(int i=0; i<sampleSet.size(); i++)
//			    {
//			    	System.out.println(sampleSet.eventAt(i).toString()+" "+expectedSet.eventAt(i).toString());
//			    	System.out.println(sampleSet.eventAt(i).toString().equals(expectedSet.eventAt(i).toString()));
//			    }
//		     System.out.println(expectedSet.size());
		     
		     
			    assertTrue(expectedSet.equals(sampleSet));
	}

}
