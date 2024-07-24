package com.ms.learnkanji.repositories.custom;

import com.ms.learnkanji.models.User;
import com.ms.learnkanji.repositories.CustomUserRepository;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class CustomUserRepositoryImpl implements CustomUserRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public CustomUserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<User> findByUsername(String username) {
        Session session = sessionFactory.openSession();
        Iterable<User> listUsers = session.query(User.class, "MATCH (u:User {username: $username}) RETURN u",
                Map.of("username", username));

        if (!listUsers.iterator().hasNext()) {
            return Optional.empty();
        }
        User user = listUsers.iterator().next();
        return Optional.of(user);
    }
}
