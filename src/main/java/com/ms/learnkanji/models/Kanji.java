package com.ms.learnkanji.models;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("Kanji")
public class Kanji extends BaseEntity {
    @Property("value")
    private String value;
    @Property("stroke")
    private Integer stroke;
    @Property("grade")
    private Integer grade;
    @Property("frequency")
    private Integer frequency;
    @Property("jlpt")
    private Integer jlpt;
    @Property("meaning")
    private String meaning;
    @Relationship("part_of")
    private List<Vocabulary> partOf;

    public Kanji() {
    }

    public Kanji(String id, String value, Integer stroke, Integer grade, Integer frequency, Integer jlpt, String meaning) {
        super(id);
        this.value = value;
        this.stroke = stroke;
        this.grade = grade;
        this.frequency = frequency;
        this.jlpt = jlpt;
        this.meaning = meaning;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getStroke() {
        return stroke;
    }

    public void setStroke(Integer stroke) {
        this.stroke = stroke;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Integer getJlpt() {
        return jlpt;
    }

    public void setJlpt(Integer jlpt) {
        this.jlpt = jlpt;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public List<Vocabulary> getPartOf() {
        return partOf;
    }

    public void setPartOf(List<Vocabulary> partOf) {
        this.partOf = partOf;
    }
}
