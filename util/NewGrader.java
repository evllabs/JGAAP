/**
 **/
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
public class NewGrader {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        Vector<String> docList = new Vector<String>();
        while(input.hasNextLine()){
            String curr = input.nextLine();
	    //System.out.println(i);
	    curr = curr.replace(" ", "\\ ");
	    curr = curr.replaceAll("\\(\\d\\d:\\d\\d:\\d\\d\\)","*");
	    curr=curr.replace("001.txt", "*.txt");
	    docList.add(curr);
	    //System.out.println(curr);
        }
        for(String iterator:docList){
	    if(iterator.contains("*.txt")){
		System.out.println("cat "+iterator + " | head -4");
		System.out.println("cat "+iterator + " | java GradeAAAC | tail -2");
	    }
	    }
    }
}
