package com.ms.learnkanji.repositories.custom;

import com.ms.learnkanji.models.Vocabulary;
import com.ms.learnkanji.repositories.CustomVocabularyRepository;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class ImplCustomVocabularyRepository implements CustomVocabularyRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public ImplCustomVocabularyRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Vocabulary> findByOriginal(String original) {
        Session session = sessionFactory.openSession();
        Iterable<Vocabulary> listVocabs = session.query(Vocabulary.class,
                """
                   MATCH (v:Vocabulary {original: $original}) RETURN v
                   """,
                Map.of("original", original));
        if (!listVocabs.iterator().hasNext()) {
            return Optional.empty();
        }
        Vocabulary vocabulary = listVocabs.iterator().next();
        return Optional.of(vocabulary);
    }
}
