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

    public static ExperimentJSON fromInstance(Experiment experiment) {
        ModelJSON model = ModelJSON.fromInstance(experiment.getModel());
        return new ExperimentJSON(experiment.displayName(), experiment.getParametersMap(), model);
    }

    static public ExperimentJSON fromJSON(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ExperimentJSON.class);
    }

    public String toJSON() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    @Override
    public String toString() {
        return this.toJSON();
    }
}
