package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.Vocabulary;
import org.junit.jupiter.api.*;
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
public class VocabularyRepositoryUnitTest {
    @Autowired
    private VocabularyRepository vocabularyRepository;

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
        List<String> furigana = List.of("まいげつ", "まいつき");
        List<String> meaning = List.of("every month", "monthly");
        Vocabulary vocabulary = new Vocabulary("毎月", furigana, meaning, 5);
        vocabularyRepository.save(vocabulary);
    }

    @AfterEach
    void tearDown() {
        vocabularyRepository.deleteAll();
    }

    @Test
    void whenFindByOriginal_thenReturnVocabulary() {
        // given
        List<String> furigana = List.of("まいげつ", "まいつき");
        List<String> meaning = List.of("every month", "monthly");
        Vocabulary vocabulary = new Vocabulary("毎月", furigana, meaning, 5);
        // when
        Optional<Vocabulary> vocabularyOptional = vocabularyRepository.findByOriginal("毎月");

        // then
        Assertions.assertTrue(vocabularyOptional.isPresent());
        Assertions.assertEquals(vocabulary.getOriginal(), vocabularyOptional.get().getOriginal());
    }

    @Test
    void whenFindByNullOriginal_returnNull() {
        // when
        Assertions.assertEquals(Optional.empty(), vocabularyRepository.findByOriginal(null));
    }

    @Test
    void whenFindByEmptyOriginal_returnNull() {
        // when
        Assertions.assertEquals(Optional.empty(), vocabularyRepository.findByOriginal(null));
    }
}
