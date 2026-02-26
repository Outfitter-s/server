ALTER TABLE "user"
    ADD created_at TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE "user"
    ADD CONSTRAINT uc_2cd01e89afdcebf3621eb5c7f UNIQUE (username, email);