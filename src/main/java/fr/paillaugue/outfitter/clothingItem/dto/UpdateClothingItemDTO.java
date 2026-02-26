package fr.paillaugue.outfitter.clothingItem.dto;

import fr.paillaugue.outfitter.user.entities.ClothingItemColor;
import fr.paillaugue.outfitter.user.entities.ClothingItemType;
import java.io.Serializable;
import java.util.Optional;

public record UpdateClothingItemDTO(
        Optional<ClothingItemType> type,
        Optional<ClothingItemColor> color,
        Optional<String> name,
        Optional<String> description
) implements Serializable {
}
