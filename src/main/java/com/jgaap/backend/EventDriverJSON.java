package com.jgaap.backend;

import com.jgaap.generics.Canonicizer;
import com.jgaap.generics.EventCuller;
import com.jgaap.generics.EventDriver;
import lombok.Value;

import java.util.*;

@Value
public class EventDriverJSON {
    String name;
    List<CanonicizerJSON> canonicizers;
    List<EventCullerJSON> eventCullers;
    Map<String, String> parameters;
    String descripition;

    public static EventDriverJSON fromInstance(EventDriver eventDriver) {
        List<CanonicizerJSON> canonicizers = new ArrayList<>();
        for (Canonicizer canonicizer : eventDriver.getCanonicizers()) {
            canonicizers.add(CanonicizerJSON.fromInstance(canonicizer));
        }
        List<EventCullerJSON> eventCullers = new ArrayList<>();
        for (EventCuller eventCuller : eventDriver.getEventCullers()) {
            eventCullers.add(EventCullerJSON.fromInstance(eventCuller));
        }
        return new EventDriverJSON(
                eventDriver.displayName(), canonicizers, eventCullers, eventDriver.getParametersMap(),
                eventDriver.tooltipText());
    }

    public EventDriver instanceEventDriver() throws Exception {
        EventDriver eventDriver = EventDrivers.getEventDriver(this.name);
        if (this.parameters != null)
            eventDriver.setParameters(this.parameters);
        eventDriver.addCanonicizers(this.instanceCanonicizers());
        eventDriver.addCullers(this.instanceEventCullers());
        return eventDriver;
    }

    private List<Canonicizer> instanceCanonicizers() throws Exception {
        if (this.canonicizers == null) {
            return Collections.emptyList();
        }
        List<Canonicizer> canonicizers = new ArrayList<>(this.canonicizers.size());
        for (CanonicizerJSON canonicizer : this.canonicizers){
            canonicizers.add(canonicizer.instanceCanonicizer());
        }
        return canonicizers;
    }

    private List<EventCuller> instanceEventCullers() throws Exception {
        if (this.eventCullers == null) {
            return Collections.emptyList();
        }
        List<EventCuller> eventCullers = new ArrayList<>(this.eventCullers.size());
        for (EventCullerJSON eventCuller : this.eventCullers){
            eventCullers.add(eventCuller.instanceEventCuller());
        }
        return eventCullers;
    }
}
