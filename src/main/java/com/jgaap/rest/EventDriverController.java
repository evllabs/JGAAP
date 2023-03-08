package com.jgaap.rest;

import com.jgaap.backend.EventDriverJSON;
import com.jgaap.backend.EventDrivers;
import com.jgaap.generics.EventDriver;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EventDriverController {

    @GetMapping("/event_drivers")
    public List<EventDriverJSON> eventDrivers(){
        List<EventDriverJSON> result = new ArrayList<>();
        for (EventDriver eventDiver : EventDrivers.getEventDrivers()){
            result.add(EventDriverJSON.fromInstance(eventDiver));
        }
        return result;
    }


    @GetMapping("/event_drivers/{eventDriver}")
    public EventDriverJSON eventDriver(@PathVariable String eventDriver){
        try {
            return EventDriverJSON.fromInstance(EventDrivers.getEventDriver(eventDriver));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, String.format("EventDriver %s not found.", eventDriver)
            );
        }
    }

}
