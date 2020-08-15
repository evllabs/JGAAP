package com.jgaap.backend;

import com.jgaap.generics.Language;
import lombok.Value;

import java.util.Map;

@Value
public class LanguageJSON {
    String name;

    public static LanguageJSON fromInstance(Language language) {
        return new LanguageJSON(language.displayName());
    }

    public Language instance() throws Exception {
        Language language = Languages.getLanguage(this.name);
        return language;
    }
}
