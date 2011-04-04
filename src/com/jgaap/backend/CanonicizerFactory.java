// Copyright (c) 2009, 2011 by Patrick Juola.   All rights reserved.  All unauthorized use prohibited.  
package com.jgaap.backend;

import java.util.HashMap;
import java.util.Map;

import com.jgaap.generics.Canonicizer;

public class CanonicizerFactory {

	Map<String, Canonicizer> canonicizers;
	
	public CanonicizerFactory() {
		// Load the canonicizers dynamically
		canonicizers = new HashMap<String, Canonicizer>();
		for(Canonicizer canon : AutoPopulate.getCanonicizers()){
			canonicizers.put(canon.displayName().toLowerCase().trim(), canon);
		}	
	}
	
	public Canonicizer getCanonicizer(String action) throws Exception{
		Canonicizer canonicizer;
		action = action.toLowerCase().trim();
		if(canonicizers.containsKey(action)){
			canonicizer = canonicizers.get(action).getClass().newInstance();
		}else{
			throw new Exception("Canonicizer "+action+" not found!");
		}
		return canonicizer;
	}
	
}
