package fr.paillaugue.outfitter.common.security.exceptions;

import fr.paillaugue.outfitter.common.errorHandling.OutfitterSpringException;
import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends OutfitterSpringException {
    public InvalidCredentialsException() {
        super(HttpStatus.UNAUTHORIZED, "Invalid credentials");
    }
}
