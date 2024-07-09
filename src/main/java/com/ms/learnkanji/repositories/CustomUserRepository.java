package com.ms.learnkanji.repositories;

import com.ms.learnkanji.models.User;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface CustomUserRepository {
    Optional<User> findByUsername(String username);
}
