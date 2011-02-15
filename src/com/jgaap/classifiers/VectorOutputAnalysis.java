package com.jgaap.classifiers;

import com.jgaap.generics.*;
import com.jgaap.jgaapConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Created by IntelliJ IDEA.
 * User: jnoecker
 * Date: Feb 2, 2011
 * Time: 11:53:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class VectorOutputAnalysis extends AnalysisDriver {
    	public String displayName() {
	    return "Vector Output";
	}

	public String tooltipText(){
	    return "Generates vectors from unknowns using a key (experimental)";
	}

	public boolean showInGUI(){
	    return true;
	}

        public List<Pair<String, Double>> analyze(EventSet unknown, List<EventSet> known) {

        EventHistogram hist = new EventHistogram();
        String keyFile = jgaapConstants.libDir() + "personae.key";
            Scanner keyIn = null;
            try {
                keyIn = new Scanner(new File(keyFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            FileOutputStream fsOut;
        PrintStream writer = null;
        try {
            String docPath = unknown.getDocumentName();
            String[] docPathArray = docPath.split("/");
            System.out.println(Arrays.toString(docPathArray));
            String unknownFileName = docPathArray[docPathArray.length - 1];
            fsOut = new FileOutputStream(jgaapConstants.tmpDir() + unknownFileName);
            writer = new PrintStream(fsOut);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(Event e : unknown) {
            hist.add(e);
        }

        while(keyIn.hasNextLine()) {
            String next = keyIn.nextLine();
            double freq = hist.getRelativeFrequency(new Event(next));
            if(freq > 0) {
                writer.println(freq);
            }
            else {
                writer.println("0");
            }
        }

        writer.close();

        List<Pair<String,Double>> results = new ArrayList<Pair<String,Double>>();
        results.add(new Pair<String, Double>("No analysis performed.\n", 0.0));
        return results;
    }
}
