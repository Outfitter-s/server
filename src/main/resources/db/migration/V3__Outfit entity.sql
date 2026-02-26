CREATE TABLE outfit
(
    id         UUID NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_outfit PRIMARY KEY (id)
);

CREATE TABLE outfit_clothing_items
(
    outfit_id         UUID NOT NULL,
    clothing_items_id UUID NOT NULL
);

ALTER TABLE outfit_clothing_items
    ADD CONSTRAINT uc_outfit_clothing_items_clothingitems UNIQUE (clothing_items_id);

ALTER TABLE outfit_clothing_items
    ADD CONSTRAINT fk_outcloite_on_clothing_item FOREIGN KEY (clothing_items_id) REFERENCES clothing_item (id);

ALTER TABLE outfit_clothing_items
    ADD CONSTRAINT fk_outcloite_on_outfit FOREIGN KEY (outfit_id) REFERENCES outfit (id);