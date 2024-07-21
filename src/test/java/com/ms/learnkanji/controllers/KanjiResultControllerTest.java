package com.ms.learnkanji.controllers;

import com.ms.learnkanji.commons.MessageError;
import com.ms.learnkanji.exceptions.NotFoundException;
import com.ms.learnkanji.models.Kanji;
import com.ms.learnkanji.models.results.KanjiResult;
import com.ms.learnkanji.services.KanjiResultService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.List;

@GraphQlTest(KanjiResultController.class)
class KanjiResultControllerTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private KanjiResultService kanjiResultService;

    @Test
    void whenFindByUsername_thenReturnKanjiResult() {
        // given
        Kanji kanji1 = new Kanji("一", null, null, null,
                5, List.of("one"), null, null);
        Kanji kanji2 = new Kanji("二", null, null, null,
                5, List.of("two"), null, null);
        KanjiResult kanjiResult1 = new KanjiResult(kanji1, 1);
        KanjiResult kanjiResult2 = new KanjiResult(kanji2, 2);
        List<KanjiResult> kanjiResults = List.of(kanjiResult1, kanjiResult2);
        // when
        BDDMockito.given(kanjiResultService.getBestShouldLearnKanji("test", 0, 10)).willReturn(kanjiResults);
        // then
        // language=GraphQL
        String document = """
            query {
                findBestShouldLearnKanji(username: "test", pageNum: 0, pageSize: 10) {
                    kanji {
                        value
                    }
                    occurrence
                }
            }
        """;
        graphQlTester.document(document)
                .execute()
                .path("findBestShouldLearnKanji[0].kanji.value").entity(String.class).isEqualTo("一")
                .path("findBestShouldLearnKanji[0].occurrence").entity(Integer.class).isEqualTo(1)
                .path("findBestShouldLearnKanji[1].kanji.value").entity(String.class).isEqualTo("二")
                .path("findBestShouldLearnKanji[1].occurrence").entity(Integer.class).isEqualTo(2);
    }

    @Test
    void whenFindByUsername_shouldThrowNotFoundException() {
        // given
        String username = "test";
        // when
        BDDMockito.given(kanjiResultService.getBestShouldLearnKanji(username, 0, 10)).willThrow(new NotFoundException(MessageError.User.USER_NOT_FOUND));
        // then
        // language=GraphQL
        String document = """
            query {
                findBestShouldLearnKanji(username: "test", pageNum: 0, pageSize: 10) {
                    kanji {
                        value
                    }
                    occurrence
                }
            }
        """;
        graphQlTester.document(document)
                .execute()
                .errors()
                .satisfy(errors -> {
                    Assertions.assertEquals(1, errors.size());
                    Assertions.assertEquals(MessageError.User.USER_NOT_FOUND, errors.get(0).getMessage());
                });
    }
}
