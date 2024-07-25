package com.ms.learnkanji.services;

import com.ms.learnkanji.commons.MessageError;
import com.ms.learnkanji.commons.Ordering;
import com.ms.learnkanji.exceptions.NotFoundException;
import com.ms.learnkanji.models.Kanji;
import com.ms.learnkanji.models.Role;
import com.ms.learnkanji.models.User;
import com.ms.learnkanji.models.results.KanjiResult;
import com.ms.learnkanji.repositories.KanjiResultRepository;
import com.ms.learnkanji.repositories.UserRepository;
import com.ms.learnkanji.services.custom.ImplKanjiResultService;
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
class KanjiResultServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private KanjiResultRepository kanjiResultRepository;

    private KanjiResultService kanjiResultService;

    @BeforeEach
    void setUp() {
        // initialize the service
        kanjiResultService = new ImplKanjiResultService(userRepository, kanjiResultRepository);
    }

    @Test
    void whenFindByUsername_thenThrowNotFoundException() {
        // given
        String username = "test";

        // when
        // verify that the method is not called
        Mockito.verify(kanjiResultRepository, Mockito.times(0)).findBestShouldLearnKanji(username, 0, 10, Ordering.ASC);

        // then
        Assertions.assertThrows(NotFoundException.class, () -> {
            kanjiResultService.getBestShouldLearnKanji(username, 0, 10, Ordering.ASC);
        })
                .getMessage()
                .equals(MessageError.User.USER_NOT_FOUND);
    }

    @Test
    void whenFindByUsername_thenReturnKanjiResult() {
        // given
        String username = "test";
        Kanji kanji1 = new Kanji("一", null, null, null,
                5, List.of("one"), null, null);
        Kanji kanji2 = new Kanji("二", null, null, null,
                5, List.of("two"), null, null);
        KanjiResult kanjiResult1 = new KanjiResult(kanji1, 1);
        KanjiResult kanjiResult2 = new KanjiResult(kanji2, 2);
        List<KanjiResult> kanjiResults = List.of(kanjiResult2, kanjiResult1);

        // when
        // verify that the method is called
        // verify that NotFoundException is not thrown

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User(username, Role.USER, 5)));
        Mockito.when(kanjiResultRepository.findBestShouldLearnKanji(username, 0, 10, Ordering.DESC)).thenReturn(kanjiResults);

        // then
        Assertions.assertEquals(kanjiResults, kanjiResultService.getBestShouldLearnKanji(username, 0, 10, Ordering.DESC));
    }
}
