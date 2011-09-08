// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
package WordSegment;

import java.io.*;
import java.util.*;

//import java.nio.ByteBuffer;
//import java.nio.channels.FileChannel;
public class WordSegment {
	private Dictionary dic;

	private SegStrategy segmentStrategy;

	public WordSegment() {
	}

	public WordSegment(SegStrategy strategy) {
		setDic(getClass().getResourceAsStream("/WordSegment/recources/chinese_dictionary.dat"));
		setStrategy(strategy);
	}

	public Vector<String> segment(String sentence) {
		return segmentStrategy.segment(sentence, dic);
	}

	/*
	 * public static void main(String[] args) { FMM aStrategy = new FMM();
	 * //wordSeger.Initialize("dic.dat"); WordSegment wordSeger= new
	 * WordSegment("dic.dat", aStrategy); Vector words =
	 * wordSeger.Segment("他不喜欢吃苹果。我也不喜欢鲜花！"); for (int i = 0; i
	 * < words.size(); i++) System.out.println((String)words.get(i)); }
	 */

	public void setDic(Dictionary d) {
		dic = d;
	}

	public void setDic(InputStream dicFile) {
		ObjectInputStream objectIn = null;
		try {
			objectIn = new ObjectInputStream(dicFile);
		} catch (IOException e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}

		try {
			dic = (Dictionary) (objectIn.readObject());
		} catch (ClassNotFoundException e) {
			e.printStackTrace(System.err);
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}

	public void setStrategy(SegStrategy aStrategy) {
		segmentStrategy = aStrategy;
	}
}
