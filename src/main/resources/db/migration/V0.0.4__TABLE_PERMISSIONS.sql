
DROP TABLE IF EXISTS login.permissions;
DROP SEQUENCE IF EXISTS login.permissions_seq_id;

CREATE SEQUENCE login.permissions_seq_id;

CREATE TABLE login.permissions
(
      id          BIGINT NOT NULL DEFAULT nextval('login.permissions_seq_id'),
      groupName   VARCHAR(30)              NOT NULL,
      name        TEXT                     NOT NULL,
      permission  TEXT                     NOT NULL
);

ALTER TABLE login.permissions
  ADD CONSTRAINT permission_pkey PRIMARY KEY (id);

ALTER TABLE login.permissions
  ADD CONSTRAINT permission_uq UNIQUE (id, permission);