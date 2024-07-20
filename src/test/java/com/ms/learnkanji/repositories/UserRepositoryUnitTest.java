package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.User;
import org.junit.jupiter.api.*;
import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@DataNeo4jTest
@ActiveProfiles("test")
class UserRepositoryUnitTest {
    @Autowired
    private UserRepository userRepository;

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
        User user = new User();
        user.setUsername("test");
        user.setPassword("test");
        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void testFindByUsername_thenReturnUser() {
        User user = userRepository.findByUsername("test").orElse(null);
        Assertions.assertNotNull(user);
        Assertions.assertEquals("test", user.getUsername());
    }

    @Test
    void testFindByEmptyUsername_thenReturnNull() {
        User user = userRepository.findByUsername("").orElse(null);
        Assertions.assertNull(user);
    }

    @Test
    void testFindByNullUsername_thenReturnNull() {
        User user = userRepository.findByUsername(null).orElse(null);
        Assertions.assertNull(user);
    }

    @Test
    void testFindByNonExistentUsername_thenReturnNull() {
        User user = userRepository.findByUsername("nonexistent").orElse(null);
        Assertions.assertNull(user);
    }
}