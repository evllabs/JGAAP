/**
 *   JGAAP -- Java Graphical Authorship Attribution Program
 *   Copyright (C) 2009 Patrick Juola
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation under version 3 of the License.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 **/
package com.jgaap.generics;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * A Document Set is a group of documents written by the same author. This can
 * be used to facilitate event sets that are larger than just a single document,
 * which may be more indicative of an author's entire body of work.
 * 
 * @author Astey
 * @since 1.0
 */
public class DocumentSet {

	public String displayName(){
	    return "DocumentSet";
	}

	public String tooltipText(){
	    return " ";
	}

	public boolean showInGUI(){
	    return false;
	}

    public Hashtable<Object, Integer> frequency;
    Vector<Document>                  documents;

    public DocumentSet() {
        documents = new Vector<Document>();
    }

    public DocumentSet(Document d) {
        documents = new Vector<Document>();
        documents.add(d);
    }

    /**
     * Calculates the frequency of individual characters within the entire set
     * of documents. Each character is a key in a hashtable with the value being
     * the frequency of occurrance. This is legacy code rewritten and was
     * included for completeness.
     **/
    public void characterFrequency() {
        frequency = new Hashtable<Object, Integer>();
        for (int i = 0; i < documents.size(); i++) {
            Vector<Character> pt = documents.elementAt(i).getProcessedText();
            for (int j = 0; j < documents.elementAt(i).getSize(); j++) {
                char letter = pt.elementAt(j);
                if (frequency.containsKey(letter)) {
                    Integer t = frequency.get(letter);
                    int ti = t.intValue() + 1;
                    frequency.put(letter, new Integer(ti));
                } else {
                    frequency.put(letter, new Integer(1));
                }
            }
        }
    }

    /** Number of documents currently registered in this set of documents **/
    public int documentCount() {
        return documents.size();
    }

    /**
     * Returns an individual indexed documement. The index is given by the order
     * in which the documents were registered with the DocumentSet
     **/
    public Document getDocument(int index) {
        return documents.elementAt(index);
    }

    /**
     * Returns the top most common words in the document with the rest replaced
     * with a placeholder. This is also legacy code, rewritten, generalized, and
     * replaced from the old code. Side Note: This code should probably be moved
     * to the EventSet class, along with the frequency analysis classes. This
     * will allow character and word frequencies to be generalized to event
     * frequencies, by returning the N most common events, replacing the rest
     * with a generic event.
     **/
    public void mostCommon(int n) {
        new Vector<Object>();
        new Vector<Object>();
        Vector<kvp> kvps = new Vector<kvp>();
        Enumeration<Object> ekeys = frequency.keys();
        while (ekeys.hasMoreElements()) {
            Object temp = ekeys.nextElement();
            kvps.add(new kvp(temp, frequency.get(temp)));
        }

        Collections.<kvp> sort(kvps);
        Collections.reverse(kvps);

        for (int i = 0; i < n; i++) {
            System.out.println(kvps.elementAt(i));
        }

    }

    /**
     * Registers a new document to the list of documents by a given author. The
     * document is appended on to the end of the list.
     **/
    public void register(Document d) {
        documents.add(d);
    }

    /**
     * Calculates the frequency of full words within the entire set of
     * documents. Each word is a key in a hashtable with the value being the
     * frequency of occurrance. This is legacy code rewritten and was included
     * for completeness.
     **/
    public void wordFrequency() {
        frequency = new Hashtable<Object, Integer>();
        for (int i = 0; i < documents.size(); i++) {
            String stDoc = documents.elementAt(i).stringify();
            StringTokenizer st = new StringTokenizer(stDoc, " .,;:?!\"");
            while (st.hasMoreTokens()) {
                String word = st.nextToken();
                if (frequency.containsKey(word)) {
                    Integer t = frequency.get(word);
                    int ti = t.intValue() + 1;
                    frequency.put(word, new Integer(ti));
                } else {
                    frequency.put(word, new Integer(1));
                }
            }
        }
    }

}

/**
 * kvp -> key value pair. This is a specific hashtable implementation for the
 * frequency analysis portions of DocumentSet.mostCommon()
 **/
class kvp implements Comparable<Object> {
    Object key;
    int    value;

    public kvp(Object key, Integer value) {
        this.key = key;
        this.value = value.intValue();
    }

    public int compareTo(Object ol) {
        if (value == ((kvp) ol).value) {
            return 0;
        } else if (value < ((kvp) ol).value) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        String t = new String();
        t = value + ":\t" + key;
        return t;
    }
}
