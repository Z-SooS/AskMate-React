package practice.askmaterest.utility;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PasswordAgent {
    private BCryptPasswordEncoder passwordEncoder;

    public String hashPassword(String cleartext)
    {
        return passwordEncoder.encode(cleartext);
    }

    public boolean matchesPassword(String cleartext, String hashedPassword)
    {
        return passwordEncoder.matches(cleartext, hashedPassword);
    }
}
