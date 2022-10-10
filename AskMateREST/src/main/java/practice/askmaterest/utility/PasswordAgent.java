package practice.askmaterest.utility;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordAgent {
    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordAgent(BCryptPasswordEncoder passwordEncoder) {
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
