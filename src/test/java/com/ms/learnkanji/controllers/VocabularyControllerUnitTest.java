package com.ms.learnkanji.controllers;

import com.ms.learnkanji.commons.MessageError;
import com.ms.learnkanji.exceptions.NotFoundException;
import com.ms.learnkanji.models.Vocabulary;
import com.ms.learnkanji.services.VocabularyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.List;

@GraphQlTest(VocabularyController.class)
class VocabularyControllerUnitTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private VocabularyService vocabularyService;

    @Test
    void whenFindByOriginal_thenReturnVocabulary() {
        // given
        List<String> furigana = List.of("まいげつ", "まいつき");
        List<String> meaning = List.of("every month", "monthly");
        Vocabulary vocabulary = new Vocabulary("毎月", furigana, meaning, 5);
        // when
        BDDMockito.given(vocabularyService.getVocabularyByOriginal("毎月")).willReturn(vocabulary);

        // then
        // language=GraphQL
        String document = """
            query {
                findVocabularyByOriginal(original: "毎月") {
                    original
                    jlpt
                }
            }
        """;

        graphQlTester.document(document)
                .execute()
                .path("findVocabularyByOriginal.original").entity(String.class).isEqualTo("毎月")
                .path("findVocabularyByOriginal.jlpt").entity(Integer.class).isEqualTo(5);
    }

    @Test
    void whenFindByOriginal_shouldThrowNotFoundException() {
        // given
        String original = "私";
        // when
        BDDMockito.given(vocabularyService.getVocabularyByOriginal(original)).willThrow(new NotFoundException(MessageError.Vocabulary.VOCABULARY_CANNOT_BE_FOUND));

        // then
        // language=GraphQL
        String document = """
            query {
                findVocabularyByOriginal(original: "私") {
                    original
                    jlpt
                }
            }
        """;

        graphQlTester.document(document)
                .execute()
                .errors()
                .satisfy(errors -> {
                            Assertions.assertEquals(1, errors.size());
                            Assertions.assertEquals(MessageError.Vocabulary.VOCABULARY_CANNOT_BE_FOUND, errors.get(0).getMessage());
                        }
                );
    }

}
