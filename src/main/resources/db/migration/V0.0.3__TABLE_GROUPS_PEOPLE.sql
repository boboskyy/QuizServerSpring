
DROP TABLE IF EXISTS people.groups_people;
DROP SEQUENCE IF EXISTS people.groups_people_seq_id;

CREATE SEQUENCE people.groups_people_seq_id;

CREATE TABLE people.groups_people
(
      id           BIGINT NOT NULL DEFAULT nextval('people.groups_people_seq_id'),
      groupId      BIGINT NOT NULL REFERENCES people.groups (id) ON UPDATE CASCADE,
      personId     BIGINT NOT NULL REFERENCES people.people (id) ON UPDATE CASCADE
);

ALTER TABLE people.groups_people
  ADD CONSTRAINT group_person_pkey PRIMARY KEY (id);

ALTER TABLE people.groups_people
  ADD CONSTRAINT group_person_uq UNIQUE (id);