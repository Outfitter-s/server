package fr.paillaugue.outfitter.bootstrap;

import fr.paillaugue.outfitter.user.UserRepository;
import fr.paillaugue.outfitter.user.entities.User;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
@Profile("develop")
public class DatabaseSeeder{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        try {
            doInitUsers();
        } catch (RuntimeException e) {
            System.out.println("Bootstrapping failed: " + e);
        }
    }

    public void doInitUsers() {
        String rawPassword = "password";
        String hashedPassword = passwordEncoder.encode(rawPassword);
        User user1 = new User("Angus", "angus@paillaugue.fr", hashedPassword);
//        this.userRepository.save(user1);
    }
}
