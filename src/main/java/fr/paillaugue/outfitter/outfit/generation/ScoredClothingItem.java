package fr.paillaugue.outfitter.outfit.generation;

import fr.paillaugue.outfitter.clothingItem.entities.ClothingItem;

public class ScoredClothingItem extends ClothingItem {

    private float score;

    public ScoredClothingItem(ClothingItem item, float score) {
        super(item.getType(), item.getColor(), item.getName(), item.getDescription(), item.getOwner());
        this.setId(item.getId());
        this.score = score;
    }

    public float getScore() {
        return score;
    }

    public void addScore(float delta) {
        this.score += delta;
    }

    public ClothingItem getItem() {
        var item = new ClothingItem(getType(), getColor(), getName(), getDescription(), getOwner());
        item.setId(getId());
        return item;
    }
}