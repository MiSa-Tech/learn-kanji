package com.ms.learnkanji.controllers;

import com.ms.learnkanji.models.Vocabulary;
import com.ms.learnkanji.services.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
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
                                                 @Argument Integer pageNum,
                                                 @Argument Integer pageSize) {
        return vocabularyService.getVocabularyByJlpt(jlpt, pageNum, pageSize);
    }

    @QueryMapping
    public List<Vocabulary> findVocabularyByFurigana(@Argument String furigana,
                                                     @Argument Integer pageNum,
                                                     @Argument Integer pageSize) {
        return vocabularyService.getVocabularyByFurigana(furigana, pageNum, pageSize);
    }

    @QueryMapping
    public List<Vocabulary> findVocabularyByMeaning(@Argument String meaning,
                                                     @Argument Integer pageNum,
                                                     @Argument Integer pageSize) {
        return vocabularyService.getVocabularyByMeaning(meaning, pageNum, pageSize);
    }

    @QueryMapping
    public List<Vocabulary> findAllVocabulary(@Argument Integer pageNum,
                                              @Argument Integer pageSize) {
        return vocabularyService.getAllVocabulary(pageNum, pageSize);
    }
}
