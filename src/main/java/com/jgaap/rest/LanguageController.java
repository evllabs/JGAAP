package com.jgaap.rest;

import com.jgaap.backend.LanguageJSON;
import com.jgaap.backend.Languages;
import com.jgaap.generics.Language;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class LanguageController {

    @GetMapping("/languages")
    public List<LanguageJSON> languages(){
        List<LanguageJSON> result = new ArrayList<>();
        for (Language distanceDiver : Languages.getLanguages()){
            result.add(LanguageJSON.fromInstance(distanceDiver));
        }
        return result;
    }


    @GetMapping("/languages/{language}")
    public LanguageJSON language(@PathVariable String language){
        try {
            return LanguageJSON.fromInstance(Languages.getLanguage(language));
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, String.format("Language %s not found.", language)
            );
        }
    }

}
