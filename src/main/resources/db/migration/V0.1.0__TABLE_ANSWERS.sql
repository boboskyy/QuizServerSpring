
DROP TABLE IF EXISTS test.answers;
DROP SEQUENCE IF EXISTS test.answers_seq_id;

CREATE SEQUENCE test.answers_seq_id;

CREATE TABLE test.answers
(
      id          BIGINT  NOT  NULL DEFAULT nextval('test.answers_seq_id'),
      questionId  BIGINT  NOT  NULL REFERENCES test.questions (id) ON UPDATE CASCADE,
      value       TEXT    NOT NULL,
      isCorrect   BOOLEAN NOT NULL
);

ALTER TABLE test.answers
  ADD CONSTRAINT answer_pkey PRIMARY KEY (id);

ALTER TABLE test.answers
  ADD CONSTRAINT answer_uq UNIQUE (id);