package com.jgaap.backend;

import com.google.gson.Gson;
import com.jgaap.generics.Experiment;
import com.jgaap.generics.EventDriver;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Value public class ExperimentJSON {
    String name;
    Map<String, String> parameters;
    AnalysisDriverJSON analysisDriver;
    List<EventDriverJSON> eventDrivers;


    public List<EventDriver> instanceEventDrivers() throws Exception {
        List<EventDriver> eventDrivers = new ArrayList<>(this.getEventDrivers().size());
        for (EventDriverJSON eventDriverJSON : this.getEventDrivers()){
            eventDrivers.add(eventDriverJSON.instanceEventDriver());
        }
        return eventDrivers;
    }

    public Experiment instanceExperiment() throws Exception {
        Experiment experiment = Experiments.getExperiment(this.getName());
        if (this.parameters != null)
            experiment.setParameters(this.parameters);
        experiment.addEventDrivers(this.instanceEventDrivers());
        experiment.setAnalysisDriver(this.analysisDriver.instanceAnalysisDriver());
        return experiment;
    }

    static public Experiment readExperiment(String json) throws Exception {
        Gson gson = new Gson();
        ExperimentJSON experimentJSON = gson.fromJson(json, ExperimentJSON.class);
        return experimentJSON.instanceExperiment();
    }
}
