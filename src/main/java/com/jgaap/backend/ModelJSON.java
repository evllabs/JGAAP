package com.jgaap.backend;

import com.jgaap.generics.EventDriver;
import com.jgaap.util.Model;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
public class ModelJSON {
    AnalysisDriverJSON analysisDriver;
    List<EventDriverJSON> eventDrivers;

    public Model instanceModel() throws Exception {
        Model model = new Model();
        model.addEventDrivers(this.instanceEventDrivers());
        model.setAnalysisDriver(this.analysisDriver.instanceAnalysisDriver());
        return model;
    }

    public List<EventDriver> instanceEventDrivers() throws Exception {
        List<EventDriver> eventDrivers = new ArrayList<>(this.getEventDrivers().size());
        for (EventDriverJSON eventDriverJSON : this.getEventDrivers()){
            eventDrivers.add(eventDriverJSON.instanceEventDriver());
        }
        return eventDrivers;
    }
}
