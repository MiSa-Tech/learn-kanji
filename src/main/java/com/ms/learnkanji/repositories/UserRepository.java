package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends Neo4jRepository<User, String> {
    @Query("MATCH (u:User {username: $username}) RETURN u")
    Optional<User> findByUsername(@Param("username") String username);

    @Query("MATCH (u:User) RETURN u :#{orderBy(#pageable)} SKIP $skip LIMIT $limit")
    Slice<User> findAllUser(Pageable pageable);
}
