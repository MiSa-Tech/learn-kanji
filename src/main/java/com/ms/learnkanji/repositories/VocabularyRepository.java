package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.Vocabulary;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.graphql.data.GraphQlRepository;

@GraphQlRepository
public interface VocabularyRepository extends Neo4jRepository<Vocabulary, String>, CustomVocabularyRepository {
}
