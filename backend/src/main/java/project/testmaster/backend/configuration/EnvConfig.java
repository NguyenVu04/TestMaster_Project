package project.testmaster.backend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {
    @Value("${env.frontend.url}")
    private String frontendUrl;

    public String getFrontendUrl() {
        return frontendUrl;
    }
}
