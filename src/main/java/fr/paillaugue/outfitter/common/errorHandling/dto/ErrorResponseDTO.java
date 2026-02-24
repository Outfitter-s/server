package fr.paillaugue.outfitter.common.errorHandling.dto;

import java.io.Serializable;

public record ErrorResponseDTO(int status, String message) implements Serializable {
    private static final long timestamp = System.currentTimeMillis();
}
