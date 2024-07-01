package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.User;

import java.util.Optional;

public interface ICustomUserRepository {
    Optional<User> findByUsername(String username);

    //@Query("MATCH (u:User {username: $username})-[:LEARNT_KANJI]->(k:Kanji) RETURN k")
    //List<Kanji> findKanjisLearntByUser(@Param("username") String username);

    //@Query("MATCH (u:User {username: $username})-[:LEARNT_VOCABULARY]->(v:Vocabulary) RETURN v")
    //List<Vocabulary> findVocabulariesLearntByUser(@Param("username") String username);
}
