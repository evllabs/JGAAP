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
public class GenerateTests {
    public static void main(String[] args) {
	String[] eventSets = { "Characters", "Character BiGrams", "Word Lengths", "Word BiGrams", "Words"};
	String[] analyzers = { "Cosine Distance", "LDA", "Linear SVM", "Gaussian SVM"};
	//String[] canonicizers = { "Smash Case", "Normalize Whitespace", "Strip HTML", "Strip Punctuation", "Smash Case Normalize Whitespace", "Smash Case Strip HTML", "Smash Case Strip Punctuation", "Normalize Whitespace Strip HTML", "Normalize Whitespace Strip Punctuation", "Strip HTML Strip Punctuation", "Smash Case Normalize Whitespace Strip HTML", "Normalize Whitespace Strip HTML Strip Punctuation", "Smash Case Normalize Whitespace Strip Punctuation", "Smash Case Normalize Whitespace Strip HTML Strip Punctuation", "Smash Case Strip HTML Strip Punctuation"};
	
	//	String[] canonicizers = {"Smash Case Normalize Whitespace", "Smash Case Normalize Whitespace Strip Punctuation", "Smash Case Strip Punctuation", "Smash Case", "Strip Punctuation"};
	String[] canonicizers = {"  "};

	for(String e : eventSets) {
	    for(String a : analyzers) {
		for(String c : canonicizers) {
		    System.out.println(c + "," + e + "," + a + ",");
		    System.out.println(c + "," + e + "," + a + ",mc");
		}
	    }
	}
    }
}