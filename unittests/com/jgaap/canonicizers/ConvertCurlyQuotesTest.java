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

package com.jgaap.canonicizers;

import static org.junit.Assert.*;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Test;

public class ConvertCurlyQuotesTest {

    /**
	 * Test method for
	 * {@link com.jgaap.canonicizers.ConvertCurlyQuotes#process(char[])}
	 * .
	 */
     
     @Test
     public void testProcess(){
        String example = "Here\u2019s a sentence with curly single quotes: \u2018Hello!\u2019 and straight single quotes: 'World'. " +
                         "Also, curly double quotes: \u201CThis is a test.\u201D and straight double quotes: \"Another test.\" " +
                         "Let\u2019s see how they appear when printed!";
        char[] sample = example.toCharArray();
        String expected_text = "Here's a sentence with curly single quotes: 'Hello!' and straight single quotes: 'World'. " +
                          "Also, curly double quotes: \"This is a test.\" and straight double quotes: \"Another test.\" " + 
                          "Let's see how they appear when printed!";
        char[] expected = expected_text.toCharArray();
        ConvertCurlyQuotes canon = new ConvertCurlyQuotes();
        
        String[] encodings_that_support_curly_quotes = {
            "GB2312",
            "UTF-8",
            "UTF-16"
        };

        for (String encoding : encodings_that_support_curly_quotes){
            try{
                // Convert the string to a byte array using the specified encoding
				byte[] encodedBytes = example.getBytes(Charset.forName(encoding));

				// Decode the byte array back into a string using the same encoding
				String decodedText = new String(encodedBytes, Charset.forName(encoding));

				// Convert the decoded text into a char array
				char[] procText = decodedText.toCharArray();
                char[] result = canon.process(procText);
                assertTrue(Arrays.equals(expected, result));

            }catch(Exception e){
                System.out.println("Encoding conversion did not work for " + encoding);
            }
        }
        
     }
    

     
}
