package com.ms.learnkanji.models;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.util.ArrayList;
import java.util.List;

@Node("Vocabulary")
public class Vocabulary extends BaseEntity {
    @Property("original")
    private String original;
    @Property("furigana")
    private List<String> furigana = new ArrayList<>();
    @Property("meaning")
    private List<String> meaning = new ArrayList<>();
    @Property("jlpt")
    private Integer jlpt;

    public Vocabulary() {
    }

    public Vocabulary(String original,
                      List<String> furigana, List<String> meaning,
                      Integer jlpt) {
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

    public List<String> getFurigana() {
        return furigana;
    }

    public void addFurigana(String furigana) {
        this.furigana.add(furigana);
    }

    public List<String> getMeaning() {
        return meaning;
    }

    public void addMeaning(String meaning) {
        this.meaning.add(meaning);
    }

    public Integer getJlpt() {
        return jlpt;
    }

    public void setJlpt(Integer jlpt) {
        this.jlpt = jlpt;
    }
}
