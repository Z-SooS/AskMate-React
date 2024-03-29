package practice.askmaterest.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.askmaterest.model.modelenum.AskRole;
import practice.askmaterest.model.securityModel.LoginDetails;
import practice.askmaterest.security.CookieMethods;
import practice.askmaterest.security.EncoderAgent;
import practice.askmaterest.services.WebUserService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("${user_service_path}")
public class UserAuthController {

    private final WebUserService webUserService;
    private final EncoderAgent encoderAgent;

    public UserAuthController(WebUserService webUserService, EncoderAgent encoderAgent) {
        this.webUserService = webUserService;
        this.encoderAgent = encoderAgent;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> doLogin(@RequestBody LoginDetails loginDetails, HttpServletResponse response)
    {
        AskRole userRole = webUserService.getRoleForCorrectLogin(loginDetails);
        if (userRole.equals(AskRole.UNIDENTIFIED)) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        CookieMethods.getJWTCookies(encoderAgent.generateJwt(loginDetails.getUsername(),loginDetails.getEmail(),userRole)).forEach(response::addCookie);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> doRegister(@RequestBody LoginDetails newUserDetails) {
        if(!newUserDetails.isValidEmail() || !newUserDetails.isValidUsername() || !newUserDetails.isValidPassword()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        boolean userDidNotExist = webUserService.AddWebUserIfNotExist(newUserDetails.toUser());
        if(userDidNotExist) return ResponseEntity.status(HttpStatus.CREATED).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @GetMapping("/logout")
    public void doLogout(HttpServletResponse response) {
        CookieMethods.getEmptyCookies().forEach(response::addCookie);
    }
}
