package practice.askmaterest.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import practice.askmaterest.model.WebUser;
import practice.askmaterest.model.modelenum.AskRole;
import practice.askmaterest.model.securityModel.LoginDetails;
import practice.askmaterest.security.PasswordAgent;
import practice.askmaterest.services.daos.WebUserRepo;

@Service
@AllArgsConstructor
public class WebUserService {
    private final WebUserRepo webUserRepo;
    private final PasswordAgent passwordAgent;

    public boolean isCorrectLogin(LoginDetails loginDetails) {
        var user = webUserRepo.findById(loginDetails.getUsername());
        if (user.isEmpty()) return false;
        boolean isCorrectPassword = passwordAgent.matchesPassword(loginDetails.getPassword(), user.get().getPassword());
        if (isCorrectPassword) {
            loginDetails.setEmail(user.get().getEmail());
            return true;
        }
        return false;
    }

    public boolean AddWebUserIfNotExist(WebUser user) {
        byte numberOfUsersWithThisName = webUserRepo.countByUsername(user.getUsername());
        if (numberOfUsersWithThisName> 0) return false;
        user.setPassword(passwordAgent.hashPassword(user.getPassword()));
        user.setReputation(0);
        user.setRole(AskRole.USER);
        webUserRepo.save(user);
        return true;
    }
    public boolean AddAdminUserIfNotExist(WebUser user) {
        byte numberOfUsersWithThisName = webUserRepo.countByUsername(user.getUsername());
        if (numberOfUsersWithThisName> 0) return false;
        user.setPassword(passwordAgent.hashPassword(user.getPassword()));
        user.setReputation(0);
        user.setRole(AskRole.ADMIN);
        webUserRepo.save(user);
        return true;
    }
}
