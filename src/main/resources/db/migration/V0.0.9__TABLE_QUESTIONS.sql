
DROP TABLE IF EXISTS test.questions;
DROP SEQUENCE IF EXISTS test.questions_seq_id;

CREATE SEQUENCE test.questions_seq_id;

CREATE TABLE test.questions
(
      id          BIGINT NOT NULL DEFAULT nextval('test.questions_seq_id'),
      groupId     BIGINT NOT NULL REFERENCES test.question_groups (id) ON UPDATE CASCADE,
      value       TEXT   NOT NULL
);

ALTER TABLE test.questions
  ADD CONSTRAINT question_pkey PRIMARY KEY (id);

ALTER TABLE test.questions
  ADD CONSTRAINT question_uq UNIQUE (id);