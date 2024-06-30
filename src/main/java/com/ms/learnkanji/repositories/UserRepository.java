package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.Kanji;
import com.ms.learnkanji.models.User;
import com.ms.learnkanji.models.Vocabulary;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.List;
import java.util.Optional;

@GraphQlRepository
public interface UserRepository extends Neo4jRepository<User, String> {
    @Query("MATCH (u:User {username: $username}) RETURN u")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("MATCH (u:User {username: $username})-[:LEARNT_KANJI]->(k:Kanji) RETURN k")
    List<Kanji> findKanjisLearntByUser(@Param("username") String username);

    @Query("MATCH (u:User {username: $username})-[:LEARNT_VOCABULARY]->(v:Vocabulary) RETURN v")
    List<Vocabulary> findVocabulariesLearntByUser(@Param("username") String username);
}
