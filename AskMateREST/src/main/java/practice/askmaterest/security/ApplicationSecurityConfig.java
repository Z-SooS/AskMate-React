package practice.askmaterest.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import practice.askmaterest.model.modelenum.AskRole;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig {
    private final EncoderAgent encoderAgent;

    public ApplicationSecurityConfig(EncoderAgent encoderAgent) {
        this.encoderAgent = encoderAgent;
    }

    @Value("${user_service_path}")
    private String userServicePath;

    @Value("${post_service_path}")
    private String postServicePath;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
        security.httpBasic().disable();
        security.addFilterBefore(new JwtTokenChecker(encoderAgent), UsernamePasswordAuthenticationFilter.class);
        // TODO: 2022. 10. 19. Use filterChain, unsafe while developing
        security.authorizeRequests().antMatchers(userServicePath+"/*").hasAnyAuthority(AskRole.UNIDENTIFIED.name(),AskRole.USER.name(),AskRole.ADMIN.name());
        security.authorizeRequests().antMatchers(postServicePath+"/*").hasAnyAuthority(AskRole.USER.name(),AskRole.ADMIN.name());
        security.csrf().disable();
        return security.build();
    }
}