package com.ms.learnkanji.models;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("Vocabulary")
public class Vocabulary extends BaseEntity {
    @Property("original")
    private String original;
    @Property("furigana")
    private String furigana;
    @Property("meaning")
    private String meaning;
    @Property("jlpt")
    private Integer jlpt;

    public Vocabulary() {
    }

    public Vocabulary(String id, String original, String furigana, String meaning, Integer jlpt) {
        super(id);
        this.original = original;
        this.furigana = furigana;
        this.meaning = meaning;
        this.jlpt = jlpt;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getFurigana() {
        return furigana;
    }

    public void setFurigana(String furigana) {
        this.furigana = furigana;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public Integer getJlpt() {
        return jlpt;
    }

    public void setJlpt(Integer jlpt) {
        this.jlpt = jlpt;
    }
}
