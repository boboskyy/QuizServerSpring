DROP TABLE IF EXISTS test.generated_questions;
DROP SEQUENCE IF EXISTS test.generated_questions_seq_id;

CREATE SEQUENCE test.generated_questions_seq_id;

CREATE TABLE test.generated_questions
(
      id              BIGINT NOT NULL DEFAULT nextval('test.generated_questions_seq_id'),
      questionId      BIGINT NOT NULL REFERENCES test.questions (id) ON UPDATE CASCADE,
      quizInstanceId  BIGINT NOT NULL REFERENCES test.quiz_instances (id) ON UPDATE CASCADE
);

ALTER TABLE test.generated_questions
  ADD CONSTRAINT generated_question_pkey PRIMARY KEY (id);

ALTER TABLE test.generated_questions
  ADD CONSTRAINT generated_question_uq UNIQUE (id);