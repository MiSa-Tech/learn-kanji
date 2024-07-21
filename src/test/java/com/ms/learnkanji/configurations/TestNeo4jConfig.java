package com.ms.learnkanji.configurations;

import com.ms.learnkanji.repositories.KanjiResultRepository;
import com.ms.learnkanji.repositories.custom.ImplKanjiResultRepository;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.*;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;

@Configuration
@ComponentScan(basePackages = "com.ms.learnkanji.repositories",
    includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {KanjiResultRepository.class, ImplKanjiResultRepository.class}),
        useDefaultFilters = false)
@Profile("kanji-result-test")
public class TestNeo4jConfig {
    @Bean(destroyMethod = "close")
    public Neo4j embeddedNeo4j() {
        return Neo4jBuilders.newInProcessBuilder().withDisabledServer().build();
    }

    @Bean
    @Primary
    public Driver neo4jDriver(Neo4j embeddedNeo4j) {
        return GraphDatabase.driver(embeddedNeo4j.boltURI(), org.neo4j.driver.Config.builder().withoutEncryption().build());
    }

    @Bean
    @Primary
    public Neo4jClient neo4jClient(Driver driver) {
        return Neo4jClient.create(driver);
    }

    @Bean
    @Primary
    public Neo4jTransactionManager transactionManager(Driver driver) {
        return new Neo4jTransactionManager(driver);
    }

    @Bean
    public SessionFactory sessionFactory(Neo4j embeddedNeo4j) {
        return new SessionFactory(getConfiguration(embeddedNeo4j), "com.ms.learnkanji.models");
    }

    @Bean
    public org.neo4j.ogm.config.Configuration getConfiguration(Neo4j embeddedNeo4j) {
        return new org.neo4j.ogm.config.Configuration.Builder()
                .uri(embeddedNeo4j.boltURI().toString())
                .credentials("neo4j", null)
                .build();
    }
}
