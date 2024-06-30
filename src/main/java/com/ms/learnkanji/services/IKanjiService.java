package com.ms.learnkanji.services;

import com.ms.learnkanji.models.Kanji;

import java.util.List;

public interface IKanjiService {
    Kanji createKanji(String value, Integer stroke,
                      Integer grade, Integer frequency,
                      Integer jlpt, List<String> meaning,
                      List<String> readingsOn, List<String> readingsKun);
}
