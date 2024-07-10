package com.ms.learnkanji;

import com.ms.learnkanji.models.Kanji;
import com.ms.learnkanji.repositories.KanjiRepository;
import com.ms.learnkanji.services.KanjiService;
import org.neo4j.cypherdsl.core.renderer.Configuration;
import org.neo4j.cypherdsl.core.renderer.Dialect;
import org.neo4j.driver.MetricsAdapter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.neo4j.ConfigBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class LearnKanjiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnKanjiApplication.class, args);
    }

    @Bean
    Configuration cypherDslConfiguration() {
        return Configuration.newConfig()
                .withDialect(Dialect.NEO4J_5).build();
    }

    @Bean
    ConfigBuilderCustomizer configBuilderCustomizer() {
        return configBuilder -> configBuilder.withMetricsAdapter(MetricsAdapter.MICROMETER);
    }

    @Bean
    public ApplicationRunner loadData(KanjiService kanjiService) {
        return args -> {
            Kanji kanji1 = new Kanji(
                    "毎",
                    6,
                    2,
                    436,
                    5,
                    Arrays.asList("Every"),
                    Arrays.asList("まい"),
                    Arrays.asList("ごと", "-ごと.に")
            );

            Kanji kanji2 = new Kanji(
                    "来",
                    7,
                    2,
                    102,
                    5,
                    Arrays.asList("Come", "Due", "Next", "Cause", "Become"),
                    Arrays.asList("らい", "たい"),
                    Arrays.asList("く.る", "きた.る", "きた.す", "き.たす", "き.たる", "き", "こ")
            );

            Kanji kanji3 = new Kanji(
                    "月",
                    4,
                    1,
                    23,
                    5,
                    Arrays.asList("Month", "Moon"),
                    Arrays.asList("げつ", "がつ"),
                    Arrays.asList("つき")
            );

            Kanji kanji4 = new Kanji(
                    "日",
                    4,
                    1,
                    1,
                    5,
                    Arrays.asList("Day", "Sun", "Japan", "Counter For Days"),
                    Arrays.asList("にち", "じつ"),
                    Arrays.asList("ひ", "-び", "-か")
            );

            Kanji kanji5 = new Kanji(
                    "年",
                    6,
                    1,
                    6,
                    5,
                    Arrays.asList("Year", "Counter For Years"),
                    Arrays.asList("ねん"),
                    Arrays.asList("とし")
            );

            kanjiService.createKanji(kanji1);
            kanjiService.createKanji(kanji2);
            kanjiService.createKanji(kanji3);
            kanjiService.createKanji(kanji4);
            kanjiService.createKanji(kanji5);
        };
    }
}
