package com.ms.learnkanji.services;

import com.ms.learnkanji.commons.Ordering;
import com.ms.learnkanji.models.Vocabulary;

import java.util.List;

public interface VocabularyService {
    Vocabulary createVocabulary(String original, List<String> furigana,
                                List<String> meaning, Integer jlpt);

    Vocabulary getVocabularyByOriginal(String original);

    List<Vocabulary> getVocabularyByJlpt(Integer jlpt, Integer pageNum, Integer pageSize, Ordering ordering);

    List<Vocabulary> getVocabularyByFurigana(String furigana, Integer pageNum, Integer pageSize, Ordering ordering);

    List<Vocabulary> getVocabularyByMeaning(String meaning, Integer pageNum, Integer pageSize, Ordering ordering);

    List<Vocabulary> getAllVocabulary(Integer pageNum, Integer pageSize, Ordering ordering);
}
