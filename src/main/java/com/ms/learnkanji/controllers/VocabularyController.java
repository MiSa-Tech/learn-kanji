package com.ms.learnkanji.controllers;

import com.ms.learnkanji.input.pagination.PaginationInput;
import com.ms.learnkanji.models.Vocabulary;
import com.ms.learnkanji.services.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class VocabularyController {
    private final VocabularyService vocabularyService;

    @Autowired
    public VocabularyController(VocabularyService vocabularyService) {
        this.vocabularyService = vocabularyService;
    }

    @MutationMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Vocabulary createVocabulary(@Argument String original,
                                       @Argument List<String> furigana,
                                       @Argument List<String> meaning,
                                       @Argument Integer jlpt) {
        return vocabularyService.createVocabulary(original, furigana, meaning, jlpt);
    }

    @QueryMapping
    public Vocabulary findVocabularyByOriginal(@Argument String original) {
        return vocabularyService.getVocabularyByOriginal(original);
    }

    @QueryMapping
    public List<Vocabulary> findVocabularyByJlpt(@Argument Integer jlpt,
                                                 @Argument PaginationInput paginationInput) {
        return vocabularyService.getVocabularyByJlpt(jlpt, paginationInput.getPageNum(), paginationInput.getPageSize(), paginationInput.getOrdering());
    }

    @QueryMapping
    public List<Vocabulary> findVocabularyByFurigana(@Argument String furigana,
                                                     @Argument PaginationInput paginationInput) {
        return vocabularyService.getVocabularyByFurigana(furigana, paginationInput.getPageNum(), paginationInput.getPageSize(), paginationInput.getOrdering());
    }

    @QueryMapping
    public List<Vocabulary> findVocabularyByMeaning(@Argument String meaning,
                                                    @Argument PaginationInput paginationInput) {
        return vocabularyService.getVocabularyByMeaning(meaning, paginationInput.getPageNum(), paginationInput.getPageSize(), paginationInput.getOrdering());
    }

    @QueryMapping
    public List<Vocabulary> findAllVocabulary(@Argument PaginationInput paginationInput) {
        return vocabularyService.getAllVocabulary(paginationInput.getPageNum(), paginationInput.getPageSize(), paginationInput.getOrdering());
    }
}
