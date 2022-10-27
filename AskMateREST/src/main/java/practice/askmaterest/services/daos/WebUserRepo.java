package practice.askmaterest.services.daos;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import practice.askmaterest.model.WebUser;

import java.util.List;

public interface WebUserRepo extends JpaRepository<WebUser, String> {
    List<WebUser> findAll(Pageable pageable, Sort sort);
}
