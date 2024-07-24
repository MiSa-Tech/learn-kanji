package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.Vocabulary;
import org.junit.jupiter.api.*;
import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.List;
import java.util.Optional;

@DataNeo4jTest
@ActiveProfiles("test")
class VocabularyRepositoryUnitTest {
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
        List<String> furigana_1 = List.of("まいげつ", "まいつき");
        List<String> meaning_1 = List.of("every month", "monthly");
        Vocabulary vocabulary_1 = new Vocabulary("毎月", furigana_1, meaning_1, 5);
        vocabularyRepository.save(vocabulary_1);

        List<String> furigana_2 = List.of("まいにち");
        List<String> meaning_2 = List.of("every day");
        Vocabulary vocabulary_2 = new Vocabulary("毎日", furigana_2, meaning_2, 5);
        vocabularyRepository.save(vocabulary_2);
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

    @Test
    void whenFindByJlpt_returnListVocabulary() {
        // given
        Pageable pageable = PageRequest.of(0, 2);
        // when
        List<Vocabulary> listVocabs = vocabularyRepository.findByJlpt(5, pageable).getContent();

        Assertions.assertEquals(2, listVocabs.size());
    }

    @Test
    void whenFindByNullJlpt_returnEmptyList() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Vocabulary> listVocabs = vocabularyRepository.findByJlpt(null, pageable).getContent();
        Assertions.assertTrue(listVocabs.isEmpty());
    }

    @Test
    void whenFindByFurigana_returnListVocabulary() {
        // given
        List<String> furigana = List.of("まいげつ", "まいつき");
        List<String> meaning = List.of("every month", "monthly");
        Vocabulary vocabulary = new Vocabulary("毎月", furigana, meaning, 5);
        Pageable pageable = PageRequest.of(0, 10);
        // when
        List<Vocabulary> listVocabs = vocabularyRepository.findByFurigana("まいげつ", pageable).getContent();

        Assertions.assertEquals(1, listVocabs.size());
        Assertions.assertEquals(vocabulary.getOriginal(), listVocabs.get(0).getOriginal());

    }

    @Test
    void whenFindByNullFurigana_returnEmptyList() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Vocabulary> vocabularyList = vocabularyRepository.findByFurigana(null, pageable).getContent();
        Assertions.assertTrue(vocabularyList.isEmpty());
    }

    @Test
    void whenFindByEmptyFurigana_returnEmptyList() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Vocabulary> vocabularyList = vocabularyRepository.findByFurigana("", pageable).getContent();
        Assertions.assertTrue(vocabularyList.isEmpty());
    }

    @Test
    void whenFindByMeaning_returnListVocabulary() {
        // given
        List<String> furigana = List.of("まいげつ", "まいつき");
        List<String> meaning = List.of("every month", "monthly");
        Vocabulary vocabulary = new Vocabulary("毎月", furigana, meaning, 5);
        Pageable pageable = PageRequest.of(0, 10);
        // when
        List<Vocabulary> listVocabs = vocabularyRepository.findByMeaning("every month", pageable).getContent();

        Assertions.assertEquals(1, listVocabs.size());
        Assertions.assertEquals(vocabulary.getOriginal(), listVocabs.get(0).getOriginal());
    }

    @Test
    void whenFindByNullMeaning_returnEmptyList() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Vocabulary> vocabularyList = vocabularyRepository.findByMeaning(null, pageable).getContent();
        Assertions.assertTrue(vocabularyList.isEmpty());
    }

    @Test
    void whenFindByEmptyMeaning_returnEmptyList() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Vocabulary> vocabularyList = vocabularyRepository.findByMeaning(null, pageable).getContent();
        Assertions.assertTrue(vocabularyList.isEmpty());
    }

    @Test
    void whenFindAllVocabulary_returnListVocabulary() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Vocabulary> vocabularyList = vocabularyRepository.findAllVocabulary(pageable).getContent();
        Assertions.assertEquals(2, vocabularyList.size());
    }
}
