
DROP TABLE IF EXISTS people.people;
DROP SEQUENCE IF EXISTS people.people_seq_id;

CREATE SEQUENCE people.people_seq_id;


CREATE TABLE people.people
(
      id          BIGINT                   NOT NULL DEFAULT nextval('people.people_seq_id'),
      type        SMALLINT                 NOT NULL,
      firstName   VARCHAR(15)              NOT NULL,
      lastName    VARCHAR(20)              NOT NULL,
      pesel       VARCHAR(11)              NOT NULL,
      createdAt   TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT   now(),
      modifiedAt  TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT   now(),
      deletedAt   TIMESTAMP WITH TIME ZONE NULL
);

ALTER TABLE people.people
  ADD CONSTRAINT person_pkey PRIMARY KEY (id);

ALTER TABLE people.people
  ADD CONSTRAINT person_uq UNIQUE (id);