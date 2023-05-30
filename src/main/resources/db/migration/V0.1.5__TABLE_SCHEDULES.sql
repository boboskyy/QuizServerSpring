DROP TABLE IF EXISTS test.schedules;
DROP SEQUENCE IF EXISTS test.schedules_seq_id;

CREATE SEQUENCE test.schedules_seq_id;

CREATE TABLE test.schedules
(
      id          BIGINT NOT NULL DEFAULT nextval('test.schedules_seq_id'),
      quizId      BIGINT NOT NULL REFERENCES test.quizzes (id) ON UPDATE CASCADE,
      startsAt    TIMESTAMP WITH TIME ZONE NOT NULL,
      endsAt      TIMESTAMP WITH TIME ZONE NOT NULL

);

ALTER TABLE test.schedules
  ADD CONSTRAINT schedule_pkey PRIMARY KEY (id);

ALTER TABLE test.schedules
  ADD CONSTRAINT schedule_uq UNIQUE (id);