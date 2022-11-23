package practice.askmaterest.model.securityModel;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import practice.askmaterest.model.WebUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class RegistrationDetails {
    @Value("userdetail.password.length")
    private static int minPasswordLength;
    @Value("userdetail.username.length")
    private static int minUsernameLength;

    private String username;
    private String password;
    private String email;

    public WebUser toUser() {
        return WebUser.builder().username(username).password(password).email(email).build();
    }

    public boolean isValidPassword() {
        if(password.length() < minPasswordLength) return false;
        return Pattern.compile("([A-Z]).*([a-z]).(.*\\d)").matcher(password).matches();
    }

    public boolean isValidEmail() {
        return Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$").matcher(email.toLowerCase()).matches();
    }

    public boolean isValidUsername() {
        return username.length() >= minUsernameLength;
    }


}
