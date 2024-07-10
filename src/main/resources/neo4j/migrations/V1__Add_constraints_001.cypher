CREATE CONSTRAINT kanji_unique_id FOR (k:Kanji) REQUIRE k.id IS UNIQUE;

CREATE CONSTRAINT kanji_unique_value FOR (k:Kanji) REQUIRE k.value IS UNIQUE;

CREATE CONSTRAINT vocabulary_unique_id FOR (v:Vocabulary) REQUIRE v.id IS UNIQUE;

CREATE CONSTRAINT vocabulary_unique_original FOR (v:Vocabulary) REQUIRE v.original IS UNIQUE;

CREATE CONSTRAINT user_unique_id FOR (u:User) REQUIRE u.id IS UNIQUE;

CREATE CONSTRAINT user_unique_username FOR (u:User) REQUIRE u.username IS UNIQUE;