package com.ms.learnkanji.repositories.custom;

import com.ms.learnkanji.models.Vocabulary;
import com.ms.learnkanji.repositories.CustomVocabularyRepository;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CustomVocabularyRepositoryImpl implements CustomVocabularyRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public CustomVocabularyRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Vocabulary> findByOriginal(String original) {
        Session session = sessionFactory.openSession();
        Iterable<Vocabulary> listVocabs = session.query(Vocabulary.class,
                "MATCH (v:Vocabulary {original: $original}) RETURN v",
                Map.of("original", original));
        if (!listVocabs.iterator().hasNext()) {
            return Optional.empty();
        }
        Vocabulary vocabulary = listVocabs.iterator().next();
        return Optional.of(vocabulary);
    }

    @Override
    public List<Vocabulary> findByJlpt(Integer jlpt, Integer pageNum, Integer pageSize) {
        Session session = sessionFactory.openSession();
        Iterable<Vocabulary> listVocabs = session.query(Vocabulary.class,
                    """
                        MATCH (v:Vocabulary {jlpt: $jlpt})
                        RETURN v SKIP $skip LIMIT $limit
                        ORDER BY v.original ASC
                    """,
                Map.of("jlpt", jlpt,"skip", pageNum * pageSize, "limit", pageSize));
        return (List<Vocabulary>) listVocabs;
    }

    @Override
    public List<Vocabulary> findByFurigana(String furigana, Integer pageNum, Integer pageSize) {
        Session session = sessionFactory.openSession();
        Iterable<Vocabulary> listVocabs = session.query(Vocabulary.class,
                """
                    MATCH (v: Vocabulary)
                    WHERE ANY(furigana in v.furigana WHERE furigana=$furigana)
                    RETURN v SKIP $skip LIMIT $limit
                    ORDER BY v.original ASC
                """,
                Map.of("furigana", furigana, "skip", pageNum * pageSize, "limit", pageSize));
        return (List<Vocabulary>) listVocabs;
    }

    @Override
    public List<Vocabulary> findByMeaning(String meaning, Integer pageNum, Integer pageSize) {
        Session session = sessionFactory.openSession();
        Iterable<Vocabulary> listVocabs = session.query(Vocabulary.class,
                """
                    MATCH (v: Vocabulary)
                    WHERE ANY(meaning in v.meaning WHERE meaning=$meaning)
                    RETURN v SKIP $skip LIMIT $limit
                    ORDER BY v.original ASC
                """,
                Map.of("meaning", meaning, "skip", pageNum * pageSize, "limit", pageSize));
        return (List<Vocabulary>) listVocabs;
    }
}
