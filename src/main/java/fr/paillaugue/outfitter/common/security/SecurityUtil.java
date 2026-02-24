package fr.paillaugue.outfitter.common.security;

import fr.paillaugue.outfitter.common.security.exceptions.InvalidCredentialsException;
import fr.paillaugue.outfitter.user.UserRepository;
import fr.paillaugue.outfitter.user.dto.UserDTO;
import fr.paillaugue.outfitter.user.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {
    private final UserRepository userRepository;

    public SecurityUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO getCurrentUser() throws InvalidCredentialsException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        String username = null;
        if (principal instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
        } else if (principal instanceof String s) {
            username = s;
        }
        if (username == null) return null;
        User user = userRepository.findByUsername(username).orElseThrow(InvalidCredentialsException::new);
        return UserDTO.fromEntity(user);
    }
}

