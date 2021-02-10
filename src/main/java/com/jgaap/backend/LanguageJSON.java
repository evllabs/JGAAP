package com.jgaap.backend;

import com.jgaap.generics.Language;
import lombok.Value;

@Value
public class LanguageJSON {
    String name;
    String descripition;

    public static LanguageJSON fromInstance(Language language) {
        return new LanguageJSON(language.displayName(), language.tooltipText());
    }

    public Language instance() throws Exception {
        Language language = Languages.getLanguage(this.name);
        return language;
    }
}
