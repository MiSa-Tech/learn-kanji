package com.ms.learnkanji.services;

import com.ms.learnkanji.models.User;

import java.util.List;

public interface IUserService {
    User getUserByUsername(String username);
    List<User> getAllUsers();
    User createUser(String username);
}
