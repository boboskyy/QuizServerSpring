
DROP TABLE IF EXISTS login.logins;
DROP SEQUENCE IF EXISTS login.logins_seq_id;

CREATE SEQUENCE login.logins_seq_id;

CREATE TABLE login.logins
(
      id              BIGINT      NOT NULL DEFAULT nextval('login.logins_seq_id'),
      personId        BIGINT      NOT NULL REFERENCES people.people (id) ON UPDATE CASCADE,
      roleId          BIGINT      NOT NULL REFERENCES login.roles (id) ON UPDATE CASCADE,
      login           VARCHAR(15) NOT NULL,
      password        VARCHAR(65) NOT NULL,
      resetPassword   BOOLEAN     NOT NULL DEFAULT FALSE
);

ALTER TABLE login.logins
  ADD CONSTRAINT login_pkey PRIMARY KEY (id);

ALTER TABLE login.logins
  ADD CONSTRAINT login_uq UNIQUE (id, personId, login);