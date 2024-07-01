package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.Kanji;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ICustomKanjiRepository {
    //@Query("MATCH (k:Kanji) WHERE k.value = $value RETURN k")
    Optional<Kanji> findByValue(@Param("value") String value);
}
