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
 * @author Patrick Juola
 *
 */
public class V34LetterWordEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.VMNLetterWordEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 * @throws EventGenerationException 
	 */
	@Test
	public void testCreateEventSetDocumentSet() throws EventGenerationException {

		String text = (
"a b c d e f g h i j k l m n o p q r s t u v w x y z " +
"aa bb cc dd ee ff gg hh ii jj kk ll mm nn oo pp qq rr ss tt uu vv ww xx yy zz " +
"aaa bbb ccc ddd eee fff ggg hhh iii jjj kkk lll mmm nnn ooo ppp qqq rrr sss ttt uuu vvv www xxx yyy zzz " +
"aaaa bbbbb cccc dddd eeee ffff gggg hhhh iiii jjjj kkkk llll mmmm nnnn oooo pppp qqqq rrrr ssss tttt uuuu vvvv wwww xxxx yyyy zzzz " +
"aaaaa bbbbb ccccc ddddd eeeee fffff ggggg hhhhh iiiii jjjjj kkkkk lllll mmmmm nnnnn ooooo ppppp qqqqq rrrrr sssss ttttt uuuuu vvvvv wwwww xxxxx yyyyy zzzzz" +
"A B C D E F G H I J K L M N O P Q R S T U V W X Y Z " +
"AA BB CC DD EE FF GG HH II JJ KK LL MM NN OO PP QQ RR SS TT UU VV WW XX YY ZZ " +
"AAA BBB CCC DDD EEE FFF GGG HHH III JJJ KKK LLL MMM NNN OOO PPP QQQ RRR SSS TTT UUU VVV WWW XXX YYY ZZZ " +
"AAAA BBBBB CCCC DDDD EEEE FFFF GGGG HHHH IIII JJJJ KKKK LLLL MMMM NNNN OOOO PPPP QQQQ RRRR SSSS TTTT UUUU VVVV WWWW XXXX YYYY ZZZZ " +
"AAAAA BBBBB CCCCC DDDDD EEEEE FFFFF GGGGG HHHHH IIIII JJJJJ KKKKK LLLLL MMMMM NNNNN OOOOO PPPPP QQQQQ RRRRR SSSSS TTTTT UUUUU VVVVV WWWWW XXXXX YYYYY ZZZZZ" +
"1 22 333 4444 55555 " +
"! @@ ### $$$$ %%%%% "
		);


		EventDriver eventDriver = new VowelMNLetterWordEventDriver();
		eventDriver.setParameter("M", 3);
		eventDriver.setParameter("N", 4);

		EventSet sampleEventSet = eventDriver.createEventSet(text.toCharArray());
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();

		tmp.add(new Event("aaa", eventDriver));
		tmp.add(new Event("eee", eventDriver));
		tmp.add(new Event("iii", eventDriver));
		tmp.add(new Event("ooo", eventDriver));
		tmp.add(new Event("uuu", eventDriver));
		tmp.add(new Event("yyy", eventDriver));
		tmp.add(new Event("aaaa", eventDriver));
		tmp.add(new Event("eeee", eventDriver));
		tmp.add(new Event("iiii", eventDriver));
		tmp.add(new Event("oooo", eventDriver));
		tmp.add(new Event("uuuu", eventDriver));
		tmp.add(new Event("yyyy", eventDriver));
		tmp.add(new Event("AAA", eventDriver));
		tmp.add(new Event("EEE", eventDriver));
		tmp.add(new Event("III", eventDriver));
		tmp.add(new Event("OOO", eventDriver));
		tmp.add(new Event("UUU", eventDriver));
		tmp.add(new Event("YYY", eventDriver));
		tmp.add(new Event("AAAA", eventDriver));
		tmp.add(new Event("EEEE", eventDriver));
		tmp.add(new Event("IIII", eventDriver));
		tmp.add(new Event("OOOO", eventDriver));
		tmp.add(new Event("UUUU", eventDriver));
		tmp.add(new Event("YYYY", eventDriver));


		expectedEventSet.addEvents(tmp);
		System.out.println(expectedEventSet);
		System.out.println(sampleEventSet);
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}
}
