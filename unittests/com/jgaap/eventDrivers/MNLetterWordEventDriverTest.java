// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
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
import com.jgaap.generics.EventDriver;

/**
 * @author Patrick Juola
 *
 */
public class MNLetterWordEventDriverTest {

	/**
	 * Test method for {@link com.jgaap.eventDrivers.V34LetterWordEventDriver#createEventSet(com.jgaap.generics.DocumentSet)}.
	 */
	@Test
	public void testCreateEventSetDocumentSet() {

		Document doc = new Document();
		doc.readStringText(
"a b c d e f g h i j k l m n o p q r s t u v w x y z " +
"aa bb cc dd ee ff gg hh ii jj kk ll mm nn oo pp qq rr ss tt uu vv ww xx yy zz " +
"aaa bbb ccc "+
"aaaa bbbb cccc "+
"aaaaa bbbbb ccccc ddddd eeeee fffff ggggg hhhhh iiiii jjjjj kkkkk lllll mmmmm nnnnn ooooo ppppp qqqqq rrrrr sssss ttttt uuuuu vvvvv wwwww xxxxx yyyyy zzzzz " +
"A B C D E F G H I J K L M N O P Q R S T U V W X Y Z " +
"AA BB CC DD EE FF GG HH II JJ KK LL MM NN OO PP QQ RR SS TT UU VV WW XX YY ZZ " +
"AAA BBB CCC " +
"AAAA BBBB CCCC " +
"AAAAA BBBBB CCCCC DDDDD EEEEE FFFFF GGGGG HHHHH IIIII JJJJJ KKKKK LLLLL MMMMM NNNNN OOOOO PPPPP QQQQQ RRRRR SSSSS TTTTT UUUUU VVVVV WWWWW XXXXX YYYYY ZZZZZ" +
"1 22 333 4444 55555 " +
"! @@ ### $$$$ %%%%% "
		);


		EventDriver ed = new MNLetterWordEventDriver();
		ed.setParameter("M","3");
		ed.setParameter("N","4");

		EventSet sampleEventSet = ed.createEventSet(doc);
		EventSet expectedEventSet = new EventSet();
		Vector<Event> tmp = new Vector<Event>();

		tmp.add(new Event("aaa"));
		tmp.add(new Event("bbb"));
		tmp.add(new Event("ccc"));
		tmp.add(new Event("aaaa"));
		tmp.add(new Event("bbbb"));
		tmp.add(new Event("cccc"));
		tmp.add(new Event("AAA"));
		tmp.add(new Event("BBB"));
		tmp.add(new Event("CCC"));
		tmp.add(new Event("AAAA"));
		tmp.add(new Event("BBBB"));
		tmp.add(new Event("CCCC"));
		tmp.add(new Event("333"));
		tmp.add(new Event("4444"));
		tmp.add(new Event("###"));
		tmp.add(new Event("$$$$"));


		expectedEventSet.addEvents(tmp);
		// System.out.println(sampleEventSet.toString());
		// System.out.println(expectedEventSet.toString());
		assertTrue(expectedEventSet.equals(sampleEventSet));
	}
}
