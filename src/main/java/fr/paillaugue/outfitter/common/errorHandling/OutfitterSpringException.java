package fr.paillaugue.outfitter.common.errorHandling;

import org.springframework.http.HttpStatus;
import java.io.Serializable;

public class OutfitterSpringException extends RuntimeException implements Serializable {
    private final HttpStatus status;

    public OutfitterSpringException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
