package com.ms.learnkanji.services;

import com.ms.learnkanji.commons.MessageError;
import com.ms.learnkanji.exceptions.InvalidInputException;
import com.ms.learnkanji.models.Vocabulary;
import com.ms.learnkanji.repositories.VocabularyRepository;
import com.ms.learnkanji.services.custom.ImplVocabularyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class VocabularyServiceUnitTest {
    @Mock
    private VocabularyRepository vocabularyRepository;

    private VocabularyService vocabularyService;

    @BeforeEach
    void setUp() {
        vocabularyService = new ImplVocabularyService(vocabularyRepository);
    }

    @Test
    void whenFindByOriginal_thenReturnVocabulary() {
        // given
        List<String> furigana = List.of("まいげつ", "まいつき");
        List<String> meaning = List.of("every month", "monthly");
        Vocabulary vocabulary = new Vocabulary("毎月", furigana, meaning, 5);
        // when
        Mockito.when(vocabularyRepository.findByOriginal("毎月")).thenReturn(Optional.of(vocabulary));

        // then
        Vocabulary found = vocabularyService.getVocabularyByOriginal("毎月");
        Assertions.assertEquals(vocabulary, found);
    }

    @Test
    void whenFindByEmptyOriginal_thenThrowException() {
        Assertions.assertThrows(InvalidInputException.class, () -> {
            vocabularyService.getVocabularyByOriginal("");
        });
    }

    @Test
    void whenFindByNullOriginal_thenThrowException() {
        Assertions.assertThrows(InvalidInputException.class, () -> {
            vocabularyService.getVocabularyByOriginal(null);
        });
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


        List<Vocabulary> vocabularyList = List.of(vocabulary_1, vocabulary_2);
        Sort sort = Sort.by("original");
        Pageable pageable = PageRequest.of(0, 10, sort.ascending());
        Slice<Vocabulary> vocabularySlice = new PageImpl<>(vocabularyList, pageable, vocabularyList.size());
        // when
        BDDMockito.given(vocabularyRepository.findByJlpt(5, pageable))
                .willReturn(vocabularySlice);

        // then
        List<Vocabulary> found = vocabularyService.getVocabularyByJlpt(5, 0, 10);
        Assertions.assertEquals(2, found.size());
        Assertions.assertEquals(vocabulary_1, found.get(0));
        Assertions.assertEquals(vocabulary_2, found.get(1));
    }

    @Test
    void whenFindByInvalidJlpt_thenThrowInvalidInputException() {
        Assertions.assertTrue(
            Assertions.assertThrows(InvalidInputException.class, () -> {
                        vocabularyService.getVocabularyByJlpt(6,0, 10);
                    })
                    .getMessage()
                    .contains(MessageError.Vocabulary.JLPT_LEVEL_CANNOT_BE_LESS_THAN_ONE_OR_GREATER_THAN_FIVE)
        );

        Assertions.assertTrue(
            Assertions.assertThrows(InvalidInputException.class, () -> {
                        vocabularyService.getVocabularyByJlpt(0,0, 10);
                    })
                    .getMessage()
                    .contains(MessageError.Vocabulary.JLPT_LEVEL_CANNOT_BE_LESS_THAN_ONE_OR_GREATER_THAN_FIVE)
        );

    }

    @Test
    void whenFindByJlptInvalidPagination_thenThrowInvalidInputException() {
        // pageNum invalid
        Assertions.assertTrue(
            Assertions.assertThrows(InvalidInputException.class, () -> {
                        vocabularyService.getVocabularyByJlpt(5, -1, 1);
                    })
                    .getMessage()
                    .contains(MessageError.Pagination.PAGE_NUM_CANNOT_BE_NEGATIVE)
        );


        // pageSize invalid
        Assertions.assertTrue(
            Assertions.assertThrows(InvalidInputException.class, () -> {
                        vocabularyService.getVocabularyByJlpt(5, 0, 0);
                    })
                    .getMessage()
                    .contains(MessageError.Pagination.PAGE_SIZE_CANNOT_BE_LESS_THAN_ONE)
        );

        // both invalid
        Assertions.assertTrue(
            Assertions.assertThrows(InvalidInputException.class, () -> {
                        vocabularyService.getVocabularyByJlpt(5, -1, 0);
                    })
                    .getMessage()
                    .contains(MessageError.Pagination.PAGE_NUM_CANNOT_BE_NEGATIVE)
        );

    }

    @Test
    void whenFindByFurigana_thenReturnListVocabulary() {
        // given
        List<String> furigana_2 = List.of("まいにち");
        List<String> meaning_2 = List.of("every day");
        Vocabulary vocabulary_2 = new Vocabulary("毎日", furigana_2, meaning_2, 5);

        List<Vocabulary> vocabularyList = List.of(vocabulary_2);
        Sort sort = Sort.by("original");
        Pageable pageable = PageRequest.of(0, 10, sort.ascending());
        Slice<Vocabulary> vocabularySlice = new PageImpl<>(vocabularyList, pageable, vocabularyList.size());
        // when
        BDDMockito.given(vocabularyRepository.findByFurigana("まいにち", pageable))
                .willReturn(vocabularySlice);

        // then
        List<Vocabulary> found = vocabularyService.getVocabularyByFurigana("まいにち", 0, 10);
        Assertions.assertEquals(1, found.size());
        Assertions.assertEquals(vocabulary_2, found.get(0));
    }

    @Test
    void whenFindByNullFurigana_thenThrowInputException() {
        Assertions.assertTrue(
            Assertions.assertThrows(InvalidInputException.class, () -> {
                        vocabularyService.getVocabularyByFurigana(null, 0, 1);
                    })
                    .getMessage()
                    .contains(MessageError.Vocabulary.FURIGANA_CANNOT_BE_NULL)
        );

    }

    @Test
    void whenFindByEmptyFurigana_thenThrowInputException() {
        Assertions.assertTrue(
            Assertions.assertThrows(InvalidInputException.class, () -> {
                        vocabularyService.getVocabularyByFurigana("", 0, 1);
                    })
                    .getMessage()
                    .contains(MessageError.Vocabulary.FURIGANA_CANNOT_BE_NULL)
        );

    }

    @Test
    void whenFindByFuriganaInvalidPagination_thenThrowInvalidInputException() {
        // pageNum invalid
        Assertions.assertTrue(
            Assertions.assertThrows(InvalidInputException.class, () -> {
                        vocabularyService.getVocabularyByFurigana("まいにち", -1, 1);
                    })
                    .getMessage()
                    .contains(MessageError.Pagination.PAGE_NUM_CANNOT_BE_NEGATIVE)
        );
        // pageSize invalid
        Assertions.assertTrue(
            Assertions.assertThrows(InvalidInputException.class, () -> {
                        vocabularyService.getVocabularyByFurigana("まいにち", 0, 0);
                    })
                    .getMessage()
                    .contains(MessageError.Pagination.PAGE_SIZE_CANNOT_BE_LESS_THAN_ONE)
        );
        // both invalid
        Assertions.assertTrue(
            Assertions.assertThrows(InvalidInputException.class, () -> {
                        vocabularyService.getVocabularyByFurigana("まいにち", -1, 0);
                    })
                    .getMessage()
                    .contains(MessageError.Pagination.PAGE_NUM_CANNOT_BE_NEGATIVE)
        );

    }

    @Test
    void whenFindByMeaning_thenReturnListVocabulary() {
        // given
        List<String> furigana_1 = List.of("まいげつ", "まいつき");
        List<String> meaning_1 = List.of("every month", "monthly");
        Vocabulary vocabulary_1 = new Vocabulary("毎月", furigana_1, meaning_1, 5);

        List<Vocabulary> vocabularyList = List.of(vocabulary_1);
        Sort sort = Sort.by("original");
        Pageable pageable = PageRequest.of(0, 10, sort.ascending());
        Slice<Vocabulary> vocabularySlice = new PageImpl<>(vocabularyList, pageable, vocabularyList.size());

        // when
        BDDMockito.given(vocabularyRepository.findByMeaning("every month", pageable))
                .willReturn(vocabularySlice);

        // then
        List<Vocabulary> found = vocabularyService.getVocabularyByMeaning("every month", 0, 10);
        Assertions.assertEquals(1, found.size());
        Assertions.assertEquals(vocabulary_1, found.get(0));
    }

    @Test
    void whenFindByNullMeaning_thenThrowInputException() {
        Assertions.assertTrue(
            Assertions.assertThrows(InvalidInputException.class, () -> {
                        vocabularyService.getVocabularyByMeaning(null, 0, 1);
                    })
                    .getMessage()
                    .contains(MessageError.Vocabulary.MEANING_CANNOT_BE_NULL)
        );
    }

    @Test
    void whenFindByEmptyMeaning_thenThrowInputException() {
        Assertions.assertTrue(
                Assertions.assertThrows(InvalidInputException.class, () -> {
                    vocabularyService.getVocabularyByMeaning("", 0, 1);
                })
                .getMessage()
                .contains(MessageError.Vocabulary.MEANING_CANNOT_BE_NULL)
        );
    }

    @Test
    void whenFindByMeaningInvalidPagination_thenThrowInvalidInputException() {
        // pageNum invalid
        Assertions.assertTrue(
            Assertions.assertThrows(InvalidInputException.class, () -> {
                        vocabularyService.getVocabularyByMeaning("every month", -1, 1);
                    })
                    .getMessage()
                    .contains(MessageError.Pagination.PAGE_NUM_CANNOT_BE_NEGATIVE)
        );
        // pageSize invalid
        Assertions.assertTrue(
            Assertions.assertThrows(InvalidInputException.class, () -> {
                        vocabularyService.getVocabularyByMeaning("every month", 0, 0);
                    })
                    .getMessage()
                    .contains(MessageError.Pagination.PAGE_SIZE_CANNOT_BE_LESS_THAN_ONE)
        );
        // both invalid
        Assertions.assertTrue(
            Assertions.assertThrows(InvalidInputException.class, () -> {
                        vocabularyService.getVocabularyByMeaning("every month", -1, 0);
                    })
                    .getMessage()
                    .contains(MessageError.Pagination.PAGE_NUM_CANNOT_BE_NEGATIVE)
        );

    }

    @Test
    void whenFindAllVocabulary_thenReturnVocabularyList() {
        // given
        List<String> furigana_1 = List.of("まいげつ", "まいつき");
        List<String> meaning_1 = List.of("every month", "monthly");
        Vocabulary vocabulary_1 = new Vocabulary("毎月", furigana_1, meaning_1, 5);

        List<String> furigana_2 = List.of("まいにち");
        List<String> meaning_2 = List.of("every day");
        Vocabulary vocabulary_2 = new Vocabulary("毎日", furigana_2, meaning_2, 5);


        List<Vocabulary> vocabularyList = List.of(vocabulary_1, vocabulary_2);
        Sort sort = Sort.by("original");
        Pageable pageable = PageRequest.of(0, 10, sort.ascending());
        Slice<Vocabulary> vocabularySlice = new PageImpl<>(vocabularyList, pageable, vocabularyList.size());
        // when
        BDDMockito.given(vocabularyRepository.findAllVocabulary(pageable))
                .willReturn(vocabularySlice);

        // then
        List<Vocabulary> found = vocabularyService.getAllVocabulary(0, 10);
        Assertions.assertEquals(2, found.size());
        Assertions.assertEquals(vocabulary_1, found.get(0));
    }

    @Test
    void whenFindAllVocabularyInvalidPagination_thenThrowInvalidInputException() {
        // pageNum invalid
        Assertions.assertTrue(
                Assertions.assertThrows(InvalidInputException.class, () -> {
                            vocabularyService.getAllVocabulary( -1, 1);
                        })
                        .getMessage()
                        .contains(MessageError.Pagination.PAGE_NUM_CANNOT_BE_NEGATIVE)
        );
        // pageSize invalid
        Assertions.assertTrue(
                Assertions.assertThrows(InvalidInputException.class, () -> {
                            vocabularyService.getAllVocabulary(0, 0);
                        })
                        .getMessage()
                        .contains(MessageError.Pagination.PAGE_SIZE_CANNOT_BE_LESS_THAN_ONE)
        );
        // both invalid
        Assertions.assertTrue(
                Assertions.assertThrows(InvalidInputException.class, () -> {
                            vocabularyService.getAllVocabulary(-1, 0);
                        })
                        .getMessage()
                        .contains(MessageError.Pagination.PAGE_NUM_CANNOT_BE_NEGATIVE)
        );

    }
}
