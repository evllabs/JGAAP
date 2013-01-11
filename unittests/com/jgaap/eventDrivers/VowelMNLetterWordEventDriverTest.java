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
import com.jgaap.generics.EventDriver;

/**
 * @author Patrick Juola
 *
 */
public class VowelMNLetterWordEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.V34LetterWordEventDriver#createEventSet(com.jgaap.generics.Document)}.
	 * @throws EventGenerationException 
	 */
	@Test
	public void testCreateEventSetDocumentSet() throws EventGenerationException {

		String text = (
"a b c d e f g h i j k l m n o p q r s t u v w x y z " +
"aa bb cc dd ee ff gg hh ii jj kk ll mm nn oo pp qq rr ss tt uu vv ww xx yy zz " +
"aaa bbb ccc ddd eee fff ggg hhh iii jjj kkk lll mmm nnn ooo ppp qqq rrr sss ttt uuu vvv www xxx yyy zzz " +
"aaaa bbbbb cccc dddd eeee ffff gggg hhhh iiii jjjj kkkk llll mmmm nnnn oooo pppp qqqq rrrr ssss tttt uuuu vvvv wwww xxxx yyyy zzzz " +
"aaaaa bbbbb ccccc ddddd eeeee fffff ggggg hhhhh iiiii jjjjj kkkkk lllll mmmmm nnnnn ooooo ppppp qqqqq rrrrr sssss ttttt uuuuu vvvvv wwwww xxxxx yyyyy zzzzz " +
"A B C D E F G H I J K L M N O P Q R S T U V W X Y Z " +
"AA BB CC DD EE FF GG HH II JJ KK LL MM NN OO PP QQ RR SS TT UU VV WW XX YY ZZ " +
"AAA BBB CCC DDD EEE FFF GGG HHH III JJJ KKK LLL MMM NNN OOO PPP QQQ RRR SSS TTT UUU VVV WWW XXX YYY ZZZ " +
"AAAA BBBBB CCCC DDDD EEEE FFFF GGGG HHHH IIII JJJJ KKKK LLLL MMMM NNNN OOOO PPPP QQQQ RRRR SSSS TTTT UUUU VVVV WWWW XXXX YYYY ZZZZ " +
"AAAAA BBBBB CCCCC DDDDD EEEEE FFFFF GGGGG HHHHH IIIII JJJJJ KKKKK LLLLL MMMMM NNNNN OOOOO PPPPP QQQQQ RRRRR SSSSS TTTTT UUUUU VVVVV WWWWW XXXXX YYYYY ZZZZZ" +
"1 22 333 4444 55555 " +
"! @@ ### $$$$ %%%%% "
		);


		EventDriver ed = new VowelMNLetterWordEventDriver();
		ed.setParameter("M","1");
		ed.setParameter("N","2");

		EventSet sampleEventSet = ed.createEventSet(text.toCharArray());
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();

		tmp.add(new Event("a", null));
		tmp.add(new Event("e", null));
		tmp.add(new Event("i", null));
		tmp.add(new Event("o", null));
		tmp.add(new Event("u", null));
		tmp.add(new Event("y", null));
		tmp.add(new Event("aa", null));
		tmp.add(new Event("ee", null));
		tmp.add(new Event("ii", null));
		tmp.add(new Event("oo", null));
		tmp.add(new Event("uu", null));
		tmp.add(new Event("yy", null));
		tmp.add(new Event("A", null));
		tmp.add(new Event("E", null));
		tmp.add(new Event("I", null));
		tmp.add(new Event("O", null));
		tmp.add(new Event("U", null));
		tmp.add(new Event("Y", null));
		tmp.add(new Event("AA", null));
		tmp.add(new Event("EE", null));
		tmp.add(new Event("II", null));
		tmp.add(new Event("OO", null));
		tmp.add(new Event("UU", null));
		tmp.add(new Event("YY", null));


		expectedEventSet.addEvents(tmp);
		//System.out.println(sampleEventSet.toString());
		//System.out.println(expectedEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}
}
