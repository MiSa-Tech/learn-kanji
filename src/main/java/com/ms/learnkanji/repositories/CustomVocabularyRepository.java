package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.Vocabulary;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface CustomVocabularyRepository {
    Optional<Vocabulary> findByOriginal(String original);
}
