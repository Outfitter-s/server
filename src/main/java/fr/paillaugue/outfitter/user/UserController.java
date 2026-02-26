package fr.paillaugue.outfitter.user;

import fr.paillaugue.outfitter.user.dto.CreateUserDTO;
import fr.paillaugue.outfitter.user.dto.UserDTO;
import fr.paillaugue.outfitter.common.security.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    private final SecurityUtil securityUtil;
    private final UserService userService;

    public UserController(SecurityUtil securityUtil, UserService userService) {
        this.securityUtil = securityUtil;
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserDTO getUser() {
        return securityUtil.getCurrentUser();
    }

    @PostMapping("/register")
    public UserDTO registerUser(
        @RequestBody @Valid CreateUserDTO createUserDTO
    ) {
        return userService.register(createUserDTO);
    }
}
