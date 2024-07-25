package com.ms.learnkanji.controllers;

import com.ms.learnkanji.commons.MessageError;
import com.ms.learnkanji.commons.Ordering;
import com.ms.learnkanji.exceptions.AlreadyPresentException;
import com.ms.learnkanji.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import com.ms.learnkanji.models.Kanji;
import com.ms.learnkanji.services.KanjiService;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.List;

@GraphQlTest(KanjiController.class)
class KanjiControllerUnitTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private KanjiService kanjiService;

    @Test
    void whenFindByValue_thenReturnKanji() {
        // given
        List<String> meaning = List.of("one");
        Kanji kanji = new Kanji("一", null, null, null,
                5, meaning, null, null);
        // when
        BDDMockito.given(kanjiService.getKanjiByValue("一")).willReturn(kanji);

        // then
        // language=GraphQL
        String document = """
            query {
                findKanjiByValue(value: "一") {
                    value
                    meaning
                }
            }
        """;

        graphQlTester.document(document)
                .execute()
                .path("findKanjiByValue.value").entity(String.class).isEqualTo("一")
                .path("findKanjiByValue.meaning[0]").entity(String.class).isEqualTo("one");
    }

    @Test
    void whenFindByValue_shouldThrowNotFoundException() {
        // given
        String value = "一";
        // when
        BDDMockito.given(kanjiService.getKanjiByValue(value)).willThrow(new NotFoundException(MessageError.Kanji.KANJI_NOT_FOUND));

        // then
        // language=GraphQL
        String document = """
            query {
                findKanjiByValue(value: "一") {
                    value
                    meaning
                }
            }
        """;

        graphQlTester.document(document)
                .execute()
                .errors()
                .satisfy(errors -> {
                            Assertions.assertEquals(1, errors.size());
                            Assertions.assertEquals(MessageError.Kanji.KANJI_NOT_FOUND, errors.get(0).getMessage());
                        }
                );
    }

    @Test
    void whenFindByJlpt_thenReturnListKanji() {
        // given
        List<String> meaning = List.of("one");
        Kanji kanji = new Kanji("一", null, null, null,
                5, meaning, null, null);
        int jlpt = 5;
        int pageNum = 0;
        int pageSize = 10;
        // when
        BDDMockito.given(kanjiService.getKanjiByJlpt(jlpt, pageNum, pageSize, Ordering.ASC)).willReturn(List.of(kanji));

        // then
        // language=GraphQL
        String document = """
            query {
                findKanjiByJlpt(jlpt: 5) {
                    value
                }
            }
        """;

        graphQlTester.document(document)
                .execute()
                .path("findKanjiByJlpt")
                .entityList(Kanji.class)
                .hasSize(1);
    }

    @Test
    void shouldCreateKanji() {
        // given
        List<String> meaning = List.of("one");
        String value = "一";
        int jlpt = 5;
        // when
        BDDMockito.given(kanjiService.createKanji(value, null, null, null, jlpt, meaning, null, null))
                .willReturn(new Kanji(value, null, null, null, jlpt, meaning, null, null));

        // then
        // language=GraphQL
        String document = """
            mutation {
                createKanji(value: "一", jlpt: 5, meaning: ["one"]) {
                    value
                    meaning
                }
            }
        """;

        graphQlTester.document(document)
                .execute()
                .path("createKanji.value").entity(String.class).isEqualTo("一")
                .path("createKanji.meaning[0]").entity(String.class).isEqualTo("one");
    }

    @Test
    void shouldNotCreateKanji_DuplicateValue() {
        // given
        List<String> meaning = List.of("one");
        String value = "一";
        int jlpt = 5;
        // when
        BDDMockito.given(kanjiService.createKanji(value, null, null, null, jlpt, meaning, null, null))
                .willThrow(new AlreadyPresentException(MessageError.Kanji.KANJI_ALREADY_PRESENT));

        // then
        // language=GraphQL
        String document = """
            mutation {
                createKanji(value: "一", jlpt: 5, meaning: ["one"]) {
                    value
                    meaning
                }
            }
        """;

        graphQlTester.document(document)
                .execute()
                .errors()
                .satisfy(errors -> {
                            Assertions.assertEquals(1, errors.size());
                            Assertions.assertEquals(MessageError.Kanji.KANJI_ALREADY_PRESENT, errors.get(0).getMessage());
                        }
                );
    }
}