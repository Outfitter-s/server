package fr.paillaugue.outfitter.user.dto;

import fr.paillaugue.outfitter.user.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * Input DTO for register route
 */
public record CreateUserDTO(@NotBlank String username, @Email @NotBlank String email, @NotBlank String password) implements Serializable {
}