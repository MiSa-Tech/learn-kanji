package com.ms.learnkanji.services.custom;

import com.ms.learnkanji.commons.MessageError;
import com.ms.learnkanji.exceptions.AlreadyPresentException;
import com.ms.learnkanji.exceptions.InvalidInputException;
import com.ms.learnkanji.exceptions.NotFoundException;
import com.ms.learnkanji.models.Kanji;
import com.ms.learnkanji.repositories.KanjiRepository;
import com.ms.learnkanji.services.KanjiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImplKanjiService implements KanjiService {
    private final KanjiRepository kanjiRepository;

    @Autowired
    public ImplKanjiService(KanjiRepository kanjiRepository) {
        this.kanjiRepository = kanjiRepository;
    }

    @Override
    public Kanji createKanji(String value, Integer strokes,
                             Integer grade, Integer frequency,
                             Integer jlpt, List<String> meaning,
                             List<String> readingsOn, List<String> readingsKun) {
        if (value == null || value.isEmpty()) {
            throw new InvalidInputException(MessageError.Kanji.VALUE_CANNOT_BE_NULL);
        }
        if (meaning == null || meaning.isEmpty()) {
            throw new InvalidInputException(MessageError.Kanji.MEANING_CANNOT_BE_NULL);
        }
        Kanji kanji = kanjiRepository.findByValue(value).orElse(null);
        if (kanji != null) {
            throw new AlreadyPresentException(MessageError.Kanji.KANJI_ALREADY_PRESENT);
        }
        Kanji toSave = new Kanji();
        toSave.setValue(value);
        toSave.setStrokes(strokes);
        toSave.setGrade(grade);
        toSave.setFrequency(frequency);
        toSave.setJlpt(jlpt);
        meaning.forEach(toSave::addMeaning);
        // check if empty, if not, add
        if (readingsOn != null) {
            readingsOn.forEach(toSave::addReadingsOn);
        }
        if (readingsKun != null) {
            readingsKun.forEach(toSave::addReadingsKun);
        }
        return kanjiRepository.save(toSave);
    }

    @Override
    public Kanji getKanjiByValue(String value) {
        if (value == null || value.isEmpty()) {
            throw new InvalidInputException(MessageError.Kanji.VALUE_CANNOT_BE_NULL);
        }
        return kanjiRepository.findByValue(value)
                .orElseThrow(() -> new NotFoundException(MessageError.Kanji.KANJI_NOT_FOUND));
    }

    @Override
    public List<Kanji> getKanjiByJlpt(Integer jlpt, Integer pageNum, Integer pageSize) {
        if (jlpt < 1 || jlpt > 5) {
            throw new InvalidInputException(MessageError.Kanji.JLPT_LEVEL_CANNOT_BE_LESS_THAN_ONE_OR_GREATER_THAN_FIVE);
        }
        if (pageNum < 0) {
            throw new InvalidInputException(MessageError.Pagination.PAGE_NUM_CANNOT_BE_NEGATIVE);
        }
        if (pageSize < 1) {
            throw new InvalidInputException(MessageError.Pagination.PAGE_SIZE_CANNOT_BE_LESS_THAN_ONE);
        }
        return kanjiRepository.findByJlpt(jlpt, pageNum, pageSize);
    }

    @Override
    public List<Kanji> getKanjiByStrokes(int strokes) {
        if (strokes <= 0) {
            throw new InvalidInputException(MessageError.Kanji.STROKES_NUMBER_NEGATIVE);
        }
        return kanjiRepository.findByStrokes(strokes);
    }

    @Override
    public List<Kanji> getKanjiByGrade(int grade) {
        if (grade < 1 | grade > 12) {
            throw new InvalidInputException(MessageError.Kanji.GRADE_INVALID);
        }
        return kanjiRepository.findByGrade(grade);
    }
}
