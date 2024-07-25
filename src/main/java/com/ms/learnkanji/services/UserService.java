package com.ms.learnkanji.services;

import com.ms.learnkanji.commons.Ordering;
import com.ms.learnkanji.models.User;

import java.util.List;

public interface UserService {
    User getUserByUsername(String username);
    List<User> getAllUsers(Integer pageNum, Integer pageSize, Ordering ordering);
    User createUser(String username, String password, Integer jlpt);
    User userLearntKanji(String username, String kanji);
}
