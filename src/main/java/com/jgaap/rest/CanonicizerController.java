package com.jgaap.rest;

import com.jgaap.backend.CanonicizerJSON;
import com.jgaap.backend.Canonicizers;
import com.jgaap.generics.Canonicizer;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CanonicizerController {

    @GetMapping("/canonicizers")
    public List<CanonicizerJSON> canonicizers(){
        List<CanonicizerJSON> result = new ArrayList<>();
        for (Canonicizer canonicizer : Canonicizers.getCanonicizers()){
            result.add(CanonicizerJSON.fromInstance(canonicizer));
        }
        return result;
    }


    @GetMapping("/canonicizers/{canonicizer}")
    public CanonicizerJSON canonicizer(@PathVariable String canonicizer){
        try {
            return CanonicizerJSON.fromInstance(Canonicizers.getCanonicizer(canonicizer));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, String.format("Canonicizer %s not found.", canonicizer)
            );
        }
    }

}
