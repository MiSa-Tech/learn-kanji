package com.ms.learnkanji.models;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
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
    private List<String> meaning = new ArrayList<>();
    @Property("readings_on")
    private List<String> readingsOn = new ArrayList<>();
    @Property("readings_kun")
    private List<String> readingsKun = new ArrayList<>();
    @Relationship(type = "PART_OF", direction = Relationship.Direction.OUTGOING)
    private List<Vocabulary> partOf = new ArrayList<>();

    public Kanji() {
    }

    public Kanji(String value,
                 Integer stroke, Integer grade,
                 Integer frequency, Integer jlpt,
                 List<String> meaning, List<String> readingsOn,
                 List<String> readingsKun) {
        this.value = value;
        this.stroke = stroke;
        this.grade = grade;
        this.frequency = frequency;
        this.jlpt = jlpt;
        this.meaning = meaning;
        this.readingsOn = readingsOn;
        this.readingsKun = readingsKun;
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

    public List<String> getMeaning() {
        return meaning;
    }

    public void addMeaning(String meaning) {
        this.meaning.add(meaning);
    }

    public List<Vocabulary> getPartOf() {
        return partOf;
    }

    public void setPartOf(List<Vocabulary> partOf) {
        this.partOf = partOf;
    }

    public void addPartOf(Vocabulary vocabulary) {
        this.partOf.add(vocabulary);
    }

    public List<String> getReadingsOn() {
        return readingsOn;
    }

    public void addReadingsOn(String readingsOn) {
        this.readingsOn.add(readingsOn);
    }

    public List<String> getReadingsKun() {
        return readingsKun;
    }

    public void addReadingsKun(String readingsKun) {
        this.readingsKun.add(readingsKun);
    }
}
