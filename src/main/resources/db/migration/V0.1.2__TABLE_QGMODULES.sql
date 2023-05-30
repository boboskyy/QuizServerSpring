
DROP TABLE IF EXISTS test.qgmodules;
DROP SEQUENCE IF EXISTS test.qgmodules_seq_id;

CREATE SEQUENCE test.qgmodules_seq_id;

CREATE TABLE test.qgmodules
(
      id                   BIGINT NOT NULL DEFAULT nextval('test.qgmodules_seq_id'),
      questionGroupId      BIGINT NOT NULL REFERENCES test.question_groups (id) ON UPDATE CASCADE,
      quizId               BIGINT NOT NULL REFERENCES test.quizzes (id) ON UPDATE CASCADE,
      genQuestionsCount    INTEGER NOT NULL
);

ALTER TABLE test.qgmodules
  ADD CONSTRAINT qgmodule_pkey PRIMARY KEY (id);

ALTER TABLE test.qgmodules
  ADD CONSTRAINT qgmodule_uq UNIQUE (id);