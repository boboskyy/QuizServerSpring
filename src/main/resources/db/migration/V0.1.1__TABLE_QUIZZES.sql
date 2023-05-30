
DROP TABLE IF EXISTS test.quizzes;
DROP SEQUENCE IF EXISTS test.quizzes_seq_id;

CREATE SEQUENCE test.quizzes_seq_id;

CREATE TABLE test.quizzes
(
      id              BIGINT        NOT NULL DEFAULT nextval('test.quizzes_seq_id'),
      creatorId       BIGINT        NOT NULL REFERENCES people.people (id) ON UPDATE CASCADE,
      title           VARCHAR(50)   NOT NULL,
      description     TEXT          NOT NULL,
      quizTime        SMALLINT      NOT NULL
);

ALTER TABLE test.quizzes
  ADD CONSTRAINT quiz_pkey PRIMARY KEY (id);

ALTER TABLE test.quizzes
  ADD CONSTRAINT quiz_uq UNIQUE (id);