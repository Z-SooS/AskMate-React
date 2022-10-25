package practice.askmaterest.services.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.askmaterest.model.Post;

public interface PostRepo extends JpaRepository<Post, Long> {
}
