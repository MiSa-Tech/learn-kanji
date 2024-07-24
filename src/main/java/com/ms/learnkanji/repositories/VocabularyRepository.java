package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.Vocabulary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface VocabularyRepository extends Neo4jRepository<Vocabulary, String>, PagingAndSortingRepository<Vocabulary, String> {
    @Query("MATCH (v:Vocabulary {original: $original}) RETURN v ORDER BY v.original ASC")
    Optional<Vocabulary> findByOriginal(@Param("original") String original);

    @Query("MATCH (v:Vocabulary {jlpt: $jlpt}) RETURN v ORDER BY v.original ASC")
    Slice<Vocabulary> findByJlpt(@Param("jlpt") Integer jlpt, Pageable pageable);

    @Query("MATCH (v: Vocabulary) WHERE ANY(furigana in v.furigana WHERE furigana=$furigana) RETURN v ORDER BY v.original ASC")
    Slice<Vocabulary> findByFurigana(@Param("furigana") String furigana, Pageable pageable);

    @Query("MATCH (v: Vocabulary) WHERE ANY(meaning in v.meaning WHERE meaning=$meaning) RETURN v ORDER BY v.original ASC")
    Slice<Vocabulary> findByMeaning(@Param("meaning") String meaning, Pageable pageable);

    @Query("Match (v: Vocabulary) RETURN v ORDER BY v.original ASC")
    Slice<Vocabulary> findAllVocabulary(Pageable pageable);
}
