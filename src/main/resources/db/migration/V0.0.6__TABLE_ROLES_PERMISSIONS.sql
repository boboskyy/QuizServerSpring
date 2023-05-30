
DROP TABLE IF EXISTS login.roles_permissions;
DROP SEQUENCE IF EXISTS login.roles_permissions_seq_id;

CREATE SEQUENCE login.roles_permissions_seq_id;

CREATE TABLE login.roles_permissions
(
      id              BIGINT NOT NULL DEFAULT nextval('login.roles_permissions_seq_id'),
      roleId          BIGINT NOT NULL REFERENCES login.roles (id) ON UPDATE CASCADE,
      permissionId    BIGINT NOT NULL REFERENCES login.permissions (id) ON UPDATE CASCADE
);

ALTER TABLE login.roles_permissions
  ADD CONSTRAINT role_permission_pkey PRIMARY KEY (id);

ALTER TABLE login.roles_permissions
  ADD CONSTRAINT role_permission_uq UNIQUE (id);