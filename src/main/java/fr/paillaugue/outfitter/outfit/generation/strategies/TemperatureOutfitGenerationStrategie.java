package fr.paillaugue.outfitter.outfit.generation.strategies;

import fr.paillaugue.outfitter.clothingItem.entities.ClothingItem;
import fr.paillaugue.outfitter.outfit.generation.ColorUtils;
import fr.paillaugue.outfitter.outfit.generation.OutfitGenerationStrategie;
import fr.paillaugue.outfitter.outfit.generation.ProfileWeight;
import fr.paillaugue.outfitter.outfit.generation.TempIdeal;
import fr.paillaugue.outfitter.user.entities.ClothingItemType;
import fr.paillaugue.outfitter.weather.entities.WeatherType;

public class TemperatureOutfitGenerationStrategie extends OutfitGenerationStrategie {

    @Override
    public float score(ClothingItem baseItem, WeatherType weatherType, ProfileWeight weights) {
        return 0.0f;
//        var ideal = getTempIdeal(baseItem.getType());
//        float tempDiff = Math.abs(weather.temp - ideal.ideal());
//        return ColorUtils.clamp01(1 - tempDiff / Math.max(1, ideal.tol()));
    }

    private TempIdeal getTempIdeal(ClothingItemType type) {
        if (ColorUtils.TEMP_IDEALS.containsKey(type)) {
            return ColorUtils.TEMP_IDEALS.get(type);
        }
        return new TempIdeal(20, 10);
    }

}