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
package com.jgaap.eventDrivers;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventGenerationException;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

/**
 * 
 * @author David Berdik
 *
 */
public class KSkipNGramCharacterEventDriverTest {
	@Test
	public void testCreateEventDocumentSet() throws EventGenerationException {
		String sampleInput = "Lorem ipsum dolor sit amet, ";
		EventDriver eventDriver = new KSkipNGramCharacterEventDriver();
		eventDriver.setParameter("K", 3);
		eventDriver.setParameter("N", 4);
		EventSet actualSet = eventDriver.createEventSet(sampleInput.toCharArray());
		
		EventSet expectedSet = new EventSet();
		expectedSet.addEvent(new Event("L m s d", eventDriver));
		expectedSet.addEvent(new Event("o   u o", eventDriver));
		expectedSet.addEvent(new Event("r i m l", eventDriver));
		expectedSet.addEvent(new Event("e p   o", eventDriver));
		expectedSet.addEvent(new Event("m s d r", eventDriver));
		expectedSet.addEvent(new Event("u o", eventDriver));
		expectedSet.addEvent(new Event("i m l s", eventDriver));
		expectedSet.addEvent(new Event("p   o i", eventDriver));
		expectedSet.addEvent(new Event("s d r t", eventDriver));
		expectedSet.addEvent(new Event("u o", eventDriver));
		expectedSet.addEvent(new Event("m l s a", eventDriver));
		expectedSet.addEvent(new Event("o i m", eventDriver));
		expectedSet.addEvent(new Event("d r t e", eventDriver));
		expectedSet.addEvent(new Event("o     t", eventDriver));
		expectedSet.addEvent(new Event("l s a ,", eventDriver));
		expectedSet.addEvent(new Event("o i m", eventDriver));
		
		assertTrue(expectedSet.equals(actualSet));
	}
}