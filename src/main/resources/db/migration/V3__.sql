ALTER TABLE usuario
    ADD active BOOLEAN;

ALTER TABLE usuario
    ALTER COLUMN active SET NOT NULL;