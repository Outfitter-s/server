package fr.paillaugue.outfitter.clothingItem.exceptions;

import fr.paillaugue.outfitter.common.errorHandling.OutfitterSpringException;
import org.springframework.http.HttpStatus;

import java.util.Collection;
import java.util.UUID;

public class ClothingItemNotFoundException extends OutfitterSpringException {
    public ClothingItemNotFoundException(UUID id) {
        super(HttpStatus.NOT_FOUND, "Clothing item " + id + " not found");
    }
    public ClothingItemNotFoundException(Collection<UUID> ids) {
        super(HttpStatus.NOT_FOUND, "Clothing items " + String.join(", ", ids.stream().map(UUID::toString).toList()) + " not found");
    }
}