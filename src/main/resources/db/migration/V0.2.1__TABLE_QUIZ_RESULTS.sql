DROP TABLE IF EXISTS test.quiz_results;
DROP SEQUENCE IF EXISTS test.quiz_results_seq_id;

CREATE SEQUENCE test.quiz_results_seq_id;

CREATE TABLE test.quiz_results
(
      id              BIGINT NOT NULL DEFAULT nextval('test.quiz_results_seq_id'),
      personId        BIGINT NOT NULL REFERENCES people.people (id) ON UPDATE CASCADE,
      score           INT DEFAULT 0,
      questionsCount  INT DEFAULT 0,
      quizTitle       TEXT NOT NULL,
      quizCreator     TEXT NOT NULL
);

ALTER TABLE test.quiz_results
  ADD CONSTRAINT quiz_result_pkey PRIMARY KEY (id);

ALTER TABLE test.quiz_results
  ADD CONSTRAINT quiz_result_uq UNIQUE (id);