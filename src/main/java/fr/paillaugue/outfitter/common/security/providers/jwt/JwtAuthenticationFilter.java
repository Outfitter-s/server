package fr.paillaugue.outfitter.common.security.providers.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.paillaugue.outfitter.common.errorHandling.OutfitterSpringException;
import fr.paillaugue.outfitter.common.errorHandling.dto.ErrorResponseDTO;
import fr.paillaugue.outfitter.common.security.CustomUserDetailsService;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends GenericFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String header = httpRequest.getHeader("Authorization");

        try {
            if (header != null && header.startsWith("Bearer ")) {
                String token = header.substring(7);
                if (jwtTokenProvider.validateToken(token)) {
                    String username = jwtTokenProvider.getUsernameFromToken(token);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            chain.doFilter(request, response);
        } catch (OutfitterSpringException ex) {
            // Inline exception handler because we can't use @ControllerAdvice here
            // TODO: maybe we should create a custom exception handler for filters and use it here and in the JwtAuthenticationEntryPoint
            ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                    ex.getStatus().value(),
                    ex.getMessage()
            );
            httpResponse.setStatus(ex.getStatus().value());
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
        }
    }
}