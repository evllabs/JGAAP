package com.jgaap.backend;

import com.jgaap.generics.Canonicizer;
import lombok.Value;

import java.util.Map;

@Value public class CanonicizerJSON {
    String name;
    Map<String, String> parameters;

    public Canonicizer instanceCanonicizer() throws Exception {
        Canonicizer canonicizer = Canonicizers.getCanonicizer(this.name);
        if (this.parameters != null)
            canonicizer.setParameters(this.parameters);
        return canonicizer;
    }
}
