package practice.askmaterest.model.securityModel;

import lombok.Getter;
import lombok.Setter;
import practice.askmaterest.model.WebUser;

@Getter
@Setter
public class RegistrationDetails {
    private String username;
    private String password;
    private String email;

    public WebUser toUser() {
        return WebUser.builder().username(username).password(password).email(email).build();
    }
}
