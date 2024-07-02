package com.ms.learnkanji.models;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;

@Node("User")
public class User extends BaseEntity {
    @Property("username")
    private String username;
    @Property("password")
    private String password;
    @Property("role")
    private Role role;
    @Property("jlpt")
    private Integer jlpt;
    @Relationship(type = "LEARNT_KANJI", direction = Relationship.Direction.OUTGOING)
    private List<Kanji> kanjis = new ArrayList<>();
    @Relationship(type = "LEARNT_VOCABULARY", direction = Relationship.Direction.OUTGOING)
    private List<Vocabulary> vocabularies = new ArrayList<>();

    public User() {
    }

    public User(String username, Role role, Integer jlpt) {
        this.username = username;
        this.role = role;
        this.jlpt = jlpt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role roles) {
        this.role = role;
    }

    public Integer getJlpt() {
        return jlpt;
    }

    public void setJlpt(Integer jlpt) {
        this.jlpt = jlpt;
    }

    public List<Kanji> getKanjis() {
        return kanjis;
    }

    public void setKanjis(List<Kanji> kanjis) {
        this.kanjis = kanjis;
    }

    public void addKanji(Kanji kanji) {
        this.kanjis.add(kanji);
    }

    public List<Vocabulary> getVocabularies() {
        return vocabularies;
    }

    public void setVocabularies(List<Vocabulary> vocabularies) {
        this.vocabularies = vocabularies;
    }

    public void addVocabulary(Vocabulary vocabulary) {
        this.vocabularies.add(vocabulary);
    }
}
