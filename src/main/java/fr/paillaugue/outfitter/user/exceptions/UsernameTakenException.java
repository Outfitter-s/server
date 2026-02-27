package fr.paillaugue.outfitter.user.exceptions;

import fr.paillaugue.outfitter.common.errorHandling.OutfitterSpringException;
import org.springframework.http.HttpStatus;

public class UsernameTakenException extends OutfitterSpringException {
    public UsernameTakenException(String username) {
        super(HttpStatus.CONFLICT, "An account with username " + username + " already exists.");
    }
}