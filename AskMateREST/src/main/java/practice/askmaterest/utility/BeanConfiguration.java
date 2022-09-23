package practice.askmaterest.utility;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BeanConfiguration {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoderFactory()
    {
        return new BCryptPasswordEncoder();
    }
}
