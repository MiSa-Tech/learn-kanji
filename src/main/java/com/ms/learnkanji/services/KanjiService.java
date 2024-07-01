package com.ms.learnkanji.services;

import com.ms.learnkanji.commons.MessageError;
import com.ms.learnkanji.exceptions.AlreadyPresentException;
import com.ms.learnkanji.exceptions.InvalidInputException;
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
            throw new InvalidInputException("Value cannot be null or empty");
        }
        if (meaning == null || meaning.isEmpty()) {
            throw new InvalidInputException("Meaning cannot be null or empty");
        }
        Kanji kanji = kanjiRepository.findByValue(value).orElse(null);
        if (kanji != null) {
            throw new AlreadyPresentException(MessageError.KANJI_ALREADY_PRESENT);
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
            throw new InvalidInputException("Value cannot be null or empty");
        }
        return kanjiRepository.findByValue(value).orElse(null);
    }
}
