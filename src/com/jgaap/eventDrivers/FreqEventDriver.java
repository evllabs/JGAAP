/*
 * JGAAP -- a graphical program for stylometric authorship attribution
 * Copyright (C) 2009,2011 by Patrick Juola
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 **/
package com.jgaap.eventDrivers;

import com.jgaap.jgaapConstants;
import com.jgaap.backend.API;
import com.jgaap.generics.Document;
import com.jgaap.generics.NumericEventSet;

/**
 * Corpus frequencies taken from English Lexicon Project. Converts each word
 * (using table) to the the (log-scaled) frequency in which that word appears in
 * the general purpose HAL corpus as recorded in the ELP database. Obviously
 * English-only, and obviously incomplete; words that are not in the database
 * are silently removed.
 */

public class FreqEventDriver extends NumericTransformationEventDriver {

    @Override
    public String displayName(){
    	return "Lexical Frequencies";
    }
    
    @Override
    public String tooltipText(){
    	return "(Log Scaled) HAL frequencies from English Lexicon Project";
    }
    
    @Override
    public boolean showInGUI(){
    	return API.getInstance().getLanguage().displayName().equalsIgnoreCase("english");
    }

    @Override
    public NumericEventSet createEventSet(Document ds) {
        NumericTransformationEventDriver theDriver = new NumericTransformationEventDriver();
        // uses NaiveWordEventSet for now
        theDriver.setParameter("implicitWhitelist", "true");
        theDriver.setParameter("filename", jgaapConstants.JGAAP_RESOURCE_PACKAGE + "ELPfreq.dat");

        return theDriver.createEventSet(ds);
    }

}
