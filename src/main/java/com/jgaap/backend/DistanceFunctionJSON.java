package com.jgaap.backend;

import com.jgaap.generics.DistanceFunction;
import lombok.Value;

import java.util.Map;

@Value public class DistanceFunctionJSON {
    String name;
    Map<String, String> parameters;

    public static DistanceFunctionJSON fromInstance(DistanceFunction distanceFunction) {
        return new DistanceFunctionJSON(distanceFunction.displayName(), distanceFunction.getParametersMap());
    }

    public DistanceFunction instanceDistanceFunction() throws Exception {
        DistanceFunction distanceFunction = DistanceFunctions.getDistanceFunction(this.name);
        if (this.parameters != null)
            distanceFunction.setParameters(this.parameters);
        return distanceFunction;
    }
}
