package com.ms.learnkanji.repositories.custom;

import com.ms.learnkanji.models.results.KanjiResult;
import com.ms.learnkanji.repositories.KanjiResultRepository;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class KanjiResultRepositoryImpl implements KanjiResultRepository {

    private final SessionFactory sessionFactory;

    @Autowired
    public KanjiResultRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<KanjiResult> findBestShouldLearnKanji(String username, Integer pageNum, Integer pageSize) {
        Session session = sessionFactory.openSession();
        String query = """
                MATCH (u:User {username: $username})-[:LEARNT_KANJI]->(n:Kanji)
                WITH collect(n) AS kanjiList    
                           
                MATCH (v:Vocabulary)<-[:PART_OF]-(k:Kanji)
                
                WITH v, collect(k) AS kanjis, kanjiList
                WITH v, kanjis, [kanji IN kanjis WHERE NOT kanji IN kanjiList] AS kanjisNotInList
                WHERE size(kanjisNotInList) = 1
                WITH kanjisNotInList[0] AS kanji
                RETURN kanji, count(*) as occurrence
                ORDER BY occurrence DESC
                SKIP $skip
                LIMIT $limit
                """;

        List<KanjiResult> listKanjiResults = session.queryDto(query, Map.of("username", username, "skip", pageNum * pageSize, "limit", pageSize), KanjiResult.class);
        return listKanjiResults;
    }
}
