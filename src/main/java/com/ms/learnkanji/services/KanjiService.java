package com.ms.learnkanji.services;

import com.ms.learnkanji.commons.MessageError;
import com.ms.learnkanji.exceptions.AlreadyPresentException;
import com.ms.learnkanji.exceptions.InvalidInputException;
import com.ms.learnkanji.exceptions.NotFoundException;
import com.ms.learnkanji.models.Kanji;
import com.ms.learnkanji.repositories.KanjiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KanjiService implements IKanjiService {
    private final KanjiRepository kanjiRepository;

    @Autowired
    public KanjiService(KanjiRepository kanjiRepository) {
        this.kanjiRepository = kanjiRepository;
    }

    @Override
    public Kanji createKanji(String value, Integer stroke,
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
        toSave.setStroke(stroke);
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

    public List<Kanji> getKanjiByJlpt(int jlpt) {
        if(jlpt < 1 || jlpt > 5) {
            throw new InvalidInputException(MessageError.Kanji.JLPT_LEVEL_INVALID);
        }
        return kanjiRepository.findByJlpt(jlpt);
    }
}
