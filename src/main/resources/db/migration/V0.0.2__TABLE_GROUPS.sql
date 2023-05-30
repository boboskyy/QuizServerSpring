
DROP TABLE IF EXISTS people.groups;
DROP SEQUENCE IF EXISTS people.groups_seq_id;

CREATE SEQUENCE people.groups_seq_id;

CREATE TABLE people.groups
(
      id          BIGINT NOT NULL DEFAULT nextval('people.groups_seq_id'),
      name        VARCHAR(20)              NOT NULL,
      createdAt   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT   now(),
      modifiedAt  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT   now(),
      deletedAt   TIMESTAMP WITH TIME ZONE NULL
);

ALTER TABLE people.groups
  ADD CONSTRAINT group_pkey PRIMARY KEY (id);

ALTER TABLE people.groups
  ADD CONSTRAINT group_uq UNIQUE (id);