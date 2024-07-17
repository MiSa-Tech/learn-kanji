package com.ms.learnkanji.controllers;

import com.ms.learnkanji.models.Kanji;
import com.ms.learnkanji.models.results.KanjiResult;
import com.ms.learnkanji.repositories.KanjiResultRepository;
import com.ms.learnkanji.services.KanjiResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class KanjiResultController {

    private final KanjiResultService kanjiResultService;

    @Autowired
    public KanjiResultController(KanjiResultService kanjiResultService) {
        this.kanjiResultService = kanjiResultService;
    }

    @QueryMapping
    public List<KanjiResult> findBestShouldLearnKanji(@Argument String username) {
        return kanjiResultService.getBestShouldLearnKanji(username);
    }
}
