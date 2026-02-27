package fr.paillaugue.outfitter.user;

import fr.paillaugue.outfitter.user.dto.CreateUserDTO;
import fr.paillaugue.outfitter.user.dto.UserDTO;
import fr.paillaugue.outfitter.user.entities.User;
import fr.paillaugue.outfitter.user.exceptions.EmailTakenException;
import fr.paillaugue.outfitter.user.exceptions.UsernameTakenException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private CreateUserDTO createUserDTO;

    @BeforeEach
    void setUp() {
        createUserDTO = new CreateUserDTO("Angus", "angus@paillaugue.fr", "password");
    }

    @AfterEach
    void tearDown() {
        createUserDTO = null;
    }

    @Test
    @DisplayName("registers a new user successfully")
    void registerSuccess() {
        when(userRepository.existsByUsername(createUserDTO.username())).thenReturn(false);
        when(userRepository.existsByEmail(createUserDTO.email())).thenReturn(false);
        when(passwordEncoder.encode(createUserDTO.password())).thenReturn(createUserDTO.password());

        var savedUser = new User(createUserDTO.username(), createUserDTO.email(), createUserDTO.password());
        savedUser.setId(UUID.randomUUID());
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        UserDTO result = userService.register(createUserDTO);

        assertNotNull(result);
        // verify encode and save were called with expected values
        verify(passwordEncoder).encode(createUserDTO.password());
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertEquals(createUserDTO.username(), userCaptor.getValue().getUsername());
        assertEquals(createUserDTO.email(), userCaptor.getValue().getEmail());
    }

    @Test
    @DisplayName("throws when username is already taken")
    void doNotRegisterWhenUsernameTaken() {
        when(userRepository.existsByUsername(createUserDTO.username())).thenReturn(true);

        assertThrows(UsernameTakenException.class, () -> userService.register(createUserDTO));

        verify(userRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    @DisplayName("throws when email is already taken")
    void doNotRegisterWhenEmailTaken() {
        when(userRepository.existsByUsername(createUserDTO.username())).thenReturn(false);
        when(userRepository.existsByEmail(createUserDTO.email())).thenReturn(true);

        assertThrows(EmailTakenException.class, () -> userService.register(createUserDTO));

        verify(userRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(any());
    }
}
