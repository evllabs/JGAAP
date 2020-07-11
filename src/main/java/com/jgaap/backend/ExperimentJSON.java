package com.jgaap.backend;

import com.google.gson.Gson;
import com.jgaap.generics.Experiment;
import lombok.Value;

import java.util.Map;

@Value public class ExperimentJSON {
    String name;
    Map<String, String> parameters;
    ModelJSON model;

    public Experiment instanceExperiment() throws Exception {
        Experiment experiment = Experiments.getExperiment(this.getName());
        if (this.parameters != null)
            experiment.setParameters(this.parameters);
        ModelJSON model = this.getModel();
        experiment.setModel(model.instanceModel());
        return experiment;
    }

    static public Experiment readExperiment(String json) throws Exception {
        Gson gson = new Gson();
        ExperimentJSON experimentJSON = gson.fromJson(json, ExperimentJSON.class);
        return experimentJSON.instanceExperiment();
    }
}
