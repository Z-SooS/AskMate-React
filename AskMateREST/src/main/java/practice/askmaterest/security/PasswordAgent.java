package practice.askmaterest.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordAgent {
    private final PasswordEncoder passwordEncoder;

    public PasswordAgent(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String hashPassword(String cleartext)
    {
        return passwordEncoder.encode(cleartext);
    }

    public boolean matchesPassword(String cleartext, String hashedPassword)
    {
        return passwordEncoder.matches(cleartext, hashedPassword);
    }
}
