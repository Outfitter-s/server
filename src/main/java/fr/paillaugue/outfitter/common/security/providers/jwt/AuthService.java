package fr.paillaugue.outfitter.common.security.providers.jwt;

import fr.paillaugue.outfitter.common.security.AuthRequest;
import fr.paillaugue.outfitter.common.security.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );
        String token = jwtTokenProvider.generateToken(authentication.getName());
        return new AuthResponse(token);
    }
}