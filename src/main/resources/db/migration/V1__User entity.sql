CREATE TABLE "user"
(
    id            UUID NOT NULL,
    username      VARCHAR(255),
    email         VARCHAR(255),
    password_hash VARCHAR(255),
    updated_at    TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_user PRIMARY KEY (id)
);