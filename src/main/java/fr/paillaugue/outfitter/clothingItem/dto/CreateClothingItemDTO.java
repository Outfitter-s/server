package fr.paillaugue.outfitter.clothingItem.dto;

import fr.paillaugue.outfitter.user.entities.ClothingItemColor;
import fr.paillaugue.outfitter.user.entities.ClothingItemType;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record CreateClothingItemDTO(@NotNull ClothingItemType type, @NotNull ClothingItemColor color, @NotBlank String name, @Nullable String description) implements Serializable {
}