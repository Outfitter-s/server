package fr.paillaugue.outfitter.outfit.generation.strategies;

import fr.paillaugue.outfitter.clothingItem.entities.ClothingItem;
import fr.paillaugue.outfitter.outfit.generation.ColorUtils;
import fr.paillaugue.outfitter.outfit.generation.OutfitGenerationStrategie;

public class MonochromeOutfitGenerationStrategie extends OutfitGenerationStrategie {

    @Override
    public float score(ClothingItem baseItem, ClothingItem comparedItem) {
        if (baseItem.getColor() == comparedItem.getColor()) return 1f;
        if (ColorUtils.NEUTRAL_COLORS.contains(comparedItem.getColor())) return 0.5f;
        return 0f;
    }

}