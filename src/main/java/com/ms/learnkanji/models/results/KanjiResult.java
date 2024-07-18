package com.ms.learnkanji.models.results;

import com.ms.learnkanji.commons.Tuple;
import com.ms.learnkanji.models.Kanji;

public class KanjiResult implements Tuple<Kanji, Integer> {

    private Kanji kanji;
    private Integer occurrence;

    public KanjiResult() {
    }

    public KanjiResult(Kanji kanji, Integer occurrence) {
        this.kanji = kanji;
        this.occurrence = occurrence;
    }


    @Override
    public Kanji getFirst() {
        return kanji;
    }

    @Override
    public Integer getSecond() {
        return occurrence;
    }

    @Override
    public void setFirst(Kanji kanji) {
        this.kanji = kanji;
    }

    @Override
    public void setSecond(Integer occurrence) {
        this.occurrence = occurrence;
    }

    @Override
    public String toString() {
        return "KanjiResult{" +
                "kanji=" + kanji +
                ", occurrence=" + occurrence +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KanjiResult kanjiResult = (KanjiResult) o;

        if (!kanji.equals(kanjiResult.kanji)) return false;
        return occurrence.equals(kanjiResult.occurrence);
    }

    @Override
    public int hashCode() {
        int result = kanji.hashCode();
        result = 31 * result + occurrence.hashCode();
        return result;
    }
}

