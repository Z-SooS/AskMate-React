package practice.askmaterest.model.securityModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LoginDetails {
        private String username;
        private String email;
        private String password;
}
