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


		EventSet sampleEventSet = new PorterStemmerWithIrregularEventDriver().createEventSet(text.toCharArray());
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();

		tmp.add(new Event("test", null));
		tmp.add(new Event("Test", null));
		tmp.add(new Event("TEST", null));
		tmp.add(new Event("TeSt", null));

		tmp.add(new Event("867-5309", null));

		tmp.add(new Event("a", null));
		tmp.add(new Event("aaron", null));
		tmp.add(new Event("abaissiez", null));
		tmp.add(new Event("abandon", null));
		tmp.add(new Event("abandon", null));
		tmp.add(new Event("abas", null));
		tmp.add(new Event("abash", null));
		tmp.add(new Event("abat", null));
		tmp.add(new Event("abat", null));
		tmp.add(new Event("abat", null));
		tmp.add(new Event("abat", null));
		tmp.add(new Event("abat", null));
		tmp.add(new Event("abbess", null));
		tmp.add(new Event("abbei", null));
		tmp.add(new Event("abbei", null));
		tmp.add(new Event("abbomin", null));
		tmp.add(new Event("abbot", null));
		tmp.add(new Event("abbot", null));
		tmp.add(new Event("abbrevi", null));
		tmp.add(new Event("ab", null));
		tmp.add(new Event("abel", null));
		tmp.add(new Event("aberga", null));
		tmp.add(new Event("abergavenni", null));
		tmp.add(new Event("abet", null));
		tmp.add(new Event("abet", null));
		tmp.add(new Event("abhomin", null));
		tmp.add(new Event("abhor", null));
		tmp.add(new Event("abhorr", null));
		tmp.add(new Event("abhor", null));
		tmp.add(new Event("abhor", null));
		tmp.add(new Event("abhor", null));
		tmp.add(new Event("abhorson", null));
		tmp.add(new Event("abid", null));
		tmp.add(new Event("abid", null));
		tmp.add(new Event("abil", null));
		tmp.add(new Event("abil", null));
		tmp.add(new Event("abject", null));
		tmp.add(new Event("abjectli", null));
		tmp.add(new Event("abject", null));
		tmp.add(new Event("abjur", null));
		tmp.add(new Event("abjur", null));
		tmp.add(new Event("abl", null));
		tmp.add(new Event("abler", null));
		tmp.add(new Event("aboard", null));
		tmp.add(new Event("abod", null));
		tmp.add(new Event("abod", null));
		tmp.add(new Event("abod", null));
		tmp.add(new Event("abod", null));
		tmp.add(new Event("abomin", null));
		tmp.add(new Event("abomin", null));
		tmp.add(new Event("abomin", null));
		tmp.add(new Event("abort", null));
		tmp.add(new Event("abort", null));
		tmp.add(new Event("abound", null));
		tmp.add(new Event("abound", null));
		tmp.add(new Event("about", null));
		tmp.add(new Event("abov", null));
		tmp.add(new Event("abr", null));
		tmp.add(new Event("abraham", null));
		tmp.add(new Event("abram", null));
		tmp.add(new Event("abreast", null));
		tmp.add(new Event("abridg", null));
		tmp.add(new Event("abridg", null));
		tmp.add(new Event("abridg", null));
		tmp.add(new Event("abridg", null));
		tmp.add(new Event("abroach", null));
		tmp.add(new Event("abroad", null));
		tmp.add(new Event("abrog", null));
		tmp.add(new Event("abrook", null));
		tmp.add(new Event("abrupt", null));
		tmp.add(new Event("abrupt", null));
		tmp.add(new Event("abruptli", null));
		tmp.add(new Event("absenc", null));
		tmp.add(new Event("absent", null));
		tmp.add(new Event("absei", null));
		tmp.add(new Event("absolut", null));
		tmp.add(new Event("absolut", null));
		tmp.add(new Event("absolv", null));
		tmp.add(new Event("absolv", null));
		tmp.add(new Event("abstain", null));
		tmp.add(new Event("abstemi", null));
		tmp.add(new Event("abstin", null));
		tmp.add(new Event("abstract", null));
		tmp.add(new Event("absurd", null));
		tmp.add(new Event("absyrtu", null));
		tmp.add(new Event("abund", null));
		tmp.add(new Event("abund", null));
		tmp.add(new Event("abundantli", null));
		tmp.add(new Event("abu", null));
		tmp.add(new Event("abus", null));
		tmp.add(new Event("abus", null));
		tmp.add(new Event("abus", null));
		tmp.add(new Event("abus", null));
		tmp.add(new Event("abus", null));
		tmp.add(new Event("abut", null));
		tmp.add(new Event("abi", null));
		tmp.add(new Event("abysm", null));
		tmp.add(new Event("ac", null));
		tmp.add(new Event("academ", null));
		tmp.add(new Event("academ", null));
		tmp.add(new Event("accent", null));
		tmp.add(new Event("accent", null));
		tmp.add(new Event("accept", null));
		tmp.add(new Event("accept", null));
		tmp.add(new Event("accept", null));
		tmp.add(new Event("accept", null));
		tmp.add(new Event("accept", null));
		tmp.add(new Event("access", null));
		tmp.add(new Event("accessari", null));
		tmp.add(new Event("access", null));
		tmp.add(new Event("accid", null));
		tmp.add(new Event("accid", null));
		tmp.add(new Event("accident", null));
		tmp.add(new Event("accident", null));
		tmp.add(new Event("accid", null));
		tmp.add(new Event("accit", null));
		tmp.add(new Event("accit", null));
		tmp.add(new Event("accit", null));
		tmp.add(new Event("acclam", null));
		tmp.add(new Event("accommod", null));
		tmp.add(new Event("accommod", null));
		tmp.add(new Event("accommod", null));
		tmp.add(new Event("accommod", null));
		tmp.add(new Event("accommodo", null));
		tmp.add(new Event("accompani", null));
		tmp.add(new Event("accompani", null));
		tmp.add(new Event("accompani", null));
		tmp.add(new Event("accomplic", null));
		tmp.add(new Event("accomplish", null));
		tmp.add(new Event("accomplish", null));
		tmp.add(new Event("accomplish", null));
		tmp.add(new Event("accomplish", null));
		tmp.add(new Event("accompt", null));
		tmp.add(new Event("accord", null));
		tmp.add(new Event("accord", null));
		tmp.add(new Event("accord", null));
		tmp.add(new Event("accordeth", null));
		tmp.add(new Event("accord", null));
		tmp.add(new Event("accordingli", null));
		tmp.add(new Event("accord", null));
		tmp.add(new Event("accost", null));
		tmp.add(new Event("accost", null));
		tmp.add(new Event("account", null));
		tmp.add(new Event("account", null));
		tmp.add(new Event("account", null));
		tmp.add(new Event("account", null));
		tmp.add(new Event("accoutr", null));
		tmp.add(new Event("accoutr", null));
		tmp.add(new Event("accoutr", null));
		tmp.add(new Event("accru", null));
		tmp.add(new Event("alumnus", null));
		tmp.add(new Event("catch", null));
		expectedEventSet.addEvents(tmp);

		//System.out.println(expectedEventSet.toString());
		//System.out.println(sampleEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
		
	}

}
