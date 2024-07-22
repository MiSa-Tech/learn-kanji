package com.ms.learnkanji.services.custom;

import com.ms.learnkanji.commons.MessageError;
import com.ms.learnkanji.exceptions.InvalidInputException;
import com.ms.learnkanji.exceptions.NotFoundException;
import com.ms.learnkanji.models.Vocabulary;
import com.ms.learnkanji.repositories.VocabularyRepository;
import com.ms.learnkanji.services.VocabularyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImplVocabularyService implements VocabularyService {
    private final VocabularyRepository vocabularyRepository;

    @Autowired
    public ImplVocabularyService(VocabularyRepository vocabularyRepository) { this.vocabularyRepository = vocabularyRepository; }


    @Override
    public Vocabulary createVocabulary(String original, List<String> furigana,
                                       List<String> meaning, Integer jlpt) {
        if (original == null || original.isEmpty()) {
            throw new InvalidInputException(MessageError.Vocabulary.ORIGINAL_CANNOT_BE_NULL);
        }
        if (furigana == null || furigana.isEmpty()) {
            throw new InvalidInputException(MessageError.Vocabulary.FURIGANA_CANNOT_BE_NULL);
        }
        if (meaning == null || meaning.isEmpty()) {
            throw new InvalidInputException(MessageError.Vocabulary.MEANING_CANNOT_BE_NULL);
        }
        Vocabulary toSave = new Vocabulary();
        toSave.setOriginal(original);
        toSave.setJlpt(jlpt);
        furigana.forEach(toSave::addFurigana);
        meaning.forEach(toSave::addMeaning);

        return vocabularyRepository.save(toSave);
    }

    @Override
    public Vocabulary getVocabularyByOriginal(String original) {
        if (original == null || original.isEmpty()) {
            throw new InvalidInputException(MessageError.Vocabulary.ORIGINAL_CANNOT_BE_NULL);
        }
        return vocabularyRepository.findByOriginal(original)
                .orElseThrow(() -> new NotFoundException(MessageError.Vocabulary.VOCABULARY_CANNOT_BE_FOUND));
    }
}
