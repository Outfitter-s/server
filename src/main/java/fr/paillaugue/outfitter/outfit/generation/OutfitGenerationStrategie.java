package fr.paillaugue.outfitter.outfit.generation;

import fr.paillaugue.outfitter.clothingItem.entities.ClothingItem;
import fr.paillaugue.outfitter.weather.entities.WeatherType;

public class OutfitGenerationStrategie {
    public float score(ClothingItem baseItem, ClothingItem comparedItem) {
        return 0.0f;
    }

    public float score(ClothingItem baseItem, WeatherType weatherType, ProfileWeight weights) {
        return 0.0f;
    }
}
