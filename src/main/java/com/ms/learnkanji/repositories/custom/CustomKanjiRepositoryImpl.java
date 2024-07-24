package com.ms.learnkanji.repositories.custom;

import com.ms.learnkanji.models.Kanji;
import com.ms.learnkanji.repositories.CustomKanjiRepository;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CustomKanjiRepositoryImpl implements CustomKanjiRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public CustomKanjiRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Kanji> findByValue(String value) {
        Session session = sessionFactory.openSession();
        Iterable<Kanji> listKanjis = session.query(Kanji.class, "MATCH (k:Kanji {value: $value}) RETURN k",
                Map.of("value", value));
        if (!listKanjis.iterator().hasNext()) {
            return Optional.empty();
        }
        return Optional.of(listKanjis.iterator().next());
    }

    @Override
    public List<Kanji> findByJlpt(Integer jlpt, Integer pageNum, Integer pageSize) {
        Session session = sessionFactory.openSession();
        Iterable<Kanji> listKanjis = session.query(Kanji.class,
                "MATCH (k:Kanji {jlpt: $jlpt}) RETURN k SKIP $skip LIMIT $limit ORDER BY k.value ASC",
                Map.of("jlpt", jlpt, "skip", pageNum * pageSize, "limit", pageSize));
        return (List<Kanji>) listKanjis;
    }

    @Override
    public List<Kanji> findByStrokes(int strokes, Integer pageNum, Integer pageSize) {
        Session session = sessionFactory.openSession();
        Iterable<Kanji> listKanjis = session.query(Kanji.class,
                "MATCH (k:Kanji {strokes: $strokes}) RETURN k SKIP $skip LIMIT $limit ORDER BY k.value ASC",
                Map.of("strokes", strokes, "skip", pageNum * pageSize, "limit", pageSize));
        return (List<Kanji>) listKanjis;
    }

    @Override
    public List<Kanji> findByGrade(int grade, Integer pageNum, Integer pageSize) {
        Session session = sessionFactory.openSession();
        Iterable<Kanji> listKanjis = session.query(Kanji.class,
                "MATCH (k:Kanji {grade: $grade}) RETURN k SKIP $skip LIMIT $limit ORDER BY k.value ASC",
                Map.of("grade", grade, "skip", pageNum * pageSize, "limit", pageSize));
        return (List<Kanji>) listKanjis;
    }
}
