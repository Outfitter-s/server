package fr.paillaugue.outfitter.outfit.dto;

import fr.paillaugue.outfitter.clothingItem.dto.ClothingItemDTO;
import fr.paillaugue.outfitter.clothingItem.entities.ClothingItem;
import fr.paillaugue.outfitter.outfit.entities.Outfit;

import java.io.Serializable;
import java.util.Collection;

/**
 * DTO for {@link fr.paillaugue.outfitter.outfit.entities.Outfit}
 */
public record OutfitDTO(String id, Collection<ClothingItemDTO> items) implements Serializable {
    public static OutfitDTO fromEntity(Outfit outfit) {
        return new OutfitDTO(
            outfit.getId().toString(),
            outfit.getClothingItems().stream().map(ClothingItemDTO::fromEntity).toList()
        );
    }

    public static Collection<OutfitDTO> fromEntities(Collection<Outfit> outfits) {
        return outfits.stream().map(OutfitDTO::fromEntity).toList();
    }
}