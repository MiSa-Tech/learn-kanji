package com.ms.learnkanji.controllers;

import com.ms.learnkanji.models.Kanji;
import com.ms.learnkanji.models.Vocabulary;
import com.ms.learnkanji.input.pagination.PaginationInput;
import com.ms.learnkanji.services.KanjiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    public Kanji createKanji(@Argument String value, @Argument Integer strokes,
                             @Argument Integer grade, @Argument Integer frequency,
                             @Argument Integer jlpt, @Argument("meaning") List<String> meaning,
                             @Argument("readings_on") List<String> readingsOn, @Argument("readings_kun") List<String> readingsKun) {
        return kanjiService.createKanji(value, strokes, grade, frequency, jlpt, meaning, readingsOn, readingsKun);
    }

    @QueryMapping
    public Kanji findKanjiByValue(@Argument String value) {
        return kanjiService.getKanjiByValue(value);
    }

    @QueryMapping
    public List<Kanji> findKanjiByJlpt(@Argument Integer jlpt,
                                       @Argument PaginationInput paginationInput) {
        return kanjiService.getKanjiByJlpt(jlpt, paginationInput.getPageNum(), paginationInput.getPageSize(), paginationInput.getOrdering());
    }

    @QueryMapping
    public List<Kanji> findKanjiByStrokes(@Argument Integer strokes,
                                          @Argument PaginationInput paginationInput) {
        return kanjiService.getKanjiByStrokes(strokes, paginationInput.getPageNum(), paginationInput.getPageSize(), paginationInput.getOrdering());
    }

    @QueryMapping
    public List<Kanji> findKanjiByGrade(@Argument int grade,
                                        @Argument PaginationInput paginationInput) {
        return kanjiService.getKanjiByGrade(grade, paginationInput.getPageNum(), paginationInput.getPageSize(), paginationInput.getOrdering());
    }

    @SchemaMapping(typeName = "Kanji", field = "readings_on")
    public List<String> listReadingsOn(Kanji kanji) {
        return kanji.getReadingsOn();
    }

    @SchemaMapping(typeName = "Kanji", field = "readings_kun")
    public List<String> listReadingsKun(Kanji kanji) {
        return kanji.getReadingsKun();
    }

    @SchemaMapping(typeName = "Kanji", field = "PART_OF")
    public List<Vocabulary> listPartOf(Kanji kanji) {
        return kanji.getPartOf();
    }
}
