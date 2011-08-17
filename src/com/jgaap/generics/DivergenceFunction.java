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
package com.jgaap.generics;

import com.jgaap.generics.DivergenceType;

/**
 * @author Michael Ryan
 * @since 4.5.0
 */

public abstract class DivergenceFunction extends DistanceFunction {

	abstract public String displayName();

	@Override
	public double distance(EventSet es1, EventSet es2) {
		double dist;
		double first;
		double second;
		String divergenceString = getParameter("divergenceType");
		DivergenceType divergenceType;
		if(!divergenceString.isEmpty())
			divergenceType = DivergenceType.valueOf(divergenceString.toUpperCase());
		else
			divergenceType = DivergenceType.STANDARD;
		switch (divergenceType.ordinal()) {
		case 1:
			dist = (divergence(es1, es2) + divergence(es2, es1)) / 2.0;
			break;
		case 2:
			first = divergence(es1, es2);
			second = divergence(es1, es2);
			dist = (first > second ? first : second);
			break;
		case 3:
			first = divergence(es1, es2);
			second = divergence(es1, es2);
			dist = (first < second ? first : second);
			break;
		case 4:
			dist = divergence(es2, es1);
			break;
		case 5:
			first = divergence(es1, es2);
			second = divergence(es1, es2);
			dist = first * second;
            break;
		case 0:
		default:
			dist = divergence(es1, es2);
			break;
		}
		return dist;
	}

	public String getDivergenceType() {
		String result = "";
		String divergenceString = getParameter("divergenceType");
		int divergenceType = Integer
				.parseInt((divergenceString.equals("") ? "0" : divergenceString));
		switch (divergenceType) {
		case 1:
			result += " (Average)";
			break;
		case 2:
			result += " (Max)";
			break;
		case 3:
			result += " (Min)";
			break;
		case 4:
			result += " (Reverse)";
			break;
		case 5:
			result += " (Cross)";
			break;
		default:
		}
		return result;
	}

	abstract protected double divergence(EventSet es1, EventSet es2);

	@Override
	abstract public boolean showInGUI();

	@Override
	abstract public String tooltipText();

}
