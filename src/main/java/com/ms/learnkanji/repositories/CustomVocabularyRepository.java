package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.Vocabulary;

import java.util.Optional;

public interface CustomVocabularyRepository {

    Optional<Vocabulary> findByOriginal(String original);
}
