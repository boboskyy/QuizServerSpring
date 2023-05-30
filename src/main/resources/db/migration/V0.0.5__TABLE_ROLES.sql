DROP TABLE IF EXISTS login.roles;
DROP SEQUENCE IF EXISTS login.roles_seq_id;

CREATE SEQUENCE login.roles_seq_id;

CREATE TABLE login.roles
(
      id          BIGINT      NOT NULL DEFAULT nextval('login.roles_seq_id'),
      name        VARCHAR(15) NOT NULL
);

ALTER TABLE login.roles
  ADD CONSTRAINT role_pkey PRIMARY KEY (id);

ALTER TABLE login.roles
  ADD CONSTRAINT role_uq UNIQUE (id, name);