package project.testmaster.backend.configuration;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import project.testmaster.backend.utils.JwtUtils;

/**
 * JwtFilter is responsible for filtering incoming HTTP requests by validating
 * the JSON Web Token (JWT) present in the Authorization header. It ensures
 * that only authenticated and authorized users can access secured endpoints.
 * <p>
 * It extends OncePerRequestFilter, making sure that the filtering logic is
 * executed only once per request.
 * <p>
 * The filter performs the following operations:
 * - Extracts the JWT token from the Authorization header.
 * - Validates the token using JwtUtils to ensure its authenticity and integrity.
 * - Obtains user details and roles from the token claims.
 * - Sets the authentication in the SecurityContext for further authorization checks.
 * - Logs any errors that occur during the token validation process.
 * <p>
 * This filter is typically added to the security filter chain to intercept
 * requests and enforce authentication.
 */
public class JwtFilter extends OncePerRequestFilter {
    private final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    /**
     * Constructs a JwtFilter instance for processing HTTP requests by validating
     * JSON Web Tokens (JWT) and setting authentication in the SecurityContext.
     *
     * @param jwtUtils the utility class responsible for handling JWT operations, such as validation and claim extraction
     * @param userDetailsService the service for loading user-specific data, used for retrieving user details based on the token
     */
    public JwtFilter(JwtUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Filters incoming HTTP requests to validate and process a JSON Web Token (JWT)
     * for authentication and authorization purposes. The method intercepts the request,
     * extracts the token, validates it, retrieves user details, and sets the authentication
     * context for further processing.
     *
     * @param request  the {@code HttpServletRequest} object that contains the request
     *                 the client has made to the servlet
     * @param response the {@code HttpServletResponse} object that contains the response
     *                 the servlet sends to the client
     * @param filterChain the {@code FilterChain} object for invoking the next filter
     *                    in the chain
     * @throws ServletException if the request could not be handled
     * @throws IOException if an input or output error is detected when the servlet handles
     *                     the request
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = extractToken(request);
        try {

            if (token != null && jwtUtils.validateToken(token)) {
                Claims claims = jwtUtils.getClaimsFromToken(token);
    
                String username = claims.getSubject();
                String role = claims.get("role", String.class);
    
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, List.of(new SimpleGrantedAuthority("ROLE_" + role)));
    
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            
        } catch (Exception e) {
            
            logger.error("Error occurred while processing JWT token", token);

        }

        filterChain.doFilter(request, response);
    }

    /**
     * Extracts the JWT token from the Authorization header of the incoming HTTP request.
     * The token should be in the format "Bearer {token}". If the header is absent,
     * does not start with "Bearer ", or is null, the method returns null.
     *
     * @param request the HTTP request containing the Authorization header
     * @return the extracted token as a {@link String}, or null if no valid token is found
     */
    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        
        return null;
    }
}
