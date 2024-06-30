package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.Kanji;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.Optional;

@GraphQlRepository
public interface KanjiRepository extends Neo4jRepository<Kanji, String> {
    @Query("MATCH (k:Kanji) WHERE k.value = $value RETURN k")
    Optional<Kanji> findByValue(@Param("value") String value);
}
