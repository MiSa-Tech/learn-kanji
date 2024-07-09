package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.Kanji;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface CustomKanjiRepository {
    Optional<Kanji> findByValue(String value);

    List<Kanji> findByJlpt(Integer jlpt, Integer pageNum, Integer pageSize);
}
