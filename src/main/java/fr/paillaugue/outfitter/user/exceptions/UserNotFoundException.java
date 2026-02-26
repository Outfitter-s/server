package fr.paillaugue.outfitter.user.exceptions;

import fr.paillaugue.outfitter.common.errorHandling.OutfitterSpringException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends OutfitterSpringException {
    public UserNotFoundException(String username) {
        super(HttpStatus.NOT_FOUND, "User "+username+" not found");
    }
    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "User not found");
    }
}