package com.jgaap.util;

import java.util.Set;
/**
 * Interface used in analysis for different types of histograms.
 * @author ryan
 *
 */
public interface Histogram extends Iterable<Event> {
	double getRelativeFrequency(Event event);
	double getNormalizedFrequency(Event event);
	int getAbsoluteFrequency(Event event);
	boolean contains(Event event);
	Set<Event> uniqueEvents();
}
