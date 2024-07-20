package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.Vocabulary;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface VocabularyRepository extends Neo4jRepository<Vocabulary, String>, CustomVocabularyRepository {
}
