package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.Kanji;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.List;
import java.util.Optional;

@DataNeo4jTest
@ActiveProfiles("test")
class KanjiRepositoryUnitTest {
    @Autowired
    private KanjiRepository kanjiRepository;

    private static Neo4j embeddedDatabaseServer;

    @BeforeAll
    static void initializeNeo4j() {
        embeddedDatabaseServer = Neo4jBuilders.newInProcessBuilder()
                .withDisabledServer()
                .build();
    }

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri", embeddedDatabaseServer::boltURI);
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", () -> null);
    }

    @AfterAll
    static void stopNeo4j() {
        embeddedDatabaseServer.close();
    }

    @BeforeEach
    void setUp() {
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
    void whenFindByValue_thenReturnKanji() {
        // given
        List<String> meaning = List.of("one");
        Kanji kanji = new Kanji("一", null, null, null,
                5, meaning, null, null);
        // when
        Optional<Kanji> kanjiOptional = kanjiRepository.findByValue("一");

        // then
        Assertions.assertTrue(kanjiOptional.isPresent());
        Assertions.assertEquals(kanji.getValue(), kanjiOptional.get().getValue());
    }

    @Test
    void whenFindByValueNull_returnNull() {
        // when
        Assertions.assertEquals(Optional.empty(), kanjiRepository.findByValue(null));
    }

    @Test
    void whenFindByEmptyValue_returnNull() {
        // when
        Assertions.assertEquals(Optional.empty(), kanjiRepository.findByValue(""));
    }
}