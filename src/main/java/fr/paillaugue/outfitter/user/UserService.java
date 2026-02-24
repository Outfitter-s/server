package fr.paillaugue.outfitter.user;

import fr.paillaugue.outfitter.user.dto.UserDTO;
import fr.paillaugue.outfitter.user.entities.User;
import fr.paillaugue.outfitter.user.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }
}
