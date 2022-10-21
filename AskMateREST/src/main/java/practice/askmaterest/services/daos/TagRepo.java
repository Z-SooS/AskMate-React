package practice.askmaterest.services.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.askmaterest.model.Tag;

public interface TagRepo extends JpaRepository<Tag,Long> {
}
