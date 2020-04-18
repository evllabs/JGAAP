package com.jgaap.util;

import java.util.Set;
/**
 * Interface used in analysis for different types of histograms.
 * @author ryan
 *
 */
public interface Histogram {
	public double relativeFrequency(Event event);
	public double normalizedFrequency(Event event);
	public int absoluteFrequency(Event event);
	public boolean contains(Event event);
	public Set<Event> uniqueEvents();
}
