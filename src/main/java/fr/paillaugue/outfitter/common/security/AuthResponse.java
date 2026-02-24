package fr.paillaugue.outfitter.common.security;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

public record AuthResponse(@NotBlank String token) implements Serializable {
}