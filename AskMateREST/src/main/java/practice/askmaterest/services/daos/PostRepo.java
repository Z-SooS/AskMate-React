package practice.askmaterest.services.daos;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import practice.askmaterest.model.Post;

import java.util.Collection;
import java.util.List;

public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findAllByTags_IdIn(Collection<Integer> tags, Pageable pageable);
}
