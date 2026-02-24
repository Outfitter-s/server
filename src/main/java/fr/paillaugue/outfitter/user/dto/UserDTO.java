package fr.paillaugue.outfitter.user.dto;

import fr.paillaugue.outfitter.user.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * DTO for {@link fr.paillaugue.outfitter.user.entities.User}
 */
public record UserDTO(String id, @NotBlank String username, @Email @NotBlank String email) implements Serializable {
    public static UserDTO fromEntity(User user) {
        return new UserDTO(user.getId().toString(), user.getUsername(), user.getEmail());
    }
}