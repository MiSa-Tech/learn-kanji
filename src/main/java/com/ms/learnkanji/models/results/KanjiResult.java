package com.ms.learnkanji.models.results;

import com.ms.learnkanji.models.Kanji;

public class KanjiResult {

    private Kanji kanji;
    private Integer occurrence;

    public KanjiResult() {
    }

    public KanjiResult(Kanji kanji, Integer occurrence) {
        this.kanji = kanji;
        this.occurrence = occurrence;
    }

    public Kanji getKanji() {
        return kanji;
    }

    public void setKanji(Kanji kanji) {
        this.kanji = kanji;
    }

    public Integer getOccurrence() {
        return occurrence;
    }

    public void setOccurrence(Integer occurrence) {
        this.occurrence = occurrence;
    }
}

