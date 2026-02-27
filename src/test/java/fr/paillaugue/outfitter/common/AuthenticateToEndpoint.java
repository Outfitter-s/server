package fr.paillaugue.outfitter.common;

import fr.paillaugue.outfitter.bootstrap.DatabaseSeeder;
import fr.paillaugue.outfitter.common.security.providers.jwt.JwtTokenProvider;
import fr.paillaugue.outfitter.user.entities.User;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

@Service
public class AuthenticateToEndpoint {

    private final JwtTokenProvider jwtTokenProvider;
    private final DatabaseSeeder seeder;

    public AuthenticateToEndpoint(JwtTokenProvider jwtTokenProvider, DatabaseSeeder seeder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.seeder = seeder;
    }

    public RequestPostProcessor withAuthentication(User user) {
        return request -> {
            String token = jwtTokenProvider.generateToken(user.getUsername());
            request.addHeader("Authorization", "Bearer " + token);
            return request;
        };
    }

    public RequestPostProcessor withAuthentication() {
        return withAuthentication(seeder.getInitService().getUser1());
    }

    public User asUser1() {
        return seeder.getInitService().getUser1();
    }

    public User asUser2() {
        return seeder.getInitService().getUser2();
    }

    public User asUser3() {
        return seeder.getInitService().getUser3();
    }
}
