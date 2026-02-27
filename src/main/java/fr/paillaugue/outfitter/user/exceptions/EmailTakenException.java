package fr.paillaugue.outfitter.user.exceptions;

import fr.paillaugue.outfitter.common.errorHandling.OutfitterSpringException;
import org.springframework.http.HttpStatus;

public class EmailTakenException extends OutfitterSpringException {
    public EmailTakenException(String email) {
        super(HttpStatus.CONFLICT, "An account with email " + email + " already exists.");
    }
}