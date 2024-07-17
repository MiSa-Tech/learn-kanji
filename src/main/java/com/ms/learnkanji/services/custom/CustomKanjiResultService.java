package com.ms.learnkanji.services.custom;

import com.ms.learnkanji.models.Kanji;
import com.ms.learnkanji.models.results.KanjiResult;
import com.ms.learnkanji.repositories.KanjiResultRepository;
import com.ms.learnkanji.services.KanjiResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomKanjiResultService implements KanjiResultService {
    private final KanjiResultRepository kanjiResultRepository;

    @Autowired
    public CustomKanjiResultService(KanjiResultRepository kanjiResultRepository) {
        this.kanjiResultRepository = kanjiResultRepository;
    }


    @Override
    public List<KanjiResult> getBestShouldLearnKanji(String username) {
        List<KanjiResult> kanjiResultList = kanjiResultRepository.findBestShouldLearnKanji(username);
        return kanjiResultList;
    }
}
