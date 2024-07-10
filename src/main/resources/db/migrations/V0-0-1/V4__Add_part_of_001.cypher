MATCH (v:Vocabulary)
// Split the 'original' field into individual characters
WITH v, split(v.original, "") AS kanjiList
// Iterate over each character in the kanjiList
UNWIND kanjiList AS kanjiChar
// Match the Kanji node corresponding to each character
MATCH (k:Kanji {value: kanjiChar})
// Create the PART_OF relationship
MERGE (k)-[:PART_OF]->(v)