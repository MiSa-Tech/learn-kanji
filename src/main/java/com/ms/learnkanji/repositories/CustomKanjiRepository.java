package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.Kanji;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface CustomKanjiRepository {
    Optional<Kanji> findByValue(@Param("value") String value);

    List<Kanji> findByJlpt(@Param("jlpt") int jlpt);
}
