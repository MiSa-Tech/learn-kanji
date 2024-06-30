package com.ms.learnkanji.controllers;

import com.ms.learnkanji.models.Kanji;
import com.ms.learnkanji.services.KanjiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class KanjiController {
    private final KanjiService kanjiService;

    @Autowired
    public KanjiController(KanjiService kanjiService) {
        this.kanjiService = kanjiService;
    }

    @MutationMapping
    public Kanji createKanji(@Argument String value, @Argument Integer stroke,
                             @Argument Integer grade, @Argument Integer frequency,
                             @Argument Integer jlpt, @Argument("meaning") List<String> meaning,
                             @Argument("readings_on") List<String> readingsOn, @Argument("readings_kun") List<String> readingsKun) {
        return kanjiService.createKanji(value, stroke, grade, frequency, jlpt, meaning, readingsOn, readingsKun);
    }
}
