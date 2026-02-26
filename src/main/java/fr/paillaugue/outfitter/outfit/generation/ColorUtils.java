package fr.paillaugue.outfitter.outfit.generation;

import fr.paillaugue.outfitter.outfit.entities.OutfitStyle;
import fr.paillaugue.outfitter.user.entities.ClothingItemColor;
import fr.paillaugue.outfitter.user.entities.ClothingItemType;
import fr.paillaugue.outfitter.weather.entities.WeatherType;
import org.jspecify.annotations.NonNull;

import java.time.ZonedDateTime;
import java.util.*;

public class ColorUtils {
    public static final List<ClothingItemColor> COLOR_WHEEL = List.of(
            ClothingItemColor.RED,
            ClothingItemColor.ORANGE,
            ClothingItemColor.YELLOW,
            ClothingItemColor.GREEN,
            ClothingItemColor.BLUE,
            ClothingItemColor.PURPLE,
            ClothingItemColor.PINK,
            ClothingItemColor.WHITE,
            ClothingItemColor.BLACK,
            ClothingItemColor.GRAY,
            ClothingItemColor.BROWN
    );
    public static final List<ClothingItemColor> NEUTRAL_COLORS = List.of(ClothingItemColor.WHITE, ClothingItemColor.BLACK, ClothingItemColor.GRAY, ClothingItemColor.BROWN);
    public static final Map<OutfitStyle, ProfileWeight> PROFILE_WEIGHTS = Map.of(
            OutfitStyle.COMFORT, new ProfileWeight(0.3f, 0.3f, 0.2f, 0.1f, 0.1f),
            OutfitStyle.NEW, new ProfileWeight(0.2f, 0.2f, 0.1f, 0.4f, 0.1f),
            OutfitStyle.STYLE, new ProfileWeight(0.1f, 0.2f, 0.2f, 0.2f, 0.3f),
            OutfitStyle.FORMAL, new ProfileWeight(0.1f, 0.1f, 0.1f, 0.1f, 0.6f),
            OutfitStyle.DEFAULT, new ProfileWeight(0.2f, 0.2f, 0.2f, 0.2f, 0.2f)
    );
    public static final Map<ClothingItemType, TempIdeal> TEMP_IDEALS = Map.of(
            ClothingItemType.JACKET, new TempIdeal(6, 10),
            ClothingItemType.SWEATER, new TempIdeal(12, 8),
            ClothingItemType.PANTS, new TempIdeal(18, 12),
            ClothingItemType.DRESS, new TempIdeal(22, 8),
            ClothingItemType.SHIRT, new TempIdeal(20, 8),
            ClothingItemType.SHOES, new TempIdeal(20, 12),
            ClothingItemType.ACCESSORY, new TempIdeal(20, 15)
    );
    public static final Map<ClothingItemColor, List<ClothingItemColor>> triadicGroups = new HashMap<>();
    static {
        // Groupe 1 : Primaires classiques
        triadicGroups.put(ClothingItemColor.RED, List.of(ClothingItemColor.BLUE, ClothingItemColor.YELLOW, ClothingItemColor.WHITE, ClothingItemColor.BLACK, ClothingItemColor.GRAY, ClothingItemColor.BROWN));
        triadicGroups.put(ClothingItemColor.BLUE, List.of(ClothingItemColor.YELLOW, ClothingItemColor.RED, ClothingItemColor.WHITE, ClothingItemColor.BLACK, ClothingItemColor.GRAY, ClothingItemColor.BROWN));
        triadicGroups.put(ClothingItemColor.YELLOW, List.of(ClothingItemColor.RED, ClothingItemColor.BLUE, ClothingItemColor.WHITE, ClothingItemColor.BLACK, ClothingItemColor.GRAY, ClothingItemColor.BROWN));
        // Groupe 2 : Secondaires
        triadicGroups.put(ClothingItemColor.ORANGE, List.of(ClothingItemColor.GREEN, ClothingItemColor.PURPLE, ClothingItemColor.WHITE, ClothingItemColor.BLACK, ClothingItemColor.GRAY, ClothingItemColor.BROWN));
        triadicGroups.put(ClothingItemColor.GREEN, List.of(ClothingItemColor.PURPLE, ClothingItemColor.ORANGE, ClothingItemColor.WHITE, ClothingItemColor.BLACK, ClothingItemColor.GRAY, ClothingItemColor.BROWN));
        triadicGroups.put(ClothingItemColor.PURPLE, List.of(ClothingItemColor.ORANGE, ClothingItemColor.GREEN, ClothingItemColor.WHITE, ClothingItemColor.BLACK, ClothingItemColor.GRAY, ClothingItemColor.BROWN));
        // Groupe 3 : Pink avec primaires froides
        triadicGroups.put(ClothingItemColor.PINK, List.of(ClothingItemColor.BLUE, ClothingItemColor.GREEN, ClothingItemColor.WHITE, ClothingItemColor.BLACK, ClothingItemColor.GRAY, ClothingItemColor.BROWN));
        // Neutres : vont avec tout
        triadicGroups.put(ClothingItemColor.WHITE, List.of(ClothingItemColor.RED, ClothingItemColor.BLUE, ClothingItemColor.YELLOW, ClothingItemColor.GREEN, ClothingItemColor.ORANGE, ClothingItemColor.PURPLE, ClothingItemColor.PINK, ClothingItemColor.BLACK, ClothingItemColor.GRAY, ClothingItemColor.BROWN));
        triadicGroups.put(ClothingItemColor.BLACK, List.of(ClothingItemColor.RED, ClothingItemColor.BLUE, ClothingItemColor.YELLOW, ClothingItemColor.GREEN, ClothingItemColor.ORANGE, ClothingItemColor.PURPLE, ClothingItemColor.PINK, ClothingItemColor.WHITE, ClothingItemColor.GRAY, ClothingItemColor.BROWN));
        triadicGroups.put(ClothingItemColor.GRAY, List.of(ClothingItemColor.RED, ClothingItemColor.BLUE, ClothingItemColor.YELLOW, ClothingItemColor.GREEN, ClothingItemColor.ORANGE, ClothingItemColor.PURPLE, ClothingItemColor.PINK, ClothingItemColor.WHITE, ClothingItemColor.BLACK, ClothingItemColor.BROWN));
        triadicGroups.put(ClothingItemColor.BROWN, List.of(ClothingItemColor.RED, ClothingItemColor.BLUE, ClothingItemColor.YELLOW, ClothingItemColor.GREEN, ClothingItemColor.ORANGE, ClothingItemColor.PURPLE, ClothingItemColor.PINK, ClothingItemColor.WHITE, ClothingItemColor.BLACK, ClothingItemColor.GRAY));
    }

    public static Collection<ScoredClothingItem> getItemsOfType(Collection<ScoredClothingItem> items, ClothingItemType type) {
        return items.stream().filter((item) -> item.getType() == type).toList();
    }

    public static Optional<ScoredClothingItem> selectRandomFromTop(Collection<ScoredClothingItem> items, int count, int randomnessMultiplier) {
        if (items.isEmpty()) return Optional.empty();
        var expandedCount = Math.min(count * randomnessMultiplier, items.size());
        var topItems = items.stream().limit(expandedCount).toList();
        return Optional.of(topItems.get((int) Math.floor(Math.random() * topItems.size())));
    }

    public static float clamp01(float v) {
        return Math.max(0, Math.min(1, v));
    }

    public static float lastWornScore(ScoredClothingItem item) {
        if (item.getLastWornAt() == null) return 1;
        var days = Math.max(
                0,
                ZonedDateTime.now().toEpochSecond() - item.getLastWornAt().toEpochSecond()
        );
        return clamp01((float) ((float) Math.log(days + 1) / Math.log(31)));
    }

    public static void scoreItems(
            List<ScoredClothingItem> scored,
            Optional<ScoredClothingItem> baseItem,
            WeatherType weatherType,
            ProfileWeight weights,
            float colorHarmonyWeight,
            float diversityPenalty,
            Set<UUID> excludeTopIds,
            List<OutfitGenerationStrategie> generationStrategies
    ) {
        for (ScoredClothingItem item : scored) {
            item.addScore(lastWornScore(item) * weights.lastWorn());

            // Penalize recently used tops to force diversity
            if ((item.getType() == ClothingItemType.SHIRT || item.getType() == ClothingItemType.DRESS) && excludeTopIds.contains(item.getId())) {
                item.addScore(diversityPenalty * -1);
            }

            if (baseItem.isPresent()) {
//                item.score += monochromeScore(baseItem, item) * colorHarmonyWeight;
                for (OutfitGenerationStrategie strategie : generationStrategies) {
                    item.addScore(strategie.score(baseItem.get(), weatherType, weights));
                    item.addScore(strategie.score(item, baseItem.get()) * colorHarmonyWeight);
                }
            }
        }
    }

    private static ClothingItemColor getComplementaryColor(ClothingItemColor color) {
        var chromaticColors = COLOR_WHEEL.stream().filter((c) -> !NEUTRAL_COLORS.contains(c)).toList();
        if (NEUTRAL_COLORS.contains(color)) {
            return chromaticColors.get((int) Math.floor(Math.random() * chromaticColors.size()));
        }

        var index = COLOR_WHEEL.indexOf(color);
        if (index == -1) return color;

        var chromaticIndex = chromaticColors.indexOf(color);

        if (chromaticIndex == -1) return color;

        var complementaryIndex =
                (int) (chromaticIndex + (double) (chromaticColors.size() / 2)) % chromaticColors.size();

        return chromaticColors.get(complementaryIndex);
    }

    public static List<ClothingItemColor> getComplementaryColors(ClothingItemColor color) {
        if (NEUTRAL_COLORS.contains(color)) {
            return COLOR_WHEEL.stream().filter((c) -> !NEUTRAL_COLORS.contains(c)).toList();
        }

        var complementary = getComplementaryColor(color);
        var complementaryIndex = COLOR_WHEEL.indexOf(complementary);

        if (complementaryIndex == -1) return List.of(color, complementary);

        return getClothingItemColors(color, complementary, complementaryIndex);
    }

    private static @NonNull List<ClothingItemColor> getClothingItemColors(ClothingItemColor color, ClothingItemColor complementary, int complementaryIndex) {
        List<ClothingItemColor> colors = new ArrayList<>(List.of(color, complementary));

        // Couleur avant la complémentaire
        var prevIndex = (complementaryIndex - 1 + COLOR_WHEEL.size()) % COLOR_WHEEL.size();
        var prevColor = COLOR_WHEEL.get(prevIndex);
        if (!NEUTRAL_COLORS.contains(prevColor)) {
            colors.add(prevColor);
        }

        // Couleur après la complémentaire
        var nextIndex = (complementaryIndex + 1) % COLOR_WHEEL.size();
        var nextColor = COLOR_WHEEL.get(nextIndex);
        if (!NEUTRAL_COLORS.contains(nextColor)) {
            colors.add(nextColor);
        }
        return colors;
    }
}