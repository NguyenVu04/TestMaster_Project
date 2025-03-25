package project.testmaster.backend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for environment-specific properties.
 */
@Configuration
public class EnvConfig {
    @Value("${env.frontend.url}")
    private String frontendUrl;

    @Value("${jwt.private-key-pem}")
    private String privateKeyPemPath;

    @Value("${jwt.public-key-pem}")
    private String publicKeyPemPath;

    @Value("${jwt.expiration}")
    private long expiration;

    /**
     * Returns the frontend URL from the environment configuration.
     *
     * @return the frontend URL
     */
    public String getFrontendUrl() {
        return frontendUrl;
    }

    /**
     * Returns the path to the private key PEM file from the environment configuration.
     *
     * @return the path to the private key PEM file
     */
    public String getPrivateKeyPemPath() {
        return privateKeyPemPath;
    }

    /**
     * Returns the path to the public key PEM file from the environment configuration.
     *
     * @return the path to the public key PEM file
     */
    public String getPublicKeyPemPath() {
        return publicKeyPemPath;
    }

    /**
     * Returns the expiration time for the JWT token from the environment configuration.
     *
     * @return the expiration time for the JWT token
     */
    public long getExpiration() {
        return expiration;
    }
}
