package fr.paillaugue.outfitter.user;

import fr.paillaugue.outfitter.user.entities.User;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTests {

    private User user;
    private Validator validator;

    @BeforeEach
    public void setUp() {
        this.user = new User("angus", "angus@paillaugue.fr", "hashed_password");
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    @DisplayName("User should be valid with correct fields")
    public void testValidUser() {
        var violations = validator.validate(user);
        assert (violations.isEmpty());
    }

    @Test
    @DisplayName("User should be invalid with blank username")
    public void testBlankUsername() {
        user.setUsername("");
        var violations = validator.validate(user);
        assert (!violations.isEmpty());
    }

    @Test
    @DisplayName("User should be invalid with blank email")
    public void testBlankEmail() {
        user.setEmail("");
        var violations = validator.validate(user);
        assert (!violations.isEmpty());
    }

    @Test
    @DisplayName("User should be invalid with invalid email")
    public void testInvalidEmail() {
        user.setEmail("invalid-email");
        var violations = validator.validate(user);
        assert (!violations.isEmpty());
    }
}
