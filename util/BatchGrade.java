/**
 **/
import java.util.Scanner;
import java.io.*;

public class BatchGrade {
    public static void main(String[] args) throws IOException {
	Scanner input = new Scanner(System.in);
	String line = "";
	while(input.hasNextLine()) {
	    for(int i = 0; i < 13; i++) {
		if(!input.hasNextLine()) 
		    System.exit(0);
		else line = input.nextLine();
	    }

	    int indexStart = line.indexOf(')');
	    int indexStop = line.indexOf(".txt");
	    String searchString = line.substring((indexStart+1), (indexStop-2)).replace(" ", "*");
	    
	    System.out.println("cat ../tmp/Characters/LDA/*" + searchString + "*.txt | head -4");
	    System.out.println("cat ../tmp/Characters/LDA/*" + searchString + "*.txt | java GradeAAAC | tail -2");

	    /*	    Runtime runtime = Runtime.getRuntime();
	    Process proc = runtime.exec("ls /Users/jnoecker/jgaap/jgaapsvn/jgaapTrunk/tmp/*" + searchString + "*.txt");

	    InputStream inputstream = proc.getInputStream();
	    InputStreamReader inputstreamreader = new InputStreamReader(inputstream);
	    BufferedReader bufferedreader = new BufferedReader(inputstreamreader);

	    String output;
	    while((output = bufferedreader.readLine()) != null) {
		System.out.println(output);
	    }

	    try { 
		if(proc.waitFor() != 0) {
		    System.err.println("Err: " + proc.exitValue());
		}
	    }
	    catch(InterruptedException e) {
		System.err.println(e);
		} */
	}
    }
}
