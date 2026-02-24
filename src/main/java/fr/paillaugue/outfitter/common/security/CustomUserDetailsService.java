package fr.paillaugue.outfitter.common.security;

import fr.paillaugue.outfitter.user.UserRepository;
import fr.paillaugue.outfitter.user.entities.User;
import fr.paillaugue.outfitter.user.exceptions.UserNotFoundException;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPasswordHash())
                .build();
    }
}