package com.ms.learnkanji.controllers;

import com.ms.learnkanji.models.results.KanjiResult;
import com.ms.learnkanji.input.pagination.PaginationInput;
import com.ms.learnkanji.services.KanjiResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    //@PostAuthorize("returnObject.username == authentication.principal.username or hasRole('ADMIN')")
    public List<KanjiResult> findBestShouldLearnKanji(@Argument String username,
                                                      @Argument PaginationInput paginationInput) {
        return kanjiResultService.getBestShouldLearnKanji(username, paginationInput.getPageNum(),
                                                          paginationInput.getPageSize(), paginationInput.getOrdering());
    }
}
