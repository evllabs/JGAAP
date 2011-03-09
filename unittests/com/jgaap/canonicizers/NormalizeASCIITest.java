/**
 * 
 */
package com.jgaap.canonicizers;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

/**
 * @author juola
 * @date 9 Mar 2011
 * 
 */
public class NormalizeASCIITest {

	/**
	 * Test method for
	 * {@link com.jgaap.canonicizers.NormalizeASCII#process(java.util.Vector)}
	 * .
	 */
	@Test
	public void testProcess() {
		char[] sample = new char[256];
		for (int i=0;i<255;i++)
			sample[i] = (char) i;
	

		char[] expected = {
'\t', '\n', 0x0B, '\f', '\r', 
' ',
'!', '\"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-','.','/',
'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
':', ';', '<', '=', '>', '?', '@', 
'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
'[', '\\', ']', '^', '_', '`',
'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
'{',
'|',
'}',
'~'
		};

		char[] test = new NormalizeASCII().process(sample);

/*
		for (int i = 0;i<100;i++) {
			System.out.print(expected[i]);
			System.out.print(" ");
			System.out.print((int) expected[i]);
			System.out.print("\t");
			System.out.print(test[i]);
			System.out.print(" ");
			System.out.print((int) test[i]);
			System.out.println("");
		}
*/

		assertTrue(Arrays.equals(expected, test));
	}

}
