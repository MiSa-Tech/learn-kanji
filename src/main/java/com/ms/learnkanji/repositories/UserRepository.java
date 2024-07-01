package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.graphql.data.GraphQlRepository;

@GraphQlRepository
public interface UserRepository extends Neo4jRepository<User, String>, ICustomUserRepository {
}
