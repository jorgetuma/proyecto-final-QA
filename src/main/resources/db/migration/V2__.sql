ALTER TABLE usuario_roles
    DROP CONSTRAINT fk_usuario_roles_on_usuario;

ALTER TABLE usuario
    ADD role VARCHAR(255);

DROP TABLE usuario_roles CASCADE;