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

import java.util.HashMap;

/**
 * A class of things-that-can-take-(label:value)-parameters.
 * 
 * @author Juola
 * @since 1.0
 */
public class Parameterizable {

    /** Parameters are stored using pairs of Strings in a HashMap */
    private HashMap<String, String> Parameters;

    /** Construct new Parameterizable with empty set */
    public Parameterizable() {
        Parameters = new HashMap<String, String>();
    }

    /** Removes all label and their associated values */
    public void clearParameterSet() {
        Parameters.clear();
    }

    /**
     * Removes a label and its associated value
     * 
     * @param label
     *            the label to set
     */
    public void deleteParameter(String label) {
        Parameters.remove(label);
    }

    /**
     * return the value associated with label
     * 
     * @param label
     *            the label to set
     * @return the appropriate value stored in the parameter set
     */
    public String getParameter(String label) {
        if (Parameters.containsKey(label.toLowerCase())) {
            return Parameters.get(label.toLowerCase());
        } else {
            return new String("");
        }
    }

    /**
     * Set label=String.valueOf(value) (persistantly)
     * 
     * @param label
     *            the label to set
     * @param value
     *            the (double) value to set the label to
     */
    public void setParameter(String label, double value) {
        Parameters.put(label.toLowerCase(), String.valueOf(value));
    }

    /**
     * Set label=String.valueOf(value) (persistantly)
     * 
     * @param label
     *            the label to set
     * @param value
     *            the (integer) value to set the label to
     */
    public void setParameter(String label, int value) {
        Parameters.put(label.toLowerCase(), String.valueOf(value));
    }

    // do we need things that return Integers and so forth?

    /**
     * Set label=String.valueOf(value) (persistantly)
     * 
     * @param label
     *            the label to set
     * @param value
     *            the (long) value to set the label to
     */
    public void setParameter(String label, long value) {
        Parameters.put(label.toLowerCase(), String.valueOf(value));
    }

    /**
     * Set label=value (persistantly)
     * 
     * @param label
     *            the label to set
     * @param value
     *            the value to set the label to
     */
    public void setParameter(String label, String value) {
        Parameters.put(label.toLowerCase(), value);
    }
}
