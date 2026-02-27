package fr.paillaugue.outfitter.outfit.exceptions;

import fr.paillaugue.outfitter.common.errorHandling.OutfitterSpringException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class OutfitNotFoundException extends OutfitterSpringException {
    public OutfitNotFoundException(UUID outfitId) {
        super(HttpStatus.NOT_FOUND, "Outfit "+outfitId+" not found");
    }
}