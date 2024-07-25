package com.ms.learnkanji.services;

import com.ms.learnkanji.commons.Ordering;
import com.ms.learnkanji.models.Kanji;

import java.util.List;

public interface KanjiService {

    Kanji createKanji(String value, Integer strokes,
                      Integer grade, Integer frequency,
                      Integer jlpt, List<String> meaning,
                      List<String> readingsOn, List<String> readingsKun);
    Kanji getKanjiByValue(String value);
    List<Kanji> getKanjiByJlpt(Integer jlpt, Integer pageNum, Integer pageSize, Ordering ordering);
    List<Kanji> getKanjiByStrokes(Integer strokes, Integer pageNum, Integer pageSize, Ordering ordering);
    List<Kanji> getKanjiByGrade(Integer grade, Integer pageNum, Integer pageSize, Ordering ordering);

}
