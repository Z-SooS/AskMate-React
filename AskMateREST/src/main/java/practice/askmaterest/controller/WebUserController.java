package practice.askmaterest.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import practice.askmaterest.model.WebUser;
import practice.askmaterest.services.WebUserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class WebUserController {
    private WebUserService webUserService;

    @GetMapping("/{username}")
    public ResponseEntity<WebUser> getWebUserDetails(@PathVariable String username) {
        WebUser requestedUser = webUserService.getUserByUsername(username);
        return requestedUser == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.status(HttpStatus.OK).body(requestedUser);
    }

    @GetMapping("/popular/users/{page}")
    public List<WebUser> getPopularUsers(@PathVariable int page)
    {
        return webUserService.getPopularUsersPage(page);
    }
}
