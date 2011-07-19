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

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;

/**
 * @author Joshua Booth
 *
 */

public class NullEventDriverTest{

    /**
     * Test method for {@link com.jgaap.eventDrivers.NullEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
     */
	@SuppressWarnings("deprecation")
	@Test
	public void testCreateEventSetDocumentSet() {
	    /*Test One - Text to Text with new line char*/
	    Document doc = new Document();
	    //Text Taken from Patrick Juola to standardize testing	
	    doc.readStringText("sir I send a rhyme excelling\n"+
				   "in sacred truth and rigid spelling\n"+
				   "numerical sprites elucidate\n"+
				   "for me the lexicons full weight\n"+
				   "if nature gain who can complain\n"+
				   "tho dr johnson fulminate");
	     EventSet sampleEventSet = new NullEventDriver().createEventSet(doc);
	     EventSet expectedEventSet = new EventSet();
	     Vector<Event> tmp = new Vector<Event>();
	     tmp.add(new Event("sir I send a rhyme excelling\n"+
				   "in sacred truth and rigid spelling\n"+
				   "numerical sprites elucidate\n"+
				   "for me the lexicons full weight\n"+
				   "if nature gain who can complain\n"+
			           "tho dr johnson fulminate"));
	     expectedEventSet.addEvents(tmp);
	     assertTrue(expectedEventSet.equals(sampleEventSet));

	     /*null case would be covered by the spaces in the above test*/
	  }
	}
    
