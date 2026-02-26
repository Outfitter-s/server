package fr.paillaugue.outfitter.outfit.dto;

import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.util.Collection;
import java.util.UUID;

public record CreateOutfitDTO(@Size(min = 1) Collection<UUID> clothingItemIds) implements Serializable {
}
