package practice.askmaterest.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import practice.askmaterest.model.WebUser;
import practice.askmaterest.model.modelenum.AskRole;
import practice.askmaterest.model.securityModel.LoginDetails;
import practice.askmaterest.security.PasswordAgent;
import practice.askmaterest.services.daos.WebUserRepo;

import java.util.List;

@Service
@AllArgsConstructor
public class WebUserService {
    private final WebUserRepo webUserRepo;
    private final PasswordAgent passwordAgent;

    public AskRole getRoleForCorrectLogin(LoginDetails loginDetails) {
        var user = webUserRepo.findById(loginDetails.getUsername());
        if (user.isEmpty()) return AskRole.UNIDENTIFIED;
        boolean isCorrectPassword = passwordAgent.matchesPassword(loginDetails.getPassword(), user.get().getPassword());
        if (isCorrectPassword) {
            loginDetails.setEmail(user.get().getEmail());
            return user.get().getRole();
        }
        return AskRole.UNIDENTIFIED;
    }

    public boolean AddWebUserIfNotExist(WebUser user) {
        if (webUserRepo.existsById(user.getUsername())) return false;
        user.setPassword(passwordAgent.hashPassword(user.getPassword()));
        user.setReputation(0);
        user.setRole(AskRole.USER);
        webUserRepo.save(user);
        return true;
    }
    public boolean AddAdminUserIfNotExist(WebUser user) {
        if (webUserRepo.existsById(user.getUsername())) return false;
        user.setPassword(passwordAgent.hashPassword(user.getPassword()));
        user.setReputation(0);
        user.setRole(AskRole.ADMIN);
        webUserRepo.save(user);
        return true;
    }

    public WebUser getUserByUsername(String username) {
        return webUserRepo.findById(username).orElse(null);
    }

    public List<WebUser> getPopularUsersPage(int page) {
        return webUserRepo.findAll(PageRequest.of(page,15,Sort.by(Sort.Direction.DESC, "reputation"))).getContent();
    }
}
