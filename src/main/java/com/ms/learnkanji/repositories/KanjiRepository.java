package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.Kanji;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface KanjiRepository extends Neo4jRepository<Kanji, String>, CustomKanjiRepository {
}
