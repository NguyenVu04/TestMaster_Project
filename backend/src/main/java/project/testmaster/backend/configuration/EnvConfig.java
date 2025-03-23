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

    /**
     * Returns the frontend URL from the environment configuration.
     *
     * @return the frontend URL
     */
    public String getFrontendUrl() {
        return frontendUrl;
    }
}
