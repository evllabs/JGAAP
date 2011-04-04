/**
 **/
package com.jgaap.distances;

import java.util.HashSet;

import com.jgaap.generics.DivergenceFunction;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventSet;

/**
 * LZWDistance, : conditional LZW distance, basically LZW(ab)-LZW(b). This is YA
 * distance for Nearest Neighbor algorithms. based
 * 
 * @author Juola
 * @version 1.0
 */

public class LZWDivergence extends DivergenceFunction {
	public String displayName(){
	    return "LZW Divergence"+getDivergenceType();
	}
	
	public String tooltipText(){
	    return "Lempel-Ziv-Welch Nearest Neighbor Classifier";
	}

	public boolean showInGUI(){
	    return true;
	}
    /**
     * A boolean flag to print debugging info. 
     */
    private static final boolean debugging = false;

    /**
     * Returns conditional LZW distance between event sets es1 and es2
     * 
     * @param es1
     *            The first EventSet
     * @param es2
     *            The second EventSet
     * @return the conditional LZW distance between them
     */

    @Override
    public double divergence(EventSet es1, EventSet es2) {
        HashSet<String> theDict = new HashSet<String>();
        Event currentEvent;
        String currentString = "";

        int asize, absize;

        for (int i = 0; i < es1.size(); i++) {

            currentEvent = es1.eventAt(i);
            currentString += currentEvent.toString();

            if (!theDict.contains(currentString)) {
                theDict.add(currentString);
                if (debugging) {
                    System.out.print(currentString + ", ");
                }
                currentString = currentEvent.toString();
            }
        }

        if (debugging) {
            System.out.print("(" + currentString + ")");
            System.out.println();
        }

        asize = theDict.size();
        if (!currentString.equals("")) {
            asize++; // there's leftover stuff in A
        }

        for (int i = 0; i < es2.size(); i++) {

            currentEvent = es2.eventAt(i);
            currentString += currentEvent.toString();

            if (!theDict.contains(currentString)) {
                theDict.add(currentString);
                if (debugging) {
                    System.out.print(currentString + ", ");
                }
                currentString = currentEvent.toString();
            }
        }

        if (debugging) {
            System.out.print("(" + currentString + ")");
            System.out.println();
        }

        absize = theDict.size();
        if (!currentString.equals("")) {
            absize++; // leftover stuff in B
        }

        if (debugging) {
            System.out.println("ABsize is " + absize + "; Asize is " + asize);
            System.out.println("Distance is " + (absize - asize));
        }

        return 1.0 * (absize - asize);
    }
}
