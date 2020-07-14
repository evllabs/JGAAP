package com.jgaap.backend;

import com.jgaap.generics.Language;
import lombok.Value;

import java.util.Map;

@Value
public class LanguageJSON {
    String name;
    Map<String, String> parameters;

    public static LanguageJSON fromInstance(Language language) {
        return new LanguageJSON(language.displayName(), language.getParametersMap());
    }

    public Language instance() throws Exception {
        Language language = Languages.getLanguage(this.name);
        if (this.parameters != null)
            language.setParameters(this.parameters);
        return language;
    }
}
