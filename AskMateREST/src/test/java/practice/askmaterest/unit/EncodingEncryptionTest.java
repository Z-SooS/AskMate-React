package practice.askmaterest.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import practice.askmaterest.security.EncoderAgent;
import practice.askmaterest.security.PasswordAgent;

public class EncodingEncryptionTest {
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final PasswordAgent passwordAgent = new PasswordAgent(bCryptPasswordEncoder);
    private final EncoderAgent encoderAgent = new EncoderAgent();

    @ParameterizedTest
    @ValueSource(strings = {"randomPassword", "","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"})
    public void passwordAgent_hashPassword_validString_matchesWithDifferentSalt(String cleartext) {
        String expectedHash = bCryptPasswordEncoder.encode(cleartext);

        Assertions.assertTrue(passwordAgent.matchesPassword(cleartext,expectedHash));
    }

}