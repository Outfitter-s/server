package fr.paillaugue.outfitter.outfit.generation.strategies;

import fr.paillaugue.outfitter.clothingItem.dto.ClothingItemDTO;
import fr.paillaugue.outfitter.clothingItem.entities.ClothingItem;
import fr.paillaugue.outfitter.outfit.generation.ColorUtils;
import fr.paillaugue.outfitter.outfit.generation.OutfitGenerationStrategie;
import fr.paillaugue.outfitter.user.entities.ClothingItemColor;

import java.util.ArrayList;
import java.util.List;

public class AnalogousOutfitGenerationStrategie extends OutfitGenerationStrategie {
    @Override
    public float score(ClothingItem baseItem, ClothingItem comparedItem) {
        if (baseItem.getColor() == null || comparedItem.getColor() == null) return 0;
        if (baseItem.getColor() == comparedItem.getColor()) return 1;

        var neighbors1 = getAnalogousColors(baseItem.getColor(), 1);
        var neighbors2 = getAnalogousColors(baseItem.getColor(), 2);

        if (neighbors1.contains(comparedItem.getColor())) return 0.8f;
        if (neighbors2.contains(comparedItem.getColor())) return 0.5f;
        return 0;
    }

    private List<ClothingItemColor> getAnalogousColors(
        ClothingItemColor color,
        float distance
    ) {
        var index = ColorUtils.COLOR_WHEEL.indexOf(color);
        if (index == -1) return List.of(color);

        if (ColorUtils.NEUTRAL_COLORS.contains(color)) {
            var chromaticColors = ColorUtils.COLOR_WHEEL.stream().filter((c) -> !ColorUtils.NEUTRAL_COLORS.contains(c)).toList();
            chromaticColors.addFirst(color);
            return chromaticColors;
        }

        List<ClothingItemColor> analogousColors = new ArrayList<>(List.of(color));

        for(int i = 1; i <= distance; i++) {
            int prevIndex = (index - i + ColorUtils.COLOR_WHEEL.size()) % ColorUtils.COLOR_WHEEL.size();
            ClothingItemColor prevColor = ColorUtils.COLOR_WHEEL.get(prevIndex);
            if (!ColorUtils.NEUTRAL_COLORS.contains(prevColor)) analogousColors.add(prevColor);

            int nextIndex = (index + i) % ColorUtils.COLOR_WHEEL.size();
            ClothingItemColor nextColor = ColorUtils.COLOR_WHEEL.get(nextIndex);
            if (!ColorUtils.NEUTRAL_COLORS.contains(nextColor)) analogousColors.add(nextColor);
        }

        return analogousColors;
    }
}
