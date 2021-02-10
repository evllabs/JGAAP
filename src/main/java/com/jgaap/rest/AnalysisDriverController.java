package com.jgaap.rest;

import com.jgaap.backend.AnalysisDriverJSON;
import com.jgaap.backend.AnalysisDrivers;
import com.jgaap.generics.AnalysisDriver;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AnalysisDriverController {

    @GetMapping("/analysis_drivers")
    public List<AnalysisDriverJSON> canonicizers(){
        List<AnalysisDriverJSON> result = new ArrayList<>();
        for (AnalysisDriver analysisDiver : AnalysisDrivers.getAnalysisDrivers()){
            result.add(AnalysisDriverJSON.fromInstance(analysisDiver));
        }
        return result;
    }


    @GetMapping("/analysis_drivers/{analysisDriver}")
    public AnalysisDriverJSON canonicizer(@PathVariable String analysisDriver){
        try {
            return AnalysisDriverJSON.fromInstance(AnalysisDrivers.getAnalysisDriver(analysisDriver));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, String.format("AnalysisDriver %s not found.", analysisDriver)
            );
        }
    }

}
