package com.ms.learnkanji.services;

import com.ms.learnkanji.exceptions.InvalidInputException;
import com.ms.learnkanji.models.Kanji;
import com.ms.learnkanji.repositories.KanjiRepository;
import com.ms.learnkanji.services.custom.ImplKanjiService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class KanjiServiceUnitTest {
    @Mock
    private KanjiRepository kanjiRepository;

    private KanjiService kanjiService;

    @BeforeEach
    void setUp() {
        // initialize the service
        kanjiService = new ImplKanjiService(kanjiRepository);
        // save a kanji
        List<String> meaning = List.of("one");
        Kanji kanji = new Kanji("一", null, null, null,
                5, meaning, null, null);
        kanjiRepository.save(kanji);
    }

    @AfterEach
    void tearDown() {
        kanjiRepository.deleteAll();
    }


    @Test
    void whenFindByEmptyValue_thenThrowException() {
        Mockito.verify(kanjiRepository, Mockito.times(1)).save(Mockito.any(Kanji.class));
        Assertions.assertThrows(InvalidInputException.class, () -> {
            kanjiService.getKanjiByValue(null);
        });
    }
}