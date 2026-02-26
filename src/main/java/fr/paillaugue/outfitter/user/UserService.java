package fr.paillaugue.outfitter.user;

import fr.paillaugue.outfitter.user.dto.CreateUserDTO;
import fr.paillaugue.outfitter.user.dto.UserDTO;
import fr.paillaugue.outfitter.user.entities.User;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserDTO register(@Valid CreateUserDTO createUserDTO) {
        var hashedPassword = this.passwordEncoder.encode(createUserDTO.password());
        var user = new User(createUserDTO.username(), createUserDTO.email(), hashedPassword);
        var savedUser = this.userRepository.save(user);
        return UserDTO.fromEntity(savedUser);
    }
}
