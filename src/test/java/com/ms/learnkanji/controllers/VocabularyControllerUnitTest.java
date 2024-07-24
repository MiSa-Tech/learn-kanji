package com.ms.learnkanji.controllers;

import com.ms.learnkanji.commons.MessageError;
import com.ms.learnkanji.exceptions.InvalidInputException;
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

    @Test
    void whenFindByJlpt_thenReturnListVocabulary() {
        // given
        List<String> furigana_1 = List.of("まいげつ", "まいつき");
        List<String> meaning_1 = List.of("every month", "monthly");
        Vocabulary vocabulary_1 = new Vocabulary("毎月", furigana_1, meaning_1, 5);

        List<String> furigana_2 = List.of("まいにち");
        List<String> meaning_2 = List.of("every day");
        Vocabulary vocabulary_2 = new Vocabulary("毎日", furigana_2, meaning_2, 5);
        // when
        BDDMockito.given(vocabularyService.getVocabularyByJlpt(5, 0, 10))
                .willReturn(List.of(vocabulary_1, vocabulary_2));

        // then
        // language=GraphQL
        String document = """
            query {
                findVocabularyByJlpt(jlpt: 5, pageNum: 0, pageSize: 10) {
                    original
                    jlpt
                }
            }
        """;

        graphQlTester.document(document)
                .execute()
                .path("findVocabularyByJlpt[0].original").entity(String.class).isEqualTo("毎月")
                .path("findVocabularyByJlpt[1].original").entity(String.class).isEqualTo("毎日")
                .path("findVocabularyByJlpt[0].jlpt").entity(Integer.class).isEqualTo(5)
                .path("findVocabularyByJlpt[1].jlpt").entity(Integer.class).isEqualTo(5)
                .path("findVocabularyByJlpt").entityList(Vocabulary.class).hasSize(2);
    }

    @Test
    void whenFindByJlpt_shouldThrowNotFoundException() {
        // given
        Integer jlpt = 6;
        Integer pageNum = 0;
        Integer pageSize = 10;
        // when
        BDDMockito.given(vocabularyService.getVocabularyByJlpt(jlpt, pageNum, pageSize)).willThrow(
                new NotFoundException(MessageError.Vocabulary.JLPT_LEVEL_CANNOT_BE_LESS_THAN_ONE_OR_GREATER_THAN_FIVE));

        // then
        // language=GraphQL
        String document = """
            query {
                findVocabularyByJlpt(jlpt: 6, pageNum: 0, pageSize: 10) {
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
                            Assertions.assertEquals(MessageError.Vocabulary.JLPT_LEVEL_CANNOT_BE_LESS_THAN_ONE_OR_GREATER_THAN_FIVE,
                                    errors.get(0).getMessage());
                        }
                );
    }

    @Test
    void whenFindByJlpt_shouldThrowInvalidInputException() {
        // given
        Integer jlpt = 1;
        Integer pageNum = 0;
        Integer pageSize = 10;
        // when
        BDDMockito.given(vocabularyService.getVocabularyByJlpt(jlpt, pageNum, pageSize)).willThrow(
                new InvalidInputException(MessageError.Vocabulary.JLPT_LEVEL_CANNOT_BE_LESS_THAN_ONE_OR_GREATER_THAN_FIVE));

        // then
        // language=GraphQL
        String document = """
            query {
                findVocabularyByJlpt(jlpt: 1, pageNum: 0, pageSize: 10) {
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
                            Assertions.assertEquals(MessageError.Vocabulary.JLPT_LEVEL_CANNOT_BE_LESS_THAN_ONE_OR_GREATER_THAN_FIVE,
                                    errors.get(0).getMessage());
                        }
                );
    }

    @Test
    void whenFindByFurigana_thenReturnListVocabulary() {
        // given
        List<String> furigana_1 = List.of("まいげつ", "まいつき");
        List<String> meaning_1 = List.of("every month", "monthly");
        Vocabulary vocabulary_1 = new Vocabulary("毎月", furigana_1, meaning_1, 5);

        // when
        BDDMockito.given(vocabularyService.getVocabularyByFurigana("まいげつ", 0, 10))
                .willReturn(List.of(vocabulary_1));

        // then
        // language=GraphQL
        String document = """
            query {
                findVocabularyByFurigana(furigana: "まいげつ", pageNum: 0, pageSize: 10) {
                    original
                    furigana
                }
            }
        """;

        graphQlTester.document(document)
                .execute()
                .path("findVocabularyByFurigana[0].original").entity(String.class).isEqualTo("毎月")
                .path("findVocabularyByFurigana[0].furigana").entityList(String.class).isEqualTo(furigana_1)
                .path("findVocabularyByFurigana").entityList(Vocabulary.class).hasSize(1);
    }

    @Test
    void whenFindByFurigana_shouldThrowNotFoundException() {
        // given
        String furigana = "ん"; // non-matching furigana
        Integer pageNum = 0;
        Integer pageSize = 10;
        // when
        BDDMockito.given(vocabularyService.getVocabularyByFurigana(furigana, pageNum, pageSize))
                .willThrow(new NotFoundException(MessageError.Vocabulary.VOCABULARY_CANNOT_BE_FOUND));

        // then
        // language=GraphQL
        String document = """
            query {
                findVocabularyByFurigana(furigana: "ん", pageNum: 0, pageSize: 10) {
                    original
                    furigana
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

    @Test
    void whenFindByMeaning_thenReturnListVocabulary() {
        // given
        List<String> furigana_1 = List.of("まいげつ", "まいつき");
        List<String> meaning_1 = List.of("every month", "monthly");
        Vocabulary vocabulary_1 = new Vocabulary("毎月", furigana_1, meaning_1, 5);

        // when
        BDDMockito.given(vocabularyService.getVocabularyByMeaning("monthly", 0, 10))
                .willReturn(List.of(vocabulary_1));

        // then
        // language=GraphQL
        String document = """
            query {
                findVocabularyByMeaning(meaning: "monthly", pageNum: 0, pageSize: 10) {
                    original
                    meaning
                }
            }
        """;

        graphQlTester.document(document)
                .execute()
                .path("findVocabularyByMeaning[0].original").entity(String.class).isEqualTo("毎月")
                .path("findVocabularyByMeaning[0].meaning").entityList(String.class).isEqualTo(meaning_1)
                .path("findVocabularyByMeaning").entityList(Vocabulary.class).hasSize(1);
    }

    @Test
    void whenFindByMeaning_shouldThrowNotFoundException() {
        // given
        String meaning = "abcde"; // non-matching meaning
        Integer pageNum = 0;
        Integer pageSize = 10;
        // when
        BDDMockito.given(vocabularyService.getVocabularyByMeaning(meaning, pageNum, pageSize))
                .willThrow(new NotFoundException(MessageError.Vocabulary.VOCABULARY_CANNOT_BE_FOUND));

        // then
        // language=GraphQL
        String document = """
            query {
                findVocabularyByMeaning(meaning: "abcde", pageNum: 0, pageSize: 10) {
                    original
                    meaning
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

    @Test
    void whenFindAllVocabulary_thenReturnListVocabulary() {
        // given
        List<String> furigana_1 = List.of("まいげつ", "まいつき");
        List<String> meaning_1 = List.of("every month", "monthly");
        Vocabulary vocabulary_1 = new Vocabulary("毎月", furigana_1, meaning_1, 5);

        List<String> furigana_2 = List.of("まいにち");
        List<String> meaning_2 = List.of("every day");
        Vocabulary vocabulary_2 = new Vocabulary("毎日", furigana_2, meaning_2, 5);
        // when
        BDDMockito.given(vocabularyService.getAllVocabulary(0, 10))
                .willReturn(List.of(vocabulary_1, vocabulary_2));

        // then
        // language=GraphQL
        String document = """
            query {
                findAllVocabulary(pageNum: 0, pageSize: 10) {
                    original
                    jlpt
                }
            }
        """;

        graphQlTester.document(document)
                .execute()
                .path("findAllVocabulary[0].original").entity(String.class).isEqualTo("毎月")
                .path("findAllVocabulary[1].original").entity(String.class).isEqualTo("毎日")
                .path("findAllVocabulary[0].jlpt").entity(Integer.class).isEqualTo(5)
                .path("findAllVocabulary[1].jlpt").entity(Integer.class).isEqualTo(5)
                .path("findAllVocabulary").entityList(Vocabulary.class).hasSize(2);
    }

}
