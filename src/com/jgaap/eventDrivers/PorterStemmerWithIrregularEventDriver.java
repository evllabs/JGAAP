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
package com.jgaap.eventDrivers;

import com.google.common.collect.ImmutableMap;
import com.jgaap.backend.API;
import com.jgaap.util.Event;
import com.jgaap.util.EventSet;

/*
 *@author Darren Vescovi
 *
 *  This uses the porter stemmer but replaces the irregular nouns and verbs that the porter stemmer does
 *  not change into the base form they should be changed into.
 * 
 */

public class PorterStemmerWithIrregularEventDriver extends PorterStemmerEventDriver {

	private static ImmutableMap<String, String> verbs = ImmutableMap.<String, String> builder().put("awoke", "awake")
			.put("awoken", "awake").put("was", "be").put("were", "be").put("been", "be").put("bore", "bear")
			.put("born", "bear").put("beat", "beat").put("became", "become").put("become", "become")
			.put("began", "begin").put("begun", "begin").put("bent", "bend").put("beset", "beset").put("bet", "bet")
			.put("bid", "bid").put("bade", "bid").put("bidden", "bid").put("bound", "bind").put("bit", "bite")
			.put("bitten", "bite").put("bled", "bleed").put("blew", "blow").put("blown", "blow").put("broke", "break")
			.put("broken", "break").put("bred", "breed").put("brought", "bring").put("broadcast", "broadcast")
			.put("built", "build").put("burned", "burn").put("burnt", "burn").put("burst", "burst")
			.put("bought", "buy").put("cast", "cast").put("caught", "catch").put("chose", "choose")
			.put("chosen", "choose").put("clung", "cling").put("came", "come").put("come", "come").put("cost", "cost")
			.put("crept", "creep").put("cut", "cut").put("dealt", "deal").put("dug", "dig").put("dived", "dive")
			.put("dove", "dive").put("did", "do").put("done", "do").put("drew", "draw").put("drawn", "draw")
			.put("dreamed", "dream").put("dreamt", "dream").put("drove", "drive").put("driven", "drive")
			.put("drank", "drink").put("drunk", "drink").put("ate", "eat").put("eaten", "eat").put("fell", "fall")
			.put("fallen", "fall").put("fed", "feed").put("felt", "feel").put("fought", "fight").put("found", "find")
			.put("fit", "fit").put("fled", "flee").put("flung", "fling").put("flew", "fly").put("flown", "fly")
			.put("forbade", "forbid").put("forbidden", "forbid").put("forgot", "forget").put("forgotten", "forget")
			.put("forewent", "forego").put("foregone", "forego").put("forgave", "forgive").put("forgiven", "forgive")
			.put("forsook", "forsake").put("forsaken", "forsake").put("froze", "freeze").put("frozen", "freeze")
			.put("got", "get").put("gotten", "get").put("gave", "give").put("given", "give").put("went", "go")
			.put("gone", "go").put("ground", "grind").put("grew", "grow").put("grown", "grow").put("hung", "hang")
			.put("heard", "hear").put("hid", "hide").put("hidden", "hide").put("hit", "hit").put("held", "hold")
			.put("hurt", "hurt").put("kept", "keep").put("knelt", "kneel").put("knit", "knit").put("knew", "know")
			.put("know", "know").put("laid", "lay").put("led", "lead").put("leaped", "leap").put("leapt", "leap")
			.put("learned", "learn").put("learnt", "learn").put("left", "leave").put("lent", "lend").put("let", "let")
			.put("lay", "lie").put("lain", "lie").put("lighted", "light").put("lit", "light").put("lost", "lose")
			.put("made", "make").put("meant", "mean").put("met", "meet").put("misspelled", "misspell")
			.put("misspelt", "misspell").put("mistook", "mistake").put("mistaken", "mistake").put("mowed", "mow")
			.put("mown", "mow").put("overcame", "overcome").put("overcome", "overcome").put("overdid", "overdo")
			.put("overdone", "overdo").put("overtook", "overtake").put("overtaken", "overtake")
			.put("overthrew", "overthrow").put("overthrown", "overthrow").put("paid", "pay").put("pled", "plead")
			.put("proved", "prove").put("proven", "prove").put("put", "put").put("quit", "quit").put("read", "read")
			.put("rid", "rid").put("rode", "ride").put("ridden", "ride").put("rang", "ring").put("rung", "ring")
			.put("rose", "rise").put("risen", "rise").put("ran", "run").put("run", "run").put("sawed", "saw")
			.put("sawn", "saw").put("said", "say").put("saw", "see").put("seen", "see").put("sought", "seek")
			.put("sold", "sell").put("sent", "send").put("set", "set").put("sewed", "sew").put("sewn", "sew")
			.put("shook", "shake").put("shaken", "shake").put("shaved", "shave").put("shaven", "shave")
			.put("shore", "shear").put("shorn", "shear").put("shed", "shed").put("shone", "shine").put("shoed", "shoe")
			.put("shod", "shoe").put("shot", "shoot").put("showed", "show").put("shown", "show")
			.put("shrank", "shrink").put("shrunk", "shrink").put("shut", "shut").put("sang", "sing")
			.put("sung", "sing").put("sank", "sink").put("sunk", "sink").put("sat", "sit").put("slept", "sleep")
			.put("slew", "slay").put("slain", "slay").put("slid", "slide").put("slung", "sling").put("slit", "slit")
			.put("smote", "smite").put("smitten", "smite").put("sowed", "sow").put("sown", "sow").put("spoke", "speak")
			.put("spoken", "speak").put("sped", "speed").put("spent", "spend").put("spilled", "spill")
			.put("spilt", "spill").put("spun", "spin").put("spit", "spit").put("spat", "spit").put("split", "split")
			.put("spread", "spread").put("sprang", "spring").put("sprung", "spring").put("stood", "stand")
			.put("stole", "steal").put("stolen", "steal").put("stuck", "stick").put("stung", "sting")
			.put("stank", "stink").put("stunk", "stink").put("strod", "stride").put("stridden", "stride")
			.put("struck", "strike").put("strung", "string").put("strove", "strive").put("striven", "strive")
			.put("swore", "swear").put("sworn", "swear").put("swept", "sweep").put("swelled", "swell")
			.put("swollen", "swell").put("swam", "swim").put("swum", "swim").put("swung", "swing").put("took", "take")
			.put("taken", "take").put("taught", "teach").put("tore", "tear").put("torn", "tear").put("told", "tell")
			.put("thought", "think").put("thrived", "thrive").put("throve", "thrive").put("threw", "throw")
			.put("thrown", "throw").put("thrust", "thrust").put("trod", "tread").put("trodden", "tread")
			.put("understood", "understand").put("upheld", "uphold").put("upset", "upset").put("woke", "wake")
			.put("woken", "wake").put("wore", "wear").put("worn", "wear").put("weaved", "weave").put("wove", "weave")
			.put("woven", "weave").put("wed", "wed").put("wept", "weep").put("wound", "wind").put("won", "win")
			.put("withheld", "withhold").put("withstood", "withstand").put("wrung", "wring").put("wrote", "write")
			.put("written", "write").build();
	private static ImmutableMap<String, String> nouns = ImmutableMap.<String, String> builder().put("alumni", "alumnus")
			.put("analyses", "analysis").put("antennae", "antenna").put("antennas", "antenna")
			.put("appendices", "appendix").put("axes", "axis").put("bacteria", "bacterium").put("bases", "basis")
			.put("beaux", "beau").put("bureaux", "bureau").put("bureaus", "bureau").put("children", "child")
			.put("corpora", "corpus").put("corpuses", "corpus").put("crises", "crisis").put("criteria", "criterion")
			.put("curricula", "curriculum").put("data", "datum").put("deer", "deer").put("diagnoses", "diagnosis")
			.put("ellipses", "ellipsis").put("fish", "fish").put("foci", "focus").put("focuses", "focus")
			.put("feet", "foot").put("formulae", "formula").put("formulas", "formula").put("fungi", "fungus")
			.put("funguses", "fungus").put("genera", "genus").put("geese", "goose").put("hypotheses", "hypothesis")
			.put("indices/indexes", "index").put("lice", "louse").put("men", "man").put("matrices", "matrix")
			.put("means", "means").put("media", "medium").put("mice", "mouse").put("nebulae", "nebula")
			.put("nuclei", "nucleus").put("oases", "oasis").put("oxen", "ox").put("paralyses", "paralysis")
			.put("parentheses", "parenthesis").put("phenomena", "phenomenon").put("radii", "radius")
			.put("series", "series").put("sheep", "sheep").put("species", "species").put("stimuli", "stimulus")
			.put("strata", "stratum").put("syntheses", "synthesis").put("synopses", "synopsis")
			.put("tableaux", "tableau").put("theses", "thesis").put("teeth", "tooth").put("vertebrae", "vertebra")
			.put("vitae", "vita").put("women", "woman").build();

	@Override
	public EventSet createEventSet(char[] text) {
		EventSet ev = super.createEventSet(text);
		EventSet returnEv = new EventSet();

		for (Event event : ev) {
			String current = event.toString();
			if (verbs.containsKey(current)) {
				returnEv.addEvent(new Event(verbs.get(current), this));
			} else if (nouns.containsKey(event.toString())) {
				returnEv.addEvent(new Event(nouns.get(current), this));
			} else {
				returnEv.addEvent(event);
			}
		}

		return returnEv;
	}

	@Override
	public String displayName() {
		return "Word stems w/ Irregular";
	}

	@Override
	public boolean showInGUI() {
		return API.getInstance().getLanguage().getLanguage().equalsIgnoreCase("English");
	}

	@Override
	public String tooltipText() {

		return "Word stems from the Porter Stemmer with ability to handle irregular nouns and verbs";
	}

}
