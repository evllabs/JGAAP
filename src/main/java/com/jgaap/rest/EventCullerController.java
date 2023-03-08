package com.jgaap.rest;

import com.jgaap.backend.EventCullerJSON;
import com.jgaap.backend.EventCullers;
import com.jgaap.generics.EventCuller;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EventCullerController {

    @GetMapping("/event_cullers")
    public List<EventCullerJSON> eventCullers(){
        List<EventCullerJSON> result = new ArrayList<>();
        for (EventCuller eventCuller : EventCullers.getEventCullers()){
            result.add(EventCullerJSON.fromInstance(eventCuller));
        }
        return result;
    }


    @GetMapping("/event_cullters/{eventCuller}")
    public EventCullerJSON eventCuller(@PathVariable String eventCuller){
        try {
            return EventCullerJSON.fromInstance(EventCullers.getEventCuller(eventCuller));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, String.format("EventCuller %s not found.", eventCuller)
            );
        }
    }

}
