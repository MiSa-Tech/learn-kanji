package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.Kanji;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KanjiRepository extends Neo4jRepository<Kanji, String> {
    @Query("MATCH (k:Kanji {value: $value}) RETURN k")
    Optional<Kanji> findByValue(@Param("value") String value);

    @Query("MATCH (k:Kanji {jlpt: $jlpt}) RETURN k :#{orderBy(#pageable)} SKIP $skip LIMIT $limit")
    Slice<Kanji> findByJlpt(@Param("jlpt") Integer jlpt, Pageable pageable);

    @Query("MATCH (k: Kanji {strokes: $strokes}) RETURN k :#{orderBy(#pageable)} SKIP $skip LIMIT $limit")
    Slice<Kanji> findByStrokes(@Param("strokes") Integer strokes, Pageable pageable);

    @Query("MATCH (k: Kanji {grade: $grade}) RETURN k :#{orderBy(#pageable)} SKIP $skip LIMIT $limit")
    Slice<Kanji> findByGrade(@Param("grade") Integer grade, Pageable pageable);

}
