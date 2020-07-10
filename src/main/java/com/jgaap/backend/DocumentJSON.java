package com.jgaap.backend;

import com.jgaap.generics.Language;
import com.jgaap.util.Document;
import lombok.Value;

@Value
public class DocumentJSON {
    String author;
    String title;
    String language;
    String text;
    String path;

    public Document instanceDocument() throws Exception {
        Document document;
        if (this.path != null && !this.path.isEmpty() ){
            document = new Document(this.path, "");
        } else if (this.text != null && !this.text.isEmpty()){
            document = new Document();
            document.setText(this.text);
        } else {
            throw new Exception("No Text Provided!");
        }
        if (this.author != null && !this.author.isEmpty()) {
            document.setAuthor(this.author);
        }
        if (this.title != null && !this.title.isEmpty()) {
            document.setTitle(this.title);
        }
        if (this.language != null && !this.language.isEmpty()) {
            Language language = Languages.getLanguage(this.language);
            document.setLanguage(language);
        }
        return document;
    }
}
