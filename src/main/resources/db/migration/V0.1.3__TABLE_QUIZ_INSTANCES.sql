
DROP TABLE IF EXISTS test.quiz_instances;
DROP SEQUENCE IF EXISTS test.quiz_instances_seq_id;

CREATE SEQUENCE test.quiz_instances_seq_id;

CREATE TABLE test.quiz_instances
(
      id            BIGINT NOT NULL DEFAULT nextval('test.quiz_instances_seq_id'),
      quizId        BIGINT NOT NULL REFERENCES test.quizzes (id) ON UPDATE CASCADE,
      studentId     BIGINT NOT NULL REFERENCES people.people (id) ON UPDATE CASCADE,
      startedAt     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT now()
);

ALTER TABLE test.quiz_instances
  ADD CONSTRAINT quiz_instance_pkey PRIMARY KEY (id);

ALTER TABLE test.quiz_instances
  ADD CONSTRAINT quiz_instance_uq UNIQUE (id);