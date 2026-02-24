package fr.paillaugue.outfitter.common.security;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

public record AuthRequest(@NotBlank String username, @NotBlank String password) implements Serializable {
}