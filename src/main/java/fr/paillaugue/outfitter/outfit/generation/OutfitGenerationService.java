package fr.paillaugue.outfitter.outfit.generation;

import fr.paillaugue.outfitter.clothingItem.ClothingItemRepository;
import fr.paillaugue.outfitter.clothingItem.entities.ClothingItem;
import fr.paillaugue.outfitter.outfit.entities.OutfitStyle;
import fr.paillaugue.outfitter.outfit.generation.strategies.*;
import fr.paillaugue.outfitter.user.dto.UserDTO;
import fr.paillaugue.outfitter.user.entities.ClothingItemType;
import fr.paillaugue.outfitter.weather.entities.WeatherType;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Stream;

import static java.util.List.copyOf;

@Service
public class OutfitGenerationService {
    private final ClothingItemRepository clothingItemRepository;
    private final List<OutfitGenerationStrategie> generationStrategies = List.of(
            new AnalogousOutfitGenerationStrategie(),
            new ComplementaryOutfitGenerationStrategie(),
            new LastWornOutfitGenerationStrategie(),
            new MonochromeOutfitGenerationStrategie(),
            new TemperatureOutfitGenerationStrategie(),
            new TriadicOutfitGenerationStrategie(),
            new UVOutfitGenerationStrategie()
    );

    public OutfitGenerationService(ClothingItemRepository clothingItemRepository) {
        this.clothingItemRepository = clothingItemRepository;
    }

    public Collection<Collection<ClothingItem>> generateOutfitsForUser(UserDTO user, WeatherType weatherType, OutfitStyle style, int numberOfOutfits) {
        var wardrobe = clothingItemRepository.findAllByOwnerId(UUID.fromString(user.id()));
        if (wardrobe.isEmpty()) return List.of();
        Collection<Collection<ScoredClothingItem>> outfits = new ArrayList<>();
        Set<UUID> usedTopIds = new HashSet<>();

        for (int i = 0; i < numberOfOutfits; i++) {
            var diversityPenalty = 0.3f * i;
            var colorHarmonyWeight = Math.max(0.3f, 11 - i * 0.2f);
            var outfit = generateOutfitForUser(wardrobe, style, weatherType, diversityPenalty, colorHarmonyWeight, usedTopIds);
            outfits.add(outfit);
            outfit
                    .stream()
                    .filter(
                            (item) ->
                                    item.getType() == ClothingItemType.SHIRT ||
                                            item.getType() == ClothingItemType.DRESS
                    )
                    .findFirst()
                    .ifPresent(
                            topItem -> usedTopIds.add(topItem.getId())
                    );

        }

        return copyOf(outfits.stream().map(
                (outfit) -> outfit.stream().map(ScoredClothingItem::getItem).toList()
        ).toList());
    }

    private Collection<ScoredClothingItem> generateOutfitForUser(
            Collection<ClothingItem> wardrobe,
            OutfitStyle style,
            WeatherType weatherType,
            float diversityPenalty,
            float colorHarmonyWeight,
            Set<UUID> usedTopIds
    ) {
        var scored = wardrobe.stream().map((item) -> new ScoredClothingItem(item, (float) Math.random())).toList();
        // Get a random top (shirt or dress) from a wider pool for more diversity
        var tops = Stream.concat(ColorUtils.getItemsOfType(scored, ClothingItemType.SHIRT).stream(), ColorUtils.getItemsOfType(scored, ClothingItemType.DRESS).stream()).toList();
        var selectedTop = ColorUtils.selectRandomFromTop(tops, tops.size(), 1);

        var weights = ColorUtils.PROFILE_WEIGHTS.get(style);

        // Score all items based on selected top and weather
        ColorUtils.scoreItems(
                scored,
                selectedTop,
                weatherType,
                weights,
                colorHarmonyWeight,
                diversityPenalty,
                usedTopIds,
                generationStrategies
        );

        // Sort by score
        scored = scored.stream().sorted((a, b) -> (int) (b.getScore() - a.getScore())).toList();

        // Build outfit
        List<ScoredClothingItem> outfitItems = new ArrayList<>();

        // Add selected top
        if (selectedTop.isPresent()) {
            outfitItems.add(selectedTop.get());
            // Add bottom (pants) if wearing a shirt
            if (selectedTop.get().getType() == ClothingItemType.SHIRT) {
                var pants = ColorUtils.getItemsOfType(scored, ClothingItemType.PANTS);
                var selectedPants = ColorUtils.selectRandomFromTop(
                        pants,
                        3,
                        1
                );
                selectedPants.ifPresent(outfitItems::add);
            }
        }


        // Add shoes
        var shoes = ColorUtils.getItemsOfType(scored, ClothingItemType.SHOES);
        var selectedShoes = ColorUtils.selectRandomFromTop(shoes, 3, 1);
        selectedShoes.ifPresent(outfitItems::add);

        // Add accessories
        var accessories = ColorUtils.getItemsOfType(scored, ClothingItemType.ACCESSORY);
        var selectedAccessories = selectRandomAccessories(accessories, 3);
        outfitItems.addAll(selectedAccessories);

        // Add extra layer if cold or rainy
        var tempThreshold = 20;
        if (weatherType.isCold() || weatherType.isWet()) {
            var jackets = ColorUtils.getItemsOfType(scored, ClothingItemType.JACKET);
            var sweaters = ColorUtils.getItemsOfType(scored, ClothingItemType.SWEATER);

            Optional<ScoredClothingItem> extraTop = Optional.empty();
            if (!jackets.isEmpty()) {
                extraTop = ColorUtils.selectRandomFromTop(jackets, 3, 1);
            } else if (!sweaters.isEmpty()) {
                extraTop = ColorUtils.selectRandomFromTop(sweaters, 3, 1);
            }

            extraTop.ifPresent(outfitItems::add);
        }
        return outfitItems;
    }

    private List<ScoredClothingItem> selectRandomAccessories(
            Collection<ScoredClothingItem> availableAccessories,
            int maxCount
    ) {
        List<ScoredClothingItem> accessories = new ArrayList<>();
        if (!availableAccessories.isEmpty()) {
            var maxNum = Math.min(availableAccessories.size(), maxCount);
            var count = randIntInclusive(0, maxNum);
            var pool = new ArrayList<>(availableAccessories);
            for (int i = 0; i < count; i++) {
                var idx = (int) Math.floor(Math.random() * pool.size());
                accessories.add(pool.get(idx));
                pool.remove(idx);
            }
        }
        return accessories;
    }

    private int randIntInclusive(int i, int maxNum) {
        return (int) (i + Math.floor(Math.random() * (maxNum - i + 1)));
    }
}
