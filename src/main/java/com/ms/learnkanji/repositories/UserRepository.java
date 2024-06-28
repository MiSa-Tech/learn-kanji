package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.graphql.data.GraphQlRepository;

import java.util.Optional;

@GraphQlRepository
public interface UserRepository extends Neo4jRepository<User, String> {
    @Query("MATCH (u:User) WHERE u.username = $username RETURN u")
    Optional<User> findByUsername(@Param("username") String username);
}
