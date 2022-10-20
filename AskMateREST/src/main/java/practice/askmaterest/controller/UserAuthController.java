package practice.askmaterest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.askmaterest.model.securityModel.LoginDetails;
import practice.askmaterest.model.securityModel.RegistrationDetails;
import practice.askmaterest.security.CookieAgent;
import practice.askmaterest.security.EncoderAgent;
import practice.askmaterest.services.WebUserService;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@RestController
@RequestMapping("${user_service_path}")
public class UserAuthController {
    EncoderAgent encoderAgent;

    private final WebUserService webUserService;

    public UserAuthController(EncoderAgent encoderAgent, WebUserService webUserService) {
        this.encoderAgent = encoderAgent;
        this.webUserService = webUserService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> doLogin(@RequestBody LoginDetails loginDetails, HttpServletResponse response)
    {
        boolean isAuthenticated = webUserService.isCorrectLogin(loginDetails);
        if (!isAuthenticated) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        CookieAgent.getJWTCookies(encoderAgent.generateJwt(loginDetails.getUsername(),loginDetails.getPassword())).forEach(response::addCookie);

        return ResponseEntity.status(HttpStatus.SEE_OTHER).location(URI.create("/")).build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> doRegister(@RequestBody RegistrationDetails newUserDetails) {
        boolean userDidNotExist = webUserService.AddWebUserIfNotExist(newUserDetails.toUser());
        if(userDidNotExist) return ResponseEntity.status(HttpStatus.CREATED).location(URI.create("/")).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}