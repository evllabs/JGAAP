// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
import com.jgaap.backend.CSVIO;

import java.util.Scanner;
import java.util.Vector;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Michael
 */
public class BuildSpreedsheet {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        Vector<String> canon = new Vector<String>();
        Vector<String> event = new Vector<String>();
        Vector<String> analysis = new Vector<String>();
        Vector<String> score = new Vector<String>();
        Vector<String> aaac = new Vector<String>();
        while(input.hasNextLine()){
            String currentLine=input.nextLine();
            if(!currentLine.equals("")){
                String[] line = currentLine.split(" ");
                String tmp = new String();
                canon.add(line[1]);
                line = input.nextLine().split(" ");
                for (int i = 2; i<line.length;i++){
                    tmp = tmp + line[i]+" ";
                }
                event.add(tmp);
                tmp = new String();
                line = input.nextLine().split(" ");
                for (int i = 2; i<line.length;i++){
                    tmp = tmp + line[i]+" ";
                }
                analysis.add(tmp.trim());
                line = input.nextLine().split(" ");
		//                System.out.println(line[4]);
		score.add(line[4]);
                line=input.nextLine().split(" ");
		//System.out.println(line[3]);
                aaac.add(line[3]);
            }
        }
        Vector<String> init = new Vector<String>();
        init.add("_");
        init.add("_");
        init.add("_");
        init.add("_");
        init.add("_");
        init.add("_");
        Vector<Vector<String>> table = new Vector<Vector<String>>();
        table.add(new Vector<String>());
        table.elementAt(0).add("");
        table.elementAt(0).add("cross entropy distance");
        table.elementAt(0).add("lzw distance");
        table.elementAt(0).add("Kullback-Leibler Distance");
        table.elementAt(0).add("cross entropy distance (Commutativity Enforced)");
        table.elementAt(0).add("lzw distance (Commutativity Enforced)");
        table.elementAt(0).add("Kullback-Leibler Distance (Commutativity Enforced)");
        table.add(new Vector<String>());
        table.elementAt(1).add("characters");
	table.elementAt(1).addAll(init);
        table.add(new Vector<String>());
        table.elementAt(2).add("character bigrams");
        table.elementAt(2).addAll(init);
        table.add(new Vector<String>());
        table.elementAt(3).add("character trigrams");
        table.elementAt(3).addAll(init);
        table.add(new Vector<String>());
        table.elementAt(4).add("character tetragrams");
        table.elementAt(4).addAll(init);
        table.add(new Vector<String>());
        table.elementAt(5).add("words");
        table.elementAt(5).addAll(init);
        table.add(new Vector<String>());
        table.elementAt(6).add("word bigrams");
        table.elementAt(6).addAll(init);
        table.add(new Vector<String>());
        table.elementAt(7).add("word trigrams");
        table.elementAt(7).addAll(init);
        table.add(new Vector<String>());
        table.elementAt(8).add("word tetragrams");
        table.elementAt(8).addAll(init);
	table.add(new Vector<String>());
        table.elementAt(9).add("most common characters");
	table.elementAt(9).addAll(init);
        table.add(new Vector<String>());
        table.elementAt(10).add("most common character bigrams");
        table.elementAt(10).addAll(init);
        table.add(new Vector<String>());
        table.elementAt(11).add("most common character trigrams");
        table.elementAt(11).addAll(init);
        table.add(new Vector<String>());
        table.elementAt(12).add("most common character tetragrams");
        table.elementAt(12).addAll(init);
        table.add(new Vector<String>());
        table.elementAt(13).add("most common words");
        table.elementAt(13).addAll(init);
        table.add(new Vector<String>());
        table.elementAt(14).add("most common word bigrams");
        table.elementAt(14).addAll(init);
        table.add(new Vector<String>());
        table.elementAt(15).add("most common word trigrams");
        table.elementAt(15).addAll(init);
        table.add(new Vector<String>());
        table.elementAt(16).add("most common word tetragrams");
        table.elementAt(16).addAll(init);
        for(int i=0;i<event.size();i++){
            int j =0;
            for(String curr : table.elementAt(0)){
                if (curr.equalsIgnoreCase(analysis.elementAt(i))){
                    int k = 0;
                    for(Vector<String>cur:table){
                        if(cur.elementAt(0).trim().equalsIgnoreCase(event.elementAt(i).trim())){
			    //          System.out.println("hi");
			    cur.add(j, (aaac.elementAt(i)+" "+score.elementAt(i)));
			    break;
			}
                        else 
                            k++;
                    }
		    break;
                }
                else
                    j++;
            }        
        }
	for(Vector<String> X : table){
	    for(int m =0;m< X.size();m++){
		if(X.elementAt(m).equals("_"))
		    X.remove(m);
	    }
	}
        CSVIO.writeCSV(table, "finalResults.csv");
    }
}
