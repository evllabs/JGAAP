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
    ModelJSON model;

    public Experiment instanceExperiment() throws Exception {
        Experiment experiment = Experiments.getExperiment(this.getName());
        if (this.parameters != null)
            experiment.setParameters(this.parameters);
        experiment.setModel(this.getModel().instanceModel());
        return experiment;
    }

    static public Experiment readExperiment(String json) throws Exception {
        Gson gson = new Gson();
        ExperimentJSON experimentJSON = gson.fromJson(json, ExperimentJSON.class);
        return experimentJSON.instanceExperiment();
    }
}
