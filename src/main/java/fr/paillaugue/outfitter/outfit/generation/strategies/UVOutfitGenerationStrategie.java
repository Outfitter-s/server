package fr.paillaugue.outfitter.outfit.generation.strategies;

import fr.paillaugue.outfitter.clothingItem.entities.ClothingItem;
import fr.paillaugue.outfitter.outfit.generation.ColorUtils;
import fr.paillaugue.outfitter.outfit.generation.OutfitGenerationStrategie;
import fr.paillaugue.outfitter.outfit.generation.ProfileWeight;
import fr.paillaugue.outfitter.user.entities.ClothingItemColor;
import fr.paillaugue.outfitter.weather.entities.WeatherType;

import java.util.List;

public class UVOutfitGenerationStrategie extends OutfitGenerationStrategie {

    @Override
    public float score(ClothingItem item, WeatherType weatherType, ProfileWeight weights) {
        var lightColors = List.of(ClothingItemColor.WHITE, ClothingItemColor.YELLOW, ClothingItemColor.PINK, ClothingItemColor.ORANGE);
        var neutralColors = List.of(ClothingItemColor.BLACK, ClothingItemColor.GRAY, ClothingItemColor.BROWN);
        var chromatic = List.of(ClothingItemColor.RED, ClothingItemColor.BLUE, ClothingItemColor.GREEN, ClothingItemColor.PURPLE);

        var c = item.getColor();
        var base = 0.5f;

        if (lightColors.contains(c)) base = 0.8f;
        else if (neutralColors.contains(c)) base = 0.6f;


        if (weatherType == WeatherType.SNOWY) {
            if (lightColors.contains(c)) base += 0.15f;
            else if (chromatic.contains(c)) base -= 0.1f;
        } else if (weatherType == WeatherType.COLD) {
            if (chromatic.contains(c)) base += 0.1f;
            else if (lightColors.contains(c)) base -= 0.05f;
        }
        return ColorUtils.clamp01(base);
    }

}
