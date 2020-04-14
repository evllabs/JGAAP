package com.jgaap.backend;

import com.jgaap.generics.EventCuller;
import lombok.Value;

import java.util.Map;

@Value public class EventCullerJSON {
    String name;
    Map<String, String> parameters;

    public EventCuller instanceEventCuller() throws Exception {
        EventCuller eventCuller = EventCullers.getEventCuller(this.name);
        if (this.parameters != null)
            eventCuller.setParameters(this.parameters);
        return eventCuller;
    }
}
