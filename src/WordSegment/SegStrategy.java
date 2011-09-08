// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
package WordSegment;

//import java.io.*;
import java.util.*;

public abstract class SegStrategy {
	protected Dictionary dic = null;

	public void setDic(Dictionary d) {
		dic = d;
	}

	public Vector<String> segment(String sentence) {
		return segment(sentence, dic);
	}

	abstract public Vector<String> segment(String sentence, Dictionary d);
}
