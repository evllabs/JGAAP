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
package com.jgaap.classifiers;

import com.jgaap.generics.*;
import com.jgaap.JGAAPConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by IntelliJ IDEA. User: jnoecker Date: Feb 2, 2011 Time: 11:53:53 AM
 * To change this template use File | Settings | File Templates.
 */
public class VectorOutputAnalysis extends AnalysisDriver {

	private List<Event> key;

	public String displayName() {
		return "Vector Output";
	}

	public String tooltipText() {
		return "Generates vectors from unknowns using a key (experimental)";
	}

	public boolean showInGUI() {
		return false;
	}

	public void train(List<EventSet> knowns) throws AnalyzeException {
		key = new ArrayList<Event>();
		String keyFile = JGAAPConstants.JGAAP_LIBDIR + "l1.key";
		Scanner input;
		try {
			input = new Scanner(new File(keyFile));
		} catch (FileNotFoundException e) {
			throw new AnalyzeException("Problem Opening Key file");
		}
		while (input.hasNextLine()) {
			key.add(new Event(input.nextLine()));
		}
		input.close();
	}

	public List<Pair<String, Double>> analyze(EventSet unknown) throws AnalyzeException {

		EventHistogram hist = new EventHistogram(unknown);
		List<Pair<String, Double>> results = new ArrayList<Pair<String, Double>>();

		FileOutputStream fsOut;
		PrintStream writer = null;
		String docPath = unknown.getDocumentName();
		String[] docPathArray = docPath.split("/");
		String unknownFileName = docPathArray[docPathArray.length - 1];
		try {
			fsOut = new FileOutputStream(JGAAPConstants.JGAAP_TMPDIR
					+ unknownFileName);
			writer = new PrintStream(fsOut);

		} catch (FileNotFoundException e) {
			throw new AnalyzeException("Problem opening "+JGAAPConstants.JGAAP_TMPDIR
					+ unknownFileName +" for output vector");
		}

		for (Event event : key) {
			double freq = hist.getRelativeFrequency(event);
			if (freq > 0) {
				writer.println(freq);
			} else {
				writer.println("0");
			}
		}
		writer.close();

		results.add(new Pair<String, Double>("No analysis performed.\n", 0.0));
		return results;
	}
}
