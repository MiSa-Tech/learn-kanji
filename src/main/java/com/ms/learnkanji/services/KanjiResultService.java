package com.ms.learnkanji.services;

import com.ms.learnkanji.models.Kanji;
import com.ms.learnkanji.models.results.KanjiResult;

import java.util.List;

public interface KanjiResultService {
    List<KanjiResult> getBestShouldLearnKanji(String username);
}
