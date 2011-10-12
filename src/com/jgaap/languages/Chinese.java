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
 * A generic chinese representation using GB2123 that does not parse the documents.
 * 
 * @author Michael Ryan
 *
 */
public class Chinese extends Language {
	public Chinese() {
		super("Chinese (GB2123)", "chinese", "GB2123");
	}

	public boolean showInGUI() {
		return true;
	}
}
