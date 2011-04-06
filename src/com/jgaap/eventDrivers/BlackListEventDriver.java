// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.eventDrivers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;
import javax.swing.*;

/**
 * Filters all Event strings against named file and removes named events.
 * Compare to WhiteListEventSet, which removes all BUT named events
 * 
 * @see WhiteListEventDriver
 */
public class BlackListEventDriver extends EventDriver {

    @Override
    public String displayName(){
    	return "Black-List";
    }
    
    @Override
    public String tooltipText(){
    	return "Filtered Event Set with Named Events Removed";
    }

    @Override
    public String longDescription(){
    	return "Filtered Event Set with Named Events Removed";
    }
    
    @Override
    public boolean showInGUI(){
    	return false;
    }

    @Override
    public GroupLayout getGUILayout(JPanel panel){
    	javax.swing.GroupLayout layout = new javax.swing.GroupLayout(panel);
        return layout;
    }


    private EventDriver underlyingEvents;

    private String      filename;

    @Override
    public EventSet createEventSet(Document ds) {
        String param;
        HashSet<String> blacklist = new HashSet<String>();

        String word;

        if (!(param = (getParameter("underlyingEvents"))).equals("")) {
            try {
                Object o = Class.forName(param).newInstance();
                if (o instanceof EventDriver) {
                    underlyingEvents = (EventDriver) o;
                } else {
                    throw new ClassCastException();
                }
            } catch (Exception e) {
                System.out.println("Error: cannot create EventDriver " + param);
                System.out.println(" -- Using NaiveWordEventSet");
                underlyingEvents = new NaiveWordEventDriver();
            }
        } else { // no underlyingEventsParameter, use NaiveWordEventSet
            underlyingEvents = new NaiveWordEventDriver();
        }

        if (!(param = (getParameter("filename"))).equals("")) {
            filename = param;
        } else { // no underlyingfilename,
            filename = null;
        }

        EventSet es = underlyingEvents.createEventSet(ds);

        EventSet newEs = new EventSet();
        newEs.setAuthor(es.getAuthor());
        newEs.setNewEventSetID(es.getAuthor());

        BufferedReader br = null;

        if (filename != null) {
            try {
                FileInputStream fis = new FileInputStream(filename);
                br = new BufferedReader(new InputStreamReader(fis));

                while ((word = br.readLine()) != null) {
                    blacklist.add(word.trim());
                }

            } catch (IOException e) {
                // catch io errors from FileInputStream or readLine()
                System.out.println("Cannot open/read " + filename);
                System.out.println("IOException error! " + e.getMessage());
            } finally {
                // if the file opened okay, make sure we close it
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException ioe) {
                    }
                }
            }
        } else {
            blacklist.clear();
        }

        for (Event event : es) {
            String s = (event).toString();
            if (!blacklist.contains(s)) {
                newEs.addEvent(event);
            }
        }
        return newEs;
    }

}
