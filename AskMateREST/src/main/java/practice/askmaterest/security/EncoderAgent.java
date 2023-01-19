package practice.askmaterest.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import practice.askmaterest.model.modelenum.AskRole;

import java.util.Base64;
import java.util.Date;

@Component
public class EncoderAgent {
    @Value("${jwt_secret}")
    private String secret;

    private final String issuer = "AskMateSpring/AskMate";

    public String generateJwt(String username, String email, AskRole role) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject(username)
                .withClaim("email", email)
                .withClaim("role",role.name())
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .sign(Algorithm.HMAC256(secret));
    }
    public DecodedJWT tryValidateToken(String token) throws JWTVerificationException{
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
//                .withClaim("email", email)
//                .withClaim("role", role)
                .withIssuer(issuer)
                .build();
        return verifier.verify(token);
    }

    public static String base64Encode(String cleartext) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(cleartext.getBytes());
    }
    public static String base64Decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }

    public String getValueFromJwtCookie(String key, String jwtPayload) {
        if(key.equals("subject")) return JWT.decode(jwtPayload).getSubject();
        return JWT.decode(jwtPayload).getClaim(key).asString();
    }
}
