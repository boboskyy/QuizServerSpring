DROP TABLE IF EXISTS test.available_quizzes;
DROP SEQUENCE IF EXISTS test.available_quizzes_seq_id;

CREATE SEQUENCE test.available_quizzes_seq_id;

CREATE TABLE test.available_quizzes
(
      id              BIGINT NOT NULL DEFAULT nextval('test.available_quizzes_seq_id'),
      scheduleId      BIGINT NOT NULL REFERENCES test.schedules (id) ON UPDATE CASCADE,
      personId        BIGINT NOT NULL REFERENCES people.people (id) ON UPDATE CASCADE,
      quizInstanceId  BIGINT NULL REFERENCES test.quiz_instances (id) ON UPDATE CASCADE

);

ALTER TABLE test.available_quizzes
  ADD CONSTRAINT available_quiz_pkey PRIMARY KEY (id);

ALTER TABLE test.available_quizzes
  ADD CONSTRAINT available_quiz_uq UNIQUE (id);