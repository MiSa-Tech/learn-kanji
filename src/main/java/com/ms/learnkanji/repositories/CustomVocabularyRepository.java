package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.Vocabulary;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface CustomVocabularyRepository {
    @Query("MATCH (v:Vocabulary {original: $original}) RETURN v")
    Optional<Vocabulary> findByOriginal(@Param("original") String original);

    @Query("MATCH (v:Vocabulary {jlpt: $jlpt}) RETURN v ORDER BY v.original ASC")
    List<Vocabulary> findByJlpt(@Param("jlpt") Integer jlpt, Integer pageNum, Integer pageSize);

    @Query("MATCH (v: Vocabulary) WHERE ANY(furigana in v.furigana WHERE furigana=$furigana) RETURN v")
    List<Vocabulary> findByFurigana(@Param("furigana") String furigana, Integer pageNum, Integer pageSize);

    @Query("MATCH (v: Vocabulary) WHERE ANY(meaning in v.meaning WHERE meaning=$meaning) RETURN v")
    List<Vocabulary> findByMeaning(@Param("meaning") String meaning, Integer pageNum, Integer pageSize);
}
