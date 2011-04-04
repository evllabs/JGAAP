package WordSegment;

//import java.io.*;
import java.util.*;

public abstract class SegStrategy {
	protected Dictionary dic = null;

	public void SetDic(Dictionary d) {
		dic = d;
	}

	public Vector<String> Segment(String sentence) {
		return Segment(sentence, dic);
	}

	abstract public Vector<String> Segment(String sentence, Dictionary d);
}
