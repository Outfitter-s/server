package fr.paillaugue.outfitter.clothingItem.exceptions;

import fr.paillaugue.outfitter.common.errorHandling.OutfitterSpringException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ClothingItemNotFoundException extends OutfitterSpringException {
    public ClothingItemNotFoundException(UUID id) {
        super(HttpStatus.NOT_FOUND, "Clothing item " + id + " not found");
    }
    public ClothingItemNotFoundException() {
        super(HttpStatus.NOT_FOUND, "Clothing item not found");
    }
}