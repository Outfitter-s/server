package fr.paillaugue.outfitter.outfit.generation.strategies;

import fr.paillaugue.outfitter.clothingItem.entities.ClothingItem;
import fr.paillaugue.outfitter.outfit.generation.ColorUtils;
import fr.paillaugue.outfitter.outfit.generation.OutfitGenerationStrategie;

import java.util.List;

public class TriadicOutfitGenerationStrategie extends OutfitGenerationStrategie {

    @Override
    public float score(ClothingItem baseItem, ClothingItem comparedItem) {
        var triadicColors = ColorUtils.triadicGroups.getOrDefault(baseItem.getColor(), List.of(baseItem.getColor()));
        return ColorUtils.clamp01(triadicColors.contains(comparedItem.getColor()) ? 1 : 0);
    }


}