
DROP TABLE IF EXISTS test.question_groups;
DROP SEQUENCE IF EXISTS test.question_groups_seq_id;

CREATE SEQUENCE test.question_groups_seq_id;

CREATE TABLE test.question_groups
(
      id              BIGINT      NOT NULL DEFAULT nextval('test.question_groups_seq_id'),
      creatorId       BIGINT      NOT NULL REFERENCES people.people (id) ON UPDATE CASCADE,
      name            VARCHAR(25) NOT NULL,
      deletedAt      TIMESTAMP WITH TIME ZONE NULL
);

ALTER TABLE test.question_groups
  ADD CONSTRAINT question_groups_pkey PRIMARY KEY (id);

ALTER TABLE test.question_groups
  ADD CONSTRAINT question_groups_uq UNIQUE (id);