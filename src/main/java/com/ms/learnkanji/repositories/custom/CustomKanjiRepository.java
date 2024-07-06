package com.ms.learnkanji.repositories.custom;

import com.ms.learnkanji.models.Kanji;
import com.ms.learnkanji.models.Vocabulary;
import com.ms.learnkanji.repositories.ICustomKanjiRepository;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CustomKanjiRepository implements ICustomKanjiRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public CustomKanjiRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<Kanji> findByValue(String value) {
        Session session = sessionFactory.openSession();
        Iterable<Kanji> listKanjis = session.query(Kanji.class, "MATCH (k:Kanji {value: $value}) RETURN k",
                Map.of("value", value));
        Iterable<Vocabulary> listPartOf = session.query(Vocabulary.class,
                "MATCH (k:Kanji {value: $value})-[:PART_OF]->(v:Vocabulary) RETURN v",
                Map.of("value", value));
        if (!listKanjis.iterator().hasNext()) {
            return Optional.empty();
        }
        Kanji kanji = listKanjis.iterator().next();
        for (Vocabulary vocabulary : listPartOf) {
            kanji.addPartOf(vocabulary);
        }
        return Optional.of(kanji);
    }

    @Override
    public List<Kanji> findByJlpt(int jlpt) {
        Session session = sessionFactory.openSession();
        Iterable<Kanji> listKanjis = session.query(Kanji.class,
                """
                MATCH (k:Kanji {jlpt: $jlpt}) RETURN k LIMIT 25
                """,
                Map.of("jlpt", jlpt));
        return (List<Kanji>) listKanjis;
    }
}
