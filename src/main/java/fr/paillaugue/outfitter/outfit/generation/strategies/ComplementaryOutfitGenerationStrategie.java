package fr.paillaugue.outfitter.outfit.generation.strategies;

import fr.paillaugue.outfitter.clothingItem.entities.ClothingItem;
import fr.paillaugue.outfitter.outfit.generation.ColorUtils;
import fr.paillaugue.outfitter.outfit.generation.OutfitGenerationStrategie;

public class ComplementaryOutfitGenerationStrategie extends OutfitGenerationStrategie {

    @Override
    public float score(ClothingItem base, ClothingItem compare) {
        var complementaryColors = ColorUtils.getComplementaryColors(base.getColor());

        if (compare.getColor() == complementaryColors.get(1)) {
            return 1f;
        }
        if (complementaryColors.subList(0, 2).contains(compare.getColor())) {
            return 0.5f;
        }
        return 0f;
    }

}
