package practice.askmaterest.services.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.askmaterest.model.WebUser;

public interface WebUserRepo extends JpaRepository<WebUser, String> {
    byte countByUsername(String name);
}
