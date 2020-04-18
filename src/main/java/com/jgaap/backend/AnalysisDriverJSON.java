package com.jgaap.backend;

import com.jgaap.generics.AnalysisDriver;
import com.jgaap.generics.NeighborAnalysisDriver;
import lombok.Value;

import java.util.Map;

@Value
public class AnalysisDriverJSON {
    String name;
    Map<String, String> parameters;
    DistanceFunctionJSON distanceFunction;

    public AnalysisDriver instanceAnalysisDriver() throws Exception {
        AnalysisDriver analysisDriver = AnalysisDrivers.getAnalysisDriver(this.name);
        if (this.parameters != null)
            analysisDriver.setParameters(this.parameters);
        if (analysisDriver instanceof NeighborAnalysisDriver) {
            if (this.distanceFunction == null){
                throw new Exception("No DistanceFunction set on Distance based AnalysisDriver "+analysisDriver.displayName());
            }
            ((NeighborAnalysisDriver)analysisDriver).setDistance(this.distanceFunction.instanceDistanceFunction());
        }
        return analysisDriver;
    }
}
