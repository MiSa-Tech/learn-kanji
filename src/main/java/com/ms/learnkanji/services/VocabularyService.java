package com.ms.learnkanji.services;

import com.ms.learnkanji.models.Vocabulary;

import java.util.List;

public interface VocabularyService {
    Vocabulary createVocabulary(String original, List<String> furigana,
                                List<String> meaning, Integer jlpt);

    Vocabulary getVocabularyByOriginal(String original);
}
