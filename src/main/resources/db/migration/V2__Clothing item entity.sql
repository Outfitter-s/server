CREATE TABLE clothing_item
(
    id          UUID     NOT NULL,
    type        SMALLINT NOT NULL,
    color       SMALLINT NOT NULL,
    name        VARCHAR(255),
    description VARCHAR(255),
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    owner_id    UUID,
    CONSTRAINT pk_clothingitem PRIMARY KEY (id)
);

ALTER TABLE clothing_item
    ADD CONSTRAINT FK_CLOTHINGITEM_ON_OWNER FOREIGN KEY (owner_id) REFERENCES "user" (id);