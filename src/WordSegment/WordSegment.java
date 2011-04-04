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

	public WordSegment(String dicFile, SegStrategy strategy) {
		SetDic(dicFile);
		setStrategy(strategy);
	}

	public Vector<String> Segment(String sentence) {
		return segmentStrategy.Segment(sentence, dic);
	}

	/*
	 * public static void main(String[] args) { FMM aStrategy = new FMM();
	 * //wordSeger.Initialize("dic.dat"); WordSegment wordSeger= new
	 * WordSegment("dic.dat", aStrategy); Vector words =
	 * wordSeger.Segment("他不喜欢吃苹果。我也不喜欢鲜花！"); for (int i = 0; i
	 * < words.size(); i++) System.out.println((String)words.get(i)); }
	 */

	public void SetDic(Dictionary d) {
		dic = d;
	}

	public void SetDic(String dicFile) {
		ObjectInputStream objectIn = null;
		try {
			objectIn = new ObjectInputStream(new FileInputStream(new File(
					dicFile)));
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
