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
 * @author student
 *
 */
public class PorterStemmerWithIrregularEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.PorterStemmerWithIrregularEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 * @throws EventGenerationException 
	 */
	@Test
	public void testCreateEventSet() throws EventGenerationException {
		
		/* test case 1 -- no punctuation */
		String text = (
"tests Tested TESTING TeSt " +
"867-5309 " +
"a aaron abaissiez abandon abandoned abase abash abate abated abatement "+
"abatements abates abbess abbey abbeys abbominable abbot abbots abbreviated "+
"abed abel aberga abergavenny abet abetting abhominable abhor abhorr abhorred "+
"abhorring abhors abhorson abide abides abilities ability abject abjectly "+
"abjects abjur abjure able abler aboard abode aboded abodements aboding "+
"abominable abominably abominations abortive abortives abound abounding "+
"about above abr abraham abram abreast abridg abridge abridged abridgment "+
"abroach abroad abrogate abrook abrupt abruption abruptly absence absent "+
"absey absolute absolutely absolv absolver abstains abstemious abstinence "+
"abstract absurd absyrtus abundance abundant abundantly abus abuse abused "+
"abuser abuses abusing abutting aby abysm ac academe academes accent accents "+
"accept acceptable acceptance accepted accepts access accessary accessible "+
"accidence accident accidental accidentally accidents accite accited accites "+
"acclamations accommodate accommodated accommodation accommodations "+
"accommodo accompanied accompany accompanying accomplices accomplish "+
"accomplished accomplishing accomplishment accompt accord accordant accorded "+
"accordeth according accordingly accords accost accosted account accountant "+
"accounted accounts accoutred accoutrement accoutrements accrue alumni caught");

		EventDriver eventDriver = new PorterStemmerWithIrregularEventDriver();
		EventSet sampleEventSet = eventDriver.createEventSet(text.toCharArray());
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();

		tmp.add(new Event("test", eventDriver));
		tmp.add(new Event("Test", eventDriver));
		tmp.add(new Event("TEST", eventDriver));
		tmp.add(new Event("TeSt", eventDriver));

		tmp.add(new Event("867-5309", eventDriver));

		tmp.add(new Event("a", eventDriver));
		tmp.add(new Event("aaron", eventDriver));
		tmp.add(new Event("abaissiez", eventDriver));
		tmp.add(new Event("abandon", eventDriver));
		tmp.add(new Event("abandon", eventDriver));
		tmp.add(new Event("abas", eventDriver));
		tmp.add(new Event("abash", eventDriver));
		tmp.add(new Event("abat", eventDriver));
		tmp.add(new Event("abat", eventDriver));
		tmp.add(new Event("abat", eventDriver));
		tmp.add(new Event("abat", eventDriver));
		tmp.add(new Event("abat", eventDriver));
		tmp.add(new Event("abbess", eventDriver));
		tmp.add(new Event("abbei", eventDriver));
		tmp.add(new Event("abbei", eventDriver));
		tmp.add(new Event("abbomin", eventDriver));
		tmp.add(new Event("abbot", eventDriver));
		tmp.add(new Event("abbot", eventDriver));
		tmp.add(new Event("abbrevi", eventDriver));
		tmp.add(new Event("ab", eventDriver));
		tmp.add(new Event("abel", eventDriver));
		tmp.add(new Event("aberga", eventDriver));
		tmp.add(new Event("abergavenni", eventDriver));
		tmp.add(new Event("abet", eventDriver));
		tmp.add(new Event("abet", eventDriver));
		tmp.add(new Event("abhomin", eventDriver));
		tmp.add(new Event("abhor", eventDriver));
		tmp.add(new Event("abhorr", eventDriver));
		tmp.add(new Event("abhor", eventDriver));
		tmp.add(new Event("abhor", eventDriver));
		tmp.add(new Event("abhor", eventDriver));
		tmp.add(new Event("abhorson", eventDriver));
		tmp.add(new Event("abid", eventDriver));
		tmp.add(new Event("abid", eventDriver));
		tmp.add(new Event("abil", eventDriver));
		tmp.add(new Event("abil", eventDriver));
		tmp.add(new Event("abject", eventDriver));
		tmp.add(new Event("abjectli", eventDriver));
		tmp.add(new Event("abject", eventDriver));
		tmp.add(new Event("abjur", eventDriver));
		tmp.add(new Event("abjur", eventDriver));
		tmp.add(new Event("abl", eventDriver));
		tmp.add(new Event("abler", eventDriver));
		tmp.add(new Event("aboard", eventDriver));
		tmp.add(new Event("abod", eventDriver));
		tmp.add(new Event("abod", eventDriver));
		tmp.add(new Event("abod", eventDriver));
		tmp.add(new Event("abod", eventDriver));
		tmp.add(new Event("abomin", eventDriver));
		tmp.add(new Event("abomin", eventDriver));
		tmp.add(new Event("abomin", eventDriver));
		tmp.add(new Event("abort", eventDriver));
		tmp.add(new Event("abort", eventDriver));
		tmp.add(new Event("abound", eventDriver));
		tmp.add(new Event("abound", eventDriver));
		tmp.add(new Event("about", eventDriver));
		tmp.add(new Event("abov", eventDriver));
		tmp.add(new Event("abr", eventDriver));
		tmp.add(new Event("abraham", eventDriver));
		tmp.add(new Event("abram", eventDriver));
		tmp.add(new Event("abreast", eventDriver));
		tmp.add(new Event("abridg", eventDriver));
		tmp.add(new Event("abridg", eventDriver));
		tmp.add(new Event("abridg", eventDriver));
		tmp.add(new Event("abridg", eventDriver));
		tmp.add(new Event("abroach", eventDriver));
		tmp.add(new Event("abroad", eventDriver));
		tmp.add(new Event("abrog", eventDriver));
		tmp.add(new Event("abrook", eventDriver));
		tmp.add(new Event("abrupt", eventDriver));
		tmp.add(new Event("abrupt", eventDriver));
		tmp.add(new Event("abruptli", eventDriver));
		tmp.add(new Event("absenc", eventDriver));
		tmp.add(new Event("absent", eventDriver));
		tmp.add(new Event("absei", eventDriver));
		tmp.add(new Event("absolut", eventDriver));
		tmp.add(new Event("absolut", eventDriver));
		tmp.add(new Event("absolv", eventDriver));
		tmp.add(new Event("absolv", eventDriver));
		tmp.add(new Event("abstain", eventDriver));
		tmp.add(new Event("abstemi", eventDriver));
		tmp.add(new Event("abstin", eventDriver));
		tmp.add(new Event("abstract", eventDriver));
		tmp.add(new Event("absurd", eventDriver));
		tmp.add(new Event("absyrtu", eventDriver));
		tmp.add(new Event("abund", eventDriver));
		tmp.add(new Event("abund", eventDriver));
		tmp.add(new Event("abundantli", eventDriver));
		tmp.add(new Event("abu", eventDriver));
		tmp.add(new Event("abus", eventDriver));
		tmp.add(new Event("abus", eventDriver));
		tmp.add(new Event("abus", eventDriver));
		tmp.add(new Event("abus", eventDriver));
		tmp.add(new Event("abus", eventDriver));
		tmp.add(new Event("abut", eventDriver));
		tmp.add(new Event("abi", eventDriver));
		tmp.add(new Event("abysm", eventDriver));
		tmp.add(new Event("ac", eventDriver));
		tmp.add(new Event("academ", eventDriver));
		tmp.add(new Event("academ", eventDriver));
		tmp.add(new Event("accent", eventDriver));
		tmp.add(new Event("accent", eventDriver));
		tmp.add(new Event("accept", eventDriver));
		tmp.add(new Event("accept", eventDriver));
		tmp.add(new Event("accept", eventDriver));
		tmp.add(new Event("accept", eventDriver));
		tmp.add(new Event("accept", eventDriver));
		tmp.add(new Event("access", eventDriver));
		tmp.add(new Event("accessari", eventDriver));
		tmp.add(new Event("access", eventDriver));
		tmp.add(new Event("accid", eventDriver));
		tmp.add(new Event("accid", eventDriver));
		tmp.add(new Event("accident", eventDriver));
		tmp.add(new Event("accident", eventDriver));
		tmp.add(new Event("accid", eventDriver));
		tmp.add(new Event("accit", eventDriver));
		tmp.add(new Event("accit", eventDriver));
		tmp.add(new Event("accit", eventDriver));
		tmp.add(new Event("acclam", eventDriver));
		tmp.add(new Event("accommod", eventDriver));
		tmp.add(new Event("accommod", eventDriver));
		tmp.add(new Event("accommod", eventDriver));
		tmp.add(new Event("accommod", eventDriver));
		tmp.add(new Event("accommodo", eventDriver));
		tmp.add(new Event("accompani", eventDriver));
		tmp.add(new Event("accompani", eventDriver));
		tmp.add(new Event("accompani", eventDriver));
		tmp.add(new Event("accomplic", eventDriver));
		tmp.add(new Event("accomplish", eventDriver));
		tmp.add(new Event("accomplish", eventDriver));
		tmp.add(new Event("accomplish", eventDriver));
		tmp.add(new Event("accomplish", eventDriver));
		tmp.add(new Event("accompt", eventDriver));
		tmp.add(new Event("accord", eventDriver));
		tmp.add(new Event("accord", eventDriver));
		tmp.add(new Event("accord", eventDriver));
		tmp.add(new Event("accordeth", eventDriver));
		tmp.add(new Event("accord", eventDriver));
		tmp.add(new Event("accordingli", eventDriver));
		tmp.add(new Event("accord", eventDriver));
		tmp.add(new Event("accost", eventDriver));
		tmp.add(new Event("accost", eventDriver));
		tmp.add(new Event("account", eventDriver));
		tmp.add(new Event("account", eventDriver));
		tmp.add(new Event("account", eventDriver));
		tmp.add(new Event("account", eventDriver));
		tmp.add(new Event("accoutr", eventDriver));
		tmp.add(new Event("accoutr", eventDriver));
		tmp.add(new Event("accoutr", eventDriver));
		tmp.add(new Event("accru", eventDriver));
		tmp.add(new Event("alumnus", eventDriver));
		tmp.add(new Event("catch", eventDriver));
		expectedEventSet.addEvents(tmp);

//		System.out.println(expectedEventSet.toString());
//		System.out.println(sampleEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}

}
