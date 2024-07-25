package com.ms.learnkanji.commons;

public enum Ordering {
    ASC("ASC"),
    DESC("DESC");

    private final String name;

    Ordering(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
