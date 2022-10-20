package practice.askmaterest.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig {
    @Value("${user_service_path}")
    private String userServicePath;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security.httpBasic().disable();
        // TODO: 2022. 10. 19. Use filterChain, unsafe while developing
        security.csrf().disable().authorizeRequests().antMatchers(userServicePath+"/**").permitAll();
        return security.build();
    }
}