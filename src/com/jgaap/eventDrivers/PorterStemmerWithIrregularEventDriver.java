// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
package com.jgaap.eventDrivers;

import java.util.Hashtable;
import java.util.Iterator;

import com.jgaap.generics.Document;
import com.jgaap.generics.Event;
import com.jgaap.generics.EventDriver;
import com.jgaap.generics.EventSet;


/*
 *@author Darren Vescovi
 *
 *  This uses the porter stemmer but replaces the irregular nouns and verbs that the porter stemmer does
 *  not change into the base form they should be changed into.
 * 
 */

public class PorterStemmerWithIrregularEventDriver extends EventDriver {

	@Override
	public EventSet createEventSet(Document doc) {
		Hashtable<String, String> verbs = new Hashtable<String, String>();
		verbs.put("awoke", "awake");
		verbs.put("awoken", "awake");
		verbs.put("was", "be");
		verbs.put("were", "be");
		verbs.put("been", "be");
		verbs.put("bore", "bear");
		verbs.put("born", "bear");
		verbs.put("beat", "beat");
		verbs.put("became", "become");
		verbs.put("become", "become");
		verbs.put("began", "begin");
		verbs.put("begun", "begin");
		verbs.put("bent", "bend");
		verbs.put("beset", "beset");
		verbs.put("bet", "bet");
		verbs.put("bid", "bid");
		verbs.put("bade", "bid");
		verbs.put("bidden", "bid");
		verbs.put("bound", "bind");
		verbs.put("bit", "bite");
		verbs.put("bitten", "bite");
		verbs.put("bled", "bleed");
		verbs.put("blew", "blow");
		verbs.put("blown", "blow");
		verbs.put("broke", "break");
		verbs.put("broken", "break");
		verbs.put("bred", "breed");
		verbs.put("brought", "bring");
		verbs.put("broadcast", "broadcast");
		verbs.put("built", "build");
		verbs.put("burned", "burn");
		verbs.put("burnt", "burn");
		verbs.put("burst", "burst");
		verbs.put("bought", "buy");
		verbs.put("cast", "cast");
		verbs.put("caught", "catch");
		verbs.put("chose", "choose");
		verbs.put("chosen", "choose");
		verbs.put("clung", "cling");
		verbs.put("came", "come");
		verbs.put("come", "come");
		verbs.put("cost", "cost");
		verbs.put("crept", "creep");
		verbs.put("cut", "cut");
		verbs.put("dealt", "deal");
		verbs.put("dug", "dig");
		verbs.put("dived", "dive");
		verbs.put("dove", "dive");
		verbs.put("did", "do");
		verbs.put("done", "do");
		verbs.put("drew", "draw");
		verbs.put("drawn", "draw");
		verbs.put("dreamed", "dream");
		verbs.put("dreamt", "dream");
		verbs.put("drove", "drive");
		verbs.put("driven", "drive");
		verbs.put("drank", "drink");
		verbs.put("drunk", "drink");
		verbs.put("ate", "eat");
		verbs.put("eaten", "eat");
		verbs.put("fell", "fall");
		verbs.put("fallen", "fall");
		verbs.put("fed", "feed");
		verbs.put("felt", "feel");
		verbs.put("fought", "fight");
		verbs.put("found", "find");
		verbs.put("fit", "fit");
		verbs.put("fled", "flee");
		verbs.put("flung", "fling");
		verbs.put("flew", "fly");
		verbs.put("flown", "fly");
		verbs.put("forbade", "forbid");
		verbs.put("forbidden", "forbid");
		verbs.put("forgot", "forget");
		verbs.put("forgotten", "forget");
		verbs.put("forewent", "forego");
		verbs.put("foregone", "forego");
		verbs.put("forgave", "forgive");
		verbs.put("forgiven", "forgive");
		verbs.put("forsook", "forsake");
		verbs.put("forsaken", "forsake");
		verbs.put("froze", "freeze");
		verbs.put("frozen", "freeze");
		verbs.put("got", "get");
		verbs.put("gotten", "get");
		verbs.put("gave", "give");
		verbs.put("given", "give");
		verbs.put("went", "go");
		verbs.put("gone", "go");
		verbs.put("ground", "grind");
		verbs.put("grew", "grow");
		verbs.put("grown", "grow");
		verbs.put("hung", "hang");
		verbs.put("heard", "hear");
		verbs.put("hid", "hide");
		verbs.put("hidden", "hide");
		verbs.put("hit", "hit");
		verbs.put("held", "hold");
		verbs.put("hurt", "hurt");
		verbs.put("kept", "keep");
		verbs.put("knelt", "kneel");
		verbs.put("knit", "knit");
		verbs.put("knew", "know");
		verbs.put("know", "know");
		verbs.put("laid", "lay");
		verbs.put("led", "lead");
		verbs.put("leaped", "leap");
		verbs.put("leapt", "leap");
		verbs.put("learned", "learn");
		verbs.put("learnt", "learn");
		verbs.put("left", "leave");
		verbs.put("lent", "lend");
		verbs.put("let", "let");
		verbs.put("lay", "lie");
		verbs.put("lain", "lie");
		verbs.put("lighted", "light");
		verbs.put("lit", "light");
		verbs.put("lost", "lose");
		verbs.put("made", "make");
		verbs.put("meant", "mean");
		verbs.put("met", "meet");
		verbs.put("misspelled", "misspell");
		verbs.put("misspelt", "misspell");
		verbs.put("mistook", "mistake");
		verbs.put("mistaken", "mistake");
		verbs.put("mowed", "mow");
		verbs.put("mown", "mow");
		verbs.put("overcame", "overcome");
		verbs.put("overcome", "overcome");
		verbs.put("overdid", "overdo");
		verbs.put("overdone", "overdo");
		verbs.put("overtook", "overtake");
		verbs.put("overtaken", "overtake");
		verbs.put("overthrew", "overthrow");
		verbs.put("overthrown", "overthrow");
		verbs.put("paid", "pay");
		verbs.put("pled", "plead");
		verbs.put("proved", "prove");
		verbs.put("proven", "prove");
		verbs.put("put", "put");
		verbs.put("quit", "quit");
		verbs.put("read", "read");
		verbs.put("rid", "rid");
		verbs.put("rode", "ride");
		verbs.put("ridden", "ride");
		verbs.put("rang", "ring");
		verbs.put("rung", "ring");
		verbs.put("rose", "rise");
		verbs.put("risen", "rise");
		verbs.put("ran", "run");
		verbs.put("run", "run");
		verbs.put("sawed", "saw");
		verbs.put("sawn", "saw");
		verbs.put("said", "say");
		verbs.put("saw", "see");
		verbs.put("seen", "see");
		verbs.put("sought", "seek");
		verbs.put("sold", "sell");
		verbs.put("sent", "send");
		verbs.put("set", "set");
		verbs.put("sewed", "sew");
		verbs.put("sewn", "sew");
		verbs.put("shook", "shake");
		verbs.put("shaken", "shake");
		verbs.put("shaved", "shave");
		verbs.put("shaven", "shave");
		verbs.put("shore", "shear");
		verbs.put("shorn", "shear");
		verbs.put("shed", "shed");
		verbs.put("shone", "shine");
		verbs.put("shoed", "shoe");
		verbs.put("shod", "shoe");
		verbs.put("shot", "shoot");
		verbs.put("showed", "show");
		verbs.put("shown", "show");
		verbs.put("shrank", "shrink");
		verbs.put("shrunk", "shrink");
		verbs.put("shut", "shut");
		verbs.put("sang", "sing");
		verbs.put("sung", "sing");
		verbs.put("sank", "sink");
		verbs.put("sunk", "sink");
		verbs.put("sat", "sit");
		verbs.put("slept", "sleep");
		verbs.put("slew", "slay");
		verbs.put("slain", "slay");
		verbs.put("slid", "slide");
		verbs.put("slung", "sling");
		verbs.put("slit", "slit");
		verbs.put("smote", "smite");
		verbs.put("smitten", "smite");
		verbs.put("sowed", "sow");
		verbs.put("sown", "sow");
		verbs.put("spoke", "speak");
		verbs.put("spoken", "speak");
		verbs.put("sped", "speed");
		verbs.put("spent", "spend");
		verbs.put("spilled", "spill");
		verbs.put("spilt", "spill");
		verbs.put("spun", "spin");
		verbs.put("spit", "spit");
		verbs.put("spat", "spit");
		verbs.put("split", "split");
		verbs.put("spread", "spread");
		verbs.put("sprang", "spring");
		verbs.put("sprung", "spring");
		verbs.put("stood", "stand");
		verbs.put("stole", "steal");
		verbs.put("stolen", "steal");
		verbs.put("stuck", "stick");
		verbs.put("stung", "sting");
		verbs.put("stank", "stink");
		verbs.put("stunk", "stink");
		verbs.put("strod", "stride");
		verbs.put("stridden", "stride");
		verbs.put("struck", "strike");
		verbs.put("strung", "string");
		verbs.put("strove", "strive");
		verbs.put("striven", "strive");
		verbs.put("swore", "swear");
		verbs.put("sworn", "swear");
		verbs.put("swept", "sweep");
		verbs.put("swelled", "swell");
		verbs.put("swollen", "swell");
		verbs.put("swam", "swim");
		verbs.put("swum", "swim");
		verbs.put("swung", "swing");
		verbs.put("took", "take");
		verbs.put("taken", "take");
		verbs.put("taught", "teach");
		verbs.put("tore", "tear");
		verbs.put("torn", "tear");
		verbs.put("told", "tell");
		verbs.put("thought", "think");
		verbs.put("thrived", "thrive");
		verbs.put("throve", "thrive");
		verbs.put("threw", "throw");
		verbs.put("thrown", "throw");
		verbs.put("thrust", "thrust");
		verbs.put("trod", "tread");
		verbs.put("trodden", "tread");
		verbs.put("understood", "understand");
		verbs.put("upheld", "uphold");
		verbs.put("upset", "upset");
		verbs.put("woke", "wake");
		verbs.put("woken", "wake");
		verbs.put("wore", "wear");
		verbs.put("worn", "wear");
		verbs.put("weaved", "weave");
		verbs.put("wove", "weave");
		verbs.put("woven", "weave");
		verbs.put("wed", "wed");
		verbs.put("wept", "weep");
		verbs.put("wound", "wind");
		verbs.put("won", "win");
		verbs.put("withheld", "withhold");
		verbs.put("withstood", "withstand");
		verbs.put("wrung", "wring");
		verbs.put("wrote", "write");
		verbs.put("written", "write");
		
		
		Hashtable<String, String> nouns = new Hashtable<String, String>();
		nouns.put("alumni", "alumnus");
		nouns.put("analyses", "analysis");
		nouns.put("antennae", "antenna");
		nouns.put("antennas", "antenna");
		nouns.put("appendices", "appendix");
		nouns.put("axes", "axis");
		nouns.put("bacteria", "bacterium");
		nouns.put("bases", "basis");
		nouns.put("beaux", "beau");
		nouns.put("bureaux", "bureau");
		nouns.put("bureaus", "bureau");
		nouns.put("children", "child");
		nouns.put("corpora", "corpus");
		nouns.put("corpuses", "corpus");
		nouns.put("crises", "crisis");
		nouns.put("criteria", "criterion");
		nouns.put("curricula", "curriculum");
		nouns.put("data", "datum");
		nouns.put("deer", "deer");
		nouns.put("diagnoses", "diagnosis");
		nouns.put("ellipses", "ellipsis");
		nouns.put("fish", "fish");
		nouns.put("foci", "focus");
		nouns.put("focuses", "focus");
		nouns.put("feet", "foot");
		nouns.put("formulae", "formula");
		nouns.put("formulas", "formula");
		nouns.put("fungi", "fungus");
		nouns.put("funguses", "fungus");
		nouns.put("genera", "genus");
		nouns.put("geese", "goose");
		nouns.put("hypotheses", "hypothesis");
		nouns.put("indices/indexes", "index");
		nouns.put("lice", "louse");
		nouns.put("men", "man");
		nouns.put("matrices", "matrix");
		nouns.put("means", "means");
		nouns.put("media", "medium");
		nouns.put("mice", "mouse");
		nouns.put("nebulae", "nebula");
		nouns.put("nuclei", "nucleus");
		nouns.put("oases", "oasis");
		nouns.put("oxen", "ox");
		nouns.put("paralyses", "paralysis");
		nouns.put("parentheses", "parenthesis");
		nouns.put("phenomena", "phenomenon");
		nouns.put("radii", "radius");
		nouns.put("series", "series");
		nouns.put("sheep", "sheep");
		nouns.put("species", "species");
		nouns.put("stimuli", "stimulus");
		nouns.put("strata", "stratum");
		nouns.put("syntheses", "synthesis");
		nouns.put("synopses", "synopsis");
		nouns.put("tableaux", "tableau");
		nouns.put("theses", "thesis");
		nouns.put("teeth", "tooth");
		nouns.put("vertebrae", "vertebra");
		nouns.put("vitae", "vita");
		nouns.put("women", "woman");
		
		PorterStemmerEventDriver stemmer = new PorterStemmerEventDriver();
		
		EventSet ev = stemmer.createEventSet(doc);
		EventSet returnEv = new EventSet(ev.getAuthor());
		Iterator<Event> it = ev.iterator();
		
		while(it.hasNext()){
			Event event = it.next();
			if(verbs.containsKey(event.getEvent())){
				returnEv.addEvent(new Event(verbs.get(event.getEvent())));
			}else if(nouns.containsKey(event.getEvent())){
				returnEv.addEvent(new Event(nouns.get(event.getEvent())));
			}
			else{
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
		
		return true;
	}

	@Override
	public String tooltipText() {
		
		return "Word stems from the Porter Stemmer with ability to handle irregular nouns and verbs";
	}

}
