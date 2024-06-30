package com.ms.learnkanji.services;

import com.ms.learnkanji.models.User;

import java.util.List;

public interface IUserService {
    User getUserByUsername(String username);
    List<User> getAllUsers(Integer pageNum, Integer pageSize);
    User createUser(String username, Integer jlpt);
    User userLearntKanji(String username, String kanji);
}
