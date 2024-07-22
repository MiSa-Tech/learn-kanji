package com.ms.learnkanji.services;

import com.ms.learnkanji.exceptions.InvalidInputException;
import com.ms.learnkanji.models.Vocabulary;
import com.ms.learnkanji.repositories.VocabularyRepository;
import com.ms.learnkanji.services.custom.ImplVocabularyService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class VocabularyServiceUnitTest {
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
        BDDMockito.given(vocabularyRepository.findByOriginal("毎月")).willReturn(Optional.of(vocabulary));

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
}
