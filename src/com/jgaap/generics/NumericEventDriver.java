// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
/**
 **/
package com.jgaap.generics;


/** Any driver of a NumericEventSet is a NumericEventDriver */
public abstract class NumericEventDriver extends EventDriver {
	public abstract String displayName();

	public abstract String tooltipText();

	public abstract boolean showInGUI();


    @Override
    abstract public NumericEventSet createEventSet(Document ds);

}
