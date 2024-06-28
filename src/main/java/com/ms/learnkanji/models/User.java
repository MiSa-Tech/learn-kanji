package com.ms.learnkanji.models;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("User")
public class User extends BaseEntity {
    @Property("username")
    private String username;
    @Property("jlpt")
    private Integer jlpt;

    public User() {
    }

    public User(String id, String username, Integer jlpt) {
        super(id);
        this.username = username;
        this.jlpt = jlpt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getJlpt() {
        return jlpt;
    }

    public void setJlpt(Integer jlpt) {
        this.jlpt = jlpt;
    }
}
