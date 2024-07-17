CREATE (k1:Kanji {id: randomUUID(), value: "毎", strokes: 6, grade: 2, frequency: 436, jlpt: 5, 
                 meaning: ["Every"], readings_on: ["まい"], readings_kun: ["ごと", "ごと.に"]});

CREATE (k2:Kanji {id: randomUUID(), value: "来", strokes: 7, grade: 2, frequency: 102, jlpt: 5,
                  meaning: ["Come", "Due", "Next", "Cause", "Become"],
                  readings_on: ["らい", "たい"], readings_kun: ["く.る", "きた.る", "きた.す", "き.たす", "き.たる", "き", "こ"]});

CREATE (k3:Kanji {id: randomUUID(), value: "月", strokes: 4, grade: 1, frequency: 23, jlpt: 5,
                  meaning: ["Month", "Moon"], readings_on: ["げつ", "がつ"], readings_kun: ["つき"]});

CREATE (k4:Kanji {id: randomUUID(), value: "日", strokes: 4, grade: 1, frequency: 1, jlpt: 5,
                  meaning: ["Day", "Sun", "Japan", "Counter For Days"], readings_on: ["にち", "じつ"], readings_kun: ["ひ", "び", "か"]});

CREATE (k5:Kanji {id: randomUUID(), value: "年", strokes: 6, grade: 1, frequency: 6, jlpt: 5,
                  meaning: ["Year", "Counter For Years"], readings_on: ["ねん"], readings_kun: ["とし"]});

CREATE (k6:Kanji {id: randomUUID(), value: "私", strokes: 7, grade: 6, frequency: 242, jlpt: 4,
                  meaning: ["Private", "I", "Me"], readings_on: ["し"], readings_kun: ["わたくし", "わたし"]});

CREATE (k7:Kanji {id: randomUUID(), value: "木", strokes: 4, grade: 1, frequency: 317, jlpt: 5,
                  meaning: ["Tree", "Wood"], readings_on: ["ぼく", "もく"], readings_kun: ["き", "こ-"]});

CREATE (k8:Kanji {id: randomUUID(), value: "店", strokes: 8, grade: 2, frequency: 378, jlpt: 4,
                  meaning: ["Store", "Shop"], readings_on: ["てん"], readings_kun: ["みせ", "たな"]});
CREATE (k9:Kanji {id: randomUUID(), value: "見", strokes: 7, grade: 1, frequency: 22, jlpt: 5,
                  meaning: ["See", "Hopes", "Chances", "Idea", "Opinion", "Look At", "Visible"], readings_on: ["けん"], readings_kun: ["み.る", "み.える", "み.せる"]});

CREATE (k10:Kanji {id: randomUUID(), value: "曜", strokes: 18, grade: 2, frequency: 940, jlpt: 4, meaning: ["Weekday"], readings_on: ["よう"],
                  readings_kun: []});
