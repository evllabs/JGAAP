package com.jgaap.rest;

import com.jgaap.backend.DistanceFunctionJSON;
import com.jgaap.backend.DistanceFunctions;
import com.jgaap.generics.DistanceFunction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class DistanceFunctionController {

    @GetMapping("/distance_functions")
    public List<DistanceFunctionJSON> distanceFunctions(){
        List<DistanceFunctionJSON> result = new ArrayList<>();
        for (DistanceFunction distanceDiver : DistanceFunctions.getDistanceFunctions()){
            result.add(DistanceFunctionJSON.fromInstance(distanceDiver));
        }
        return result;
    }


    @GetMapping("/distance_functions/{distanceFunction}")
    public DistanceFunctionJSON distanceFunction(@PathVariable String distanceFunction){
        try {
            return DistanceFunctionJSON.fromInstance(DistanceFunctions.getDistanceFunction(distanceFunction));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, String.format("DistanceFunction %s not found.", distanceFunction)
            );
        }
    }

}
