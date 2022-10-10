package practice.askmaterest.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import practice.askmaterest.model.WebUser;
import practice.askmaterest.services.daos.WebUserRepo;

@Service
@AllArgsConstructor
public class WebUserService {
    private final WebUserRepo webUserRepo;

    public void AddWebUser(WebUser user)
    {
        webUserRepo.save(user);
    }
}
