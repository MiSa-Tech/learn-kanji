package com.ms.learnkanji.services;

import com.ms.learnkanji.commons.Ordering;
import com.ms.learnkanji.models.results.KanjiResult;

import java.util.List;

public interface KanjiResultService {
    List<KanjiResult> getBestShouldLearnKanji(String username, Integer pageNum, Integer pageSize, Ordering ordering);
}
