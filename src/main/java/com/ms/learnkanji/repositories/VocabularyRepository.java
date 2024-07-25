package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.Vocabulary;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VocabularyRepository extends Neo4jRepository<Vocabulary, String> {

    @Query("MATCH (v:Vocabulary {original: $original}) RETURN v")
    Optional<Vocabulary> findByOriginal(@Param("original") String original);

    @Query("MATCH (v:Vocabulary {jlpt: $jlpt}) RETURN v :#{orderBy(#pageable)} SKIP $skip LIMIT $limit")
    Slice<Vocabulary> findByJlpt(@Param("jlpt") Integer jlpt, Pageable pageable);

    @Query("MATCH (v: Vocabulary) WHERE ANY(furigana in v.furigana WHERE furigana CONTAINS $furigana) RETURN v :#{orderBy(#pageable)} SKIP $skip LIMIT $limit")
    Slice<Vocabulary> findByFurigana(@Param("furigana") String furigana, Pageable pageable);

    @Query("MATCH (v: Vocabulary) WHERE ANY(meaning in v.meaning WHERE meaning CONTAINS $meaning) RETURN v :#{orderBy(#pageable)} SKIP $skip LIMIT $limit")
    Slice<Vocabulary> findByMeaning(@Param("meaning") String meaning, Pageable pageable);

    @Query("Match (v: Vocabulary) RETURN v :#{orderBy(#pageable)} SKIP $skip LIMIT $limit")
    Slice<Vocabulary> findAllVocabulary(Pageable pageable);

}
