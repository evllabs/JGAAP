// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 * 
 */
package WordSegment;

import java.util.Vector;

/**
 * @author Truman
 * 
 */
public class BMM extends SegStrategy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see WordSegment.SegStrategy#Segment(java.lang.String,
	 * WordSegment.Dictionary)
	 */
	// @Override
	public Vector<String> Segment(String sentence, Dictionary dic) {
		int maxLength = dic.getMaxLength(); // the length of the longest word in
											// the dictionary
		int negPos = sentence.length();
		int targetLength = maxLength;
		int restLength = sentence.length();
		Vector<String> seged = new Vector<String>();

		while (restLength > 0) {
			if (targetLength > restLength)
				targetLength = restLength;
			String tempStr = sentence.substring(negPos - targetLength, negPos);
			if (dic.checkWord(tempStr) || targetLength == 1) {
				seged.add(0, tempStr);
				negPos -= targetLength;
				restLength -= targetLength;
				targetLength = maxLength;
			} else
				targetLength--;
		}
		return seged;
	}
}
