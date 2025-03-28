package project.testmaster.backend.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for environment-specific properties.
 */
@Getter
@Configuration
public class EnvConfig {
    /**
     * -- GETTER --
     *  Returns the frontend URL from the environment configuration.
     *
     */
    @Value("${env.frontend.url}")
    private String frontendUrl;

    /**
     * -- GETTER --
     *  Returns the path to the private key PEM file from the environment configuration.
     *
     */
    @Value("${jwt.private-key-pem}")
    private String privateKeyPemPath;

    /**
     * -- GETTER --
     *  Returns the path to the public key PEM file from the environment configuration.
     *
     */
    @Value("${jwt.public-key-pem}")
    private String publicKeyPemPath;

    /**
     * -- GETTER --
     *  Returns the expiration time for the JWT token from the environment configuration.
     *
     */
    @Value("${jwt.expiration}")
    private long expiration;

}
