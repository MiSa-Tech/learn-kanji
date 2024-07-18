package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.results.KanjiResult;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface KanjiResultRepository {
    List<KanjiResult> findBestShouldLearnKanji(String username, Integer pageNum, Integer pageSize);
}
