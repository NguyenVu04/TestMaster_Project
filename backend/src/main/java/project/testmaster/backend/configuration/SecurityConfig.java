package project.testmaster.backend.configuration;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import project.testmaster.backend.service.AccountDetailsService;
import project.testmaster.backend.utils.JwtUtils;

/**
 * Security configuration class for setting up security-related configurations.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final EnvConfig env;

    private final AccountDetailsService userDetailsService;

    public SecurityConfig(EnvConfig env, AccountDetailsService userDetailsService) {
        this.env = env;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Configures the security filter chain.
     *
     * @param http the HttpSecurity to modify
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs while configuring the security filter
     *                   chain
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/api/teacher/**").hasRole("TEACHER")
                        .requestMatchers("/api/student/**").hasRole("STUDENT")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/exam/**").authenticated()
                        .requestMatchers("/api/user/**").authenticated()
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().permitAll())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configures CORS settings.
     *
     * @return the configured UrlBasedCorsConfigurationSource
     */
    UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin(CorsConfiguration.ALL);
        config.addAllowedHeader(CorsConfiguration.ALL);
        config.addAllowedMethod(CorsConfiguration.ALL);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return source;
    }

    /**
     * Creates a PasswordEncoder bean.
     *
     * @return the PasswordEncoder bean
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    JwtUtils jwtUtils() throws Exception {
        return new JwtUtils(
                env.getPrivateKeyPemPath(),
                env.getPublicKeyPemPath(),
                env.getExpiration());
    }

    JwtFilter jwtFilter() throws Exception {
        return new JwtFilter(
                jwtUtils(),
                userDetailsService);
    }
}