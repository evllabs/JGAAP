// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
package WordSegment;
import java.io.*;
import java.util.*;

public class Dictionary implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, Integer> dic = new HashMap<String, Integer>();
	private int maxWordLength = 0;
	private long wordsNumOfTrainDoc = 0;
	
	/**
	 * @param newWord Add the word to dictionary
	 */
	public void addWord(String newWord)		
	{
		if (newWord.length() > maxWordLength)
			maxWordLength = newWord.length();
		if (checkWord(newWord))
		{
			int t = dic.get(newWord);
			dic.put(newWord, new Integer(t + 1));
		}
		else
			dic.put(newWord, new Integer(1));
		wordsNumOfTrainDoc++;
	}
	
	public int getFrequency(String word)
	{
		if(checkWord(word))
			return dic.get(word);
		else
			return 0;
	}

	public boolean checkWord(String word)	//Check if the word is in the dictionary
	{
		if (dic.get(word) == null)
			return false;
		else
			return true;
	}

	public int getMaxLength() { return maxWordLength; }
	public String toString()
	{
		Iterator<String> keyIter = dic.keySet().iterator();
		String value = new String();
		while (keyIter.hasNext())
		{
			String key = keyIter.next();
			value += key + " " + getFrequency(key) + "\n";
		}
		return value;
	}

	
	public long getWordsNumOfTrainDoc() {
		return wordsNumOfTrainDoc;
	}
	
}
