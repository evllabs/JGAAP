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
package com.jgaap.languages;

import com.jgaap.generics.Language;
/**
 * 
 * Representation of Russian in jgaap using ISO-8859-5
 * 
 * @author 
 *
 */
public class Russian extends Language {
	public Russian() {
		super("Russian (ISO-8859-5)", "russian", "ISO-8859-5");
	}

	public boolean showInGUI() {
		return true;
	}
}
