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
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class KanjiServiceUnitTest {
    @Mock
    private KanjiRepository kanjiRepository;

    private KanjiService kanjiService;

    @BeforeEach
    void setUp() {
        // initialize the service
        kanjiService = new ImplKanjiService(kanjiRepository);
    }


    @Test
    void whenFindByValue_thenReturnKanji() {
        // given
        List<String> meaning = List.of("one");
        Kanji kanji = new Kanji("一", null, null, null,
                5, meaning, null, null);
        // when
        Mockito.when(kanjiRepository.findByValue("一")).thenReturn(Optional.of(kanji));

        // then
        Kanji found = kanjiService.getKanjiByValue("一");
        Assertions.assertEquals(kanji, found);
    }

    @Test
    void whenFindByEmptyValue_thenThrowException() {
        Assertions.assertThrows(InvalidInputException.class, () -> {
            kanjiService.getKanjiByValue("");
        });
    }

    @Test
    void whenFindByNullValue_thenThrowException() {
        Assertions.assertThrows(InvalidInputException.class, () -> {
            kanjiService.getKanjiByValue(null);
        });
    }


}