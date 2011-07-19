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
package com.jgaap.classifiers;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Vector;

import org.junit.Test;

import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.Pair;

/**
 * @author darren vescovi
 * 
 * 
 */
public class MahalanobisDistanceTest {

	@Test
	public void testAnalyze() {
		EventSet known1 = new EventSet();
		EventSet known2 = new EventSet();
		EventSet unknown = new EventSet();

		known1.addEvent(new Event("Mary"));
		known1.addEvent(new Event("had"));
		known1.addEvent(new Event("a"));
		known1.addEvent(new Event("little"));
		known1.addEvent(new Event("lamb"));
		known1.addEvent(new Event("whose"));
		known1.addEvent(new Event("fleece"));
		known1.addEvent(new Event("was"));
		known1.addEvent(new Event("white"));
		known1.addEvent(new Event("as"));
		known1.addEvent(new Event("snow."));
		known1.addEvent(new Event("Mary"));
		known1.addEvent(new Event("had"));
		known1.addEvent(new Event("a"));
		known1.addEvent(new Event("little"));
		known1.addEvent(new Event("lamb"));
		known1.addEvent(new Event("whose"));
		known1.addEvent(new Event("fleece"));
		known1.addEvent(new Event("was"));
		known1.addEvent(new Event("white"));
		known1.addEvent(new Event("as"));
		known1.addEvent(new Event("snow."));
		known1.addEvent(new Event("Mary"));
		known1.addEvent(new Event("had"));
		known1.addEvent(new Event("a"));
		known1.addEvent(new Event("little"));
		known1.addEvent(new Event("lamb"));
		known1.addEvent(new Event("whose"));
		known1.addEvent(new Event("fleece"));
		known1.addEvent(new Event("was"));
		known1.addEvent(new Event("white"));
		known1.addEvent(new Event("as"));
		known1.addEvent(new Event("snow."));
		known1.addEvent(new Event("Mary"));
		known1.addEvent(new Event("had"));
		known1.addEvent(new Event("a"));
		known1.addEvent(new Event("little"));
		known1.addEvent(new Event("lamb"));
		known1.addEvent(new Event("whose"));
		known1.addEvent(new Event("fleece"));
		known1.addEvent(new Event("was"));
		known1.addEvent(new Event("white"));
		known1.addEvent(new Event("as"));
		known1.addEvent(new Event("snow."));
		known1.setAuthor("Mary");

		known2.addEvent(new Event("Peter"));
		known2.addEvent(new Event("piper"));
		known2.addEvent(new Event("picked"));
		known2.addEvent(new Event("a"));
		known2.addEvent(new Event("pack"));
		known2.addEvent(new Event("of"));
		known2.addEvent(new Event("pickled"));
		known2.addEvent(new Event("peppers."));
		known2.addEvent(new Event("Peter"));
		known2.addEvent(new Event("piper"));
		known2.addEvent(new Event("picked"));
		known2.addEvent(new Event("a"));
		known2.addEvent(new Event("pack"));
		known2.addEvent(new Event("of"));
		known2.addEvent(new Event("pickled"));
		known2.addEvent(new Event("peppers."));
		known2.addEvent(new Event("Peter"));
		known2.addEvent(new Event("piper"));
		known2.addEvent(new Event("picked"));
		known2.addEvent(new Event("a"));
		known2.addEvent(new Event("pack"));
		known2.addEvent(new Event("of"));
		known2.addEvent(new Event("pickled"));
		known2.addEvent(new Event("peppers."));
		known2.addEvent(new Event("Peter"));
		known2.addEvent(new Event("piper"));
		known2.addEvent(new Event("picked"));
		known2.addEvent(new Event("a"));
		known2.addEvent(new Event("pack"));
		known2.addEvent(new Event("of"));
		known2.addEvent(new Event("pickled"));
		known2.addEvent(new Event("peppers."));
		known2.addEvent(new Event("Peter"));
		known2.addEvent(new Event("piper"));
		known2.addEvent(new Event("picked"));
		known2.addEvent(new Event("a"));
		known2.addEvent(new Event("pack"));
		known2.addEvent(new Event("of"));
		known2.addEvent(new Event("pickled"));
		known2.addEvent(new Event("peppers."));
		known2.setAuthor("Peter");

		unknown.addEvent(new Event("Mary"));
		unknown.addEvent(new Event("had"));
		unknown.addEvent(new Event("a"));
		unknown.addEvent(new Event("little"));
		unknown.addEvent(new Event("lambda"));
		unknown.addEvent(new Event("whose"));
		unknown.addEvent(new Event("syntax"));
		unknown.addEvent(new Event("was"));
		unknown.addEvent(new Event("white"));
		unknown.addEvent(new Event("as"));
		unknown.addEvent(new Event("snow."));
		unknown.addEvent(new Event("Mary"));
		unknown.addEvent(new Event("had"));
		unknown.addEvent(new Event("a"));
		unknown.addEvent(new Event("little"));
		unknown.addEvent(new Event("lambda"));
		unknown.addEvent(new Event("whose"));
		unknown.addEvent(new Event("syntax"));
		unknown.addEvent(new Event("was"));
		unknown.addEvent(new Event("white"));
		unknown.addEvent(new Event("as"));
		unknown.addEvent(new Event("snow."));
		unknown.addEvent(new Event("Mary"));
		unknown.addEvent(new Event("had"));
		unknown.addEvent(new Event("a"));
		unknown.addEvent(new Event("little"));
		unknown.addEvent(new Event("lambda"));
		unknown.addEvent(new Event("whose"));
		unknown.addEvent(new Event("syntax"));
		unknown.addEvent(new Event("was"));
		unknown.addEvent(new Event("white"));
		unknown.addEvent(new Event("as"));
		unknown.addEvent(new Event("snow."));
		unknown.addEvent(new Event("Mary"));
		unknown.addEvent(new Event("had"));
		unknown.addEvent(new Event("a"));
		unknown.addEvent(new Event("little"));
		unknown.addEvent(new Event("lambda"));
		unknown.addEvent(new Event("whose"));
		unknown.addEvent(new Event("syntax"));
		unknown.addEvent(new Event("was"));
		unknown.addEvent(new Event("white"));
		unknown.addEvent(new Event("as"));
		unknown.addEvent(new Event("snow."));

		Vector<EventSet> esv = new Vector<EventSet>();
		esv.add(known1);
		esv.add(known2);

		List<Pair<String, Double>> t = new MahalanobisDistance().analyze(
				unknown, esv);
		String r = t.get(0).getFirst();

		String s = "Mary";

		assertTrue(r.equals(s));
	}
}
