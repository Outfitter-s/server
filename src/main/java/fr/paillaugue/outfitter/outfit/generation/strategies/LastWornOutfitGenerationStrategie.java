package fr.paillaugue.outfitter.outfit.generation.strategies;

import fr.paillaugue.outfitter.clothingItem.entities.ClothingItem;
import fr.paillaugue.outfitter.outfit.generation.ColorUtils;
import fr.paillaugue.outfitter.outfit.generation.OutfitGenerationStrategie;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class LastWornOutfitGenerationStrategie extends OutfitGenerationStrategie {

    @Override
    public float score(ClothingItem item, ClothingItem comparedItem) {
        if(item.getLastWornAt() == null) {
            return 0;
        }
        var daysSinceLastWorn = item.getLastWornAt().until(ZonedDateTime.now(), ChronoUnit.DAYS);
        return ColorUtils.clamp01((float) (Math.log(daysSinceLastWorn + 1) / Math.log(31)));
    }

}