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
package com.jgaap.classifiers;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.jgaap.generics.AnalyzeException;
import com.jgaap.generics.DistanceCalculationException;
import com.jgaap.generics.EventSet;
import com.jgaap.generics.NeighborAnalysisDriver;
import com.jgaap.generics.Pair;

/**
 * Assigns authorship labels by using a nearest-neighbor approach on a given
 * distance/divergence function.
 * 
 */
public class NearestNeighborDriver extends NeighborAnalysisDriver {

	private Logger logger = Logger.getLogger(NearestNeighborDriver.class);
	
	private List<EventSet> knowns;
	
	public String displayName() {
		return "Nearest Neighbor Driver" + getDistanceName();
	}

	public String tooltipText() {
		return "Assigns authorship labels by using a nearest-neighbor approach on a given distance/divergence function. ";
	}

	public boolean showInGUI() {
		return true;
	}
	
	public void train(List<EventSet> knowns){
		this.knowns = knowns;
	}

	@Override
	public List<Pair<String, Double>> analyze(EventSet unknown) throws AnalyzeException {
		List<Pair<String, Double>> results = new ArrayList<Pair<String,Double>>();

		for (EventSet known : knowns){
			double current;
			try {
				current = distance.distance(unknown, known);
			} catch (DistanceCalculationException e) {
				logger.error("Distance "+distance.displayName()+" failed", e);
				throw new AnalyzeException("Distance "+distance.displayName()+" failed");
			}
			results.add(new Pair<String, Double>(known.getAuthor() + "-" + known.getDocumentName(),current,2));
			logger.debug(unknown.getDocumentName()+"(Unknown):"+known.getDocumentName()+"("+known.getAuthor()+") Distance:"+current);
		}
		Collections.sort(results);
		return results;
	}

}
