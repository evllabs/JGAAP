// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
package WordSegment;
import java.io.*;
import java.util.*;
//import java.nio.ByteBuffer;
//import java.nio.channels.FileChannel;

public class DicTrainer
{
	private Dictionary dic = new Dictionary();

	public Dictionary getDic()
	{
		return dic;
	}
	
	public void Train(String fileName)
	{
		File aFile = new File(fileName);
		FileInputStream inFile = null;
		try
		{
			inFile = new FileInputStream(aFile);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}

		try
		{
			BufferedReader inStream = new BufferedReader(new InputStreamReader(inFile));
			String line;
			while ((line = inStream.readLine()) != null)	
			{
				StringTokenizer st = new StringTokenizer(line);
				while(st.hasMoreTokens())
					dic.addWord(st.nextToken());
			}
			inFile.close();
		}
		catch (IOException e)
		{
			e.printStackTrace(System.err);
			System.exit(0);
		}
	}

	public void SaveDic(String fileName)
	{
		ObjectOutputStream objout;
		try
		{
			objout = new ObjectOutputStream(new FileOutputStream(new File(fileName)));
			objout.writeObject(dic);
			objout.close();
		}
		catch (IOException e)
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}
	public static void main(String[] args) 
	{
		DicTrainer trainer = new DicTrainer();
		trainer.Train("test.txt");
		trainer.SaveDic("dic.dat");		
	}
}
