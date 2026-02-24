package fr.paillaugue.outfitter.user;

import fr.paillaugue.outfitter.user.dto.UserDTO;
import fr.paillaugue.outfitter.user.entities.User;
import fr.paillaugue.outfitter.common.security.SecurityUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private final SecurityUtil securityUtil;

    public UserController(SecurityUtil securityUtil) {
        this.securityUtil = securityUtil;
    }

    @GetMapping
    public UserDTO getUser() {
        return securityUtil.getCurrentUser();
    }
}
