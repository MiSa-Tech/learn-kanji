package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.results.KanjiResult;
import org.junit.jupiter.api.*;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@SpringBootTest
@ActiveProfiles({"kanji-result-test", "test"})
class KanjiResultRepositoryTest {

    @Autowired
    @Qualifier("kanjiResultRepositoryImpl")
    private KanjiResultRepository kanjiResultRepository;

    @Autowired
    private Driver driver;

    @BeforeEach
    void setUp() {
        // load dataset
        try (Session session = driver.session()) {
            String cypher = Files.readString(Path.of(new ClassPathResource("test-data.cypher").getURI()));
            String[] queries = cypher.split("(?<=;\\s*)"); // Split queries by semicolon
            for (String query : queries) {
                if (!query.trim().isEmpty()) { // Ensure the query is not empty
                    session.run(query.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        Session session = driver.session();
        String query = "MATCH (n) DETACH DELETE n";
        session.run(query);
    }

    @Test
    void testFindBestShouldLearnKanji_thenReturnKanjiResult() {
        // given

        // when
        List<KanjiResult> kanjiResult = kanjiResultRepository.findBestShouldLearnKanji("test", 0, 10);
        // then
        Assertions.assertEquals(5, kanjiResult.size());
    }

    @Test
    void findBestShouldLearnKanjiForBeginner_Return3KanjiResult() {
        // given

        // when
        // test2 does not exist in the database
        List<KanjiResult> kanjiResult = kanjiResultRepository.findBestShouldLearnKanji("test2", 0, 10);
        // then
        Assertions.assertEquals(3, kanjiResult.size());
    }
}
