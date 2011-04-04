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
