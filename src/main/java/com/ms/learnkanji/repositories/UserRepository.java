package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserRepository extends Neo4jRepository<User, String>, CustomUserRepository {
}
