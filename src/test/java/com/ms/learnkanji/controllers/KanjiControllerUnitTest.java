package com.ms.learnkanji.controllers;

import com.ms.learnkanji.configuration.TestSecurityConfig;
import com.ms.learnkanji.models.Kanji;
import com.ms.learnkanji.repositories.KanjiRepository;
import com.ms.learnkanji.services.KanjiService;
import com.ms.learnkanji.services.custom.CustomUserDetailsService;
import com.ms.learnkanji.utils.JwtUtil;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(KanjiController.class)
@Import({TestSecurityConfig.class})
class KanjiControllerUnitTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KanjiRepository kanjiRepository;

    @MockBean
    private KanjiService kanjiService;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @MockBean
    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
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
    void whenFindByValue_thenReturnKanji() throws Exception {
        // given
        List<String> meaning = List.of("one");
        Kanji kanji = new Kanji("一", null, null, null,
                5, meaning, null, null);
        // when
        BDDMockito.given(kanjiService.getKanjiByValue("一")).willReturn(kanji);
        // then
        String query = """
                    query {
                        findKanjiByValue(value: "一") {
                            value
                        }
                    }
                """;
        mockMvc.perform(MockMvcRequestBuilders.post("/graphql")
                .content(query)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}