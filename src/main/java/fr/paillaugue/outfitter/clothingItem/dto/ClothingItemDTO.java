package fr.paillaugue.outfitter.clothingItem.dto;

import fr.paillaugue.outfitter.clothingItem.entities.ClothingItem;
import fr.paillaugue.outfitter.user.entities.ClothingItemColor;
import fr.paillaugue.outfitter.user.entities.ClothingItemType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * DTO for {@link fr.paillaugue.outfitter.clothingItem.entities.ClothingItem}
 */
public record ClothingItemDTO(String id, @NotNull ClothingItemType type, @NotNull ClothingItemColor color, @NotBlank String name, @Nullable String description, @Nullable String lastWornAt) implements Serializable {
    public static ClothingItemDTO fromEntity(ClothingItem clothingItem) {
        return new ClothingItemDTO(
                clothingItem.getId().toString(),
                clothingItem.getType(),
                clothingItem.getColor(),
                clothingItem.getName(),
                clothingItem.getDescription(),
                Optional.ofNullable(clothingItem.getLastWornAt()).isPresent() ? clothingItem.getLastWornAt().toString() : null
        );
    }

    public static List<ClothingItemDTO> fromEntities(Collection<ClothingItem> clothingItems) {
        return clothingItems.stream().map(ClothingItemDTO::fromEntity).toList();
    }
}