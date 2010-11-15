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
package com.jgaap.eventDrivers;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;

/**
 * This event set is the most common N of an underlying event model
 * (parameterized as underlyingevents)
 *
 * Note: As of JGAAP 4.0 the MostCommonEventSet is deprecated.
 * public Vector<EventSet> createEventSet(Vector<DocumentSet> dsv)
 * should be final in EventDriver, as it is only a wrapper and all
 * event sets mnust use createEventSet(DocumentSet) instead.  Thus
 * MostCommonEventSet should be moved to an intermediate step and
 * the above method should be made final.
 **/
@Deprecated
public class MostCommonEventDriver extends EventDriver {
    // test all named files directly using main() method
    /*public static void main(String[] args) {
        MostCommonEventDriver me = new MostCommonEventDriver();
        me.setN(10);
        // for (int i = 0; i < args.length; i++)
        // {
        // int retval = me.selftest(args[i]);
        // System.out.println(
        // me.getClass().getName() + ": test case " + args[i] + " returned " +
        // retval
        // );
        // }
        int retval = me.selftest(args);
        System.out.println(me.getClass().getName() + ": test case " + args
                + " returned " + retval);
    }*/
    @Override
    public String displayName(){
    	return "Most Common Events";
    }
    
    @Override
    public String tooltipText(){
    	return "(Should never be user-visible)";
    }
    
    @Override
    public boolean showInGUI(){
    	return false;
    }

    private EventDriver underlyingevents = new NaiveWordEventDriver();
    private int         N                = 50;

    private int         initialsize      = 1001;                             ;

    @Override
    public EventSet createEventSet(Document ds) {
        Vector<Document> v = new Vector<Document>();
        v.add(ds);

        Vector<EventSet> v2 = createEventSet(v);
        return v2.elementAt(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Vector<EventSet> createEventSet(Vector<Document> dsv) {

		// DEBUG
		System.out.println("dsv.size = " + dsv.size());
		
        // Extract local field values based on parameter settings
        String param;
        if (!(param = (getParameter("N"))).equals("")) {
            try {
                int value = Integer.parseInt(param);
                setN(value);
            } catch (NumberFormatException e) {
                System.out
                        .println("Error: cannot parse N:" + param + " as int");
                System.out.println(" -- Using default value (50)");
                setN(50);
            }
        }

        if (!(param = (getParameter("initialsize"))).equals("")) {
            try {
                int value = Integer.parseInt(param);
                setInitialSize(value);
            } catch (NumberFormatException e) {
                System.out.println("Error: cannot parse initialsize:" + param
                        + " as int");
                System.out.println(" -- Using default value (1001)");
                setInitialSize(1001);
            }
        }

        if (!(param = (getParameter("underlyingEvents"))).equals("")) {
            try {
                Object o = Class.forName(param).newInstance();
                if (o instanceof EventDriver) {
                    setEvents((EventDriver) o);
                } else {
                    throw new ClassCastException();
                }
            } catch (Exception e) {
                System.out.println("Error: cannot create EventDriver " + param);
                System.out.println(" -- Using NaiveWordEventSet");
                setEvents(new NaiveWordEventDriver());
            }
        }

        Vector<EventSet> esv = new Vector<EventSet>();
        HashMap<Event, Integer> theHist = new HashMap<Event, Integer>(
                initialsize);

        for (int j = 0; j < dsv.size(); j++) {

            /**
             * Get underlying events
             **/
            EventSet es = underlyingevents.createEventSet(dsv.elementAt(j));

            // System.out.println(es.toString());

            /**
             * Make event histogram
             **/
            for (int i = 0; i < es.size(); i++) {
                /* copied from EventHistogram.java */
                Event e = es.eventAt(i);
                Integer v = theHist.get(e);
                if (v == null) {
                    theHist.put(e, new Integer(1));
                } else {
                    theHist.put(e, new Integer(v.intValue() + 1));
                }
            }

            // Set printMe = theHist.entrySet();
            // Iterator printMeIter = printMe.iterator();
            // while (printMeIter.hasNext())
            // System.out.println(printMeIter.next().toString());
        }

        /**
         * Convert event histogram to SortedSet of data items.
         **/

        MyComparator compare = new MyComparator();

        SortedSet<Map.Entry<Event, Integer>> s = new TreeSet<java.util.Map.Entry<Event, java.lang.Integer>>(
                compare);

        SortedSet<Map.Entry<Event, Integer>> sprime;

        // s.addAll(theHist.entrySet() );

        Iterator<Map.Entry<Event, Integer>> printMeIter = theHist.entrySet()
                .iterator();
        while (printMeIter.hasNext()) {
            Map.Entry<Event, Integer> m;
            m = printMeIter.next();
            s.add(m);
        }

        // System.out.println(s);
        /**
         * Sort list -- should be automatic from SortedSet
         **/

        /**
         * Calculate top N elements and remove rest.
         **/

        // System.out.println(s.toArray());
        Object eventarray[] = s.toArray();

        // for (int i=0;i<2*N;i++)
        // System.out.println(eventarray[i].toString());

        if (eventarray.length >= N) {
            Map.Entry<Event, Integer> cutoff = (Map.Entry<Event, Integer>) eventarray[eventarray.length
                    - N];
            sprime = s.tailSet(cutoff);
        } else {
            sprime = s;
        }

        /**
         * Create list of remaining Events (lose counts);
         **/

        Vector<Event> v = new Vector<Event>();
        for (Map.Entry<Event, java.lang.Integer> iter : sprime) {
            v.add(iter.getKey());
        }

        /**
         * V should now be a Vector of the most common events
         **/

        for (int j = 0; j < dsv.size(); j++) {
            EventSet newEs = new EventSet();
            /* NB Should memoify this for speed */
            EventSet es = underlyingevents.createEventSet(dsv.elementAt(j));

            for (int i = 0; i < es.size(); i++) {
                Event e = es.eventAt(i);
                if (v.contains(e)) {
                    newEs.events.add(e);
                }
            }
            esv.add(newEs);
            // System.out.println(newEs.toString);
        }

        // System.out.println(newEs);

        return esv;

    }

    public EventDriver getEvents() {
        return underlyingevents;
    }

    public int getInitialSize() {
        return initialsize;
    };

    /* Parameter settings */
    public int getN() {
        return N;
    }

    // default set of tests accessed via selftest()
    /*
    @Override
    public int selftest() {
        // add lots of cases later as needed
        // return selftest("unittestcases/PiRhyme.txt");
        return -2;
    }

    // test on specific named files
    public int selftest(String[] file) {
        Vector<DocumentSet> dsv = new Vector<DocumentSet>();
        for (int i = 0; i < file.length; i++) {
            DocumentSet ds = new DocumentSet(new Document(file[i]));
            dsv.add(ds);
        }
		
        Vector<EventSet> esv = this.createEventSet(dsv);

        for (int i = 0; i < esv.size(); i++) {
            System.out.println(esv.elementAt(i).toString());
        }
        return -1; // no actual test as of yet
    }
	*/
    


    public void setEvents(EventDriver underlyingevents) {
        this.underlyingevents = underlyingevents;
    }

    public void setInitialSize(int init) {
        initialsize = init;
    }

    public void setN(int N) {
        this.N = N;
    }

}

class MyComparator implements Comparator<Map.Entry<Event, Integer>> {

    public int compare(Map.Entry<Event, Integer> x, Map.Entry<Event, Integer> y) {

        int retval;

        // System.out.println("Comparing " +x.toString() + " and " +
        // y.toString());
        // System.out.println("Comparing " +x.getValue().toString() + " and " +
        // y.getValue().toString());

        // System.out.println(x.getValue() - y.getValue());
        // System.out.println(x.getValue() != y.getValue());

        // System.out.println("Comparing " +x.getKey().toString() + " and " +
        // y.getKey().toString());

        // System.out.println( x.getKey().compareTo(y.getKey()));
        // System.out.println( x.equals(y));

        if (!x.getValue().equals(y.getValue())) {
            retval = x.getValue() - y.getValue();
        } else {
            retval = x.getKey().compareTo(y.getKey());
        }
        // System.out.println("Returning " + retval);
        return retval;
    }
}
