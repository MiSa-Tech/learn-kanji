package com.ms.learnkanji.repositories.custom;

import com.ms.learnkanji.models.Kanji;
import com.ms.learnkanji.models.results.KanjiResult;
import com.ms.learnkanji.repositories.KanjiResultRepository;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ImplKanjiResultRepository implements KanjiResultRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public ImplKanjiResultRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<KanjiResult> findBestShouldLearnKanji(String username) {
        Session session = sessionFactory.openSession();
        String query = """
                MATCH (u:User {username: $username})-[:LEARNT_KANJI]->(n:Kanji)
                WITH collect(n) AS kanjiList    
                           
                MATCH (v:Vocabulary)<-[:PART_OF]-(k:Kanji)
                
                WITH v, collect(k) AS kanjis, kanjiList
                WITH v, kanjis, [kanji IN kanjis WHERE NOT kanji IN kanjiList] AS kanjisNotInList
                WHERE size(kanjisNotInList) = 1
                WITH kanjisNotInList[0] as kanji
                WITH kanji, count(*) as occurrence
                WITH {kanji: kanji, 
                       occurrence: occurrence} AS kanjiResult
                RETURN kanjiResult
                ORDER BY kanjiResult.occurrence DESC
                """;

        Iterable<KanjiResult> listKanjiResults = session.query(KanjiResult.class, query,
                Map.of("username", username));
        return (List<KanjiResult>) listKanjiResults;

    }
}
