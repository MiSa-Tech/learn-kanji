package com.ms.learnkanji.services.custom;

import com.ms.learnkanji.commons.MessageError;
import com.ms.learnkanji.commons.Ordering;
import com.ms.learnkanji.exceptions.InvalidInputException;
import com.ms.learnkanji.exceptions.NotFoundException;
import com.ms.learnkanji.models.results.KanjiResult;
import com.ms.learnkanji.repositories.KanjiResultRepository;
import com.ms.learnkanji.repositories.UserRepository;
import com.ms.learnkanji.services.KanjiResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImplKanjiResultService implements KanjiResultService {
    private final UserRepository userRepository;

    private final KanjiResultRepository kanjiResultRepository;

    @Autowired
    public ImplKanjiResultService(UserRepository userRepository,
                                  KanjiResultRepository kanjiResultRepository) {
        this.userRepository = userRepository;
        this.kanjiResultRepository = kanjiResultRepository;
    }


    @Override
    public List<KanjiResult> getBestShouldLearnKanji(String username, Integer pageNum, Integer pageSize, Ordering ordering) {
        if (username == null) {
            throw new InvalidInputException(MessageError.User.USERNAME_CANNOT_BE_NULL);
        }
        if (username.isEmpty()) {
            throw new InvalidInputException(MessageError.User.USERNAME_CANNOT_BE_EMPTY);
        }
        if (pageNum < 0) {
            throw new InvalidInputException(MessageError.Pagination.PAGE_NUM_CANNOT_BE_NEGATIVE);
        }
        if (pageSize < 1) {
            throw new InvalidInputException(MessageError.Pagination.PAGE_SIZE_CANNOT_BE_LESS_THAN_ONE);
        }

        userRepository.findByUsername(username).orElseThrow(
                () -> new NotFoundException(MessageError.User.USER_NOT_FOUND)
        );

        return kanjiResultRepository.findBestShouldLearnKanji(username, pageNum, pageSize, ordering);
    }
}
