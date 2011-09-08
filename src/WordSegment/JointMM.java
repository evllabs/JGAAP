// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
package WordSegment;

import java.util.Vector;

/*We split the orginal string into blocks by punctuations, and then do Forward Maximize Matching and backward maximize matching on each block.
 We compare the numbers of words we get from both methods for the same block. If they  are different, we take the result that has fewer words,
 otherwise we take the result from Backward Maximize Matching as our result.*/

public class JointMM extends SegStrategy {

	public Vector<String> segment(String sentence, Dictionary dic) {

		BMM b = new BMM();
		FMM f = new FMM();

		String[] substring = sentence
				.split("£¡|¡°|¡±|¡®|¡¯|£¬|¡£|£º|£»|£¿|¡¢|¡­¡­");
		// String[] substring= sentence.split("\\p{Punct}"); // ("\\p{Punct}")
		// for English text file

		Vector<String> seged;
		Vector<String> Jointsegmented = new Vector<String>();

		for (int i = 0; i < substring.length; i++) {
			String s = substring[i]; // Let s be each small block

			Vector<String> Bsegmented = b.segment(s, dic); // Bsegmented is the
															// segmented vector
															// using BMM
			Vector<String> Fsegmented = f.segment(s, dic); // Fsegmented is the
															// segmented vector
															// using FMM

			int Bsize = Bsegmented.size(); // size of Bsegmented, i.e number of
											// words in Bsegmented
			int Fsize = Fsegmented.size();

			if (Fsize < Bsize) // if Fsegmented has fewer words, let
								// seged=Fsegmented, otherwise, let
								// seged=Bsegmented;

				seged = Fsegmented;

			else
				seged = Bsegmented;

			for (int j = 0; j < seged.size(); j++) {

				Jointsegmented.add(seged.elementAt(j)); // adding words of each
														// small block together,
														// Jointsegmented will
														// be the whole thing
														// after segmenting the
														// orginal sentence.
			}

		}
		return Jointsegmented;

	}

}
