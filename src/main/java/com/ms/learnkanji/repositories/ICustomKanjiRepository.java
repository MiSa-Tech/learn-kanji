package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.Kanji;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ICustomKanjiRepository {
    //@Query("MATCH (k:Kanji) WHERE k.value = $value RETURN k")
    Optional<Kanji> findByValue(@Param("value") String value);

    List<Kanji> findByJlpt(@Param("jlpt") int jlpt);
}
